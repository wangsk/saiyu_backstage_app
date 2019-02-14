package com.saiyu.transactions.https.response;

public class SellerReplaceRet extends BaseRet {

    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        String receiveOrderNum;	//充值订单号
        String sellerAccount;	//卖家会员
        String productInfo;		//充值游戏信息
        String reserveInfo;		//接单数量信息
        String succQBCount;		//成功数量
        String succMoney;		//成功金额
        String confirmType;		//确认方式 -1无 0买家手动确认 1客服代理确认 2客服验图确认
        String confirmTypeStr;		//确认方式文案
        String reserveAccount;	//充值号码
        String reservePwd;		//充值密码
        String oftenLoginArea;  //常登陆地
        String contactMobile;	//联系手机
        String contactQQ;		//联系QQ
        String rechargeTime;	//充值时间
        String finishTime;		//完成时间

        String onceLimit;		//单次限制
        String buyerAccount;	//买家会员
        String buyerRealName;	//买家姓名
        String sellerRealName;	//卖家姓名
        String createTime;		//接单时间
        String penaltyMoney;	//获得违约金
        String autoConfirmTime;	//自动确认时间
        String averageConfirmTime;	//平均确认时间
        String pic_RechargeSucc; //充值成功截图
        String pic_TradeInfo;	//交易明细截图
        String pic_BillRecord;	//消费记录截图

        public String getReceiveOrderNum() {
            return receiveOrderNum;
        }

        public void setReceiveOrderNum(String receiveOrderNum) {
            this.receiveOrderNum = receiveOrderNum;
        }

        public String getSellerAccount() {
            return sellerAccount;
        }

        public void setSellerAccount(String sellerAccount) {
            this.sellerAccount = sellerAccount;
        }

        public String getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(String productInfo) {
            this.productInfo = productInfo;
        }

        public String getReserveInfo() {
            return reserveInfo;
        }

        public void setReserveInfo(String reserveInfo) {
            this.reserveInfo = reserveInfo;
        }

        public String getSuccQBCount() {
            return succQBCount;
        }

        public void setSuccQBCount(String succQBCount) {
            this.succQBCount = succQBCount;
        }

        public String getSuccMoney() {
            return succMoney;
        }

        public void setSuccMoney(String succMoney) {
            this.succMoney = succMoney;
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

        public String getReserveAccount() {
            return reserveAccount;
        }

        public void setReserveAccount(String reserveAccount) {
            this.reserveAccount = reserveAccount;
        }

        public String getReservePwd() {
            return reservePwd;
        }

        public void setReservePwd(String reservePwd) {
            this.reservePwd = reservePwd;
        }

        public String getOftenLoginArea() {
            return oftenLoginArea;
        }

        public void setOftenLoginArea(String oftenLoginArea) {
            this.oftenLoginArea = oftenLoginArea;
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

        public String getRechargeTime() {
            return rechargeTime;
        }

        public void setRechargeTime(String rechargeTime) {
            this.rechargeTime = rechargeTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getOnceLimit() {
            return onceLimit;
        }

        public void setOnceLimit(String onceLimit) {
            this.onceLimit = onceLimit;
        }

        public String getBuyerAccount() {
            return buyerAccount;
        }

        public void setBuyerAccount(String buyerAccount) {
            this.buyerAccount = buyerAccount;
        }

        public String getBuyerRealName() {
            return buyerRealName;
        }

        public void setBuyerRealName(String buyerRealName) {
            this.buyerRealName = buyerRealName;
        }

        public String getSellerRealName() {
            return sellerRealName;
        }

        public void setSellerRealName(String sellerRealName) {
            this.sellerRealName = sellerRealName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPenaltyMoney() {
            return penaltyMoney;
        }

        public void setPenaltyMoney(String penaltyMoney) {
            this.penaltyMoney = penaltyMoney;
        }

        public String getAutoConfirmTime() {
            return autoConfirmTime;
        }

        public void setAutoConfirmTime(String autoConfirmTime) {
            this.autoConfirmTime = autoConfirmTime;
        }

        public String getAverageConfirmTime() {
            return averageConfirmTime;
        }

        public void setAverageConfirmTime(String averageConfirmTime) {
            this.averageConfirmTime = averageConfirmTime;
        }

        public String getPic_RechargeSucc() {
            return pic_RechargeSucc;
        }

        public void setPic_RechargeSucc(String pic_RechargeSucc) {
            this.pic_RechargeSucc = pic_RechargeSucc;
        }

        public String getPic_TradeInfo() {
            return pic_TradeInfo;
        }

        public void setPic_TradeInfo(String pic_TradeInfo) {
            this.pic_TradeInfo = pic_TradeInfo;
        }

        public String getPic_BillRecord() {
            return pic_BillRecord;
        }

        public void setPic_BillRecord(String pic_BillRecord) {
            this.pic_BillRecord = pic_BillRecord;
        }
    }
}
