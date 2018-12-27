package hr.fer.opp.projekt.dao;

import hr.fer.opp.projekt.domain.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    int countByEmail(String email);
    Optional<Administrator> findByEmail(String email);
}
