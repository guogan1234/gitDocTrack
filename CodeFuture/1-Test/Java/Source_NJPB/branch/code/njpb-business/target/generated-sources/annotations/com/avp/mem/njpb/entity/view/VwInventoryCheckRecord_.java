package com.avp.mem.njpb.entity.view;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VwInventoryCheckRecord.class)
public abstract class VwInventoryCheckRecord_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<VwInventoryCheckRecord, Integer> corpId;
	public static volatile SingularAttribute<VwInventoryCheckRecord, Date> checkTime;
	public static volatile SingularAttribute<VwInventoryCheckRecord, String> stationNo;
	public static volatile SingularAttribute<VwInventoryCheckRecord, String> stationNoName;
	public static volatile SingularAttribute<VwInventoryCheckRecord, Integer> count;
	public static volatile SingularAttribute<VwInventoryCheckRecord, Integer> checkUserId;
	public static volatile SingularAttribute<VwInventoryCheckRecord, String> checkUserName;
	public static volatile SingularAttribute<VwInventoryCheckRecord, String> stationName;
	public static volatile SingularAttribute<VwInventoryCheckRecord, String> corpName;
	public static volatile SingularAttribute<VwInventoryCheckRecord, Integer> paramVersion;
	public static volatile SingularAttribute<VwInventoryCheckRecord, String> checkRemark;
	public static volatile SingularAttribute<VwInventoryCheckRecord, Integer> stationId;

}

