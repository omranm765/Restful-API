package de.rest.RestApiProjekt;

public class Validator {

    public static void check(boolean condition, String message) throws ShopException {
        if (condition){
            throw new ShopException(message);
        }
    }
}
