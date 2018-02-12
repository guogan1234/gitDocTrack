package com.avp.guo.controller;

import com.avp.guo.entity.DataModel;
import com.avp.guo.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guo on 2018/2/7.
 */
@RestController
public class TestController {
    @Autowired
    DataRepository dataRepository;

    private List<DataModel> list = null;

    @RequestMapping("/test")
    public void test(){
        Date start = new Date();
        System.out.println(start);
        list = new ArrayList<DataModel>();

        int page = 0;
        while (true) {
            list.clear();

            Pageable p = new PageRequest(page, 100);
            Page<DataModel> pages = dataRepository.findAll(p);
            int size = pages.getSize();
//            System.out.println(size);

            if(size == 0){
                Date end = new Date();
                System.out.println(end);
                return;
            }else {
                //java.lang.OutOfMemoryError: GC overhead limit exceeded
                //测试结果--运行10分钟左右，内存溢出
                //第一种遍历方式
                for(DataModel dataModel:pages){
//                    String str = dataModel.getCol_1();
//                    System.out.println(str);
                    list.add(dataModel);
                }
//                //第二种遍历方式
//                //编码繁琐，不测试了(Hibernate可能无法满足性能要求，直接使用JDBC)。
//                Iterator<DataModel> it=pages.iterator();
//                while(it.hasNext()){
//                    System.out.println("value:"+((DataModel)it.next()).getId());
//                }
            }
            System.out.println("page--" + page);
            page++;
//            if(page == 10){//测试代码
//                Date end = new Date();
//                System.out.println(end);
//                return;
//            }
        }
    }
}
