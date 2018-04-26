package com.avp.mem.njpb.entity.stock;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VwStockWorkOrder.class)
public abstract class VwStockWorkOrder_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<VwStockWorkOrder, Integer> stockWorkOrderStatusId;
	public static volatile SingularAttribute<VwStockWorkOrder, Integer> corpId;
	public static volatile SingularAttribute<VwStockWorkOrder, Integer> stockWorkOrderTypeId;
	public static volatile SingularAttribute<VwStockWorkOrder, String> corpName;
	public static volatile SingularAttribute<VwStockWorkOrder, String> rejectRemark;
	public static volatile SingularAttribute<VwStockWorkOrder, Date> confirmTime;
	public static volatile SingularAttribute<VwStockWorkOrder, Date> rejectTime;
	public static volatile SingularAttribute<VwStockWorkOrder, Integer> applyUserId;
	public static volatile SingularAttribute<VwStockWorkOrder, String> serialNo;
	public static volatile SingularAttribute<VwStockWorkOrder, String> applyUserName;
	public static volatile SingularAttribute<VwStockWorkOrder, String> stockWorkOrderStatusNameCn;
	public static volatile SingularAttribute<VwStockWorkOrder, String> applyRemark;
	public static volatile SingularAttribute<VwStockWorkOrder, String> processUserName;
	public static volatile SingularAttribute<VwStockWorkOrder, Integer> processUserId;
	public static volatile SingularAttribute<VwStockWorkOrder, Date> applyTime;
	public static volatile SingularAttribute<VwStockWorkOrder, Integer> operationResult;

}

