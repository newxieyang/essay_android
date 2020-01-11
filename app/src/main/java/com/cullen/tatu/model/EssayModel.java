package com.cullen.tatu.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;


@Entity
public class EssayModel implements Serializable {

    static final long serialVersionUID  = 1L;

    @Id(autoincrement = false)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "TITLE")
    private String title;
    @Property(nameInDb = "CONTENT")
    private String content;
    @Property(nameInDb = "CREATEBY")
    private Long createBy;
    @Property(nameInDb = "CREATETIME")
    private Long createTime;
    @Property(nameInDb = "STATE")
    private Integer state;

    @Generated(hash = 1460651816)
    public EssayModel(Long id, String title, String content, Long createBy,
            Long createTime, Integer state) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createBy = createBy;
        this.createTime = createTime;
        this.state = state;
    }

    @Generated(hash = 1112032825)
    public EssayModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
