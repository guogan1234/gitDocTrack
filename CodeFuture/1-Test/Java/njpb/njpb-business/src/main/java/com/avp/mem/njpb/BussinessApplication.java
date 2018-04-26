package com.avp.mem.njpb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by Amber Wang on 2017-07-04 下午 04:03.
 */
@SpringBootApplication
@EnableResourceServer
//@ComponentScan("com.avp.bts.web.*")
public class BussinessApplication {

    private static final Logger logger = LoggerFactory.getLogger(BussinessApplication.class);
//    @Autowired
//    private PersonRepository personRepository;
//
//    @Autowired
//    private CompRepository compRepository;

    public static void main(String[] args) {
        SpringApplication.run(BussinessApplication.class, args);
//        ApplicationContext ctx = SpringApplication.run(ActivitiApplication.class, args);
////
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            logger.debug(beanName);
//        }
    }

//    //初始化模拟数据
//    @Bean
//    public CommandLineRunner init(final ActivitiService myService) {
//        return new CommandLineRunner() {
//            public void run(String... strings) throws Exception {
//                if (personRepository.findAll().size() == 0) {
//                    personRepository.save(new Person("wtr"));
//                    personRepository.save(new Person("wyf"));
//                    personRepository.save(new Person("admin"));
//                }
//
//                if (compRepository.findAll().size() == 0) {
//                    Comp group = new Comp("great company");
//                    compRepository.save(group);
//                    Person admin = personRepository.findByPersonName("admin");
//                    Person wtr = personRepository.findByPersonName("wtr");
//
//                    admin.setComp(group);
//                    wtr.setComp(group);
//
//                    personRepository.save(admin);
//                    personRepository.save(wtr);
//                }
//            }
//        };
//    }
}