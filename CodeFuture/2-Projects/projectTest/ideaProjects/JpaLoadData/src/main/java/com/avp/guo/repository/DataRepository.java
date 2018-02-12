package com.avp.guo.repository;

import com.avp.guo.entity.DataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by guo on 2018/2/7.
 */
public interface DataRepository extends JpaRepository<DataModel,Integer>{
    @Override
    Page<DataModel> findAll(Pageable pageable);
}
