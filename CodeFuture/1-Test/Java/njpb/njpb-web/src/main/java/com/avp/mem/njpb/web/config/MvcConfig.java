package com.avp.mem.njpb.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author Hongfei
 */
@Configuration
@SessionAttributes("authorizationRequest")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // main -- dashboard
        registry.addViewController("/bts").setViewName("btshm/main");
        registry.addViewController("/index.html").setViewName("btshm/main");

        // monitor
        registry.addViewController("/monitor/alarm").setViewName("btshm/monitor/alarm");
        registry.addViewController("/monitor/command").setViewName("btshm/monitor/command");

        //baisc
//        registry.addViewController("/basic/logonType").setViewName("btshm/basic/logonType");
//        registry.addViewController("/basic/checkType").setViewName("btshm/basic/checkType");
//        registry.addViewController("/basic/credentials").setViewName("btshm/basic/credentials");
//        registry.addViewController("/basic/transType").setViewName("btshm/basic/transType");

        registry.addViewController("/basic/supplier").setViewName("btshm/basic/supplier");
        registry.addViewController("/basic/estateSpec").setViewName("btshm/basic/estateSpec");
        registry.addViewController("/basic/estateType").setViewName("btshm/basic/estateType");

        registry.addViewController("/basic/estate").setViewName("btshm/basic/estate");
        registry.addViewController("/basic/station").setViewName("btshm/basic/station");
        registry.addViewController("/basic/corp").setViewName("btshm/basic/corp");

        // report
        registry.addViewController("/report/faultStat").setViewName("btshm/report/faultStat");
        registry.addViewController("/report/logger").setViewName("btshm/report/logger");
        registry.addViewController("/report/passengerFlow").setViewName("btshm/report/passengerFlow");
        registry.addViewController("/report/trans").setViewName("btshm/report/trans");

        // system
        registry.addViewController("/system/user").setViewName("btshm/system/user");
        registry.addViewController("/system/role").setViewName("btshm/system/role");

        // eod
        registry.addViewController("/eod/version").setViewName("btshm/eod/version");
        registry.addViewController("/eod/issuance").setViewName("btshm/eod/issuance");
//        registry.addViewController("/eod/servicePack").setViewName("btshm/eod/servicePack");
//        registry.addViewController("/eod/operator").setViewName("btshm/eod/operator");
//        registry.addViewController("/eod/operation").setViewName("btshm/eod/operation");
//        registry.addViewController("/eod/protocol").setViewName("btshm/eod/protocol");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }
}
