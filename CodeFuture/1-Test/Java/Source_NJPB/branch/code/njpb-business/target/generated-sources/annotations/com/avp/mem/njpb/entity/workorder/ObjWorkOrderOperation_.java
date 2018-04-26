package com.avp.mem.njpb.entity.workorder;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjWorkOrderOperation.class)
public abstract class ObjWorkOrderOperation_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjWorkOrderOperation, Integer> operationTypeId;
	public static volatile SingularAttribute<ObjWorkOrderOperation, Double> workOrderScore;
	public static volatile SingularAttribute<ObjWorkOrderOperation, Date> lastOperationTime;
	public static volatile SingularAttribute<ObjWorkOrderOperation, Double> latitude;
	public static volatile SingularAttribute<ObjWorkOrderOperation, String> location;
	public static volatile SingularAttribute<ObjWorkOrderOperation, Integer> workOrderId;
	public static volatile SingularAttribute<ObjWorkOrderOperation, Integer> operatorId;
	public static volatile SingularAttribute<ObjWorkOrderOperation, Double> workOrderScoreDeduct;
	public static volatile SingularAttribute<ObjWorkOrderOperation, String> operationRemark;
	public static volatile SingularAttribute<ObjWorkOrderOperation, Double> longitude;

}

