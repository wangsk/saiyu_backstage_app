package com.saiyu.transactions.https.response;

import java.util.List;

public class BuyerRet extends BaseRet {

    private List<DatasBean> data;

    public List<DatasBean> getDatas() {
        return data;
    }

    public void setDatas(List<DatasBean> datas) {
        this.data = datas;
    }

    public static class DatasBean {
        private String auditId;
        private String orderNum;
        private String createTime;
        private String buyerAccount;
        private String sellerAccount;
        private String orderQBCount;
        private String orderMoney;
        private String discount;
        private String productName;
        private String confirmType;
        private String confirmTypeStr;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getConfirmType() {
            return confirmType;
        }

        public void setConfirmType(String confirmType) {
            this.confirmType = confirmType;
        }

        public String getConfirmTypeStr() {
            return confirmTypeStr;
        }

        public void setConfirmTypeStr(String confirmTypeStr) {
            this.confirmTypeStr = confirmTypeStr;
        }

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

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }


}
