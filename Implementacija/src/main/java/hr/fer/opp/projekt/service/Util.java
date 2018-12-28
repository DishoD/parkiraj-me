package hr.fer.opp.projekt.service;

import org.springframework.util.Assert;

public class Util {

    public static final String OIB_FORMAT = "[0-9]{11}";
    public static final String EMAIL_FORMAT = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    /**
     * Provjerava je li dani parametar non-null i sadrzi li valjani text.
     *
     * @param parameter     parametar koji provjeravamo
     * @param parameterName ime parametra u slucaju greske
     */
    public static void checkField(String parameter, String parameterName) {
        Assert.hasText(parameter, "Polje \"" + parameterName + "\" ne smije biti null i mora sadrzavati text.");
    }
}
