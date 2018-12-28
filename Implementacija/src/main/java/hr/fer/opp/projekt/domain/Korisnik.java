package hr.fer.opp.projekt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "korisnik")
public class Korisnik {

    @Id
    @GeneratedValue
    @Column(name = "korisnik_ID")
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 11)
    private String oib;

    @NotNull
    private String ime;

    @NotNull
    private String prezime;

    @NotNull
    private String brojKreditneKartice;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String passwordHash;

    @OneToMany
    private Set<Automobil> automobili;


    public Long getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getBrojKreditneKartice() {
        return brojKreditneKartice;
    }

    public void setBrojKreditneKartice(String brojKreditneKartice) {
        this.brojKreditneKartice = brojKreditneKartice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Automobil> getAutomobili() {
        return automobili;
    }

    public void setAutomobili(Set<Automobil> automobili) {
        this.automobili = automobili;
    }
}