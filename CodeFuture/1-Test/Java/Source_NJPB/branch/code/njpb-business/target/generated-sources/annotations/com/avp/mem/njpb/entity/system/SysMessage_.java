package com.avp.mem.njpb.entity.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SysMessage.class)
public abstract class SysMessage_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<SysMessage, String> messageText;
	public static volatile SingularAttribute<SysMessage, String> messageFile3Url;
	public static volatile SingularAttribute<SysMessage, String> messageTitle;
	public static volatile SingularAttribute<SysMessage, String> messageAuthor;
	public static volatile SingularAttribute<SysMessage, String> messageFile1Url;
	public static volatile SingularAttribute<SysMessage, String> messageFile2Url;
	public static volatile SingularAttribute<SysMessage, Integer> status;

}

