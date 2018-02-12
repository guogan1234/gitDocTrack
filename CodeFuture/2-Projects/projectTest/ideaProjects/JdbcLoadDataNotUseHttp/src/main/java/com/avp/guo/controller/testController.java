package com.avp.guo.controller;

import com.avp.guo.entity.DataModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guo on 2018/2/5.
 */
public class testController {
    private Connection getConn() {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/CD_AFC";
        String username = "postgres";
        String password = "123456";
        Connection conn = null;
        try {
            Class.forName(driver); // classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //普通(不分页)查询
    public void test(){
        Date start = new Date();
        System.out.println(start);
        Connection conn = getConn();
        //数据表共51列，平均1列10字节（postgreSQL）.
//        //1W条数据使用JDBC查询耗时--1s
//        String sql = "select * from \"testJdbc\".table_1";
//        //10W条数据使用SQL脚本调用存储过程写入耗时--6s
//        //10W条数据使用JDBC查询耗时--(5s到7s)
//        String sql = "select * from \"testJdbc\".bigdatatable";
        //100W条使用SQL脚本调用存储过程写入耗时--45s
        //100W条数据使用JDBC查询--堆内存(heap)溢出
        //100W条数据使用pgAdmin数据库视图工具查询--600s
        String sql = "select * from \"testJdbc\".largedatatable";
        PreparedStatement pstmt;
        List<DataModel> list = new ArrayList<DataModel>();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================"+col);
            Integer i = 0;
            while (rs.next()) {
                if(list.size() >= 1000){
                    i++;
                    System.out.println(i);
                    list.clear();
                }
//                i++;
//                System.out.println(i);
                DataModel t = new DataModel();
                t.setId(rs.getInt(1));
                t.setCol_1(rs.getString(2));
                t.setCol_2(rs.getInt(3));
                t.setCol_3(rs.getString(4));
                t.setCol_4(rs.getString(5));
                t.setCol_5(rs.getInt(6));
                t.setCol_6(rs.getString(7));
                t.setCol_7(rs.getString(8));
                t.setCol_8(rs.getString(9));
                t.setCol_9(rs.getString(10));
                t.setCol_10(rs.getString(11));
                t.setCol_11(rs.getString(12));
                t.setCol_12(rs.getString(13));
                t.setCol_13(rs.getString(14));
                t.setCol_14(rs.getInt(15));
                t.setCol_15(rs.getString(16));
                t.setCol_16(rs.getString(17));
                t.setCol_17(rs.getString(18));
                t.setCol_18(rs.getInt(19));
                t.setCol_19(rs.getString(20));
                t.setCol_20(rs.getString(21));
                t.setCol_21(rs.getString(22));
                t.setCol_22(rs.getString(23));
                t.setCol_23(rs.getString(24));
                t.setCol_24(rs.getString(25));
                t.setCol_25(rs.getString(26));
                t.setCol_26(rs.getString(27));
                t.setCol_27(rs.getString(28));
                t.setCol_28(rs.getString(29));
                t.setCol_29(rs.getString(30));
                t.setCol_30(rs.getString(31));
                t.setCol_31(rs.getString(32));
                t.setCol_32(rs.getString(33));
                t.setCol_33(rs.getString(34));
                t.setCol_34(rs.getString(35));
                t.setCol_35(rs.getString(36));
                t.setCol_36(rs.getString(37));
                t.setCol_37(rs.getString(38));
                t.setCol_38(rs.getString(39));
                t.setCol_39(rs.getString(40));
                t.setCol_40(rs.getString(41));
                t.setCol_41(rs.getString(42));
                t.setCol_42(rs.getString(43));
                t.setCol_43(rs.getString(44));
                t.setCol_44(rs.getString(45));
                t.setCol_45(rs.getString(46));
                t.setCol_46(rs.getString(47));
                t.setCol_47(rs.getString(48));
                t.setCol_48(rs.getString(49));
                t.setCol_49(rs.getString(50));

                t.setCol_50(rs.getString(51));

                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Date end = new Date();
            System.out.println(end);
            System.out.println("len:"+list.size());
        }
    }

    //分页查询
    //100W条记录，耗时--157s(两分种半)
    public void test_2(int pageSize) {
        Integer page = 0;
        Integer total = 0;

        Date start = new Date();
        System.out.println(start);
        Connection conn = getConn();
        //获取记录总数量
        try {
            String str = "select count(id) from \"testJdbc\".largedatatable";
            PreparedStatement ps;
            ps = conn.prepareStatement(str);
            ResultSet res = ps.executeQuery();
            if(res.next()){
                total = res.getInt(1);//java中SQL记录索引从1开始
                System.out.println("============================" + total);
            }

            List<DataModel> list = new ArrayList<DataModel>(pageSize + 1);
            Integer count = -1;
            while (true) {
                count++;
                list.clear();
                if(page*pageSize >= total){
                    Date end = new Date();
                    System.out.println("len:" + list.size());
                    System.out.println(end);
                    return;
                }
                String sql = "select * from \"testJdbc\".largedatatable limit " + pageSize + " offset " + pageSize * page;
                 page++;
//                System.out.println(sql);
//                System.out.println(page);
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                int col = rs.getMetaData().getColumnCount();
                System.out.println("============================" + col + " count:" + count);
                Integer i = 0;
                while (rs.next()) {
//                i++;
//                System.out.println(i);
                    DataModel t = new DataModel();
                    t.setId(rs.getInt(1));
                    t.setCol_1(rs.getString(2));
                    t.setCol_2(rs.getInt(3));
                    t.setCol_3(rs.getString(4));
                    t.setCol_4(rs.getString(5));
                    t.setCol_5(rs.getInt(6));
                    t.setCol_6(rs.getString(7));
                    t.setCol_7(rs.getString(8));
                    t.setCol_8(rs.getString(9));
                    t.setCol_9(rs.getString(10));
                    t.setCol_10(rs.getString(11));
                    t.setCol_11(rs.getString(12));
                    t.setCol_12(rs.getString(13));
                    t.setCol_13(rs.getString(14));
                    t.setCol_14(rs.getInt(15));
                    t.setCol_15(rs.getString(16));
                    t.setCol_16(rs.getString(17));
                    t.setCol_17(rs.getString(18));
                    t.setCol_18(rs.getInt(19));
                    t.setCol_19(rs.getString(20));
                    t.setCol_20(rs.getString(21));
                    t.setCol_21(rs.getString(22));
                    t.setCol_22(rs.getString(23));
                    t.setCol_23(rs.getString(24));
                    t.setCol_24(rs.getString(25));
                    t.setCol_25(rs.getString(26));
                    t.setCol_26(rs.getString(27));
                    t.setCol_27(rs.getString(28));
                    t.setCol_28(rs.getString(29));
                    t.setCol_29(rs.getString(30));
                    t.setCol_30(rs.getString(31));
                    t.setCol_31(rs.getString(32));
                    t.setCol_32(rs.getString(33));
                    t.setCol_33(rs.getString(34));
                    t.setCol_34(rs.getString(35));
                    t.setCol_35(rs.getString(36));
                    t.setCol_36(rs.getString(37));
                    t.setCol_37(rs.getString(38));
                    t.setCol_38(rs.getString(39));
                    t.setCol_39(rs.getString(40));
                    t.setCol_40(rs.getString(41));
                    t.setCol_41(rs.getString(42));
                    t.setCol_42(rs.getString(43));
                    t.setCol_43(rs.getString(44));
                    t.setCol_44(rs.getString(45));
                    t.setCol_45(rs.getString(46));
                    t.setCol_46(rs.getString(47));
                    t.setCol_47(rs.getString(48));
                    t.setCol_48(rs.getString(49));
                    t.setCol_49(rs.getString(50));

                    t.setCol_50(rs.getString(51));

                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {//如果之前使用了return返回，会在执行return结束函数方法前执行finally函数块
            Date end = new Date();
//            System.out.println("111");
            System.out.println(end);
        }
    }
}
