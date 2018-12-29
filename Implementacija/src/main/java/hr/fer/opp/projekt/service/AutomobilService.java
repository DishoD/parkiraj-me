package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Automobil;
import hr.fer.opp.projekt.rest.dto.DodajAutomobilDTO;

import java.util.List;

public interface AutomobilService {
    List<Automobil> listAll();

    List<Automobil> listByUserID(Long korisnikID);

    Automobil createAutomobil(DodajAutomobilDTO automobilDTO);

    Automobil updateAutomobilRegistracija(Long id, String novaRegistracija);

    Automobil deleteAutomobil(Long id);

    Automobil fetch(Long automobilId);

    Automobil fetchByReg(String registracija);

}
