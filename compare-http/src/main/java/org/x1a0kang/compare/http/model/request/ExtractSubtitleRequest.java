package org.x1a0kang.compare.http.model.request;

import java.util.List;

public class ExtractSubtitleRequest {
    private String name;
    private String author;
    private List<Subtitle> body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Subtitle> getBody() {
        return body;
    }

    public void setBody(List<Subtitle> body) {
        this.body = body;
    }

    public static class Subtitle {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
