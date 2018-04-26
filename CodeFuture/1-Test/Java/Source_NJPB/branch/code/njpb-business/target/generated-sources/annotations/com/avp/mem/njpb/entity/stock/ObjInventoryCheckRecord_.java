package com.avp.mem.njpb.entity.stock;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjInventoryCheckRecord.class)
public abstract class ObjInventoryCheckRecord_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjInventoryCheckRecord, Integer> corpId;
	public static volatile SingularAttribute<ObjInventoryCheckRecord, Date> checkTime;
	public static volatile SingularAttribute<ObjInventoryCheckRecord, Integer> count;
	public static volatile SingularAttribute<ObjInventoryCheckRecord, Integer> checkUserId;
	public static volatile SingularAttribute<ObjInventoryCheckRecord, Integer> paramVersion;
	public static volatile SingularAttribute<ObjInventoryCheckRecord, String> checkRemark;
	public static volatile SingularAttribute<ObjInventoryCheckRecord, Integer> stationId;

}

