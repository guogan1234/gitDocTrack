package com.avp.mems.backstage.service.basic.impl;


import com.avp.mems.backstage.entity.basic.City;
import com.avp.mems.backstage.entity.basic.Profession;
import com.avp.mems.backstage.repositories.basic.CityRepository;
import com.avp.mems.backstage.repositories.basic.ProfessionRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.rest.RestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by boris feng on 2017/5/25.
 */
@RestController
public class HelloWorld {
    @Autowired
    ProfessionRepository professionRepository;
//    @Autowired
//    CityRepository cityRepository;
//
//    @Autowired
//    ProjectRepository projectRepository;

//    @RequestMapping("sayHello")
//    @Transactional
//    ResponseEntity<RestBody<City>> sayHello(HttpServletRequest request) {
//        List<City> cities =  cityRepository.findAll();
//        List<Project> projects = projectRepository.findAll();
//
//        for(City city : cities) {
//            if (city.getId().equals(1L)) {
//                //city.setProvince(-1L);//null
//                //cityRepository.save(city);
//            }
////            if(city.getProvince() != null && city.getProvince().equals(-1L)) {
////                city.setProvince(null);
////                cityRepository.save(city);
////            }
//        }
//
//
//        for(Project project : projects) {
//            if (project.getId().equals(1L)) {
//                //project.setResponseTimeout(-1L);//5
//                //projectRepository.save(project);
//            }
////            if(project.getResponseTimeout() != null && project.getResponseTimeout().equals(-1L)) {
////                project.setResponseTimeout(5L);
////                projectRepository.save(project);
////            }
//        }
//
//        //throw new RuntimeException("db failed");
//        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//
//        ResponseBuilder builder =  ResponseBuilder.createBuilder();
//
//        ;
//        //builder.setErrorCode(ResponseCode.ALREADY_EXIST);
//        return builder.setResultEntity(new City()).getResponseEntity();
//    }
//
//    @RequestMapping("sayPage")
//    @Transactional
//    ResponseEntity<RestBody<City>> sayHello(Pageable page) {
//        Page<City> r = cityRepository.findAll(page);
//        return ResponseBuilder.createBuilder().setResultEntity(r).getResponseEntity();
//    }

    @RequestMapping("sayList")
    @Transactional
    ResponseEntity<RestBody<Profession>> sayHello() {
        List<Profession> r = professionRepository.findAll();
        return ResponseBuilder.createBuilder().setResultEntity(r).getResponseEntity();
    }
}
