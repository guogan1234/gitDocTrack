package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StationCumulativePredict.class)
public abstract class StationCumulativePredict_ {

	public static volatile SingularAttribute<StationCumulativePredict, Date> insertTime;
	public static volatile SingularAttribute<StationCumulativePredict, Integer> lineId;
	public static volatile SingularAttribute<StationCumulativePredict, Integer> flowCount;
	public static volatile SingularAttribute<StationCumulativePredict, Integer> section;
	public static volatile SingularAttribute<StationCumulativePredict, Integer> id;
	public static volatile SingularAttribute<StationCumulativePredict, Integer> stationId;
	public static volatile SingularAttribute<StationCumulativePredict, Integer> direction;
	public static volatile SingularAttribute<StationCumulativePredict, Date> timestamp;

}

