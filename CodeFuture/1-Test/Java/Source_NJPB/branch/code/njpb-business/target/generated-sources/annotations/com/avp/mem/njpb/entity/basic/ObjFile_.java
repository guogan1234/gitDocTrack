package com.avp.mem.njpb.entity.basic;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjFile.class)
public abstract class ObjFile_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjFile, String> fileName;
	public static volatile SingularAttribute<ObjFile, String> thumbnail;
	public static volatile SingularAttribute<ObjFile, Integer> fileStatus;
	public static volatile SingularAttribute<ObjFile, Integer> fileSize;
	public static volatile SingularAttribute<ObjFile, String> fileUrl;
	public static volatile SingularAttribute<ObjFile, String> fileType;
	public static volatile SingularAttribute<ObjFile, String> fileId;

}

