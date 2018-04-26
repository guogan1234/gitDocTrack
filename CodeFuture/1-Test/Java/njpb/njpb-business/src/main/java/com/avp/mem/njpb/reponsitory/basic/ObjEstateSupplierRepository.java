package com.avp.mem.njpb.reponsitory.basic;

import com.avp.mem.njpb.entity.ObjEstateSupplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/19.
 */
public interface ObjEstateSupplierRepository extends CrudRepository<ObjEstateSupplier,Integer> {

    List<ObjEstateSupplier> findByRemoveTimeIsNull();

    //根据供应商名称查询
    ObjEstateSupplier findBySupplierName(@Param("supplierName") String supplierName);

    //根据供应商ids查询供应商
    List<ObjEstateSupplier> findByIdIn(@Param("ids") List<Integer> ids);





}
