package org.x1a0kang.compare.http.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.x1a0kang.compare.common.model.ApiReturnInfo;
import org.x1a0kang.compare.common.utils.StringUtil;
import org.x1a0kang.compare.http.model.common.*;
import org.x1a0kang.compare.http.model.request.*;
import org.x1a0kang.compare.http.model.shoe.Shoe;
import org.x1a0kang.compare.http.service.ShoeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shoe")
public class ShoeController {
    @Resource
    private ShoeService shoeService;

    @PostMapping("/getAll")
    public ApiReturnInfo getAll(@RequestBody(required = false) PageRequest request) {
        if (null == request) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Shoe> shoeList = shoeService.getAll(request.getPage(), request.getPageSize());
        if (StringUtil.isNullOrEmpty(shoeList)) {
            return ApiReturnInfo.getFailure("跑鞋不存在");
        }
        return ApiReturnInfo.getSuccess(shoeList);
    }

    @PostMapping("/getOne")
    public ApiReturnInfo getOne(@RequestBody(required = false) IdRequest request) {
        if (null == request || StringUtil.isNullOrEmpty(request.getId())) {
            return ApiReturnInfo.getParamMissing();
        }
        Shoe shoe = shoeService.getShoe(request.getId());
        if (shoe == null) {
            return ApiReturnInfo.getFailure("跑鞋不存在");
        }
        return ApiReturnInfo.getSuccess(shoe);
    }

    @PostMapping("/getListById")
    public ApiReturnInfo getListById(@RequestBody(required = false) IdListRequest request) {
        if (null == request || StringUtil.isNullOrEmpty(request.getIdList())) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Shoe> shoeList = shoeService.getShoeList(request.getIdList());
        if (StringUtil.isNullOrEmpty(shoeList)) {
            return ApiReturnInfo.getFailure("跑鞋不存在");
        }
        return ApiReturnInfo.getSuccess(shoeList);
    }

    @PostMapping("/getPriceRange")
    public ApiReturnInfo getPriceRange(@RequestBody(required = false) PriceRangeRequest request) {
        if (null == request) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Shoe> shoes = shoeService.priceRange(request.getMin(), request.getMax());
        if (StringUtil.isNullOrEmpty(shoes)) {
            return ApiReturnInfo.getFailure("跑鞋不存在");
        }
        return ApiReturnInfo.getSuccess(shoes);
    }

    @PostMapping("/getSpec")
    public ApiReturnInfo getSpec() {
        List<Spec> specs = shoeService.getSpec();
        if (StringUtil.isNullOrEmpty(specs)) {
            return ApiReturnInfo.getFailure("跑鞋规格不存在");
        }
        return ApiReturnInfo.getSuccess(specs);
    }

    @PostMapping("/getBrandSplit")
    public ApiReturnInfo getBrandSplit() {
        List<List<Brand>> shoeBrandList = shoeService.getBrandSplit();
        if (StringUtil.isNullOrEmpty(shoeBrandList)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(shoeBrandList);
    }

    @PostMapping("/getBrand")
    public ApiReturnInfo getBrand() {
        List<Brand> brandList = shoeService.getBrand();
        if (StringUtil.isNullOrEmpty(brandList)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(brandList);
    }

    @PostMapping("/search")
    public ApiReturnInfo search(@RequestBody(required = false) SearchRequest request) {
        if (null == request || StringUtil.isNullOrEmpty(request.getKeyword())) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Shoe> shoeList = shoeService.search(request.getPage(), request.getPageSize(), request.getKeyword());
        return ApiReturnInfo.getSuccess(shoeList);
    }

    @PostMapping("/getCategories")
    public ApiReturnInfo getCategories(@RequestBody(required = false) PageRequest request) {
        List<Categories> categories = shoeService.getCategories(request.getPage(), request.getPageSize());
        return ApiReturnInfo.getSuccess(categories);
    }

    @PostMapping("/searchByFilter")
    public ApiReturnInfo searchByFilter(@RequestBody(required = false) SearchByFilterRequest request) {
        if (null == request) {
            return ApiReturnInfo.getParamMissing();
        }
        List<Shoe> shoeList = shoeService.searchByFilter(request);
        return ApiReturnInfo.getSuccess(shoeList);
    }

    @PostMapping("/getOrderSpec")
    public ApiReturnInfo getOrderSpec() {
        List<OrderSpec> orderSpecList = shoeService.getOrderSpec();
        if (StringUtil.isNullOrEmpty(orderSpecList)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(orderSpecList);
    }

    @PostMapping("/getBanner")
    public ApiReturnInfo getBanner() {
        List<Banner> banner = shoeService.getBanner();
        if (StringUtil.isNullOrEmpty(banner)) {
            return ApiReturnInfo.getSuccess(new ArrayList<>());
        }
        return ApiReturnInfo.getSuccess(banner);
    }
}
