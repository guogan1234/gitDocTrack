package com.avp.mem.njpb.entity.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SysUserPointRecord.class)
public abstract class SysUserPointRecord_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<SysUserPointRecord, Integer> pointRecordLevel;
	public static volatile SingularAttribute<SysUserPointRecord, String> pointChange;
	public static volatile SingularAttribute<SysUserPointRecord, String> currentDateStr;
	public static volatile SingularAttribute<SysUserPointRecord, Integer> pointSource;
	public static volatile SingularAttribute<SysUserPointRecord, Integer> workOrderId;
	public static volatile SingularAttribute<SysUserPointRecord, Integer> userId;

}

