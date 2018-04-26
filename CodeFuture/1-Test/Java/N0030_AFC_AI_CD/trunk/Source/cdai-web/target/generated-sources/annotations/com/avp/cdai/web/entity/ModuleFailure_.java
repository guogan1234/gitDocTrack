package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ModuleFailure.class)
public abstract class ModuleFailure_ {

	public static volatile SingularAttribute<ModuleFailure, Date> insertTime;
	public static volatile SingularAttribute<ModuleFailure, Integer> failureNum;
	public static volatile SingularAttribute<ModuleFailure, Integer> lineId;
	public static volatile SingularAttribute<ModuleFailure, Integer> id;
	public static volatile SingularAttribute<ModuleFailure, Integer> moduleId;
	public static volatile SingularAttribute<ModuleFailure, String> tagName;
	public static volatile SingularAttribute<ModuleFailure, Date> timestamp;

}

