package hr.fer.opp.projekt.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "rezervacija")
public class Rezervacija {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long automobilID;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijemePocetka;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijemeKraja;

    @NotNull
    private Long parkiralisteID;


    public Rezervacija() {

    }

    public Rezervacija(Long automobilID, Long parkiralisteID,
                       Date vrijemePocetka, Date vrijemeKraja) {
        this.automobilID = automobilID;
        this.parkiralisteID = parkiralisteID;
        this.vrijemePocetka = vrijemePocetka;
        this.vrijemeKraja = vrijemeKraja;
    }

    public Long getParkiralisteID() { return parkiralisteID; }

    public void setParkiralisteID(Long parkiralisteID) { this.parkiralisteID = parkiralisteID; }

    public Long getAutomobilID() { return automobilID; }

    public void setAutomobilID(Long automobilID) { this.automobilID = automobilID; }

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

}
