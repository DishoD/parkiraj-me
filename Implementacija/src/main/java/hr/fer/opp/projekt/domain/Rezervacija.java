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
    private boolean trajnaRezervacija;

    @NotNull
    private Long parkiraliste;


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

    public boolean isTrajnaRezervacija() {
        return trajnaRezervacija;
    }

    public void setTrajnaRezervacija(boolean trajnaRezervacija) {
        this.trajnaRezervacija = trajnaRezervacija;
    }
}
