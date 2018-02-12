package com.avp.guo.controller;

import com.avp.guo.entity.JdbcDataModel;
import com.avp.guo.rest.ResponseBuilder;
import com.avp.guo.rest.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo on 2018/2/9.
 */
@RestController
public class TestJdbcController {
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

    @RequestMapping(value = "/jdbcLoadLargeData")
    ResponseEntity<JdbcDataModel> JdbcLoadLargeData(Integer page,Integer pageSize){
        System.out.println("jdbcLoadLargeData..."+page);
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try{
            String sql = "select * from \"testJdbc\".largedatatable limit " + pageSize + " offset " + pageSize * page;

            PreparedStatement pstmt;

            Connection conn = getConn();
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
    //                System.out.println("============================" + col);
            Integer i = 0;
            List<JdbcDataModel> list = new ArrayList<JdbcDataModel>();
            while (rs.next()) {
    //                i++;
    //                System.out.println(i);
                JdbcDataModel t = new JdbcDataModel();
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
//            conn.close();

            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (SQLException e) {
            e.printStackTrace();
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        } finally {//如果之前使用了return返回，会在执行return结束函数方法前执行finally函数块

        }
        return builder.getResponseEntity();
    }
}
