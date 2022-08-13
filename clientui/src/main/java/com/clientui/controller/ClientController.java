package com.clientui.controller;

import com.clientui.beans.CommandeBean;
import com.clientui.beans.PaiementBean;
import com.clientui.beans.ProductBean;
import com.clientui.proxies.MicroserviceCommandeProxy;
import com.clientui.proxies.MicroservicePaiementProxy;
import com.clientui.proxies.MicroserviceProduitsProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class ClientController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MicroserviceProduitsProxy ProduitsProxy;

    @Autowired
    private MicroserviceCommandeProxy CommandesProxy;

    @Autowired
    private MicroservicePaiementProxy paiementProxy;


    /*
    * Étape (1)
    * Opération qui récupère la liste des produits et on les affichent dans la page d'accueil.
    * Les produits sont récupérés grâce à ProduitsProxy
    * On fini par rentourner la page Accueil.html à laquelle on passe la liste d'objets "produits" récupérés.
    * */
    @RequestMapping("/")
    public String accueil(Model model){

        log.info("Envoi requête vers microservice MProduit pour la liste des produits");
        List<ProductBean> produits =  ProduitsProxy.listeDesProduits();

        model.addAttribute("produits", produits);

        return "Accueil";
    }

    /*
    * Étape (2)
    * Opération qui récupère les détails d'un produit
    * On passe l'objet "produit" récupéré et qui contient les détails en question à  FicheProduit.html
    * */
    @RequestMapping("/details-produit/{id}")
    public String ficheProduit(@PathVariable int id,  Model model){

        log.info("Envoi requête vers microservice MProduit pour detail d'un produit");
        ProductBean produit = ProduitsProxy.recupererUnProduit(id);

        model.addAttribute("produit", produit);

        return "FicheProduit";
    }

    /*
    * Étape (3) et (4)
    * Opération qui fait appel au microservice de commande pour placer une commande et récupérer les détails de la commande créée
    * */
    @RequestMapping(value = "/commander-produit/{idProduit}/{montant}")
    public String passerCommande(@PathVariable int idProduit, @PathVariable Double montant,  Model model){

        log.info("Envoi requête vers microservice MCommande pour passer une commande");
        CommandeBean commande = new CommandeBean();

        //On renseigne les propriétés de l'objet de type CommandeBean que nous avons crée
        commande.setProductId(idProduit);
        commande.setQuantite(1);
        commande.setDateCommande(new Date());

        log.info("Envoi requête vers microservice MCommande pour effectuer une commande");
        //appel du microservice commandes grâce à Feign et on récupère en retour les détails de la commande créée, notamment son ID (étape 4).
        CommandeBean commandeAjoutee = CommandesProxy.ajouterCommande(commande);

        //on passe à la vue l'objet commande et le montant de celle-ci afin d'avoir les informations nécessaire pour le paiement
        model.addAttribute("commande", commandeAjoutee);
        model.addAttribute("montant", montant);

        return "Paiement";
    }

    /*
    * Étape (5)
    * Opération qui fait appel au microservice de paiement pour traiter un paiement
    * */
    @RequestMapping(value = "/payer-commande/{idCommande}/{montantCommande}")
    public String payerCommande(@PathVariable int idCommande, @PathVariable Double montantCommande, Model model){

        log.info("Envoi requête vers microservice MPaiement pour effectuer un paiement");
        PaiementBean paiementAExcecuter = new PaiementBean();

        //on reseigne les détails du produit
        paiementAExcecuter.setIdCommande(idCommande);
        paiementAExcecuter.setMontant(montantCommande);
        paiementAExcecuter.setNumeroCarte(numcarte()); // on génère un numéro au hasard pour simuler une CB

        log.info("Envoi requête vers microservice MPaiement");
        // On appel le microservice et (étape 7) on récupère le résultat qui est sous forme ResponseEntity<PaiementBean> ce qui va nous permettre de vérifier le code retour.
        ResponseEntity<PaiementBean> paiement = paiementProxy.payerUneCommande(paiementAExcecuter);

        Boolean paiementAccepte = false;
        //si le code est autre que 201 CREATED, c'est que le paiement n'a pas pu aboutir.
        if(paiement.getStatusCode() == HttpStatus.CREATED)
                paiementAccepte = true;

        model.addAttribute("paiementOk", paiementAccepte); // on envoi un Boolean paiementOk à la vue

        return "confirmation";
    }

    //Génére une serie de 16 chiffres au hasard pour simuler vaguement une CB
    private Long numcarte() {

        return ThreadLocalRandom.current().nextLong(1000000000000000L,9000000000000000L );
    }
}
