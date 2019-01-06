package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.TvrtkaRepository;
import hr.fer.opp.projekt.domain.Parkiraliste;
import hr.fer.opp.projekt.domain.Tvrtka;
import hr.fer.opp.projekt.service.*;
import hr.fer.opp.projekt.service.exceptions.EntityMissingException;
import hr.fer.opp.projekt.service.exceptions.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TvrtkaServiceJpa implements TvrtkaService {

    @Autowired
    private TvrtkaRepository tvrtkaRepository;

    @Autowired
    private ParkiralisteService parkiralisteService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private AdministratorService administratorService;

    @Override
    public List<Tvrtka> listAll() {
        return tvrtkaRepository.findAll();
    }

    @Override
    public Tvrtka createTvrtka(Tvrtka tvrtka) {
        Assert.notNull(tvrtka, "Tvrtka ne smije biti null.");
        Assert.isNull(tvrtka.getId(), "Tvrtka ne smije imati id, on se automatski generira.");

        Util.checkField(tvrtka.getEmail(), "email");
        Assert.isTrue(tvrtka.getEmail().matches(Util.EMAIL_FORMAT), "Email nije valjan.");
        Assert.isTrue(Util.checkIfUniqueEmail(tvrtka.getEmail(), korisnikService, administratorService, this),
                "Email se već koristi.");

        Util.checkField(tvrtka.getIme(), "ime");
        Util.checkField(tvrtka.getAdresaSjedista(), "adresa sjedista");

        Util.checkField(tvrtka.getOib(), "oib");
        Assert.isTrue(tvrtka.getOib().matches(Util.OIB_FORMAT), "OIB mora imati 11 znamenaka");
        if (tvrtkaRepository.countByOib(tvrtka.getOib()) > 0) {
            throw new RequestDeniedException("Tvrtka već postoji");
        }

        Util.checkField(tvrtka.getPasswordHash(), "password");
        tvrtka.setPasswordHash(new BCryptPasswordEncoder().encode(tvrtka.getPasswordHash()));
        return tvrtkaRepository.save(tvrtka);
    }

    @Override
    public Tvrtka fetchTvrtka(Long id) {
        return tvrtkaRepository.findById(id).orElseThrow(
                () -> new EntityMissingException(Tvrtka.class, id)
        );
    }

    @Override
    public Tvrtka fetchTvrtka(String email) {
        return tvrtkaRepository.findByEmail(email).orElseThrow(
                () -> new EntityMissingException(Tvrtka.class, email)
        );
    }

    @Override
    public boolean containsTvrtka(String email) {
        return tvrtkaRepository.findByEmail(email).isPresent();
    }

    @Override
    public Boolean deleteTvrtka(String email) {
        Tvrtka tvrtka = fetchTvrtka(email);
        Set<Parkiraliste> parkiralista = tvrtka.getParkiralista();

        //so filthy, ugh
        Set<Long> parkiralisteIDs = new HashSet<>();
        for (Parkiraliste p : parkiralista) {
            parkiralisteIDs.add(p.getId());
        }
        for (Long id : parkiralisteIDs) {
            parkiralisteService.deleteParkiraliste(id, tvrtka.getId());
        }
        tvrtkaRepository.delete(tvrtka);
        return true;
    }
}
