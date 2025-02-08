package org.x1a0kang.compare.http.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.x1a0kang.compare.common.model.ApiReturnInfo;
import org.x1a0kang.compare.common.utils.StringUtil;
import org.x1a0kang.compare.http.model.*;
import org.x1a0kang.compare.http.model.camera.Camera;
import org.x1a0kang.compare.http.model.camera.CameraBrand;
import org.x1a0kang.compare.http.model.camera.CameraCategories;
import org.x1a0kang.compare.http.model.camera.CameraSpec;
import org.x1a0kang.compare.http.model.request.*;
import org.x1a0kang.compare.http.service.CameraService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/camera")
public class CameraController {
    @Resource
    private CameraService cameraService;

    @PostMapping("/getAll")
    public ApiReturnInfo getAll(@RequestBody(required = false) PageRequest request) {
        if (null == request) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Camera> cameraList = cameraService.getAll(request.getPage(), request.getPageSize());
        if (StringUtil.isNullOrEmpty(cameraList)) {
            return ApiReturnInfo.getFailure("相机不存在");
        }
        return ApiReturnInfo.getSuccess(cameraList);
    }

    @PostMapping("/getOne")
    public ApiReturnInfo getOne(@RequestBody(required = false) IdRequest request) {
        if (null == request || StringUtil.isNullOrEmpty(request.getId())) {
            return ApiReturnInfo.getParamMissing();
        }
        Camera camera = cameraService.getCamera(request.getId());
        if (camera == null) {
            return ApiReturnInfo.getFailure("相机不存在");
        }
        return ApiReturnInfo.getSuccess(camera);
    }

    @PostMapping("/getListById")
    public ApiReturnInfo getListById(@RequestBody(required = false) IdListRequest request) {
        if (null == request || StringUtil.isNullOrEmpty(request.getIdList())) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Camera> cameraList = cameraService.getCameraList(request.getIdList());
        if (StringUtil.isNullOrEmpty(cameraList)) {
            return ApiReturnInfo.getFailure("相机不存在");
        }
        return ApiReturnInfo.getSuccess(cameraList);
    }

    @PostMapping("/getPriceRange")
    public ApiReturnInfo getPriceRange(@RequestBody(required = false) PriceRangeRequest request) {
        if (null == request) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Camera> cameras = cameraService.priceRange(request.getMin(), request.getMax());
        if (StringUtil.isNullOrEmpty(cameras)) {
            return ApiReturnInfo.getFailure("相机不存在");
        }
        return ApiReturnInfo.getSuccess(cameras);
    }

    @PostMapping("/getCameraSpec")
    public ApiReturnInfo getCameraSpec() {
        List<CameraSpec> cameraSpecs = cameraService.getSpec();
        if (StringUtil.isNullOrEmpty(cameraSpecs)) {
            return ApiReturnInfo.getFailure("相机规格不存在");
        }
        return ApiReturnInfo.getSuccess(cameraSpecs);
    }

    @PostMapping("/getBrandSplit")
    public ApiReturnInfo getBrandSplit() {
        List<List<CameraBrand>> cameraBrandList = cameraService.getBrandSplit();
        if (StringUtil.isNullOrEmpty(cameraBrandList)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(cameraBrandList);
    }

    @PostMapping("/getBrand")
    public ApiReturnInfo getBrand() {
        List<CameraBrand> cameraBrandList = cameraService.getBrand();
        if (StringUtil.isNullOrEmpty(cameraBrandList)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(cameraBrandList);
    }

    @PostMapping("/search")
    public ApiReturnInfo search(@RequestBody(required = false) SearchRequest request) {
        if (null == request || StringUtil.isNullOrEmpty(request.getKeyword())) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Camera> cameraList = cameraService.search(request.getPage(), request.getPageSize(), request.getKeyword());
        return ApiReturnInfo.getSuccess(cameraList);
    }

    @PostMapping("/getCategories")
    public ApiReturnInfo getCategories(@RequestBody(required = false) PageRequest request) {
        List<CameraCategories> cameraCategories = cameraService.getCategories(request.getPage(), request.getPageSize());
        return ApiReturnInfo.getSuccess(cameraCategories);
    }

    @PostMapping("/searchByFilter")
    public ApiReturnInfo searchByFilter(@RequestBody(required = false) SearchByFilterRequest request) {
        if (null == request) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Camera> cameraList = cameraService.searchByFilter(request);
        return ApiReturnInfo.getSuccess(cameraList);
    }

    @PostMapping("/getOrderSpec")
    public ApiReturnInfo getOrderSpec() {
        List<OrderSpec> orderSpecList = cameraService.getOrderSpec();
        if (StringUtil.isNullOrEmpty(orderSpecList)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(orderSpecList);
    }

    @PostMapping("/getBanner")
    public ApiReturnInfo getBanner() {
        List<Banner> banner = cameraService.getBanner();
        if (StringUtil.isNullOrEmpty(banner)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(banner);
    }
}
