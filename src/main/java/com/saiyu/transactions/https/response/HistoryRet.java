package com.saiyu.transactions.https.response;

import java.util.List;

public class HistoryRet extends BaseRet{

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
            private String auditId;
            private String orderNum;
            private String createTime;
            private String auditTime;
            private String operateName;
            private String auditType;
            private String auditTypeStr;
            private String productTypeStr;
            private String buyerAccount;
            private String sellerAccount;
            private String orderQBCount;
            private String orderMoney;
            private String auditStatus;
            private String auditStatusStr;

            public String getAuditId() {
                return auditId;
            }

            public void setAuditId(String auditId) {
                this.auditId = auditId;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getAuditTime() {
                return auditTime;
            }

            public void setAuditTime(String auditTime) {
                this.auditTime = auditTime;
            }

            public String getOperateName() {
                return operateName;
            }

            public void setOperateName(String operateName) {
                this.operateName = operateName;
            }

            public String getAuditType() {
                return auditType;
            }

            public void setAuditType(String auditType) {
                this.auditType = auditType;
            }

            public String getAuditTypeStr() {
                return auditTypeStr;
            }

            public void setAuditTypeStr(String auditTypeStr) {
                this.auditTypeStr = auditTypeStr;
            }

            public String getProductTypeStr() {
                return productTypeStr;
            }

            public void setProductTypeStr(String productTypeStr) {
                this.productTypeStr = productTypeStr;
            }

            public String getBuyerAccount() {
                return buyerAccount;
            }

            public void setBuyerAccount(String buyerAccount) {
                this.buyerAccount = buyerAccount;
            }

            public String getSellerAccount() {
                return sellerAccount;
            }

            public void setSellerAccount(String sellerAccount) {
                this.sellerAccount = sellerAccount;
            }

            public String getOrderQBCount() {
                return orderQBCount;
            }

            public void setOrderQBCount(String orderQBCount) {
                this.orderQBCount = orderQBCount;
            }

            public String getOrderMoney() {
                return orderMoney;
            }

            public void setOrderMoney(String orderMoney) {
                this.orderMoney = orderMoney;
            }

            public String getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(String auditStatus) {
                this.auditStatus = auditStatus;
            }

            public String getAuditStatusStr() {
                return auditStatusStr;
            }

            public void setAuditStatusStr(String auditStatusStr) {
                this.auditStatusStr = auditStatusStr;
            }
        }

    }

}
