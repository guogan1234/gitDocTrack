/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.repositories.basic;

import com.avp.mems.backstage.entity.basic.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Amber Wang on 2017-05-27
 */
public interface CityRepository extends PagingAndSortingRepository<City, Long> {
     @Query("select c from City c")
     Page findAllCities(Pageable p);
}
