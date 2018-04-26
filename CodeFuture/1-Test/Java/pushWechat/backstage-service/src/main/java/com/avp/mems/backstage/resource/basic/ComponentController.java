package com.avp.mems.backstage.resource.basic;

import com.avp.mems.backstage.entity.basic.Component;
import com.avp.mems.backstage.repositories.basic.ComponentRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.rest.RestBody;
import com.avp.mems.backstage.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhoujs on 2017/5/26.
 */
@RestController
public class ComponentController {

    @Autowired
    private ComponentRepository ComponentRepository;

    @RequestMapping(value = "components/search/findByEquipmentId",method = RequestMethod.GET)
    ResponseEntity getComponentListByEquipmentId(@Param("equipmentId") Long equipmentId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        if (Validator.isNotNull(equipmentId)) {
            List<Component> componentList = ComponentRepository.findByEquipmentId(equipmentId);
            builder.setResultEntity(componentList, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }
        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }
}
