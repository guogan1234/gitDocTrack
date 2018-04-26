package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TagFailureDetail.class)
public abstract class TagFailureDetail_ {

	public static volatile SingularAttribute<TagFailureDetail, Date> insertTime;
	public static volatile SingularAttribute<TagFailureDetail, Integer> failureNum;
	public static volatile SingularAttribute<TagFailureDetail, Integer> lineId;
	public static volatile SingularAttribute<TagFailureDetail, Integer> id;
	public static volatile SingularAttribute<TagFailureDetail, String> tagName;
	public static volatile SingularAttribute<TagFailureDetail, Integer> deviceId;
	public static volatile SingularAttribute<TagFailureDetail, Date> timestamp;

}

