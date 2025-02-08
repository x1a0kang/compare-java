package org.x1a0kang.compare.http.model.shoe;

import java.util.List;

public class Shoe {
    private String _id;
    private String name;
    private String brand;
    private String otherName;
    // 鞋码，鞋长
    private String length;
    // 鞋宽，鞋楦
    private String width;
    private Integer weight;
    private Integer toeHeight;
    private Integer heelHeight;
    private Integer heelToeDrop;
    // 中底材料
    private String midsole;
    // 中底结构
    private String structure;
    // 鞋面
    private String upper;
    // 板材
    private String plate;
    private String fastPace;
    private String slowPace;
    private Integer price;
    private List<String> imageList;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = _id;
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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getToeHeight() {
        return toeHeight;
    }

    public void setToeHeight(Integer toeHeight) {
        this.toeHeight = toeHeight;
    }

    public Integer getHeelHeight() {
        return heelHeight;
    }

    public void setHeelHeight(Integer heelHeight) {
        this.heelHeight = heelHeight;
    }

    public Integer getHeelToeDrop() {
        return heelToeDrop;
    }

    public void setHeelToeDrop(Integer heelToeDrop) {
        this.heelToeDrop = heelToeDrop;
    }

    public String getMidsole() {
        return midsole;
    }

    public void setMidsole(String midsole) {
        this.midsole = midsole;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getFastPace() {
        return fastPace;
    }

    public void setFastPace(String fastPace) {
        this.fastPace = fastPace;
    }

    public String getSlowPace() {
        return slowPace;
    }

    public void setSlowPace(String slowPace) {
        this.slowPace = slowPace;
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
}
