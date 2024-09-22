package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private Integer number;

    @ManyToMany(mappedBy = "addresses")
    private Set<Customer> owners = new HashSet<>();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public Set<Customer> getOwners() {
        return owners;
    }

    public void addOwner(Customer customer) {
        if (!this.owners.contains(customer)) {
            System.out.println("Adding owner: " + customer.getName() + " to address: " + this.getStreet());
            this.owners.add(customer);
            customer.getAddresses().add(this); // Asegura que el cliente conozca esta dirección
        }
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setOwners(Set<Customer> owners) {
        this.owners = owners;
    }

}
