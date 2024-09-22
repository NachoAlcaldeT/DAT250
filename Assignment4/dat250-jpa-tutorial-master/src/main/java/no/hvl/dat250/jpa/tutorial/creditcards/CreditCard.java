package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;
    private Integer balance;
    private Integer creditLimit;

    @ManyToOne
    private Pincode pincode;

    @ManyToOne
    @JoinColumn(name="bank_id")
    private Bank owningBank;

    // Getters
    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public Pincode getPincode() {
        return pincode;
    }

    public Bank getOwningBank() {
        return owningBank;
    }

    // Setters
    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setPincode(Pincode pincode) {
        this.pincode = pincode;
    }

    public void setBank(Bank bank) {
        this.owningBank = bank;
    }
}
