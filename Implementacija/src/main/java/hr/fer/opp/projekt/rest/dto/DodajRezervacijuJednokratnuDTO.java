package hr.fer.opp.projekt.rest.dto;

public class DodajRezervacijuJednokratnuDTO {

    private Long parkingID;
    private Long vrijemePocetka;
    private Long trajanje;

    public Long getParkingID() {
        return parkingID;
    }

    public void setParkingID(Long parkingID) {
        this.parkingID = parkingID;
    }

    public Long getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(Long vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public Long getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Long trajanje) {
        this.trajanje = trajanje;
    }
}
