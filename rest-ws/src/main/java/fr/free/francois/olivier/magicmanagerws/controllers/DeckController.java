package fr.free.francois.olivier.magicmanagerws.controllers;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fr.free.francois.olivier.magicmanagerws.model.Deck;
import fr.free.francois.olivier.magicmanagerws.model.DeckLine;
import fr.free.francois.olivier.magicmanagerws.model.SideboardLine;
import fr.free.francois.olivier.magicmanagerws.services.DeckService;

/**
 * Cette classe est le controller REST pour les decks.
 * Il permet grâce à l'url définie de pouvoir faire des requetes sur les decks :
 * - afficher tous les decks (format json)
 * - afficher un deck (format json)
 * - afficher toutes les cartes d'un deck (format json)
 * - ajouter un deck
 * - modifier un deck
 * - supprimer un deck
 * 
 * @author Olivier
 *
 */
@RestController
@RequestMapping(value="/api")
public class DeckController {

	@Autowired
    private DeckService deckService;
	
	/**
     * Le Logger pour gérer les logs
     */
    private static Logger logger  = LogManager.getLogger(DeckController.class);
    
    /**
     * Recupere tous les decks au format json
     * 
     * url ex : /api/decks
     * 
     * @return Les informations sur les decks
     */
    @RequestMapping(value="/decks", method=RequestMethod.GET)
    public ResponseEntity<Set<Deck>> getDecks() {
    	Set<Deck> decks = deckService.findAll();
    	logger.warn("Tous les decks : " + decks );
    	if ( decks.isEmpty() ) {
    		return new ResponseEntity<Set<Deck>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<Set<Deck>>(decks, HttpStatus.OK);
    }
    
	/**
     * Recupere les informations sur le deck au format json
     * url ex : /api/deck/1
     *  
     * @param id
     * 			id du deck a rechercher
     * 
     * @return Les informations sur le deck
     */
    @RequestMapping(value="/deck/{id}", method=RequestMethod.GET)
    public ResponseEntity<Deck> getDeck(@PathVariable int id){
    	logger.warn("Deck id = " + id);
    	Deck deck = deckService.findById(id);
    	if ( deck instanceof Deck ) {
    		logger.warn("Deck id = " + id + " => " + deck );
        	return new ResponseEntity<Deck>(deck, HttpStatus.OK);
    	} else {
    		logger.warn( "Deck Not Found" );
        	return new ResponseEntity<Deck>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Recupere les decks au format json
     * url ex : /api/deck/folie
     *  
     * @param name
     * 			name du deck a rechercher
     * 
     * @return Les Decks
     */
    @RequestMapping(value="/deckByName/{name}", method=RequestMethod.GET)
    public ResponseEntity<List<Deck>> getDeckByName(@PathVariable String name){
    	List<Deck> decks = deckService.findByName(name);
    	logger.warn("Tous les decks avec name like" + name + " : " + decks );
    	if ( decks.isEmpty() ) {
    		return new ResponseEntity<List<Deck>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<Deck>>(decks, HttpStatus.OK);
    }
    
    /**
     * Recupere les cartes d'un deck au format json
     * url ex : /api/deck/1/cards
     *  
     * @param id
     * 			id du deck a rechercher
     * 
     * @return Les informations sur le deck
     */
    @RequestMapping(value="/deck/{id}/cards", method=RequestMethod.GET)
    public ResponseEntity<Set<DeckLine>> getDeckLinesByIdDeck(@PathVariable int id) {
    	
    	Deck deck = deckService.findById(id);
    	
    	if ( deck instanceof Deck ) {
    		logger.warn("Deck id = " + id + " => " + deck );
    		Set<DeckLine> deckLines = deck.getDeckLines();
    		logger.warn("Toutes les decklines avec deck " + deck.getName() + " : " + deckLines );
        	if ( deckLines.isEmpty() ) {
        		return new ResponseEntity<Set<DeckLine>>(HttpStatus.NO_CONTENT);
        	}
        	return new ResponseEntity<Set<DeckLine>>(deckLines, HttpStatus.OK);
    		
    	} else {
    		logger.warn( "Deck Not Found" );
        	return new ResponseEntity<Set<DeckLine>>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Recupere les cartes d'un deck au format json
     * url ex : /api/deck/1/cards
     *  
     * @param id
     * 			id du deck a rechercher
     * 
     * @return Les informations sur le deck
     */
    @RequestMapping(value="/sideboard/{id}/cards", method=RequestMethod.GET)
    public ResponseEntity<Set<SideboardLine>> getSideboardLinesByIdDeck(@PathVariable int id) {
    	
    	Deck deck = deckService.findById(id);
    	
    	if ( deck instanceof Deck ) {
    		logger.warn("Deck id = " + id + " => " + deck );
    		Set<SideboardLine> sideboardLines = deck.getSideboardLines();
    		logger.warn("Toutes les sideboardLines avec deck " + deck.getName() + " : " + sideboardLines );
        	if ( sideboardLines.isEmpty() ) {
        		return new ResponseEntity<Set<SideboardLine>>(HttpStatus.NO_CONTENT);
        	}
        	return new ResponseEntity<Set<SideboardLine>>(sideboardLines, HttpStatus.OK);
    		
    	} else {
    		logger.warn( "Deck Not Found" );
        	return new ResponseEntity<Set<SideboardLine>>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Ajoute un deck dans la base
     * 
     * @param deck
     * 			Le deck a ajouter dans la base
     * 
     * @return Un status (succes ou erreur)
     */
    @RequestMapping(value="/createDeck", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> addDeck( @RequestBody Deck deck, UriComponentsBuilder ucBuilder ) {
    	if(deckService.isDeckExist(deck)) {
    		logger.warn( "Un deck avec le même nom français existe déjà.");
    		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    	}
    	
    	deckService.saveDeck( deck );
    	logger.warn( "Deck Created !\n" + deck );
		
    	/* Pour que l'url vers l'edition creee soit afficher dans le header 
    	 * de retour de la requete */
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(ucBuilder.path("/api/deck/{id}").buildAndExpand(deck.getIdDeck()).toUri());
    	return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
     }
    
    /**
     * Met a jour un deck dans la base
     * 
     * @param deck
     * 			Le deck a mettre a jour
     * 
     * @return Un Status (succes ou erreur)
     * 
     */
    @RequestMapping(value="/updateDeck", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Deck> updateDeck( @RequestBody Deck deck ) {
    	Deck currentDeck = deckService.findById(deck.getIdDeck());
    	if (currentDeck instanceof Deck) {
    		deckService.updateDeck( deck );
        	logger.warn("Update deck success.\n " + deck);
        	return new ResponseEntity<Deck>(deck, HttpStatus.OK);
    	} else {
    		logger.warn("Ce Deck n'existe pas. Impossible de mettre a jour.\n " + deck);
    		return new ResponseEntity<Deck>(deck, HttpStatus.NOT_FOUND);
    	}
    
    }
    
    /**
     * Supprime un deck dans la base
     * 
     * @param deck
     * 			Le deck a supprimer
     * 
     * @return Un Status (succes ou erreur)
     */
    @RequestMapping(value="/deleteDeck", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> deleteDeck(@ RequestBody Deck deck ) {
    	Deck currentDeck = deckService.findById(deck.getIdDeck());
    	if (currentDeck instanceof Deck) {
    		deckService.deleteDeck( deck );
        	logger.warn("Deck supprimé avec success.\n " + deck);
        	return new ResponseEntity<Void>(HttpStatus.OK);
    	} else {
    		logger.warn("Ce deck n'existe pas. Impossible de supprimer.\n " + deck);
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    	}
    	
    }
}
