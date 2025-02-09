package org.x1a0kang.compare.http.model.common;

import java.util.List;

public class Spec extends DataSelectEntity {
    private List<DataSelectEntity> optionList;
    private String description;
    private String unit;

    public List<DataSelectEntity> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<DataSelectEntity> optionList) {
        this.optionList = optionList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
