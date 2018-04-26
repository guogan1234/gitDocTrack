package com.avp.mem.njpb.entity.stock;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VwStockWorkOrderHistory.class)
public abstract class VwStockWorkOrderHistory_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<VwStockWorkOrderHistory, Integer> stockWorkOrderStatusId;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Integer> stockWorkOrderTypeId;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Date> lastOperationTime;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, String> typeName;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, String> rejectRemark;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Date> confirmTime;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Date> rejectTime;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Integer> applyUserId;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Date> operationTime;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, String> serialNo;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, String> applyUserName;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Integer> stockWorkOrderId;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, String> applyRemark;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, String> processUserName;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Integer> processUserId;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, String> statusName;
	public static volatile SingularAttribute<VwStockWorkOrderHistory, Date> applyTime;

}

