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

import fr.free.francois.olivier.magicmanagerws.model.Card;
import fr.free.francois.olivier.magicmanagerws.model.Edition;
import fr.free.francois.olivier.magicmanagerws.services.EditionService;

/**
 * Cette classe est le controller REST pour les editions.
 * Il permet grâce à l'url définie de pouvoir faire des requetes sur les editions :
 * - afficher toutes les editions (format json)
 * - afficher une edition (format json)
 * - afficher les editions en les recherchant par leur nom (format json)
 * - afficher toutes les cartes d'une edition (format json)
 * - ajouter une edition
 * - modifier une edition
 * - supprimer une edition
 * 
 * @author Olivier
 *
 */
@RestController
@RequestMapping(value="/api")
public class EditionController {

	/**
	 * Service qui va s'occuper de la manipulation et de 
	 * la recuperation des données Edition
	 */
	@Autowired
    private EditionService editionService;

	/**
     * Le Logger pour gérer les logs
     */
    private static Logger logger  = LogManager.getLogger(EditionController.class);
    
    /**
     * Recupere toutes les editions au format json
     * 
     * url ex : /api/editions
     * 
     * @return Les informations sur les editions
     */
    @RequestMapping(value="/editions", method=RequestMethod.GET)
    public ResponseEntity<Set<Edition>> getEditions() {
    	Set<Edition> editions = editionService.findAll();
    	logger.warn("Toutes les editions : " + editions );
    	if ( editions.isEmpty() ) {
    		return new ResponseEntity<Set<Edition>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<Set<Edition>>(editions, HttpStatus.OK);
    }
    
	/**
     * Recupere les informations sur les editions au format json
     * url ex : /api/editionByName/Odyss
     *  
     * @param name
     * 			name de l'edition a rechercher
     * 
     * @return Les informations sur les editions
     */
    @RequestMapping(value="/editionByName/{name}", method=RequestMethod.GET)
    public ResponseEntity<List<Edition>> getEditionByName(@PathVariable String name){
    	List<Edition> editions = editionService.findByName(name);
    	logger.warn("Toutes les éditions avec name like" + name + " : " + editions );
    	if ( editions.isEmpty() ) {
    		return new ResponseEntity<List<Edition>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<Edition>>(editions, HttpStatus.OK);
    }
    
    /**
     * Recupere les informations sur l edition au format json
     * url ex : /api/edition/1
     *  
     * @param id
     * 			id de l'edition a rechercher
     * 
     * @return Les informations sur l'edition
     */
    @RequestMapping(value="/edition/{id}", method=RequestMethod.GET)
    public ResponseEntity<Edition> getEdition(@PathVariable int id){
    	logger.warn("Edition id = " + id);
    	Edition edition = editionService.findById(id);
    	if ( edition instanceof Edition ) {
    		logger.warn("Edition id = " + id + " => " + edition );
        	return new ResponseEntity<Edition>(edition, HttpStatus.OK);
    	} else {
    		logger.warn( "Edition Not Found" );
        	return new ResponseEntity<Edition>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Recupere toutes les cartes d'une edition
     * 
     * url ex : /cards/edition/1
     * 
     * @param id
     * 				id de l'edition a rechercher
     * 
     * @return la liste de toutes les cartes au format json
     */
    @RequestMapping(value="/cards/edition/{id}", method=RequestMethod.GET)
    public ResponseEntity<Set<Card>> getCardsByIdEdition(@PathVariable int id) {
    	
    	Edition edition = editionService.findById(id);
    	
    	if ( edition instanceof Edition ) {
    		logger.warn("Edition id = " + id + " => " + edition );
    		Set<Card> cards = edition.getCards();
    		logger.warn("Toutes les cartes avec edition " + edition.getNameFr() + " : " + cards );
        	if ( cards.isEmpty() ) {
        		return new ResponseEntity<Set<Card>>(HttpStatus.NO_CONTENT);
        	}
        	return new ResponseEntity<Set<Card>>(cards, HttpStatus.OK);
    		
    	} else {
    		logger.warn( "Edition Not Found" );
        	return new ResponseEntity<Set<Card>>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Ajoute une edition dans la base
     * 
     * @param edition
     * 			L'edition a ajouter dans la base
     * 
     * @return Un status (succes ou erreur)
     */
    @RequestMapping(value="/createEdition", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> addEdition( @RequestBody Edition edition, UriComponentsBuilder ucBuilder ) {
    	if(editionService.isEditionExist(edition)) {
    		logger.warn( "Une edition avec le même nom français, anglais existe déjà.");
    		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    	}
    	
    	editionService.saveEdition( edition );
    	logger.warn( "Edition Created !\n" + edition );
		
    	/* Pour que l'url vers l'edition creee soit afficher dans le header 
    	 * de retour de la requete */
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(ucBuilder.path("/api/edition/{id}").buildAndExpand(edition.getIdEdition()).toUri());
    	return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
     }
    
    /**
     * Met a jour une edition dans la base
     * 
     * @param edition
     * 			L'edition a mettre a jour
     * 
     * @return Un Status (succes ou erreur)
     * 
     */
    @RequestMapping(value="/updateEdition", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Edition> updateEdition( @RequestBody Edition edition ) {
    	Edition currentEdition = editionService.findById(edition.getIdEdition());
    	if (currentEdition instanceof Edition) {
    		editionService.updateEdition( edition );
        	logger.warn("Update edition success.\n " + edition);
        	return new ResponseEntity<Edition>(edition, HttpStatus.OK);
    	} else {
    		logger.warn("Cette edition n'existe pas. Impossible de mettre a jour.\n " + edition);
    		return new ResponseEntity<Edition>(edition, HttpStatus.NOT_FOUND);
    	}
    
    }
    
    /**
     * Supprime une edition dans la base
     * 
     * @param edition
     * 			L'edition a supprimer
     * 
     * @return Un Status (succes ou erreur)
     */
    @RequestMapping(value="/deleteEdition", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> deleteEdition(@ RequestBody Edition edition ) {
    	Edition currentEdition = editionService.findById(edition.getIdEdition());
    	if (currentEdition instanceof Edition) {
    		editionService.deleteEdition( edition );
        	logger.warn("Edition supprimée avec success.\n " + edition);
        	return new ResponseEntity<Void>(HttpStatus.OK);
    	} else {
    		logger.warn("Cette edition n'existe pas. Impossible de supprimer.\n " + edition);
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    	}
    	
    }
}
