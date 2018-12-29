package hr.fer.opp.projekt.rest;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

public class Roles {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String COMPANY = "ROLE_COMPANY";

    public static final List<GrantedAuthority> adminAuthority = commaSeparatedStringToAuthorityList(ADMIN);
    public static final List<GrantedAuthority> userAuthority = commaSeparatedStringToAuthorityList(USER);
    public static final List<GrantedAuthority> companyAuthority = commaSeparatedStringToAuthorityList(COMPANY);
}
