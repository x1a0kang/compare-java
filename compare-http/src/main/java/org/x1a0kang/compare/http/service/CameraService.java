package org.x1a0kang.compare.http.service;

import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.x1a0kang.compare.http.model.Camera;
import org.x1a0kang.compare.http.model.CameraBrand;
import org.x1a0kang.compare.http.model.CameraSpec;

import java.util.ArrayList;
import java.util.List;

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
        int skip = (page - 1) * pageSize;
        Query query = new Query().skip(skip).limit(pageSize);
        return mongoTemplate.find(query, Camera.class, "camera");
    }

    public List<CameraSpec> getSpec() {
        return mongoTemplate.findAll(CameraSpec.class, "cameraSpec");
    }

    public List<List<CameraBrand>> getBrand() {
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
}


