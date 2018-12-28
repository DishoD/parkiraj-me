package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.KorisnikRepository;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.EntityMissingException;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.RequestDeniedException;
import hr.fer.opp.projekt.service.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class KorisnikServiceJpa implements KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public List<Korisnik> listAll() {
        return korisnikRepository.findAll();
    }

    @Override
    public Korisnik createKorisnik(Korisnik korisnik) {
        Assert.notNull(korisnik, "Korisnik ne smije biti null.");
        Assert.isNull(korisnik.getId(), "Korisnik ne smije imati id, on se automatski generira.");

        Util.checkField(korisnik.getEmail(), "email");
        Assert.isTrue(korisnik.getEmail().matches(Util.EMAIL_FORMAT), "Email nije valjan.");

        Util.checkField(korisnik.getIme(), "ime");
        Util.checkField(korisnik.getPrezime(), "prezime");

        Util.checkField(korisnik.getOib(), "oib");
        Assert.isTrue(korisnik.getOib().matches(Util.OIB_FORMAT), "OIB mora imati 11 znamenaka");
        if (korisnikRepository.countByOib(korisnik.getOib()) > 0) {
            throw new RequestDeniedException("Korisnik vec postoji");
        }

        Assert.isTrue(creditCardNumberIsValid(korisnik.getBrojKreditneKartice()), "Broj kreditne kartice nije valjan.");

        Util.checkField(korisnik.getPasswordHash(), "password");
        korisnik.setPasswordHash(new BCryptPasswordEncoder().encode(korisnik.getPasswordHash()));
        return korisnikRepository.save(korisnik);
    }

    @Override
    public Korisnik fetchKorisnik(Long id) {
        return korisnikRepository.findById(id).orElseThrow(
                () -> new EntityMissingException(Korisnik.class, id)
        );
    }

    @Override
    public Korisnik fetchKorisnik(String email) {
        return korisnikRepository.findByEmail(email).orElseThrow(
                () -> new EntityMissingException(Korisnik.class, email)
        );
    }

    @Override
    public boolean containsKorisnik(String email) {
        return korisnikRepository.findByEmail(email).isPresent();
    }

    //dummy CC broj koji prolazi provjeru: 54372012565022
    private boolean creditCardNumberIsValid(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

}
