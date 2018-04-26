package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StationFailed.class)
public abstract class StationFailed_ {

	public static volatile SingularAttribute<StationFailed, Date> insertTime;
	public static volatile SingularAttribute<StationFailed, Integer> failedNum;
	public static volatile SingularAttribute<StationFailed, Integer> lineId;
	public static volatile SingularAttribute<StationFailed, Integer> id;
	public static volatile SingularAttribute<StationFailed, String> tagName;
	public static volatile SingularAttribute<StationFailed, Integer> stationId;
	public static volatile SingularAttribute<StationFailed, Date> timestamp;

}

