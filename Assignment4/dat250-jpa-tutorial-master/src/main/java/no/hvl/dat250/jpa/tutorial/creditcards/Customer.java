package no.hvl.dat250.jpa.tutorial.creditcards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "customer_creditcard",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "creditcard_id")
    )
    private List<CreditCard> creditCards = new ArrayList<>();

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public void addCreditCard(CreditCard creditCard) {
        if (!this.creditCards.contains(creditCard)) {  // Evitar duplicados
            this.creditCards.add(creditCard);
        }
    }

    public void addAddress(Address address) {
        if (!this.addresses.contains(address)) {
            System.out.println("Adding address: " + address.getStreet() + " to customer: " + this.name);
            this.addresses.add(address);
            address.addOwner(this);
        } else {
            System.out.println("Address: " + address.getStreet() + " is already associated with customer: " + this.name);
        }
    }



}
