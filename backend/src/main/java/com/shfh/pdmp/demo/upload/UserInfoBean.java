package com.shfh.pdmp.demo.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by wpcao on 2018/3/13 15:37
 */
public class UserInfoBean implements Serializable {

    private String username;

    private String age;

    private MultipartFile image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
