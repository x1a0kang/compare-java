package org.x1a0kang.compare.http.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.x1a0kang.compare.common.factory.CustomLoggerFactory;
import org.x1a0kang.compare.common.utils.JodaDateUtil;
import org.x1a0kang.compare.common.utils.MongoUtil;
import org.x1a0kang.compare.common.utils.StringUtil;
import org.x1a0kang.compare.http.model.common.*;
import org.x1a0kang.compare.http.model.request.SearchByFilterRequest;
import org.x1a0kang.compare.http.model.shoe.Shoe;
import org.x1a0kang.compare.http.model.shoe.ShoeDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ShoeService {
    private final Logger logger = CustomLoggerFactory.getLogger(ShoeService.class);
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private CountService countService;

    public Shoe getShoe(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Shoe.class, "shoe");
    }

    public ShoeDetail getShoeDetail(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, ShoeDetail.class, "shoe");
    }

    public List<Shoe> getShoeList(List<String> idList) {
        Query query = new Query(Criteria.where("_id").in(idList));
        List<Shoe> shoeList = mongoTemplate.find(query, Shoe.class, "shoe");
        Map<String, Shoe> shoeMap = shoeList.stream().collect(Collectors.toMap(Shoe::getProductId, shoe -> shoe));
        List<Shoe> shoeListSorted = new ArrayList<>();

        for (String s : idList) {
            Shoe shoe = shoeMap.get(s);
            shoeListSorted.add(shoe);
        }
        return shoeListSorted;
    }

    public List<ShoeDetail> getShoeDetailList(List<String> idList) {
        Query query = new Query(Criteria.where("_id").in(idList));
        List<ShoeDetail> shoeDetailList = mongoTemplate.find(query, ShoeDetail.class, "shoe");
        Map<String, ShoeDetail> shoeMap = shoeDetailList.stream().collect(Collectors.toMap(ShoeDetail::getProductId, shoeDetail -> shoeDetail));
        List<ShoeDetail> shoeListSorted = new ArrayList<>();

        for (String s : idList) {
            ShoeDetail shoeDetail = shoeMap.get(s);
            shoeListSorted.add(shoeDetail);
        }
        return shoeListSorted;
    }

    public List<Shoe> priceRange(double min, double max) {
        Query query = new Query(Criteria.where("price").gte(min).lte(max));
        return mongoTemplate.find(query, Shoe.class, "shoe");
    }

    public List<Shoe> getAll(int page, int pageSize) {
        Query query = pageQuery(page, pageSize).with(Sort.by(Sort.Direction.DESC, "updateTime"));
        return mongoTemplate.find(query, Shoe.class, "shoe");
    }

    public List<Spec> getSpec() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "orderScore"));
        return mongoTemplate.find(query, Spec.class, "shoeSpec");
    }

    public List<List<Brand>> getBrandSplit() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "orderScore"));
        List<Brand> brand = mongoTemplate.find(query, Brand.class, "shoeBrand");
        List<List<Brand>> list = new ArrayList<>();
        int pageSize = 10;
        List<Brand> temp = new ArrayList<>();
        for (Brand brandItem : brand) {
            if (temp.size() == pageSize) {
                list.add(new ArrayList<>(temp));
                temp = new ArrayList<>();
            }
            temp.add(brandItem);
        }
        if (!temp.isEmpty()) {
            list.add(new ArrayList<>(temp));
        }
        return list;
    }

    public List<Brand> getBrand() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "orderScore"));
        return mongoTemplate.find(query, Brand.class, "shoeBrand");
    }

    public List<Shoe> search(int page, int pageSize, String keyword) {
        // TODO：现在先以产品名，品牌，别名，标签筛选，看有没有机会上ES吧
        Query query = pageQuery(page, pageSize).with(Sort.by(Sort.Direction.DESC, "updateTime"));
        ;
        Pattern pattern = Pattern.compile(".*" + keyword + ".*", Pattern.CASE_INSENSITIVE);
        query.addCriteria(new Criteria().orOperator(Criteria.where("brand").regex(pattern), Criteria.where("name").regex(pattern), Criteria.where("otherName").regex(pattern), Criteria.where("tab").regex(pattern)));
        return mongoTemplate.find(query, Shoe.class, "shoe");
    }

    public List<Categories> getCategories(int page, int pageSize) {
        Query query = pageQuery(page, pageSize).with(Sort.by(Sort.Direction.DESC, "orderScore"));
        return mongoTemplate.find(query, Categories.class, "shoeCategories");
    }

    public List<Shoe> searchByFilter(SearchByFilterRequest request) {
        Query query = pageQuery(request.getPage(), request.getPageSize());
        List<String> key = request.getKey();
        List<String> value = request.getValue();
        String orderKey = request.getOrderKey();

        // 排序字段
        if (StringUtil.isNotNullOrEmpty(orderKey)) {
            if ("1".equals(request.getOrder())) {
                query.with(Sort.by(Sort.Direction.ASC, orderKey));
            } else {
                query.with(Sort.by(Sort.Direction.DESC, orderKey));
            }
        } else {
            query.with(Sort.by(Sort.Direction.DESC, "updateTime"));
        }

        if (StringUtil.isNotNullOrEmpty(key)) {
            Pattern pattern;
            String searchKey = "all";
            for (int i = 0; i < key.size(); i++) {
                String str = key.get(i);
                pattern = Pattern.compile(".*" + value.get(i) + ".*", Pattern.CASE_INSENSITIVE);
                if (searchKey.equalsIgnoreCase(str)) {
                    query.addCriteria(new Criteria().orOperator(Criteria.where("brand").regex(pattern), Criteria.where("name").regex(pattern), Criteria.where("otherName").regex(pattern), Criteria.where("tab").regex(pattern)));
                } else {
                    query.addCriteria(Criteria.where(key.get(i)).regex(pattern));
                }
            }
        }
        return mongoTemplate.find(query, Shoe.class, "shoe");
    }

    public List<Shoe> getHotRank(int page, int pageSize) {
        List<Count> hotRank = countService.getHotRank(page, pageSize);
        List<String> idList = new ArrayList<>();
        for (Count count : hotRank) {
            idList.add(count.getProductId());
        }
        List<Shoe> shoeList = getShoeList(idList);

        Map<String, Shoe> shoeMap = shoeList.stream().collect(Collectors.toMap(Shoe::getProductId, shoe -> shoe));
        List<Shoe> shoeListSorted = new ArrayList<>();

        for (int i = 0; i < hotRank.size(); i++) {
            Shoe shoe = shoeMap.get(idList.get(i));
            shoe.setHot(hotRank.get(i).getHot());
            shoe.setHotUpdateTimeStr(hotRank.get(i).getUpdateTimeStr());
            shoeListSorted.add(shoe);
        }
        return shoeListSorted;
    }

    private Query pageQuery(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return new Query().with(pageable);
    }

    public List<OrderSpec> getOrderSpec() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "orderScore"));
        return mongoTemplate.find(query, OrderSpec.class, "shoeOrderSpec");
    }

    public List<Banner> getBanner() {
        Query query = new Query(Criteria.where("valid").is(true)).with(Sort.by(Sort.Direction.DESC, "orderScore"));
        return mongoTemplate.find(query, Banner.class, "shoeBanner");
    }

    public void addShoe(ShoeDetail shoeDetail) {
        shoeDetail.setFastPace(getPaceStr(shoeDetail.getFastPaceStr()));
        shoeDetail.setSlowPace(getPaceStr(shoeDetail.getSlowPaceStr()));
        shoeDetail.setPublishDate(JodaDateUtil.strToDate(shoeDetail.getPublishDateStr(), JodaDateUtil.Pattern.yyyy_MM_zh));
        shoeDetail.setUpdateTime(System.currentTimeMillis());
        Update update = MongoUtil.convertToUpdate(shoeDetail);
        if (null == update) {
            logger.error("object转update失败");
            return;
        }
        Query query = new Query(Criteria.where("name").is(shoeDetail.getName()));
        mongoTemplate.upsert(query, update, "shoe");
    }

    private Integer getPaceStr(String pace) {
        if (pace.endsWith("秒")) {
            pace = pace.substring(0, pace.length() - 1);
        }
        String[] split = pace.split("分");
        int result = Integer.parseInt(split[0]) * 100;
        if (split.length > 1) {
            result += Integer.parseInt(split[1]);
        }
        return result;
    }
}


