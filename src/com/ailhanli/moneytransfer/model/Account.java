package com.ailhanli.moneytransfer.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final Integer accountId;

    private String name;

    private BigDecimal balance;

    private Currency currency;

    public Account(String name, BigDecimal balance, Currency currency) {
        this.accountId = COUNTER.getAndIncrement();
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public Account() {
        this.accountId = COUNTER.getAndIncrement();
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
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

    public void withdraw(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }
}
