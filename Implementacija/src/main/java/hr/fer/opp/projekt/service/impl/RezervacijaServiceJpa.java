package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.AutomobilRepository;
import hr.fer.opp.projekt.dao.ParkiralisteRepository;
import hr.fer.opp.projekt.dao.RezervacijaRepository;
import hr.fer.opp.projekt.domain.Rezervacija;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuJednokratnuDTO;
import hr.fer.opp.projekt.service.RezervacijaService;
import hr.fer.opp.projekt.service.AutomobilService;
import hr.fer.opp.projekt.service.exceptions.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

public class RezervacijaServiceJpa implements RezervacijaService {

    @Autowired
    private AutomobilRepository automobilRepository;

    @Autowired
    private ParkiralisteRepository parkiralisteRepository;

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private AutomobilService automobilService;


    @Override
    public List<Rezervacija> listAll() { return rezervacijaRepository.findAll(); }

    @Override
    public Rezervacija fetch(Long rezervacijaId) {
        return rezervacijaRepository.findById(rezervacijaId).orElseThrow(
                () -> new EntityMissingException(Rezervacija.class, rezervacijaId)
        );
    }

    @Override
    public Rezervacija createRezervacijaJednokratna(DodajRezervacijuJednokratnuDTO dto, Long korisnikID) {
        Long rezervacijaId = dto.getId();
        Long parkingID = dto.getParkingID();
        Long autoID = dto.getAutoID();
        Date vrijemePoc = dto.getVrijemePocetka();
        Date trajanje = dto.getTrajanje();

        Assert.notNull(rezervacijaId, "Potrebno je predati ID rezervacije.");
        Assert.notNull(vrijemePoc, "Potrebno je predati vrijeme pocetka rezervacije.");
        Assert.notNull(trajanje, "Potrebno je predati trajanje rezervacije.");
        Assert.notNull(autoID, "Potrebno je predati ID automobila.");
        Assert.notNull(korisnikID, "Greska kod izvlacenja ID-a korisnika.");

        //provjeravamo odgovara li ID vlasnika ID-u korisnika
        String vlasnikID = automobilService.fetch(autoID).getIme();
        Assert.isTrue(korisnikID.equals(vlasnikID));

        Rezervacija nova = new Rezervacija(autoID, parkingID, vrijemePoc,  trajanje);
        rezervacijaRepository.save(nova);
        return nova;
    }

    @Override
    public Rezervacija deleteRezervacija(Long id) {
        Rezervacija rezervacija = fetch(id);
        rezervacijaRepository.delete(rezervacija);
        return rezervacija;
    }
}
