package com.avp.mem.njpb.entity.workorder;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjWorkOrder.class)
public abstract class ObjWorkOrder_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjWorkOrder, Double> workOrderScore;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> estateId;
	public static volatile SingularAttribute<ObjWorkOrder, Double> latitude;
	public static volatile SingularAttribute<ObjWorkOrder, Date> responseTimeOutDate;
	public static volatile SingularAttribute<ObjWorkOrder, String> maintainRemark;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> reportEmployee;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> assignEmployee;
	public static volatile SingularAttribute<ObjWorkOrder, String> backRemark;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> checkEmployee;
	public static volatile SingularAttribute<ObjWorkOrder, String> repairRemark;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> reponseOverTime;
	public static volatile SingularAttribute<ObjWorkOrder, String> scoreDeductRemark;
	public static volatile SingularAttribute<ObjWorkOrder, String> closeRemark;
	public static volatile SingularAttribute<ObjWorkOrder, String> assignRemark;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> stationId;
	public static volatile SingularAttribute<ObjWorkOrder, Double> longitude;
	public static volatile SingularAttribute<ObjWorkOrder, String> processInstanceId;
	public static volatile SingularAttribute<ObjWorkOrder, Date> repairConfirmTime;
	public static volatile SingularAttribute<ObjWorkOrder, Date> repairStartTime;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> level;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> reportWay;
	public static volatile SingularAttribute<ObjWorkOrder, Date> repairTimeOutDate;
	public static volatile SingularAttribute<ObjWorkOrder, Double> workOrderScoreDeduct;
	public static volatile SingularAttribute<ObjWorkOrder, String> serialNo;
	public static volatile SingularAttribute<ObjWorkOrder, Date> repairEndTime;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> workOrderSource;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> repairEmployee;
	public static volatile SingularAttribute<ObjWorkOrder, Date> repairBackTime;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> statusId;
	public static volatile SingularAttribute<ObjWorkOrder, String> repairBackRemark;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> faultId;
	public static volatile SingularAttribute<ObjWorkOrder, String> faultDescription;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> typeId;
	public static volatile SingularAttribute<ObjWorkOrder, Boolean> fixed;
	public static volatile SingularAttribute<ObjWorkOrder, String> location;
	public static volatile SingularAttribute<ObjWorkOrder, Date> assignTime;
	public static volatile SingularAttribute<ObjWorkOrder, Integer> repairOverTime;
	public static volatile SingularAttribute<ObjWorkOrder, Date> reportTime;

}

