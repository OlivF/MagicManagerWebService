package fr.free.francois.olivier.magicmanagerws.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.free.francois.olivier.magicmanagerws.model.Card;
import fr.free.francois.olivier.magicmanagerws.model.DeckLine;
import fr.free.francois.olivier.magicmanagerws.services.CardService;
import fr.free.francois.olivier.magicmanagerws.services.DeckLineService;

/**
 * Cette classe est le controller REST pour les deckLines.
 * Il permet grâce à l'url définie de :
 * - ajouter un deckLine
 * - modifier un deckLine
 * - supprimer un deckLine
 * 
 * @author Olivier
 *
 */
@RestController
@RequestMapping(value="/api")
public class DeckLineController {

	@Autowired
    private DeckLineService deckLineService;
	
	@Autowired
	private CardService cardService;
	/**
     * Le Logger pour gérer les logs
     */
    private static Logger logger  = LogManager.getLogger(DeckLineController.class);
    
	/**
     * Ajoute, met a jour ou supprime une deckLine dans la base
     * 
     * @param deckLine
     * 			La deckLine a ajouter, mettre a jour ou supprimer dans la base
     * 
     * @return Un status (succes ou erreur)
     */
    @RequestMapping(value="/createOrUpdateDeckLine", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> addOrUpdateDeckLine( @RequestBody DeckLine deckLine ) {
    	
    	/* On regarde si la deckLine existe 
    	 * Si oui on regarde la quantité :
    	 * 		si = 0 on supprime la deckLine et on remet la 
    	 * 		disponibility de base pour la carte
    	 * 		si > 0 on met a jour la deckLine et la disponibility
    	 * 		de la carte
    	 * si non on regarde la quantité
    	 * 		si = 0 on ne crée pas de deckLine
    	 * 		si > 0 on crée la deckLine et on met a jour 
    	 * 		la disponibility de la carte
    	 * 		
    	 */
    	if ( deckLineService.isDeckLineExist( deckLine ) ) {
    		DeckLine currentDeckLine = deckLineService.findByCardAndDeck( deckLine.getCard(), deckLine.getDeck() );
    		int	deltaQuantity = deckLine.getQuantity() - currentDeckLine.getQuantity();
        	if ( deckLine.getQuantity() == 0 ) {
        		deckLine.getCard().setDisponibility( deckLine.getCard().getDisponibility() - deltaQuantity );
    			deckLineService.saveDeckLine( deckLine );
        		deckLineService.deleteDeckLine( deckLine );
            	logger.warn( "Update Card Disponibility and Delete deckLine success.\n");
            	return new ResponseEntity<Void>( HttpStatus.OK );
            } else {
            	Card currentCard = cardService.findById( deckLine.getCard().getIdCard() );
           		int disponibility = currentCard.getDisponibility();
            	deckLine.getCard().setDisponibility( disponibility - deltaQuantity );
                deckLineService.saveDeckLine( deckLine );
            	logger.warn( "DeckLine Updated !\n" + deckLine );
            	return new ResponseEntity<Void>( HttpStatus.CREATED );
            }
    	} else if ( deckLine.getQuantity() > 0 ) {
    		Card currentCard = cardService.findById( deckLine.getCard().getIdCard() );
        	int deltaQuantity = deckLine.getQuantity();
        	int disponibility = currentCard.getDisponibility();
        	deckLine.getCard().setDisponibility( disponibility - deltaQuantity );
            deckLineService.saveDeckLine( deckLine );
        	logger.warn( "DeckLine Created Or Updated !\n" + deckLine );
        	return new ResponseEntity<Void>( HttpStatus.CREATED );
    	} else {
    		return new ResponseEntity<Void>( HttpStatus.OK );
    	}
     }
    
    /**
     * Supprime une deckLine dans la base
     * 
     * @param deckLine
     * 			Le deckLine a supprimer
     * 
     * @return Un Status (succes ou erreur)
     */
    @RequestMapping(value="/deleteDeckLine", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> deleteDeckLine(@ RequestBody DeckLine deckLine ) {
    	if (deckLineService.isDeckLineExist(deckLine)) {
    		deckLineService.deleteDeckLine( deckLine );
        	logger.warn("Delete deckLine success.\n ");
        	return new ResponseEntity<Void>(HttpStatus.OK);
    	} else {
    		logger.warn("Ce DeckLine n'existe pas. Impossible de mettre a jour.\n " + deckLine);
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    	}
    }
}