package com.chaychan.news.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单列表
 */
public class AccountList {

    private String recordDay;

    private BigDecimal expenditureDay;

    private BigDecimal incomeDay;

    private List<ListBean> accountBeans;


    public static class ListBean {
        private Integer id;

        private String title;

        private BigDecimal amount;

        private String classification;

        private Integer type;

        private String crateTime;

        private String modifyTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getCrateTime() {
            return crateTime;
        }

        public void setCrateTime(String crateTime) {
            this.crateTime = crateTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }
    }

    public String getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(String recordDay) {
        this.recordDay = recordDay;
    }

    public BigDecimal getExpenditureDay() {
        return expenditureDay;
    }

    public void setExpenditureDay(BigDecimal expenditureDay) {
        this.expenditureDay = expenditureDay;
    }

    public BigDecimal getIncomeDay() {
        return incomeDay;
    }

    public void setIncomeDay(BigDecimal incomeDay) {
        this.incomeDay = incomeDay;
    }

    public List<ListBean> getAccountBeans() {
        return accountBeans;
    }

    public void setAccountBeans(List<ListBean> accountBeans) {
        this.accountBeans = accountBeans;
    }
}
