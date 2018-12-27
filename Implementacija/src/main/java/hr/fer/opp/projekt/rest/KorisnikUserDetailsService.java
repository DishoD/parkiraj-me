package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.dao.KorisnikRepository;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.service.EntityMissingException;
import hr.fer.opp.projekt.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
public class KorisnikUserDetailsService implements UserDetailsService {

    @Value("${opp.admin.password}")
    private String adminPasswordHash;

    @Autowired
    private KorisnikService korisnikService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username))
            return new User(username, adminPasswordHash, commaSeparatedStringToAuthorityList(Roles.ADMIN));
        else if (korisnikService.containsKorisnik(username)) {
            Korisnik korisnik = korisnikService.fetchKorisnik(username);
            return new User(username, korisnik.getPasswordHash(), commaSeparatedStringToAuthorityList(Roles.USER));
        }
        else throw new UsernameNotFoundException("Ne postoji korisnik: " + username);
    }

}