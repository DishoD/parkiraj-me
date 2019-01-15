package hr.fer.opp.projekt.service;

import hr.fer.opp.projekt.domain.Administrator;

import java.util.List;

public interface AdministratorService {
    List<Administrator> listAll();
    Administrator createAdministrator(Administrator administrator);
    Administrator fetchAdministrator(String email);
    boolean containsAdministrator(String email);
}
