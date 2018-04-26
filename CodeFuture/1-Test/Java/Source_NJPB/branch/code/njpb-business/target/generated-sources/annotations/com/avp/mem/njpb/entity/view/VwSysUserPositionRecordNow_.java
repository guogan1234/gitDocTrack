package com.avp.mem.njpb.entity.view;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VwSysUserPositionRecordNow.class)
public abstract class VwSysUserPositionRecordNow_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<VwSysUserPositionRecordNow, Integer> createBy;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, Integer> corpId;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, Timestamp> createTime;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, Double> latitude;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, String> location;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, String> corpName;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, String> userName;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, Integer> userId;
	public static volatile SingularAttribute<VwSysUserPositionRecordNow, Double> longitude;

}

