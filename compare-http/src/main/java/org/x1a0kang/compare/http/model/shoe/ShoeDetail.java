package org.x1a0kang.compare.http.model.shoe;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class ShoeDetail {
    @Id
    private String productId;
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
    private String fastPaceStr;
    private Integer fastPace;
    private String slowPaceStr;
    private Integer slowPace;
    // 距离
    private Double distance;
    private String runnerWeight;
    private String position;
    private Integer price;
    private List<String> imageList;
    private String publishDateStr;
    private Date publishDate;
    private Integer hot;
    private String hotUpdateTimeStr;

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

    public String getFastPaceStr() {
        return fastPaceStr;
    }

    public void setFastPaceStr(String fastPaceStr) {
        this.fastPaceStr = fastPaceStr;
    }

    public String getSlowPaceStr() {
        return slowPaceStr;
    }

    public void setSlowPaceStr(String slowPaceStr) {
        this.slowPaceStr = slowPaceStr;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getRunnerWeight() {
        return runnerWeight;
    }

    public void setRunnerWeight(String runnerWeight) {
        this.runnerWeight = runnerWeight;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getFastPace() {
        return fastPace;
    }

    public void setFastPace(Integer fastPace) {
        this.fastPace = fastPace;
    }

    public Integer getSlowPace() {
        return slowPace;
    }

    public void setSlowPace(Integer slowPace) {
        this.slowPace = slowPace;
    }

    public String getHotUpdateTimeStr() {
        return hotUpdateTimeStr;
    }

    public void setHotUpdateTimeStr(String hotUpdateTimeStr) {
        this.hotUpdateTimeStr = hotUpdateTimeStr;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }
}
