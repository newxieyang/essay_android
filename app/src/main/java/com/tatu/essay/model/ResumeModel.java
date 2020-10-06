package com.tatu.essay.model;

import java.util.Date;

/**
 * @author cullen
 * @title: Resume
 * @projectName essay_android
 * @description: TODO
 * @date 2020/10/320:15
 */
public class ResumeModel {
    private Long id;
    /**
     *
     */
    private Long userId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 职业
     */
    private String profession;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 网址
     */
    private String website;
    /**
     * 简介
     */
    private String summary;
    /**
     * 兴趣爱好
     */
    private String interests;
    /**
     * 语言
     */
    private String language;
    /**
     * 奖项
     */
    private String awards;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     *
     */
    private String phone;
    /**
     * git地址
     */
    private String git;

    private String  urlName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }
}
