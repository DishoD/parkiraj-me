package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Automobil;

import java.util.List;

public interface AutomobilService {
    List<Automobil> listAll();

    List<Automobil> listByUserID(Long korisnikID);

    Automobil createAutomobil(Automobil automobil);

    Automobil updateAutomobilRegistracija(Long id, String novaRegistracija);

    Automobil deleteAutomobil(Long id);

    Automobil fetch(Long automobilId);

    Automobil fetchByReg(String registracija);

}
