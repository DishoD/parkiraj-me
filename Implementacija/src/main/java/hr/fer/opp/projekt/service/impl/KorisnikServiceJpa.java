package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.KorisnikRepository;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.EntityMissingException;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class KorisnikServiceJpa implements KorisnikService {

    private static final String OIB_FORMAT = "[0-9]{11}";
    private static final String EMAIL_FORMAT = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

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
        if (korisnikRepository.countByOib(korisnik.getOib()) > 0) {
            throw new RequestDeniedException("Korisnik vec postoji");
        }

//        Assert.isTrue(korisnikRepository.countByOib(korisnik.getOib()) == 0, "Korisnik vec postoji");
        String oib = korisnik.getOib();
        Assert.hasText(oib, "Korisnik mora imati OIB");
        Assert.isTrue(oib.matches(OIB_FORMAT), "OIB mora imati 11 znamenaka");
        Assert.isTrue(creditCardNumberIsValid(korisnik.getBrojKreditneKartice()), "Broj kreditne kartice nije valjan.");
        Assert.isTrue(korisnik.getEmail().matches(EMAIL_FORMAT), "Email nije valjan.");
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
