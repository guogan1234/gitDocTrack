package com.avp.mem.njpb.entity.stock;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VwStockWorkOrderDetail.class)
public abstract class VwStockWorkOrderDetail_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> estateTypeName;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> stockWorkOrderStatusId;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> corpId;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> stockWorkOrderTypeName;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> stockWorkOrderTypeId;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> count;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> corpName;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> rejectRemark;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Date> confirmTime;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Date> rejectTime;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> partsType;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> applyUserId;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> serialNo;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> applyUserName;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> stockWorkOrderId;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> applyRemark;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, String> processUserName;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> processUserId;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> estateTypeId;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Integer> category;
	public static volatile SingularAttribute<VwStockWorkOrderDetail, Date> applyTime;

}

