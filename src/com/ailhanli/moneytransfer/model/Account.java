package com.ailhanli.moneytransfer.model;

import java.util.Currency;

public class Account {

    private Integer accountId;

    private String name;

    private double balance;

    private Currency currency;

    public Account(Integer accountId, String name, double balance, Currency currency) {
    	this(name, balance, currency);
    	this.accountId = accountId;
    }
    
    public Account(String name, double balance, Currency currency) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public Account() {
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return accountId == account.accountId;
    }

    @Override
    public int hashCode() {
        return accountId;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }

    public void withdraw(double amount) {
        this.balance = balance-amount;
    }

    public void deposit(double amount) {
        this.balance = balance+amount;
    }
}
