package com.saiyu.transactions.https.response;

import java.util.List;

public class PostalRet extends BaseRet  {
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
            String withdrawId;			//提现Id
            String createTime;			//提现申请时间
            String orderNum	;		//提现订单号
            String withdrawWayName;		//提现渠道名称
            String applyMoney;			//申请金额

            String userAccount;			//用户账号
            String realName	;		//真实姓名
            String status;				//提现状态 0提现中 1提现成功 2提现失败
            String statusStr;			//提现状态文案


            public String getWithdrawId() {
                return withdrawId;
            }

            public void setWithdrawId(String withdrawId) {
                this.withdrawId = withdrawId;
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

            public String getWithdrawWayName() {
                return withdrawWayName;
            }

            public void setWithdrawWayName(String withdrawWayName) {
                this.withdrawWayName = withdrawWayName;
            }

            public String getApplyMoney() {
                return applyMoney;
            }

            public void setApplyMoney(String applyMoney) {
                this.applyMoney = applyMoney;
            }

            public String getUserAccount() {
                return userAccount;
            }

            public void setUserAccount(String userAccount) {
                this.userAccount = userAccount;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatusStr() {
                return statusStr;
            }

            public void setStatusStr(String statusStr) {
                this.statusStr = statusStr;
            }
        }

    }
}
