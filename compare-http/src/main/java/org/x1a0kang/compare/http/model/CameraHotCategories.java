package org.x1a0kang.compare.http.model;

import java.util.Map;

public class CameraHotCategories {
    private String name;
    private String coverUrl;
    private Map<String, String> filter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
