package org.x1a0kang.compare.http;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.x1a0kang.compare.common.factory.CustomLoggerFactory;

@SpringBootApplication(scanBasePackages = {"org.x1a0kang.compare"})
@EnableScheduling
@EnableAsync
public class CompareHttpApplication {
    private static final Logger logger = CustomLoggerFactory.getLogger(CompareHttpApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CompareHttpApplication.class, args);
        logger.info(System.getProperty("os.name") + ",jdk:" + System.getProperty("java.version") + ",compare-http 启动完成");
    }

}
