package com.chaychan.news.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class ResponseList<T> {

    public List<T> data;

    public BigDecimal expenditure;//支出

    public BigDecimal income;//收人

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
