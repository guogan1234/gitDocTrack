package com.avp.guo.repository;

import com.avp.guo.entity.TestConn;

import java.util.List;

/**
 * Created by guo on 2018/2/23.
 */
public interface TestConnDao {
    public TestConn getOne(int id);
    public List<TestConn> getList();

    public void insertOne(TestConn testConn);
    public void updateOne(TestConn testConn);
    public void deleteOne(int id);
}
