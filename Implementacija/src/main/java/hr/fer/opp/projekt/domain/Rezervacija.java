package hr.fer.opp.projekt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.function.BooleanSupplier;

@Entity
@Table(name = "rezervacija")
public class Rezervacija {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long korisnikID;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijemePocetka;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijemeKraja;

    @NotNull
    private Long parkiralisteID;

    @NotNull
    private Boolean isTrajna;


    public Rezervacija() {

    }

    public Rezervacija(@NotNull Long korisnikID, @NotNull Date vrijemePocetka, @NotNull Date vrijemeKraja, @NotNull Long parkiralisteID, @NotNull Boolean isTrajna) {
        this.korisnikID = korisnikID;
        this.vrijemePocetka = vrijemePocetka;
        this.vrijemeKraja = vrijemeKraja;
        this.parkiralisteID = parkiralisteID;
        this.isTrajna = isTrajna;
    }

    public Long getParkiralisteID() { return parkiralisteID; }

    public void setParkiralisteID(Long parkiralisteID) { this.parkiralisteID = parkiralisteID; }

    public Date getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(Date vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public Date getVrijemeKraja() {
        return vrijemeKraja;
    }

    public void setVrijemeKraja(Date vrijemeKraja) {
        this.vrijemeKraja = vrijemeKraja;
    }

    public Long getId() { return id; }

    public Boolean getTrajna() {
        return isTrajna;
    }

    public void setTrajna(Boolean trajna) {
        isTrajna = trajna;
    }

    public Long getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Long korisnikID) {
        this.korisnikID = korisnikID;
    }
}
