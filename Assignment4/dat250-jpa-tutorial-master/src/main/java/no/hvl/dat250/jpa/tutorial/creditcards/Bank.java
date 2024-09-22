package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "owningBank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> ownedCards = new ArrayList<>();

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<CreditCard> getOwnedCards() {
        return new HashSet<>(ownedCards) ;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCreditCardList(Collection<CreditCard> ownedCards) {
        this.ownedCards.clear();
        if (ownedCards != null) {
            this.ownedCards.addAll(ownedCards);
        }
    }

}
