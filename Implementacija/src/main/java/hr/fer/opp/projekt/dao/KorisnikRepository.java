package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.domain.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    int countByOib(String oib);
}
