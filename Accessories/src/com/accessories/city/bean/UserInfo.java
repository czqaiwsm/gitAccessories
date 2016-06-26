package com.accessories.city.bean;

import java.io.Serializable;

/**
 * @author 用户信息
 * @desc 请用一句话描述它
 * @date 16/3/29
 */
public class UserInfo implements Serializable {

    private String id;
    private String phone;
    private String nickname	    ;//用户头像
    private String account	    ;//昵称
    private String integral	;//积分

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
