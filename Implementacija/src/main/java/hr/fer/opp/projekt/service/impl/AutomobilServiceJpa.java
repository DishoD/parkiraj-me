package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.AutomobilRepository;
import hr.fer.opp.projekt.domain.Automobil;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.AutomobilService;
import hr.fer.opp.projekt.service.EntityMissingException;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AutomobilServiceJpa implements AutomobilService {

    private static final String REG_FORMAT = "[A-Z]{2} [0-9]{3,4}-[A-Z]{1,2}";

    @Autowired
    private AutomobilRepository automobilRepository;

    @Override
    public List<Automobil> listAll() {
        return automobilRepository.findAll();
    }

    @Override
    public Automobil fetch(Long automobilId) {
        return automobilRepository.findById(automobilId).orElseThrow(
                () -> new EntityMissingException(Automobil.class, automobilId)
        );
    }

    @Override
    public Automobil fetchByReg(String registracija) {
        return automobilRepository.findByRegistracijskaOznaka(registracija).orElseThrow(
                () -> new EntityMissingException(Automobil.class, registracija)
        );
    }

    @Override
    public Automobil createAutomobil(Automobil auto) {
        Assert.notNull(auto.getRegistracijskaOznaka(), "Potrebno je unijeti registraciju automobila.");
        Assert.hasText(auto.getRegistracijskaOznaka(), "Potrebno je unijeti registraciju automobila.");
        Assert.isNull(auto.getId(), "ID automobila se generira automatski.");
        //Assert.notNull(auto.getKorisnikID(), "Vlasnik ne postoji");   //ova provjera mozda nije potrebna?
        if (automobilRepository.findByRegistracijskaOznaka(auto.getRegistracijskaOznaka()).isPresent()) {
            throw new RequestDeniedException("Automobil je vec registriran.");
        }
//        Assert.isTrue(auto.getRegistracijskaOznaka().matches(REG_FORMAT)); //trenutno
        return automobilRepository.save(auto);
    }

    @Override
    public Automobil updateAutomobilRegistracija(Long id, String novaRegistracija) {
        Assert.hasText(novaRegistracija, "Potrebno je unijeti registraciju automobila.");
        Assert.isTrue(novaRegistracija.matches(REG_FORMAT));
        if (automobilRepository.findByRegistracijskaOznaka(novaRegistracija).isPresent()) {
            throw new RequestDeniedException("Automobil je vec registriran.");
        }

        Automobil auto = fetch(id);
        auto.setRegistracijskaOznaka(novaRegistracija);
        return automobilRepository.save(auto);
    }

    @Override
    public Automobil deleteAutomobil(Long id) {
        Automobil auto = fetch(id);
        automobilRepository.delete(auto);
        return auto;
    }
}
