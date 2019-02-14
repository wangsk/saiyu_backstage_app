package com.saiyu.transactions.https.response;

public class DataScanRet extends BaseRet{

    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        String regUserTodayCount;
        String regUserCount;
        String activatedBuyerCount;
        String activatedSellerCount;
        String successOrderCount;
        String successQBCount;
        String orderCount;
        String orderQBCount;
        String serviceMoneyToDay;
        String serviceMoneyMonth;

        public String getRegUserTodayCount() {
            return regUserTodayCount;
        }

        public void setRegUserTodayCount(String regUserTodayCount) {
            this.regUserTodayCount = regUserTodayCount;
        }

        public String getRegUserCount() {
            return regUserCount;
        }

        public void setRegUserCount(String regUserCount) {
            this.regUserCount = regUserCount;
        }

        public String getActivatedBuyerCount() {
            return activatedBuyerCount;
        }

        public void setActivatedBuyerCount(String activatedBuyerCount) {
            this.activatedBuyerCount = activatedBuyerCount;
        }

        public String getActivatedSellerCount() {
            return activatedSellerCount;
        }

        public void setActivatedSellerCount(String activatedSellerCount) {
            this.activatedSellerCount = activatedSellerCount;
        }

        public String getSuccessOrderCount() {
            return successOrderCount;
        }

        public void setSuccessOrderCount(String successOrderCount) {
            this.successOrderCount = successOrderCount;
        }

        public String getSuccessQBCount() {
            return successQBCount;
        }

        public void setSuccessQBCount(String successQBCount) {
            this.successQBCount = successQBCount;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(String orderCount) {
            this.orderCount = orderCount;
        }

        public String getOrderQBCount() {
            return orderQBCount;
        }

        public void setOrderQBCount(String orderQBCount) {
            this.orderQBCount = orderQBCount;
        }

        public String getServiceMoneyToDay() {
            return serviceMoneyToDay;
        }

        public void setServiceMoneyToDay(String serviceMoneyToDay) {
            this.serviceMoneyToDay = serviceMoneyToDay;
        }

        public String getServiceMoneyMonth() {
            return serviceMoneyMonth;
        }

        public void setServiceMoneyMonth(String serviceMoneyMonth) {
            this.serviceMoneyMonth = serviceMoneyMonth;
        }
    }

}
