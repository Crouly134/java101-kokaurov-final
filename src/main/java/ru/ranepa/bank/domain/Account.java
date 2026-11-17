package ru.ranepa.bank.domain;

public class Account {
    private String number;
    private String owner;
    private double balance;

    public Account(String number, String owner, double balance){
        //TODO: сохранять поля(проверки в сервисе)
    }

    public String getNumber() {
        return number;
    }
    public String getOwner() {
        return owner;
    }
    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
