package com.saiyu.transactions.https.response;

public class PostalDetailRet extends BaseRet{

    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        String withdrawId;			//提现Id
        String orderNum	;		//提现订单号
        String userAccount;			//提现用户账号
        String withdrawWayName;		//提现渠道名称
        String applyMoney;			//申请金额
        String realName;		//真实姓名
        String withdrawAccount;		//提现账号
        String chargeMoeny;			//手续费
        String succMoney;			//实际打款
        String userRiskLevel;		//风控等级
        String buyerOrderRSettleTotalCount;		//买家交易订单数
        String buyerOrderRSettleTotalMoney;		//买家交易订单金额
        String sellerOrderRSettleTotalCount;	//卖家交易订单数
        String sellerOrderRSettleTotalMoney;	//卖家交易订单金额

        String userRiskLevelStr;	//风控等级文案
        String status;				//提现状态 0提现中 1提现成功 2提现失败
        String statusStr;			//提现状态文案
        String remarks;

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getWithdrawId() {
            return withdrawId;
        }

        public void setWithdrawId(String withdrawId) {
            this.withdrawId = withdrawId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getWithdrawAccount() {
            return withdrawAccount;
        }

        public void setWithdrawAccount(String withdrawAccount) {
            this.withdrawAccount = withdrawAccount;
        }

        public String getChargeMoeny() {
            return chargeMoeny;
        }

        public void setChargeMoeny(String chargeMoeny) {
            this.chargeMoeny = chargeMoeny;
        }

        public String getSuccMoney() {
            return succMoney;
        }

        public void setSuccMoney(String succMoney) {
            this.succMoney = succMoney;
        }

        public String getUserRiskLevel() {
            return userRiskLevel;
        }

        public void setUserRiskLevel(String userRiskLevel) {
            this.userRiskLevel = userRiskLevel;
        }

        public String getBuyerOrderRSettleTotalCount() {
            return buyerOrderRSettleTotalCount;
        }

        public void setBuyerOrderRSettleTotalCount(String buyerOrderRSettleTotalCount) {
            this.buyerOrderRSettleTotalCount = buyerOrderRSettleTotalCount;
        }

        public String getBuyerOrderRSettleTotalMoney() {
            return buyerOrderRSettleTotalMoney;
        }

        public void setBuyerOrderRSettleTotalMoney(String buyerOrderRSettleTotalMoney) {
            this.buyerOrderRSettleTotalMoney = buyerOrderRSettleTotalMoney;
        }

        public String getSellerOrderRSettleTotalCount() {
            return sellerOrderRSettleTotalCount;
        }

        public void setSellerOrderRSettleTotalCount(String sellerOrderRSettleTotalCount) {
            this.sellerOrderRSettleTotalCount = sellerOrderRSettleTotalCount;
        }

        public String getSellerOrderRSettleTotalMoney() {
            return sellerOrderRSettleTotalMoney;
        }

        public void setSellerOrderRSettleTotalMoney(String sellerOrderRSettleTotalMoney) {
            this.sellerOrderRSettleTotalMoney = sellerOrderRSettleTotalMoney;
        }

        public String getUserRiskLevelStr() {
            return userRiskLevelStr;
        }

        public void setUserRiskLevelStr(String userRiskLevelStr) {
            this.userRiskLevelStr = userRiskLevelStr;
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
