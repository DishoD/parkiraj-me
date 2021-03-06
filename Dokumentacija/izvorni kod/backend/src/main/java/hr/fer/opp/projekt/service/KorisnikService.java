package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Korisnik;

import java.util.List;

public interface KorisnikService {
    List<Korisnik> listAll();
    Korisnik createKorisnik(Korisnik korisnik);
    Korisnik fetchKorisnik(Long id);
    Korisnik fetchKorisnik(String email);
    boolean containsKorisnik(String email);
    boolean containsKorisnikByOib(String oib);
    boolean deleteKorisnik(Korisnik korisnik);
}
