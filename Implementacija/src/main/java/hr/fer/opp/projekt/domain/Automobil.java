package hr.fer.opp.projekt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "automobil")
public class Automobil {

    @NotNull
    private Long korisnikID;

    @Id
    @Column(unique = true)
    @NotNull
    private String registracijskaOznaka;

    @NotNull
    private String ime;

    @OneToMany
    private Set<Rezervacija> rezervacije;

    public Automobil(){

    }

    public Automobil(String registracijskaOznaka, String ime, Long korisnikID) {
        this.registracijskaOznaka = registracijskaOznaka;
        this.ime = ime;
        this.korisnikID = korisnikID;
        this.rezervacije = new HashSet<>();
    }


    public String getRegistracijskaOznaka() {
        return registracijskaOznaka;
    }

    public void setRegistracijskaOznaka(String registracijskaOznaka) {
        this.registracijskaOznaka = registracijskaOznaka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Set<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(Set<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public Long getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Long korisnikID) {
        this.korisnikID = korisnikID;
    }
}