package fr.free.francois.olivier.magicmanagerws.controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fr.free.francois.olivier.magicmanagerws.model.Card;
import fr.free.francois.olivier.magicmanagerws.services.CardService;

/**
 * Cette classe est le controller REST pour les cartes.
 * Il permet grâce à l'url définie de pouvoir faire des requetes sur les cartes :
 * - afficher toutes les cards (format json)
 * - afficher une card (format json)
 * - ajouter une card
 * - modifier une card
 * - supprimer une card
 * 
 * @author Olivier
 *
 */
@RestController
@RequestMapping(value="/api")
public class CardController {

	/**
	 * Service qui va s'occuper de la manipulation et de 
	 * la recuperation des données Card
	 */
    @Autowired
    private CardService cardService;
    
    /**
     * Le Logger pour gérer les logs
     */
    private static Logger logger  = LogManager.getLogger(CardController.class);

    /**
     * Recupere toutes les cards au format json
     * 
     * url ex : /api/cards
     * 
     * @return Les informations sur les cards
     */
    @RequestMapping(value="/cards", method=RequestMethod.GET)
    public ResponseEntity<Set<Card>> getCards() {
    	Set<Card> cards = cardService.findAll();
    	logger.warn("Toutes les cartes : " + cards );
    	if ( cards.isEmpty() ) {
    		return new ResponseEntity<Set<Card>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<Set<Card>>(cards, HttpStatus.OK);
    }
    
    /**
     * Recupere les informations sur les cards au format json
     * url ex : /api/cardByName/pierre
     *  
     * @param name
     * 			un morceau du nom ou le nom complet de la card a rechercher
     * 
     * @return Les informations sur les cards
     */
    @RequestMapping(value="/cardByName/{name}", method=RequestMethod.GET)
    public ResponseEntity<List<Card>> getCardByName(@PathVariable String name){
    	List<Card> cards = cardService.findByName(name);
    	logger.warn("Toutes les cartes avec name like" + name + " : " + cards );
    	if ( cards.isEmpty() ) {
    		return new ResponseEntity<List<Card>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<Card>>(cards, HttpStatus.OK);
    }
    
    /**
     * Recupere les informations sur une card au format json
     * url ex : /api/card/1
     *  
     * @param id
     * 			id de la card a rechercher
     * 
     * @return Les informations sur la card
     */
    @RequestMapping(value="/card/{id}", method=RequestMethod.GET)
    public ResponseEntity<Card> getCard(@PathVariable int id){
    	logger.warn("Card id = " + id);
    	Card card = cardService.findById(id);
    	if ( card instanceof Card ) {
    		logger.warn("Card id = " + id + " => " + card );
        	return new ResponseEntity<Card>(card, HttpStatus.OK);
    	} else {
    		logger.warn( "Card Not Found" );
        	return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
    	}
    }

    /**
     * Ajoute une Card dans la base
     * 
     * @param Card
     * 			La Card a ajouter dans la base
     * 
     * @return une ResponseEntity
     */
    @RequestMapping(value="/createCard", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> addCard( @RequestBody Card card, UriComponentsBuilder ucBuilder ) {
    	
    	/* On met a jour la disponibility et la date d'ajout */
    	card.setDisponibility( card.getQuantity() );
    	card.setDate( new Date(System.currentTimeMillis()) );
    	
    	if(cardService.isCardExist(card)) {
    		logger.warn( "Une carte avec le même nom français, anglais et la même édition existe déjà.");
    		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    	}
    	
    	cardService.saveCard( card );
    	logger.warn( "Card Created !\n" + card );
		
    	/* Pour que l'url vers la carte creee soit afficher dans le header 
    	 * de retour de la requete */
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(ucBuilder.path("/api/card/{id}").buildAndExpand(card.getIdCard()).toUri());
    	return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    /**
     * Met a jour une Card dans la base
     * 
     * @param Card
     * 			La Card a mettre a jour
     * 
     * @return La card
     * 
     */
    @RequestMapping(value="/updateCard", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Card> updateCard( @RequestBody Card card ) {
    	
    	Card currentCard = cardService.findById(card.getIdCard());
    	if (currentCard instanceof Card) {
    		/* On met a jour la disponibility et la date d'ajout */
    		int tmpQuantity = currentCard.getQuantity();
    		int deltaQuantity = card.getQuantity() - tmpQuantity;
    		int newDisponibility = currentCard.getDisponibility() + deltaQuantity;
    		card.setDisponibility( newDisponibility );
        	card.setDate( new Date(System.currentTimeMillis()) );
        	
        	cardService.updateCard( card );
        	logger.warn("Update Card success.\n " + card);
        	
        	return new ResponseEntity<Card>(card, HttpStatus.OK);
    	} else {
    		logger.warn("Cette carte n'existe pas. Impossible de mettre a jour.\n " + card);
    		return new ResponseEntity<Card>(card, HttpStatus.NOT_FOUND);
    	}
    	
    }
    
    /**
     * Supprime une Card dans la base
     * 
     * @param Card
     * 			La Card a supprimer
     * 
     * @return Un Status (succes ou erreur)
     */
    @RequestMapping(value="/deleteCard", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> deleteCard(@ RequestBody Card card ) {
    	
    	Card currentCard = cardService.findById(card.getIdCard());
    	if (currentCard instanceof Card) {
    		cardService.deleteCard( card );
        	logger.warn("Card supprimée avec success.\n " + card);
        	return new ResponseEntity<Void>(HttpStatus.OK);
    	} else {
    		logger.warn("Cette carte n'existe pas. Impossible de supprimer.\n " + card);
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    	}
    
    }
}
