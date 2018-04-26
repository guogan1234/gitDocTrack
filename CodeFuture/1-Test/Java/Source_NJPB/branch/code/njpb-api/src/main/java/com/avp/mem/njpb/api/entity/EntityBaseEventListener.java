package com.avp.mem.njpb.api.entity;


import com.avp.mem.njpb.api.util.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by boris feng on 2017/6/20.
 */
@Component
public class EntityBaseEventListener {
    @PrePersist
    public void prePersist(EntityBase entity) {
        Integer uid = SecurityUtils.getLoginUserId();
        entity.setCreateBy(uid);
        entity.setCreateTime(new Date());
        entity.setLastUpdateBy(uid);
        entity.setLastUpdateTime(new Date());
    }

    @PreUpdate
    public void preUpdate(EntityBase entity) {
        entity.setLastUpdateBy(SecurityUtils.getLoginUserId());
        entity.setLastUpdateTime(new Date());
    }
}
