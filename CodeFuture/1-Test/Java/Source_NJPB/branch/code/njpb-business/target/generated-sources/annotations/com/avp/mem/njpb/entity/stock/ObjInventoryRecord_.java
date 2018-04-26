package com.avp.mem.njpb.entity.stock;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjInventoryRecord.class)
public abstract class ObjInventoryRecord_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjInventoryRecord, Integer> corpId;
	public static volatile SingularAttribute<ObjInventoryRecord, Integer> count;
	public static volatile SingularAttribute<ObjInventoryRecord, Integer> stockId;
	public static volatile SingularAttribute<ObjInventoryRecord, Integer> operationType;
	public static volatile SingularAttribute<ObjInventoryRecord, Integer> estateTypeId;
	public static volatile SingularAttribute<ObjInventoryRecord, Integer> operator;

}

