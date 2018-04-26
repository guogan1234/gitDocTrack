package com.avp.mems.backstage.resource.basic;

import com.avp.mems.backstage.entity.basic.FaultDescription;
import com.avp.mems.backstage.repositories.basic.FaultDescriptionRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.rest.RestBody;
import com.avp.mems.backstage.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhoujs on 2017/5/26.
 */
@RestController
public class FaultDescriptionController {
    @Autowired
    private FaultDescriptionRepository faultDescriptionRepository;

    @RequestMapping("faultDescriptions/search/findByComponentTypeId")
    ResponseEntity<RestBody<FaultDescription>> getFaultDescriptionListByComponentTypeId(@Param("componentTypeId") Long componentTypeId, HttpServletRequest request) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        if (Validator.isNotNull(componentTypeId)) {
            List<FaultDescription> faultDescriptionList = faultDescriptionRepository.findByComponentTypeId(componentTypeId);
            builder.setResultEntity(faultDescriptionList, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }
        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }
}
