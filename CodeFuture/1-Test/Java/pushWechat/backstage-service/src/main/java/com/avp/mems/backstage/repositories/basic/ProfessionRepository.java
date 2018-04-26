package com.avp.mems.backstage.repositories.basic;

import com.avp.mems.backstage.entity.basic.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber Wang on 2017-05-27 下午 07:20
 */
public interface ProfessionRepository extends JpaRepository<Profession, Long>{
    List findByIdIn(@Param("ids") List<Integer> ids);
}
