package org.x1a0kang.compare.http.model.request;

public class SearchRequest extends PageRequest {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
