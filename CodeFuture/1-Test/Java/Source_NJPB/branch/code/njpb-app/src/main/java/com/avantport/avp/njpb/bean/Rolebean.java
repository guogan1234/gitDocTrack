package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/9/2.
 */

public class Rolebean extends BaseJson{

    /**
     * code : 0
     * message : 成功
     * results : [{"corpId":1,"createBy":1,"createTime":1501051992513,"id":9,"roleGrade":1,"roleId":3,"roleName":"ROLE_ASSIGN","userId":4,"userName":"维修员"},{"corpId":2,"createBy":1,"createTime":1501555970580,"id":31,"roleGrade":1,"roleId":3,"roleName":"ROLE_ASSIGN","userId":15,"userName":"xiaozhoup"},{"corpId":1,"createTime":1503040377420,"id":38,"lastUpdateTime":1503040377420,"roleGrade":1,"roleId":3,"roleName":"ROLE_ASSIGN","userId":1,"userName":"admin"},{"corpId":1,"createTime":1503378472868,"id":39,"lastUpdateTime":1503378472868,"roleGrade":1,"roleId":3,"roleName":"ROLE_ASSIGN","userId":3,"userName":"派单员"}]
     * timestamp : 1504321707819
     */
    /**
     * corpId : 1
     * createBy : 1
     * createTime : 1501051992513
     * id : 9
     * roleGrade : 1
     * roleId : 3
     * roleName : ROLE_ASSIGN
     * userId : 4
     * userName : 维修员
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private Integer corpId;
        private Integer createBy;
        private Long createTime;
        private Integer id;
        private Integer roleGrade;
        private Integer roleId;
        private String roleName;
        private Integer userId;
        private String userName;

        public Integer getCorpId() {
            return corpId;
        }

        public void setCorpId(Integer corpId) {
            this.corpId = corpId;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRoleGrade() {
            return roleGrade;
        }

        public void setRoleGrade(Integer roleGrade) {
            this.roleGrade = roleGrade;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
