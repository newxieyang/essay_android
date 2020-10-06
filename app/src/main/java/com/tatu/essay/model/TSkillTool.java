package com.tatu.essay.model;


/**
 *
 */

public class TSkillTool {
    /**
     *
     */
    private Integer id;
    /**
     * 前端1,后端2,其他3
     */
    private Integer type;
    /**
     *
     */
    private String name;
    /**
     * 熟练程度0到100
     */
    private Integer proficiency;
    /**
     *
     */
    private Long userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProficiency() {
        return proficiency;
    }

    public void setProficiency(Integer proficiency) {
        this.proficiency = proficiency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}