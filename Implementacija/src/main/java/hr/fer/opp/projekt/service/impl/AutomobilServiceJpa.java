package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.AutomobilRepository;
import hr.fer.opp.projekt.dao.KorisnikRepository;
import hr.fer.opp.projekt.dao.ParkiralisteRepository;
import hr.fer.opp.projekt.domain.Automobil;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.domain.Parkiraliste;
import hr.fer.opp.projekt.domain.Rezervacija;
import hr.fer.opp.projekt.service.AutomobilService;
import hr.fer.opp.projekt.service.ParkiralisteService;
import hr.fer.opp.projekt.service.Util;
import hr.fer.opp.projekt.service.exceptions.EntityMissingException;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.exceptions.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

import static hr.fer.opp.projekt.service.Util.REG_FORMAT;

@Service
public class AutomobilServiceJpa implements AutomobilService {

    @Autowired
    private AutomobilRepository automobilRepository;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ParkiralisteService parkiralisteService;

    @Autowired
    private ParkiralisteRepository parkiralisteRepository;

    @Override
    public List<Automobil> listAll() {
        return automobilRepository.findAll();
    }

    @Override
    public List<Automobil> listByUserID(Long korisnikID) {
        return automobilRepository.findAllByKorisnikID(korisnikID);
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
    public Automobil createAutomobil(Automobil automobil) {
        Assert.notNull(automobil.getRegistracijskaOznaka(), "Potrebno je unijeti registraciju automobila.");
        Assert.hasText(automobil.getRegistracijskaOznaka(), "Potrebno je unijeti registraciju automobila.");

        Assert.isTrue(!automobilRepository.findByRegistracijskaOznaka(automobil.getRegistracijskaOznaka()).isPresent(),
                "Automobil je vec registriran.");
        Assert.isTrue(automobil.getRegistracijskaOznaka().matches(REG_FORMAT), "Registracija nije valjana."); //

        Korisnik korisnik = korisnikService.fetchKorisnik(automobil.getKorisnikID());
        korisnik.getAutomobili().add(automobil);
        automobilRepository.save(automobil);
        korisnikRepository.save(korisnik);
        return automobil;
    }

    @Override
    public Automobil updateAutomobilRegistracija(Long id, String novaRegistracija) {
        Assert.hasText(novaRegistracija, "Potrebno je unijeti registraciju automobila.");
        Assert.isTrue(novaRegistracija.matches(REG_FORMAT), "Registracija nije valjana.");
        if (automobilRepository.findByRegistracijskaOznaka(novaRegistracija).isPresent()) {
            throw new RequestDeniedException("Automobil je vec registriran.");
        }

        Automobil auto = fetch(id);
        auto.setRegistracijskaOznaka(novaRegistracija);
        return automobilRepository.save(auto);
    }

    @Override
    public Boolean deleteAutomobil(String registracija) {
        Automobil auto = fetchByReg(registracija);
        Korisnik vlasnik = korisnikService.fetchKorisnik(auto.getKorisnikID());

        vlasnik.getAutomobili().remove(auto);
        korisnikRepository.save(vlasnik);
        automobilRepository.delete(auto);
        return true;
    }
}
