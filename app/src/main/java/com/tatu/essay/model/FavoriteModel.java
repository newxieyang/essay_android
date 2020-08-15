package com.tatu.essay.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author cullen
 * @title: FavoriteModel
 * @projectName essay_android
 * @description: TODO
 * @date 2020/8/1217:13
 */

@Entity
public class FavoriteModel implements Serializable {


    static final long serialVersionUID  = 1L;


    @Id(autoincrement = false)
    @Property(nameInDb = "ID")
    private Long id;

    @Property(nameInDb = "essayId")
    private Long essayId;

    @Property(nameInDb = "userId")
    private Long userId;


    @Generated(hash = 410243737)
    public FavoriteModel(Long id, Long essayId, Long userId) {
        this.id = id;
        this.essayId = essayId;
        this.userId = userId;
    }

    @Generated(hash = 1709162642)
    public FavoriteModel() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEssayId() {
        return essayId;
    }

    public void setEssayId(Long essayId) {
        this.essayId = essayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
