package hr.fer.opp.projekt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "automobil")
public class Automobil {

    @Id
    @GeneratedValue
    @Column(name = "automobil_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "korisnik_ID")
    private Korisnik korisnik;

    @Column(unique = true)
    @NotNull
    private String registracijskaOznaka;

    @NotNull
    private String ime;

    @OneToMany(mappedBy = "automobil")
    private Set<Rezervacija> rezervacije;



    public Long getId() {
        return id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
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
}