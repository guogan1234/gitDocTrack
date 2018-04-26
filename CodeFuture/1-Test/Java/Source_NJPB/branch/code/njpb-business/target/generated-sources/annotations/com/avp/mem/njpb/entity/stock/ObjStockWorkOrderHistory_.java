package com.avp.mem.njpb.entity.stock;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjStockWorkOrderHistory.class)
public abstract class ObjStockWorkOrderHistory_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjStockWorkOrderHistory, Integer> stockWorkOrderStatusId;
	public static volatile SingularAttribute<ObjStockWorkOrderHistory, Integer> stockWorkOrderId;
	public static volatile SingularAttribute<ObjStockWorkOrderHistory, Integer> processUserId;
	public static volatile SingularAttribute<ObjStockWorkOrderHistory, Date> lastOperationTime;
	public static volatile SingularAttribute<ObjStockWorkOrderHistory, Date> operationTime;

}

