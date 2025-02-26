package org.x1a0kang.compare.http.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.x1a0kang.compare.common.factory.CustomLoggerFactory;
import org.x1a0kang.compare.common.utils.JodaDateUtil;
import org.x1a0kang.compare.common.utils.MongoUtil;
import org.x1a0kang.compare.http.model.shoe.ShoeDetail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class WriteDataService {
    private final Logger logger = CustomLoggerFactory.getLogger(WriteDataService.class);
    @Resource
    private MongoTemplate mongoTemplate;

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

    public void downloadImage(String imageUrl, String savePath, String fileName) {
        if (imageUrl.endsWith(".avif")) {
            imageUrl = imageUrl.substring(0, imageUrl.length() - 5);
        }
        try {
            // 创建HTTP连接
            HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
            // 获取文件扩展名
            String fileExtension = getFileExtension(connection.getContentType());

            // 构建完整保存路径
            fileName = getNameNum(savePath, fileName);
            String fullPath = Paths.get(savePath, fileName + fileExtension).toString();

            InputStream inputStream = connection.getInputStream();
            Files.copy(inputStream, Paths.get(fullPath));
            inputStream.close();
            System.out.println(" =========== 操作成功" + fileName);
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println("========== 图片下载失败" + fileName);
        }
    }

    private String getFileExtension(String contentType) {
        // 根据Content-Type判断文件类型
        return switch (contentType.toLowerCase()) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/gif" -> ".gif";
            case "image/webp" -> ".webp";
            default -> ".dat"; // 未知类型
        };
    }

    private String getNameNum(String savePath, String fileName) {
        File dictionary = new File(savePath);
        File[] files = dictionary.listFiles();
        int max = 1;
        for (File file : files) {
            String name = file.getName();
            if (name.contains(fileName)) {
                int i = Integer.parseInt(name.substring(name.length() - 5, name.length() - 4));
                max = Math.max(max, i);
            }
        }
        return fileName + "-" + (max + 1);
    }

    public void setImageList(String id, String url, int num) {
        List<String> imageList = new ArrayList<>();
        String prefix = url.substring(0, url.length() - 5);
        String suffix = url.substring(url.length() - 4);
        for (int i = 1; i <= num; i++) {
            imageList.add(prefix + i + suffix);
        }
        Update update = new Update();
        update.set("imageList", imageList);
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.upsert(query, update, "shoe");
    }
}
