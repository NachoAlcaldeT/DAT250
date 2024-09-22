package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pincode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pincode;
    private Integer count;

    @OneToMany(mappedBy = "pincode")
    private List<CreditCard> creditCards = new ArrayList<>();


    // Getters
    public Long getId() {
        return id;
    }

    public String getCode() {
        return pincode;
    }

    public Integer getCount() {
        return count;
    }

    // Setters
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
