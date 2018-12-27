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

    @ManyToOne
    @JoinColumn(name = "tvrtka_ID")
    private Tvrtka tvrtka;

    @NotNull
    private String ime;

    @NotNull
    private String lokacija;

    @NotNull
    private Integer kapacitet;

    @NotNull
    private Integer cijena;

    @OneToMany(mappedBy = "parkiraliste")
    private Set<Rezervacija> rezervacije;


    public Long getId() {
        return id;
    }

    public Tvrtka getTvrtka() {
        return tvrtka;
    }

    public void setTvrtka(Tvrtka tvrtka) {
        this.tvrtka = tvrtka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
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
}
