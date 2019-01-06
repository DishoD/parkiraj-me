package hr.fer.opp.projekt.security;

import hr.fer.opp.projekt.domain.Administrator;
import hr.fer.opp.projekt.domain.Rezervacija;
import hr.fer.opp.projekt.rest.dto.DodajRezervacijuTrajnuDTO;
import hr.fer.opp.projekt.service.AdministratorService;
import hr.fer.opp.projekt.service.RezervacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
        if (administratorService.containsAdministrator("admin@admin.com"))
            return;

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
                .failureForwardUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }

}
