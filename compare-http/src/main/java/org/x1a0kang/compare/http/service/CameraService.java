package org.x1a0kang.compare.http.service;

import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.x1a0kang.compare.http.model.Camera;
import org.x1a0kang.compare.http.model.CameraBrand;
import org.x1a0kang.compare.http.model.CameraHotCategories;
import org.x1a0kang.compare.http.model.CameraSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CameraService {
    @Resource
    private MongoTemplate mongoTemplate;

    public Camera getCamera(String id) {
        Query query = new Query(Criteria.where("productId").is(id));
        return mongoTemplate.findOne(query, Camera.class, "camera");
    }

    public List<Camera> getCameraList(List<String> idList) {
        Query query = new Query(Criteria.where("productId").in(idList));
        return mongoTemplate.find(query, Camera.class, "camera");
    }

    public List<Camera> priceRange(double min, double max) {
        Query query = new Query(Criteria.where("price").gte(min).lte(max));
        return mongoTemplate.find(query, Camera.class, "camera");
    }

    public List<Camera> getAll(int page, int pageSize) {
        Query query = pageQuery(page, pageSize);
        return mongoTemplate.find(query, Camera.class, "camera");
    }

    public List<CameraSpec> getSpec() {
        return mongoTemplate.findAll(CameraSpec.class, "cameraSpec");
    }

    public List<List<CameraBrand>> getBrandSplit() {
        List<CameraBrand> cameraBrand = mongoTemplate.findAll(CameraBrand.class, "cameraBrand");
        List<List<CameraBrand>> list = new ArrayList<>();
        int pageSize = 8;
        List<CameraBrand> temp = new ArrayList<>();
        for (CameraBrand cameraBrandItem : cameraBrand) {
            if (temp.size() == pageSize) {
                list.add(new ArrayList<>(temp));
                temp = new ArrayList<>();
            } else {
                temp.add(cameraBrandItem);
            }
        }
        if (!temp.isEmpty()) {
            list.add(new ArrayList<>(temp));
        }
        return list;
    }

    public List<CameraBrand> getBrand() {
        return mongoTemplate.findAll(CameraBrand.class, "cameraBrand");
    }

    public List<Camera> search(int page, int pageSize, String keyword) {
        // TODO：现在先以产品名，品牌，别名，标签筛选，看有没有机会上ES吧
        Query query = pageQuery(page, pageSize);
        Pattern pattern = Pattern.compile(".*" + keyword + ".*", Pattern.CASE_INSENSITIVE);
        query.addCriteria(new Criteria().orOperator(Criteria.where("brand").regex(pattern),
                Criteria.where("name").regex(pattern),
                Criteria.where("otherName").regex(pattern),
                Criteria.where("tab").regex(pattern)));
        return mongoTemplate.find(query, Camera.class, "camera");
    }

    public List<CameraHotCategories> getHotCategories(int page, int pageSize) {
        Query query = pageQuery(page, pageSize);
        return mongoTemplate.find(query, CameraHotCategories.class, "cameraHotCategories");
    }

    public List<Camera> searchByFilter(int page, int pageSize, List<String> key, List<String> value) {
        Query query = pageQuery(page, pageSize);
        Pattern pattern;
        String searchKey = "all";
        for (int i = 0; i < key.size(); i++) {
            String str = key.get(i);
            pattern = Pattern.compile(".*" + value.get(i) + ".*", Pattern.CASE_INSENSITIVE);
            if (searchKey.equalsIgnoreCase(str)) {
                query.addCriteria(new Criteria().orOperator(Criteria.where("brand").regex(pattern),
                        Criteria.where("name").regex(pattern),
                        Criteria.where("otherName").regex(pattern),
                        Criteria.where("tab").regex(pattern)));
            } else {
                query.addCriteria(Criteria.where(key.get(i)).regex(pattern));
            }
        }
        return mongoTemplate.find(query, Camera.class, "camera");
    }

    private Query pageQuery(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return new Query().with(pageable);
    }
}


