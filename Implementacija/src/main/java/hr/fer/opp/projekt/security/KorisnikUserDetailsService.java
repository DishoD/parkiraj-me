package hr.fer.opp.projekt.security;

import hr.fer.opp.projekt.domain.Administrator;
import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.domain.Tvrtka;
import hr.fer.opp.projekt.rest.Roles;
import hr.fer.opp.projekt.service.AdministratorService;
import hr.fer.opp.projekt.service.KorisnikService;
import hr.fer.opp.projekt.service.TvrtkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
public class KorisnikUserDetailsService implements UserDetailsService {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TvrtkaService tvrtkaService;

    @Autowired
    private AdministratorService administratorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (administratorService.containsAdministrator(username)){
            Administrator administrator = administratorService.fetchAdministrator(username);
            return new User(username, "{bcrypt}" + administrator.getPasswordHash(), commaSeparatedStringToAuthorityList(Roles.ADMIN));
        }
        else if (korisnikService.containsKorisnik(username)) {
            Korisnik korisnik = korisnikService.fetchKorisnik(username);
            return new User(username, "{bcrypt}" + korisnik.getPasswordHash(), commaSeparatedStringToAuthorityList(Roles.USER));
        }
        else if(tvrtkaService.containsTvrtka(username)){
            Tvrtka tvrtka = tvrtkaService.fetchTvrtka(username);
            return new User(username, "{bcrypt}" + tvrtka.getPasswordHash(), commaSeparatedStringToAuthorityList(Roles.COMPANY));
        }
        else throw new UsernameNotFoundException("Ne postoji korisnik: " + username);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}