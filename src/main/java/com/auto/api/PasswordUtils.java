package com.auto.api;

/**
 * @author auto.yin<auto.yin @ gmail.com>
 * 2018-06-19
 * @Description: <p></p>
 */
public class PasswordUtils {

    @UseCase()
    public boolean test(String password) {
        return true;
    }

    @UseCase(id=1,description="this is 1.")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }

    @UseCase(id =2)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }
}
