package hr.fer.opp.projekt.rest.dto;

public class DodajRezervacijuPonavljajucuDTO {

    private Long parkingID;
    private Integer[] dani;
    private String vrijemePocetka;
    private Long trajanje;

    public Long getParkingID() {
        return parkingID;
    }

    public void setParkingID(Long parkingID) {
        this.parkingID = parkingID;
    }

    public Integer[] getDani() {
        return dani;
    }

    public void setDani(Integer[] dani) {
        this.dani = dani;
    }

    public String getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(String vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public Long getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(Long trajanje) {
        this.trajanje = trajanje;
    }
}
