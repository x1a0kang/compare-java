package org.x1a0kang.compare.http.model.shoe;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Shoe {
    @Id
    private String productId;
    private String name;
    private String brand;
    // 定位
    private String position;
    private List<String> scenarioList;
    private Integer price;
    private List<String> imageList;
    private Integer hot;
    private String hotUpdateTimeStr;
    private boolean delete;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<String> getScenarioList() {
        return scenarioList;
    }

    public void setScenarioList(List<String> scenarioList) {
        this.scenarioList = scenarioList;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getHotUpdateTimeStr() {
        return hotUpdateTimeStr;
    }

    public void setHotUpdateTimeStr(String hotUpdateTimeStr) {
        this.hotUpdateTimeStr = hotUpdateTimeStr;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
