package com.gdou.dmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class DressBaseinfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private String id;
    @Column
    private String dressName;
    @Column
    private String dressPrice;
    @Column
    private String imgUrl;
    @Column
    private String description;
    @Column
    private String occasionId;
    @Column
    private int views;
    @Transient
    List<DressImage> dressImage;

    public List<DressImage> getDressImage() {
        return dressImage;
    }

    public void setDressImage(List<DressImage> dressImage) {
        this.dressImage = dressImage;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getDressName() {
        return dressName;
    }

    public void setDressName(String dressName) {
        this.dressName = dressName;
    }

    public String getDressPrice() {
        return dressPrice;
    }

    public void setDressPrice(String dressPrice) {
        this.dressPrice = dressPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccasionId() {
        return occasionId;
    }

    public void setOccasionId(String occasionId) {
        this.occasionId = occasionId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
