package com.gdou.dmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class User implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String userName;
    @Column
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
