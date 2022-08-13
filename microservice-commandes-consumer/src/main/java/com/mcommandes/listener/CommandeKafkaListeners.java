package com.mcommandes.listener;

import com.mcommandes.dao.CommandesDao;
import com.mcommandes.model.Commande;
import com.mcommandes.model.beans.CommandeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CommandeKafkaListeners {

    private static final Logger log = LoggerFactory.getLogger(CommandeKafkaListeners.class);

    @Autowired
    private CommandesDao commandesDao;

    @KafkaListener(
            topics = "paiement",
            groupId = "afrinnov2",
            containerFactory = "commandeFactory"
    )
    void listener(CommandeBean commandeBean) {

        log.info("Reception de la commandeBean envoyé par le microservice paiement producteur id="+commandeBean.getId());
        //On récupère la commande correspondant à ce paiement
        Optional<Commande> commandeReq = commandesDao.findById(commandeBean.getId());

        //commandeReq.get() permet d'extraire l'objet de type Commande de Optional
        if (commandeReq.isPresent()) {
            Commande commande = commandeReq.get();

            //on met à jour l'objet pour marquer la commande comme étant payée
            commande.setCommandePayee(true);

            // mettre à jour le status de la commande.
            commandesDao.save(commande);
        }

    }
}
