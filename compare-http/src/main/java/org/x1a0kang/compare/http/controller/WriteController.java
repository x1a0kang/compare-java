package org.x1a0kang.compare.http.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.x1a0kang.compare.common.model.ApiReturnInfo;
import org.x1a0kang.compare.http.model.request.DownloadImageRequest;
import org.x1a0kang.compare.http.model.shoe.ShoeDetail;
import org.x1a0kang.compare.http.service.WriteDataService;

@RestController
@RequestMapping("/write")
public class WriteController {
    @Resource
    private WriteDataService writeDataService;

    @PostMapping("/addShoe")
    public ApiReturnInfo addShoe(@RequestBody(required = false) ShoeDetail shoeDetail) {
        if (null == shoeDetail) {
            return ApiReturnInfo.getParamMissing();
        }
        writeDataService.addShoe(shoeDetail);
        return ApiReturnInfo.getSuccess();
    }

    @PostMapping("/downloadImage")
    public ApiReturnInfo downloadImage(@RequestBody(required = false) DownloadImageRequest request) {
        if (null == request) {
            return ApiReturnInfo.getParamMissing();
        }
        writeDataService.downloadImage(request.getUrl(), request.getSavePath(), request.getFileName());
        return ApiReturnInfo.getSuccess();
    }
}
