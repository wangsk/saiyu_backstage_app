package com.saiyu.transactions.https.response;

import java.util.List;

public class CapitalRet extends BaseRet{
    private DatasBean data;

    public DatasBean getData() {
        return data;
    }

    public void setData(DatasBean data) {
        this.data = data;
    }

    public static class DatasBean {
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            String Id;					//明细Id
            String createTime;			//明细时间
            String orderNum;			//相关单号
            String type;				//类型 （0收入 1支出）
            String typeStr;				//类型文案
            String bizType;				//业务类型（0充值 1提现 2我是买家 3我是卖家 4系统加减款 5资金冻结 6资金解冻）
            String bizTypeStr;			//业务类型文案
            String money;				//金额
            String currentMoney;		//当前余额

            String userAccount;			//用户账号
            String bizNote;				//项目类型
            String operateName;			//操作员
            String remarks;				//相关备注

            public String getId() {
                return Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTypeStr() {
                return typeStr;
            }

            public void setTypeStr(String typeStr) {
                this.typeStr = typeStr;
            }

            public String getBizType() {
                return bizType;
            }

            public void setBizType(String bizType) {
                this.bizType = bizType;
            }

            public String getBizTypeStr() {
                return bizTypeStr;
            }

            public void setBizTypeStr(String bizTypeStr) {
                this.bizTypeStr = bizTypeStr;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCurrentMoney() {
                return currentMoney;
            }

            public void setCurrentMoney(String currentMoney) {
                this.currentMoney = currentMoney;
            }

            public String getUserAccount() {
                return userAccount;
            }

            public void setUserAccount(String userAccount) {
                this.userAccount = userAccount;
            }

            public String getBizNote() {
                return bizNote;
            }

            public void setBizNote(String bizNote) {
                this.bizNote = bizNote;
            }

            public String getOperateName() {
                return operateName;
            }

            public void setOperateName(String operateName) {
                this.operateName = operateName;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }
        }

    }
}
