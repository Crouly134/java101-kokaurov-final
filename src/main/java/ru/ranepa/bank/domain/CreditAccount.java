package ru.ranepa.bank.domain;

public class CreditAccount extends Account {
    private double limit;
    private double interestRate;

    public CreditAccount(String number, String owner, double balance, double limit, double interestRate){
        super(number,owner,balance);
        this.limit = limit;
        this.interestRate = interestRate;
    }
    public double getLimit(){
        return limit;
    }
    public double getInterestRate(){
        return interestRate;
    }
@Override
    public String describe(){
        return "Credit account #"+getNumber()+", owner: "+getOwner()+", balance: "+getBalance()+", limit: "+getLimit();
    }


}
