package com.avp.mems.backstage.resource.basic;

import com.avp.mems.backstage.entity.basic.Profession;
import com.avp.mems.backstage.repositories.basic.ProfessionRepository;
import com.avp.mems.backstage.repositories.user.UserProfessionRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amber Wang on 2017-05-29 下午 06:45.
 */
@RestController
public class ProfessionController {
    @Autowired
    private UserProfessionRepository userProfessionRepository;

    @Autowired
    private ProfessionRepository professionRepository;

    @RequestMapping(value = "professionses/search/findByUserName", method = RequestMethod.GET)
    ResponseEntity findByUserName() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        String userName = SecurityUtil.getLoginUserName();
        List<Profession> professions = new ArrayList<>();
        //根据专业获取用户
        List<Integer> professionIds = userProfessionRepository.findByUserName(userName);

        professions = professionRepository.findByIdIn(professionIds);
        builder.setResultEntity(professions, ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }

    @RequestMapping(value="/userWechats/search/findAllProfession")
    ResponseEntity findAllProfession(){
        ResponseBuilder builder = new ResponseBuilder();
        List<Profession> professionList = new ArrayList<Profession>();
        professionList = professionRepository.findAll();
        builder.setResultEntity(professionList,ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }
}
