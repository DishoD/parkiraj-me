package hr.fer.opp.projekt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "parkiraliste")
public class Parkiraliste {

    @Id
    @GeneratedValue
    @Column(name = "parkiraliste_ID")
    private Long id;

    @NotNull
    private Long tvrtkaID;

    @NotNull
    private String ime;

    @NotNull
    private Double[] lokacija;

    @NotNull
    private Integer kapacitet;

    @NotNull
    private Integer cijena;

    @OneToMany
    private Set<Rezervacija> rezervacije;


    public void setId(Long id) { this.id = id; }

    public Long getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Integer getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }

    public Integer getCijena() {
        return cijena;
    }

    public void setCijena(Integer cijena) {
        this.cijena = cijena;
    }

    public Set<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(Set<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public Long getTvrtkaID() { return tvrtkaID; }

    public void setTvrtkaID(Long tvrtkaID) { this.tvrtkaID = tvrtkaID; }

    public Double[] getLokacija() {
        return lokacija;
    }

    public void setLokacija(Double[] lokacija) {
        this.lokacija = lokacija;
    }
}
