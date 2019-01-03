package hr.fer.opp.projekt.rest;

import hr.fer.opp.projekt.domain.Korisnik;
import hr.fer.opp.projekt.domain.Tvrtka;
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

    @Value("${opp.admin.password}")
    private String adminPasswordHash;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TvrtkaService tvrtkaService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username))
            return new User(username, "{bcrypt}" + adminPasswordHash, commaSeparatedStringToAuthorityList(Roles.ADMIN));
        else if (korisnikService.containsKorisnik(username)) {
            Korisnik korisnik = korisnikService.fetchKorisnik(username);
            System.out.println("pronaden korisnik: " + korisnik.getPasswordHash());
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