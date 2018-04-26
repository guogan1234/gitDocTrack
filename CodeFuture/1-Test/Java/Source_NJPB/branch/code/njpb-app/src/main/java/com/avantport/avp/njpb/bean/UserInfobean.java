package com.avantport.avp.njpb.bean;

import java.io.Serializable;

/**
 * Created by len on 2017/8/31.
 */

public class UserInfobean extends BaseJson implements Serializable{


    /**
     * code : 40000
     * message : 查询成功
     * result : {"corpId":1,"corpName":"总公司","createTime":1501037920893,"id":1,"lastUpdateBy":1,"lastUpdateTime":1504150056903,"roleNames":"仓库管理员,派单员,管理员","userAccount":"admin","userEmail":"sqsqqwqww","userGroup":0,"userName":"管理员","userPhone":"18638588707","userQq":0,"userStatus":0,"userType":0,"userWechart":"asasasas","whitelist":0}
     * timestamp : 1505280183127
     */
    /**
     * corpId : 1
     * corpName : 总公司
     * createTime : 1501037920893
     * id : 1
     * lastUpdateBy : 1
     * lastUpdateTime : 1504150056903
     * roleNames : 仓库管理员,派单员,管理员
     * userAccount : admin
     * userEmail : sqsqqwqww
     * userGroup : 0
     * userName : 管理员
     * userPhone : 18638588707
     * userQq : 0
     * userStatus : 0
     * userType : 0
     * userWechart : asasasas
     * whitelist : 0
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        private Integer corpId;
        private String corpName;
        private long createTime;
        private Integer id;
        private Integer lastUpdateBy;
        private long lastUpdateTime;
        private String roleNames;
        private String userAccount;
        private String userEmail;
        private Integer userGroup;
        private String userName;
        private String userPhone;
        private String userQq;
        private Integer userStatus;
        private Integer userType;
        private String userWechart;
        private Integer whitelist;

        public Integer getCorpId() {
            return corpId;
        }

        public void setCorpId(Integer corpId) {
            this.corpId = corpId;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getLastUpdateBy() {
            return lastUpdateBy;
        }

        public void setLastUpdateBy(Integer lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        public long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(String roleNames) {
            this.roleNames = roleNames;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public Integer getUserGroup() {
            return userGroup;
        }

        public void setUserGroup(Integer userGroup) {
            this.userGroup = userGroup;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserQq() {
            return userQq;
        }

        public void setUserQq(String userQq) {
            this.userQq = userQq;
        }

        public Integer getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(Integer userStatus) {
            this.userStatus = userStatus;
        }

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }

        public String getUserWechart() {
            return userWechart;
        }

        public void setUserWechart(String userWechart) {
            this.userWechart = userWechart;
        }

        public Integer getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(Integer whitelist) {
            this.whitelist = whitelist;
        }
    }
}
