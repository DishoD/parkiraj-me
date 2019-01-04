package hr.fer.opp.projekt.service.impl;

import hr.fer.opp.projekt.dao.AdministratorRepository;
import hr.fer.opp.projekt.domain.Administrator;
import hr.fer.opp.projekt.service.AdministratorService;
import hr.fer.opp.projekt.service.Util;
import hr.fer.opp.projekt.service.exceptions.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AdministratorServiceJpa implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public List<Administrator> listAll() {
        return administratorRepository.findAll();
    }

    @Override
    public Administrator createAdministrator(Administrator administrator) {
        Assert.notNull(administrator, "Administrator ne smije biti null.");
        Assert.isNull(administrator.getId(), "ID mora biti null.");

        Util.checkField(administrator.getEmail(), "email");
        Assert.isTrue(administrator.getEmail().matches(Util.EMAIL_FORMAT), "Email nije valjan.");

        Util.checkField(administrator.getPasswordHash(), "password");
        administrator.setPasswordHash(new BCryptPasswordEncoder().encode(administrator.getPasswordHash()));

        return administratorRepository.save(administrator);
    }

    @Override
    public Administrator fetchAdministrator(String email) {
        return administratorRepository.findByEmail(email).orElseThrow(() ->
            new EntityMissingException(Administrator.class, email)
        );
    }

    @Override
    public boolean containsAdministrator(String email) {
        return administratorRepository.findByEmail(email).isPresent();
    }
}
