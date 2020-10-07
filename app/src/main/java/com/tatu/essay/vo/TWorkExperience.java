package com.tatu.essay.vo;



/**
 *
 */
public class TWorkExperience {
    /**
     *
     */
    private Integer id;
    /**
     * 开始时间
     */
    private Long startDate;
    /**
     * 结束时间
     */
    private Long endDate;
    /**
     * 公司
     */
    private String company;
    /**
     * 职位
     */
    private String profession;
    /**
     * 简介
     */
    private String summary;
    /**
     * 成就
     */
    private String achievements;
    /**
     * 采用的技术
     */
    private String technologies;
    /**
     * 用户id
     */
    private Long userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}