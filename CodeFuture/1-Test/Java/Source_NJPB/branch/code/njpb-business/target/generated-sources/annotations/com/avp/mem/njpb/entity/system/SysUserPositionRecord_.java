package com.avp.mem.njpb.entity.system;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SysUserPositionRecord.class)
public abstract class SysUserPositionRecord_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<SysUserPositionRecord, Date> recordTime;
	public static volatile SingularAttribute<SysUserPositionRecord, Integer> positionSource;
	public static volatile SingularAttribute<SysUserPositionRecord, Double> latitude;
	public static volatile SingularAttribute<SysUserPositionRecord, String> location;
	public static volatile SingularAttribute<SysUserPositionRecord, Integer> workOrderId;
	public static volatile SingularAttribute<SysUserPositionRecord, Integer> userId;
	public static volatile SingularAttribute<SysUserPositionRecord, Double> longitude;

}

