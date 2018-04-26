package com.avp.mem.njpb.entity.view;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VwWorkOrderHistory.class)
public abstract class VwWorkOrderHistory_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<VwWorkOrderHistory, String> txt;
	public static volatile SingularAttribute<VwWorkOrderHistory, Integer> operationTypeId;
	public static volatile SingularAttribute<VwWorkOrderHistory, Double> workOrderScore;
	public static volatile SingularAttribute<VwWorkOrderHistory, Date> lastOperationTime;
	public static volatile SingularAttribute<VwWorkOrderHistory, Double> latitude;
	public static volatile SingularAttribute<VwWorkOrderHistory, Integer> workOrderId;
	public static volatile SingularAttribute<VwWorkOrderHistory, String> userName;
	public static volatile SingularAttribute<VwWorkOrderHistory, Integer> operatorId;
	public static volatile SingularAttribute<VwWorkOrderHistory, Double> workOrderScoreDeduct;
	public static volatile SingularAttribute<VwWorkOrderHistory, String> operationRemark;
	public static volatile SingularAttribute<VwWorkOrderHistory, String> operationTime;
	public static volatile SingularAttribute<VwWorkOrderHistory, Double> longitude;

}

