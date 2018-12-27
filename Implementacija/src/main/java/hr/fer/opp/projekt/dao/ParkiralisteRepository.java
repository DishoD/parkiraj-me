package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.domain.Parkiraliste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkiralisteRepository extends JpaRepository<Parkiraliste, Long> {
    Optional<Parkiraliste> findById(Long id);
}
