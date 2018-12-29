package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Automobil;
import hr.fer.opp.projekt.rest.DodajAutomobilDTO;

import java.util.List;
import java.util.Optional;

public interface AutomobilService {
    List<Automobil> listAll();

    List<Automobil> listByUserID(Long korisnikID);

    Automobil createAutomobil(DodajAutomobilDTO automobilDTO);

    Automobil updateAutomobilRegistracija(Long id, String novaRegistracija);

    Automobil deleteAutomobil(Long id);

    Automobil fetch(Long automobilId);

    Automobil fetchByReg(String registracija);

}
