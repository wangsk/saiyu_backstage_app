package com.saiyu.transactions.https.response;

public class BuyerReleaseRet extends BaseRet  {

    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        String orderNum;
        String reserveAccount;
        String productInfo;
        String reserveInfo;
        String contactMobile;
        String contactQQ;
        String friendLimit;
        String remarks;
        String friendLimitStr;

        public String getFriendLimitStr() {
            return friendLimitStr;
        }

        public void setFriendLimitStr(String friendLimitStr) {
            this.friendLimitStr = friendLimitStr;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getReserveAccount() {
            return reserveAccount;
        }

        public void setReserveAccount(String reserveAccount) {
            this.reserveAccount = reserveAccount;
        }

        public String getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(String productInfo) {
            this.productInfo = productInfo;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getReserveInfo() {
            return reserveInfo;
        }

        public void setReserveInfo(String reserveInfo) {
            this.reserveInfo = reserveInfo;
        }

        public String getContactMobile() {
            return contactMobile;
        }

        public void setContactMobile(String contactMobile) {
            this.contactMobile = contactMobile;
        }

        public String getContactQQ() {
            return contactQQ;
        }

        public void setContactQQ(String contactQQ) {
            this.contactQQ = contactQQ;
        }

        public String getFriendLimit() {
            return friendLimit;
        }

        public void setFriendLimit(String friendLimit) {
            this.friendLimit = friendLimit;
        }

    }
}
