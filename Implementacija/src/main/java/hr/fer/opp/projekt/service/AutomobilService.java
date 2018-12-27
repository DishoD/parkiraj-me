package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Automobil;

import java.util.List;
import java.util.Optional;

public interface AutomobilService {
    List<Automobil> listAll();

    Automobil createAutomobil(Automobil automobil);

    Automobil updateAutomobilRegistracija(Long id, String novaRegistracija);

    Automobil deleteAutomobil(Long id);

    Automobil fetch(Long automobilId);

    Automobil fetchByReg(String registracija);

}
