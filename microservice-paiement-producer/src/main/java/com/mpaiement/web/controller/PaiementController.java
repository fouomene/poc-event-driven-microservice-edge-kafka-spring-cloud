package com.mpaiement.web.controller;

import com.mpaiement.beans.CommandeBean;
import com.mpaiement.dao.PaiementDao;
import com.mpaiement.model.Paiement;
import com.mpaiement.proxies.MicroserviceCommandeProxy;
import com.mpaiement.web.exceptions.PaiementExistantException;
import com.mpaiement.web.exceptions.PaiementImpossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PaiementController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PaiementDao paiementDao;

    @Autowired
    MicroserviceCommandeProxy microserviceCommandeProxy;

    @Autowired
    KafkaTemplate<String,CommandeBean> commandeKafkaTemplate;

    /*
    * Opération pour enregistrer un paiement et notifier le microservice commandes pour mettre à jour le statut de la commande en question
    **/
    @PostMapping(value = "/paiement")
    public ResponseEntity<Paiement>  payerUneCommande(@RequestBody Paiement paiement){

         log.info("Paiement d'une commande");
        //Vérifions s'il y a déjà un paiement enregistré pour cette commande
        Paiement paiementExistant = paiementDao.findByidCommande(paiement.getIdCommande());
        if(paiementExistant != null) throw new PaiementExistantException("Cette commande est déjà payée");

        //Enregistrer le paiement
        Paiement nouveauPaiement = paiementDao.save(paiement);

        // si le DAO nous retourne null c'est que il ya eu un problème lors de l'enregistrement
        if(nouveauPaiement == null) throw new PaiementImpossibleException("Erreur, impossible d'établir le paiement, réessayez plus tard");

        // On envoie(producer) de la commande(id) via le broker Kafka pour la mise à jour de la commande par le consumer
        commandeKafkaTemplate.send("paiement",new CommandeBean(paiement.getIdCommande()));

        //on renvoi 201 CREATED pour notifier le client au le paiement à été enregistré
        return new ResponseEntity<Paiement>(nouveauPaiement, HttpStatus.CREATED);

    }




}
