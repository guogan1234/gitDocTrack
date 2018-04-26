package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DeviceDownTime.class)
public abstract class DeviceDownTime_ {

	public static volatile SingularAttribute<DeviceDownTime, Date> insertTime;
	public static volatile SingularAttribute<DeviceDownTime, Double> rate;
	public static volatile SingularAttribute<DeviceDownTime, Integer> lineId;
	public static volatile SingularAttribute<DeviceDownTime, Integer> normalCount;
	public static volatile SingularAttribute<DeviceDownTime, Integer> id;
	public static volatile SingularAttribute<DeviceDownTime, Integer> deviceId;
	public static volatile SingularAttribute<DeviceDownTime, Integer> failureCount;
	public static volatile SingularAttribute<DeviceDownTime, Integer> stationId;
	public static volatile SingularAttribute<DeviceDownTime, Date> timestamp;

}

