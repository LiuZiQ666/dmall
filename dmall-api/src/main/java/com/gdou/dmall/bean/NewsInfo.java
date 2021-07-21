package com.gdou.dmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class NewsInfo implements Serializable {
    @Id
    @Column
    String id;
    @Column
    String title;
    @Column
    String article;
    @Column
    String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
