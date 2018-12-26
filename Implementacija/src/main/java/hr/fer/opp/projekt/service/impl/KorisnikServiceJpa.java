package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.KorisnikRepository;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class KorisnikServiceJpa implements KorisnikService {

    private static final String OIB_FORMAT = "[0-9]{11}";

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public List<Korisnik> listAll() {
        return korisnikRepository.findAll();
    }

    @Override
    public Korisnik createKorisnik(Korisnik korisnik) {
        Assert.notNull(korisnik, "Korisnik object must be given");
        Assert.isNull(korisnik.getId(), "Korisnik ne smije imati id");
        if (korisnikRepository.countByOib(korisnik.getOib()) > 0) {
            throw new RequestDeniedException("Korisnik vec postoji");
        }

//        Assert.isTrue(korisnikRepository.countByOib(korisnik.getOib()) == 0, "Korisnik vec postoji");
        String oib = korisnik.getOib();
        Assert.hasText(oib, "Korisnik mora imati OIB");
        Assert.isTrue(oib.matches(OIB_FORMAT), "OIB mora imati 11 znamenaka");


        return korisnikRepository.save(korisnik);
    }

}
