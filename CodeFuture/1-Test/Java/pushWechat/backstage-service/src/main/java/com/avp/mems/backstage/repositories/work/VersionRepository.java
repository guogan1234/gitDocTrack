/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.repositories.work;

import com.avp.mems.backstage.entity.work.Version;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pw on 2017/5/22.
 */
public interface VersionRepository extends JpaRepository<Version, Long> {
    
}
