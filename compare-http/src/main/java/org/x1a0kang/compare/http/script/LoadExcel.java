package org.x1a0kang.compare.http.script;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.x1a0kang.compare.common.utils.MongoUtil;
import org.x1a0kang.compare.http.model.Camera;

@Component
public class LoadExcel {
    @Resource
    private MongoTemplate mongoTemplate;

//    @PostConstruct
    public void run() {
        load("E:\\java\\camera.xlsx");
    }

    public void load(String filePath) {
        // 读取Excel文件并转换为Map数组
        EasyExcel.read(filePath, Camera.class, new AnalysisEventListener<Camera>() {
            @Override
            public void invoke(Camera camera, AnalysisContext analysisContext) {
                camera.generateId();
                camera.setUpdateTime(System.currentTimeMillis());
                Query query = Query.query(Criteria.where("productId").is(camera.getProductId()));
                Update update = MongoUtil.convertToUpdate(camera);
                if (null != update) {
                    mongoTemplate.upsert(query, update, "camera");
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet().doRead();
    }
}
