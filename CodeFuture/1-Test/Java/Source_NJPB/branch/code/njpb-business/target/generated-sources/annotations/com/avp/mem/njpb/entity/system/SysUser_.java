package com.avp.mem.njpb.entity.system;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SysUser.class)
public abstract class SysUser_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<SysUser, String> userQq;
	public static volatile SingularAttribute<SysUser, String> userWechart;
	public static volatile SingularAttribute<SysUser, String> userPassword;
	public static volatile SingularAttribute<SysUser, Integer> userStatus;
	public static volatile SingularAttribute<SysUser, Integer> corpId;
	public static volatile SingularAttribute<SysUser, String> userPhone;
	public static volatile SingularAttribute<SysUser, Integer> whitelist;
	public static volatile SingularAttribute<SysUser, String> userName;
	public static volatile SingularAttribute<SysUser, String> userAccount;
	public static volatile SingularAttribute<SysUser, Date> expiryTime;
	public static volatile SingularAttribute<SysUser, String> userEmail;
	public static volatile SingularAttribute<SysUser, Integer> userType;
	public static volatile SingularAttribute<SysUser, Integer> userGroup;
	public static volatile SingularAttribute<SysUser, String> userSurname;

}

