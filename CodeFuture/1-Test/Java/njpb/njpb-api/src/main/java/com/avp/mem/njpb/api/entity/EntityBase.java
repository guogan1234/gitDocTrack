package com.avp.mem.njpb.api.entity;

/**
 * Created by boris feng on 2017/6/8.
 */
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(value = {com.avp.mem.njpb.api.entity.EntityBaseEventListener.class})
public abstract class EntityBase implements Serializable {
    @Id
    @Column(
            name = "id", unique = true, nullable = false
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private Date createTime;
    private Integer createBy;
    private Date lastUpdateTime;
    private Integer lastUpdateBy;
    private Date removeTime;

    public EntityBase() {
    }


    public EntityBase(Date createTime, Integer createBy, Date lastUpdateTime, Integer lastUpdateBy, Date removeTime) {
        this.createTime = createTime;
        this.createBy = createBy;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.removeTime = removeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getRemoveTime() {
        return removeTime;
    }

    public void setRemoveTime(Date removeTime) {
        this.removeTime = removeTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
}