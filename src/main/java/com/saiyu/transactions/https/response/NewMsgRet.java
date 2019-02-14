package com.saiyu.transactions.https.response;

public class NewMsgRet extends BaseRet{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        String buyerAuditCount;		//买家审核数量
        String sellerAuditCount;	//卖家审核数量
        String withdrawCount;		//提现审核数量

        public String getBuyerAuditCount() {
            return buyerAuditCount;
        }

        public void setBuyerAuditCount(String buyerAuditCount) {
            this.buyerAuditCount = buyerAuditCount;
        }

        public String getSellerAuditCount() {
            return sellerAuditCount;
        }

        public void setSellerAuditCount(String sellerAuditCount) {
            this.sellerAuditCount = sellerAuditCount;
        }

        public String getWithdrawCount() {
            return withdrawCount;
        }

        public void setWithdrawCount(String withdrawCount) {
            this.withdrawCount = withdrawCount;
        }
    }
}
