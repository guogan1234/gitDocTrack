package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FailureReason.class)
public abstract class FailureReason_ {

	public static volatile SingularAttribute<FailureReason, Integer> deviceType;
	public static volatile SingularAttribute<FailureReason, Date> insertTime;
	public static volatile SingularAttribute<FailureReason, Integer> transCount;
	public static volatile SingularAttribute<FailureReason, Integer> lineId;
	public static volatile SingularAttribute<FailureReason, Integer> normalCount;
	public static volatile SingularAttribute<FailureReason, Integer> id;
	public static volatile SingularAttribute<FailureReason, Integer> moduleId;
	public static volatile SingularAttribute<FailureReason, Integer> deviceId;
	public static volatile SingularAttribute<FailureReason, Integer> failureCount;
	public static volatile SingularAttribute<FailureReason, Integer> stationId;
	public static volatile SingularAttribute<FailureReason, Date> timestamp;

}

