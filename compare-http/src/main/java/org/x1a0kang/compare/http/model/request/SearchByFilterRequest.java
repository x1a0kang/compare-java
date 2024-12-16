package org.x1a0kang.compare.http.model.request;

import java.util.List;

public class SearchByFilterRequest extends PageRequest {
    private List<String> key;
    private List<String> value;

    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
