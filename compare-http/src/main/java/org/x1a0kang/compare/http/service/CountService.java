package org.x1a0kang.compare.http.service;

import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.x1a0kang.compare.common.utils.JodaDateUtil;
import org.x1a0kang.compare.http.model.common.Count;

import java.util.List;

@Service
public class CountService {
    @Resource
    private MongoTemplate mongoTemplate;

    public void addView(String id) {
        Query query = new Query(Criteria.where("productId").is(id));
        Update update = new Update();
        update.inc("totalView", 1);
        update.inc("todayView", 1);
        mongoTemplate.upsert(query, update, Count.class, "shoeCount");
    }

    public void addAddPk(String id) {
        Query query = new Query(Criteria.where("productId").is(id));
        Update update = new Update();
        update.inc("totalAddPk", 1);
        update.inc("todayAddPk", 1);
        mongoTemplate.upsert(query, update, Count.class, "shoeCount");
    }

    public void addPk(List<String> idList) {
        for (String id : idList) {
            Query query = new Query(Criteria.where("productId").is(id));
            Update update = new Update();
            update.inc("totalPk", 1);
            update.inc("todayPk", 1);
            mongoTemplate.upsert(query, update, Count.class, "shoeCount");
        }
    }

    public List<Count> getHotRank(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "hot")).with(pageable);
        return mongoTemplate.find(query, Count.class, "shoeCount");
    }

    @Scheduled(cron = "0 0 */1 * * ?")
//    @PostConstruct
    public void calHot() {
        List<Count> shoeCountList = mongoTemplate.findAll(Count.class, "shoeCount");
        int hot;
        long time = System.currentTimeMillis();
        String timeStr = JodaDateUtil.longToDateStr(time, JodaDateUtil.Pattern.yyyy_MM_dd_HH_mm_ss);

        for (Count shoeCount : shoeCountList) {
            // 前天：昨天：今天=1:1:3
            hot = shoeCount.getTodayView() * 3 + shoeCount.getYesterdayView() + shoeCount.getTwoYesterdayView();
            hot += shoeCount.getTodayAddPk() * 3 + shoeCount.getYesterdayAddPk() + shoeCount.getTwoYesterdayAddPk();
            hot += shoeCount.getTodayPk() * 3 + shoeCount.getYesterdayPk() + shoeCount.getTwoYesterdayPk();

            Update update = new Update();
            update.set("hot", hot);
            update.set("updateTimeStamp", time);
            update.set("updateTimeStr", timeStr);
            Query query = new Query(Criteria.where("productId").is(shoeCount.getProductId()));
            mongoTemplate.updateFirst(query, update, "shoeCount");
        }
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyClear() {
        List<Count> shoeCountList = mongoTemplate.findAll(Count.class, "shoeCount");
        for (Count shoeCount : shoeCountList) {
            Query query = new Query(Criteria.where("productId").is(shoeCount.getProductId()));
            Update update = new Update();
            update.set("twoYesterdayView", shoeCount.getYesterdayView());
            update.set("yesterdayView", shoeCount.getTodayView());
            update.set("todayView", 0);

            update.set("twoYesterdayAddPk", shoeCount.getYesterdayAddPk());
            update.set("yesterdayAddPk", shoeCount.getTodayAddPk());
            update.set("todayAddPk", 0);

            update.set("twoYesterdayPk", shoeCount.getYesterdayPk());
            update.set("yesterdayPk", shoeCount.getTodayPk());
            update.set("todayPk", 0);

            mongoTemplate.updateFirst(query, update, "shoeCount");
        }
    }
}
