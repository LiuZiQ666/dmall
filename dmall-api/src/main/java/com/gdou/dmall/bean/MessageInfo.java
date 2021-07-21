package com.gdou.dmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MessageInfo implements Serializable {

    @Id
    @Column
    private String id;
    @Column
    private String number;
    @Column
    private String qq;
    @Column
    private String vx;
    @Column
    private String email;
    @Column
    private String address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getVx() {
        return vx;
    }

    public void setVx(String vx) {
        this.vx = vx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
