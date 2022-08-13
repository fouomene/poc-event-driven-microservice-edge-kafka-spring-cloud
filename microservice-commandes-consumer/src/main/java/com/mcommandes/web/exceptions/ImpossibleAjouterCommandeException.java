package com.mcommandes.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
Si on en arrive à cette exception, c'est qu'il y a eu une erreur interne
Si la requête était mal formée, elle aurait déclenché 400 Bad Request automatiquement
**/

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImpossibleAjouterCommandeException extends RuntimeException {

    public ImpossibleAjouterCommandeException(String message) {
        super(message);
    }
}
