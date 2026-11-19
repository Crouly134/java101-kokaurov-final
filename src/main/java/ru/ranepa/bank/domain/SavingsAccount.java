package ru.ranepa.bank.domain;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String number, String owner, double balance, double interestRate){
        super(number, owner, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate(){
        return interestRate;
    }
@Override
    public String describe(){
        return "Savings account #"+getNumber()+", owner: "+getOwner()+", balance: "+getBalance()+", interest: "+getInterestRate()+"%.";
    }
}
