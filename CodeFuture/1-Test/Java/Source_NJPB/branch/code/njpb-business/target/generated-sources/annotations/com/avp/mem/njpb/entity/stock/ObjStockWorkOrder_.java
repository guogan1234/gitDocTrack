package com.avp.mem.njpb.entity.stock;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjStockWorkOrder.class)
public abstract class ObjStockWorkOrder_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjStockWorkOrder, Integer> stockWorkOrderStatusId;
	public static volatile SingularAttribute<ObjStockWorkOrder, String> applyRemark;
	public static volatile SingularAttribute<ObjStockWorkOrder, Integer> stockWorkOrderTypeId;
	public static volatile SingularAttribute<ObjStockWorkOrder, Integer> processUserId;
	public static volatile SingularAttribute<ObjStockWorkOrder, String> stockProcessInstanceId;
	public static volatile SingularAttribute<ObjStockWorkOrder, String> rejectRemark;
	public static volatile SingularAttribute<ObjStockWorkOrder, Date> confirmTime;
	public static volatile SingularAttribute<ObjStockWorkOrder, Date> applyTime;
	public static volatile SingularAttribute<ObjStockWorkOrder, Date> rejectTime;
	public static volatile SingularAttribute<ObjStockWorkOrder, Integer> applyUserId;
	public static volatile SingularAttribute<ObjStockWorkOrder, String> serialNo;

}

