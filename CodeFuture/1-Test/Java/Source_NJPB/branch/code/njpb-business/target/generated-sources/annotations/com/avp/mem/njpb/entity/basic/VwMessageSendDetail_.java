package com.avp.mem.njpb.entity.basic;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VwMessageSendDetail.class)
public abstract class VwMessageSendDetail_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<VwMessageSendDetail, Integer> sendUserId;
	public static volatile SingularAttribute<VwMessageSendDetail, String> messageText;
	public static volatile SingularAttribute<VwMessageSendDetail, Integer> receiveUserId;
	public static volatile SingularAttribute<VwMessageSendDetail, Integer> sysMessageId;
	public static volatile SingularAttribute<VwMessageSendDetail, String> author;
	public static volatile SingularAttribute<VwMessageSendDetail, String> sendUserName;
	public static volatile SingularAttribute<VwMessageSendDetail, String> messageTitle;
	public static volatile SingularAttribute<VwMessageSendDetail, String> messageAuthor;
	public static volatile SingularAttribute<VwMessageSendDetail, String> receiveUserName;
	public static volatile SingularAttribute<VwMessageSendDetail, Integer> status;

}

