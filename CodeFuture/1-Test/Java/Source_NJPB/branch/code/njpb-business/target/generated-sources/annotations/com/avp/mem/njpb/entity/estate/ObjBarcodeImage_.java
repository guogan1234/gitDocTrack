package com.avp.mem.njpb.entity.estate;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ObjBarcodeImage.class)
public abstract class ObjBarcodeImage_ extends com.avp.mem.njpb.api.entity.EntityBase_ {

	public static volatile SingularAttribute<ObjBarcodeImage, String> barCodePath;
	public static volatile SingularAttribute<ObjBarcodeImage, String> barCodeSn;
	public static volatile SingularAttribute<ObjBarcodeImage, Date> exportTime;
	public static volatile SingularAttribute<ObjBarcodeImage, String> barCodeMessage;
	public static volatile SingularAttribute<ObjBarcodeImage, Date> activateTime;
	public static volatile SingularAttribute<ObjBarcodeImage, Integer> barCodeCategory;
	public static volatile SingularAttribute<ObjBarcodeImage, Integer> relation;

}

