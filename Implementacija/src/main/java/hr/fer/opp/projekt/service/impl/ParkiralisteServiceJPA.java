package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.ParkiralisteRepository;
import hr.fer.opp.projekt.dao.TvrtkaRepository;
import hr.fer.opp.projekt.domain.Parkiraliste;
import hr.fer.opp.projekt.domain.Tvrtka;
import hr.fer.opp.projekt.service.ParkiralisteService;
import hr.fer.opp.projekt.service.TvrtkaService;
import hr.fer.opp.projekt.service.Util;
import hr.fer.opp.projekt.service.exceptions.EntityMissingException;
import hr.fer.opp.projekt.service.exceptions.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

@Service
public class ParkiralisteServiceJPA implements ParkiralisteService {

    @Autowired
    private ParkiralisteRepository parkiralisteRepository;

    @Autowired
    private TvrtkaService tvrtkaService;

    @Autowired
    private TvrtkaRepository tvrtkaRepository;

    @Override
    public List<Parkiraliste> listAll() {
        return parkiralisteRepository.findAll();
    }

    @Override
    public List<Parkiraliste> listByTvrtkaID(Long tvrtkaID) {
        return parkiralisteRepository.findAllByTvrtkaID(tvrtkaID);
    }

    @Override
    public Parkiraliste createParkiraliste(Parkiraliste parkiraliste) {
        Assert.notNull(parkiraliste, "Parkiraliste ne smije biti null.");
        Assert.isNull(parkiraliste.getId(), "Parkiraliste ne smije imati id, on se automatski generira.");

        Assert.notNull(parkiraliste.getTvrtkaID(), "TvrtkaID ne smije biti null.");

        Util.checkField(parkiraliste.getIme(), "ime");
        Util.checkField(parkiraliste.getLokacija(), "lokacija");

        Assert.notNull(parkiraliste.getKapacitet(), "Kapacitet ne smije biti null.");
        Assert.notNull(parkiraliste.getCijena(), "Cijena ne smije biti null.");

        Tvrtka tvrtka = tvrtkaService.fetchTvrtka(parkiraliste.getTvrtkaID());
        tvrtka.getParkiralista().add(parkiraliste);
        parkiralisteRepository.save(parkiraliste);
        tvrtkaRepository.save(tvrtka);
        return parkiraliste;
    }

    @Override
    public Boolean deleteParkiraliste(Long parkiralisteID, Long tvrtkaID) {
        Parkiraliste parkiraliste = fetchParkiraliste(parkiralisteID);
        if(!parkiraliste.getTvrtkaID().equals(tvrtkaID)){
            throw new RequestDeniedException("To parkiraliste vam ne pripada.");
        }
        Set<Parkiraliste> set = tvrtkaRepository.findById(tvrtkaID).get().getParkiralista();
        set.remove(parkiraliste);
        parkiralisteRepository.delete(parkiraliste);
        return true;
    }


    @Override
    public Parkiraliste fetchParkiraliste(Long parkiralisteID) {
        return parkiralisteRepository.findById(parkiralisteID).orElseThrow(()->new EntityMissingException(Parkiraliste.class, parkiralisteID));
    }
}
