package org.x1a0kang.compare.http;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.x1a0kang.compare.http.model.common.Count;
import org.x1a0kang.compare.http.service.CountService;

import java.util.List;

@SpringBootTest
class CountTests {
    @Resource
    CountService countService;

    @Test
    void testHotRank() {
        List<Count> hotRank = countService.getHotRank(1, 10);
        for (Count count : hotRank) {
            System.out.println(count.getProductId() + "  " + count.getHot());
        }
    }
}
