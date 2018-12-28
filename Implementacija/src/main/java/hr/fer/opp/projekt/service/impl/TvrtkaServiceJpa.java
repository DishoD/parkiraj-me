package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.TvrtkaRepository;
import hr.fer.opp.projekt.domain.Tvrtka;
import hr.fer.opp.projekt.service.EntityMissingException;
import hr.fer.opp.projekt.service.RequestDeniedException;
import hr.fer.opp.projekt.service.TvrtkaService;
import hr.fer.opp.projekt.service.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TvrtkaServiceJpa implements TvrtkaService {

    @Autowired
    private TvrtkaRepository tvrtkaRepository;

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

        Util.checkField(tvrtka.getIme(), "ime");
        Util.checkField(tvrtka.getAdresaSjedista(), "adresa sjedista");

        Util.checkField(tvrtka.getOib(), "oib");
        Assert.isTrue(tvrtka.getOib().matches(Util.OIB_FORMAT), "OIB mora imati 11 znamenaka");
        if (tvrtkaRepository.countByOib(tvrtka.getOib()) > 0) {
            throw new RequestDeniedException("Tvrtka vec postoji");
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
}
