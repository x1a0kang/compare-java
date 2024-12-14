package org.x1a0kang.compare.common.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import org.apache.commons.lang3.ArrayUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

public class MongoUtil {
    /**
     * 创建collection，添加索引
     */
    public static void createCollection(MongoTemplate mongoTemplate, String collectionName, Logger logger, String... indexes) {
        boolean collectionExists = mongoTemplate.collectionExists(collectionName);
        if (collectionExists) {
            logger.info("mongo collection {}已存在", collectionName);
            return;
        }

        mongoTemplate.createCollection(collectionName);
        IndexOperations indexOperations = mongoTemplate.indexOps(collectionName);
        List<IndexInfo> indexInfo = indexOperations.getIndexInfo();
        if (indexInfo.size() == 1 && "_id_".equals(indexInfo.get(0).getName())) {
            List<IndexModel> list = new ArrayList<>();

            if (!ArrayUtils.isEmpty(indexes)) {
                for (String index : indexes) {
                    list.add(new IndexModel(
                            new BasicDBObject() {{
                                put(index, -1);
                            }})
                    );
                }
            }

            mongoTemplate.getCollection(collectionName).createIndexes(list);
        }

        logger.info("mongo collection {}创建成功", collectionName);

        mongoTemplate.collectionExists(collectionName);
    }

    public static Update convertToUpdate(Object obj) {
        List<Field> fieldList = new ArrayList<>(16);
        Class<?> clazz = obj.getClass();

        // 也获取父类的所有字段
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }

        Update update = new Update();
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);
                String name = field.getName();
                if ("_id".equalsIgnoreCase(name)) {
                    continue;
                }
                // 覆盖率测试工具会导致反射新增$jacocoData字段，需排除
                if (field.isSynthetic()) {
                    continue;
                }
                // 2024-03-11，不为空才加入update
                Object value = field.get(obj);
                if (null != value) {
                    update.set(name, field.get(obj));
                }
            }
            return update;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Update convertToUpdateWithNull(Object obj) {
        List<Field> fieldList = new ArrayList<>(16);
        Class<?> clazz = obj.getClass();

        // 也获取父类的所有字段
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }

        Update update = new Update();
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);
                String name = field.getName();
                if ("_id".equalsIgnoreCase(name)) {
                    continue;
                }
                // 覆盖率测试工具会导致反射新增$jacocoData字段，需排除
                if (field.isSynthetic()) {
                    continue;
                }
                update.set(name, field.get(obj));
            }
            return update;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Update convertToUpdate(Map<String, Object> map) {
        if (StringUtil.isNullOrEmpty(map)) {
            return null;
        }
        Update update = new Update();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        return update;
    }

    public static Map<String, Object> convertToMap(Object obj) {
        List<Field> fieldList = new ArrayList<>(16);
        Class<?> clazz = obj.getClass();

        // 也获取父类的所有字段
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }

        Map<String, Object> maps = new HashMap<>();
        try {
            for (Field field : fieldList) {
                field.setAccessible(true);
                String name = field.getName();
                if ("_id".equalsIgnoreCase(name)) {
                    continue;
                }
                // 覆盖率测试工具会导致反射新增$jacocoData字段，需排除
                if (field.isSynthetic()) {
                    continue;
                }
                maps.put(name, field.get(obj));
            }
            return maps;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @param collectionName
     * @param mongoTemplate
     * @param datas
     * @param keys           参数作用：
     *                       1.表不存在除了_id_以外的索引，key作为索引创建，且索引唯一；
     *                       2.表的主键，用于唯一索引一条数据更新；
     *                       注意：第一次传入此参数后索引就固定了，以后不能更改！！！如果不传此参数无法执行更新操作！！！
     * @description 对batch里的数据不区分先后。比如相同keys的数据无法保证按某个field的最大值作为更新数据。
     * @Author: qiuxiangpeng
     * @Date: 2023/10/30
     */
    public static String batchInsertMongo(String collectionName, MongoTemplate mongoTemplate, List<Object> datas, String[] keys) {

        if (datas.size() != 0) {
            //表不存在除了_id_以外的索引，key作为索引创建，且索引唯一；
            createIndex(collectionName, mongoTemplate, keys);
            BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collectionName);
            List<Pair<Query, Update>> pairs = new ArrayList<>();
            List<Map<String, Object>> reslist = new ArrayList<>();
            for (Object data : datas) {
                Map<String, Object> soMap = convertToMap(data);
                soMap.putIfAbsent("updateTimeDate", JodaDateUtil.longToDateStr(System.currentTimeMillis(), JodaDateUtil.Pattern.yyyy_MM_dd_HH_mm_ss));
                reslist.add(soMap);
            }
            //key为null时不能走下面的逻辑，否则只会插入最后一条
            if (keys != null) {
                for (Map<String, Object> soMap : reslist) {
                    Query query = new Query();
                    Update update = new Update();
                    //执行插入和更新
                    for (String key : keys) {
                        query.addCriteria(Criteria.where(key).is(soMap.get(key)));
                    }
                    for (Map.Entry<String, Object> entry : soMap.entrySet()) {
                        update.set(entry.getKey(), entry.getValue());
                    }
                    pairs.add(Pair.of(query, update));
                }
                bulkOps.upsert(pairs);
                bulkOps.execute();
            } else {
                mongoTemplate.insert(reslist, collectionName);
            }
            return "批量操作成功！";
        }
        return null;
    }

    //表不存在除了_id_以外的索引，indexs索引创建，且索引唯一；
    public static String createIndex(String collectionName, MongoTemplate mongoTemplate, String[] indexs) {
        if (indexs != null && indexs.length != 0) {
            //构建索引
            MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
            ListIndexesIterable<Document> indexesIterable = collection.listIndexes();
            if (CollectionUtils.isEmpty(getIndexNames(indexesIterable))) {
                for (int i = 0; i < indexs.length; i++) {
                    Map<String, Object> keyIndexs = new LinkedHashMap<>();
                    String indexName = indexs[i];
                    if (indexName.equals("_id")) {
                        continue;
                    }
                    keyIndexs.put(indexName, 1);
                    IndexOptions indexOptions = new IndexOptions();
                    indexOptions.background(true);
                    indexOptions.unique(true);
                    indexOptions.name(indexName + "_1");
                    collection.createIndex(new Document(keyIndexs), indexOptions);
                }
                return "创建索引成功！";
            }
            return "索引已存在！";
        }
        return null;
    }

    public static Set<String> getIndexNames(ListIndexesIterable<Document> documents) {
        MongoCursor<Document> cursor = documents.iterator();
        Set<String> indexNames = new HashSet<>();
        while (cursor.hasNext()) {
            Object next = cursor.next();
            String name = ((Document) next).get("name").toString();
            if (!"_id_".equalsIgnoreCase(name)) {
                indexNames.add(name);
            }
        }
        return indexNames;
    }
}
