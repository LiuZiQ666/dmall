package com.gdou.dmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class DressImage implements Serializable {

    @Id
    @Column
    String id;
    @Column
    String dressId;
    @Column
    String imgName;
    @Column
    String imgUrl;
    @Column
    String isDefault;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDressId() {
        return dressId;
    }

    public void setDressId(String dressId) {
        this.dressId = dressId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
