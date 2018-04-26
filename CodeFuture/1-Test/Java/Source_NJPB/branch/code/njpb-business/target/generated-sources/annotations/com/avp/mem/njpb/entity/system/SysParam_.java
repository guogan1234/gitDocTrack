package com.avp.mem.njpb.entity.system;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SysParam.class)
public abstract class SysParam_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelThreeResponseTime;
	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelOneRepairTime;
	public static volatile SingularAttribute<SysParam, Date> stockCheckEndTime;
	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelEmergencyRepairTime;
	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelEmergencyResponseTime;
	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelTwoResponseTime;
	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelTwoRepairTime;
	public static volatile SingularAttribute<SysParam, String> workPeriod;
	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelOneResponseTime;
	public static volatile SingularAttribute<SysParam, Date> stockCheckStartTime;
	public static volatile SingularAttribute<SysParam, Integer> workOrderLevelThreeRepairTime;
	public static volatile SingularAttribute<SysParam, Integer> stockCheckVersion;
	public static volatile SingularAttribute<SysParam, Date> workEndTime;
	public static volatile SingularAttribute<SysParam, Date> workStartTime;

}

