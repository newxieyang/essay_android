package com.tatu.essay.model;



/**
 *
 */
public class TEducation {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Long userId;
    /**
     * 学校 名称
     */
    private String name;
    /**
     * 专业
     */
    private String professional;
    /**
     *
     */
    private Long startDate;
    /**
     *
     */
    private Long endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
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
}