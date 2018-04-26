package com.avp.mems.backstage.repositories.work;

import com.avp.mems.backstage.entity.work.BadComponent;
import com.avp.mems.backstage.entity.work.BadComponentPK;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Amber Wang on 2017-06-14 下午 02:13.
 */
public interface BadComponentRepository extends PagingAndSortingRepository<BadComponent,BadComponentPK> {
}
