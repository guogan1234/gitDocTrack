
package com.avp.mems.backstage.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import lombok.Data;

/**
 * Created by Amber Wang on 2017-05-27
 */

@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Role() {
    }

    public Role(final String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
