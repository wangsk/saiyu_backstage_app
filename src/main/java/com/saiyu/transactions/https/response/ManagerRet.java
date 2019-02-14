package com.saiyu.transactions.https.response;

public class ManagerRet extends BaseRet{
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        String managerId;		//管理员Id
        String account	;		//管理员账号
        String manageType;		//管理员类型
        String realName	;	//管理员姓名

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getManageType() {
            return manageType;
        }

        public void setManageType(String manageType) {
            this.manageType = manageType;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }
    }
}
