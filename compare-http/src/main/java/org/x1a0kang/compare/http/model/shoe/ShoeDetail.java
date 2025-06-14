package org.x1a0kang.compare.http.model.shoe;

import java.util.Date;

public class ShoeDetail extends Shoe {
    private String otherName;
    // 鞋码，鞋长
    private String length;
    // 鞋宽，鞋楦
    private String width;
    private Integer weight;
    private Float toeHeight;
    private Float heelHeight;
    private Float heelToeDrop;
    // 中底材料
    private String midsole;
    // 中底结构
    private String structure;
    // 鞋面
    private String upper;
    // 板材
    private String plate;
    // 鞋底材料
    private String sole;
    private String fastPaceStr;
    private Integer fastPace;
    private String slowPaceStr;
    private Integer slowPace;
    // 距离
    private Double distance;
    private String runnerWeight;
    // 跑法：前全后掌，步频步幅
    private String technique;
    // 使用场景
    private String scenario;
    // 缺点
    private String disadvantage;
    private String publishDateStr;
    private Date publishDate;
    private long updateTime;

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

    public Float getToeHeight() {
        return toeHeight;
    }

    public void setToeHeight(Float toeHeight) {
        this.toeHeight = toeHeight;
    }

    public Float getHeelHeight() {
        return heelHeight;
    }

    public void setHeelHeight(Float heelHeight) {
        this.heelHeight = heelHeight;
    }

    public Float getHeelToeDrop() {
        return heelToeDrop;
    }

    public void setHeelToeDrop(Float heelToeDrop) {
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

    public String getSole() {
        return sole;
    }

    public void setSole(String sole) {
        this.sole = sole;
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

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(String disadvantage) {
        this.disadvantage = disadvantage;
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

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
