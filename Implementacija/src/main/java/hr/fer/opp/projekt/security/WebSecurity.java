package hr.fer.opp.projekt.security;

import hr.fer.opp.projekt.domain.Administrator;
import hr.fer.opp.projekt.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private KorisnikUserDetailsService korisnikUserDetailsService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(korisnikUserDetailsService);

        Administrator administrator = new Administrator();
        administrator.setEmail("admin@admin.com");
        administrator.setPasswordHash("pass");
        administratorService.createAdministrator(administrator);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .successForwardUrl("/login?success")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

}
