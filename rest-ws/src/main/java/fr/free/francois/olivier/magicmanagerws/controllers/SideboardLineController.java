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
import fr.free.francois.olivier.magicmanagerws.model.SideboardLine;
import fr.free.francois.olivier.magicmanagerws.services.CardService;
import fr.free.francois.olivier.magicmanagerws.services.SideboardLineService;

/**
 * Cette classe est le controller REST pour les sideboardLines.
 * Il permet grâce à l'url définie de :
 * - ajouter / mettre a jour ou supprimer un sideboardLine
 * - supprimer un sideboardLine
 * 
 * @author Olivier
 *
 */
@RestController
@RequestMapping(value="/api")
public class SideboardLineController {

	@Autowired
    private SideboardLineService sideboardLineService;
	
	@Autowired
	private CardService cardService;
	/**
     * Le Logger pour gérer les logs
     */
    private static Logger logger  = LogManager.getLogger(SideboardLineController.class);
    
	/**
     * Ajoute, met a jour ou supprime une sideboardLine dans la base
     * 
     * @param sideboardLine
     * 			La sideboardLine a ajouter, mettre a jour ou supprimer dans la base
     * 
     * @return Un status (succes ou erreur)
     */
    @RequestMapping(value="/createOrUpdateSideboardLine", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> addOrUpdateSideboardLine( @RequestBody SideboardLine sideboardLine ) {
    	
    	/* On regarde si la sideboardLine existe 
    	 * Si oui on regarde la quantité :
    	 * 		si = 0 on supprime la sideboardLine et on remet la 
    	 * 		disponibility de base pour la carte
    	 * 		si > 0 on met a jour la sideboardLine et la disponibility
    	 * 		de la carte
    	 * si non on regarde la quantité
    	 * 		si = 0 on ne crée pas de sideboardLine
    	 * 		si > 0 on crée la sideboardLine et on met a jour 
    	 * 		la disponibility de la carte
    	 * 		
    	 */
    	if ( sideboardLineService.isSideboardLineExist( sideboardLine ) ) {
    		SideboardLine currentSideboardLine = sideboardLineService.findByCardAndDeck( sideboardLine.getCard(), sideboardLine.getDeck() );
    		int	deltaQuantity = sideboardLine.getQuantity() - currentSideboardLine.getQuantity();
        	if ( sideboardLine.getQuantity() == 0 ) {
        		sideboardLine.getCard().setDisponibility( sideboardLine.getCard().getDisponibility() - deltaQuantity );
    			sideboardLineService.saveSideboardLine( sideboardLine );
        		sideboardLineService.deleteSideboardLine( sideboardLine );
            	logger.warn( "Update Card Disponibility and Delete sideboardLine success.\n");
            	return new ResponseEntity<Void>( HttpStatus.OK );
            } else {
            	Card currentCard = cardService.findById( sideboardLine.getCard().getIdCard() );
           		int disponibility = currentCard.getDisponibility();
            	sideboardLine.getCard().setDisponibility( disponibility - deltaQuantity );
                sideboardLineService.saveSideboardLine( sideboardLine );
            	logger.warn( "sideboardLine Updated !\n" + sideboardLine );
            	return new ResponseEntity<Void>( HttpStatus.CREATED );
            }
    	} else if ( sideboardLine.getQuantity() > 0 ) {
    		Card currentCard = cardService.findById( sideboardLine.getCard().getIdCard() );
        	int deltaQuantity = sideboardLine.getQuantity();
        	int disponibility = currentCard.getDisponibility();
        	sideboardLine.getCard().setDisponibility( disponibility - deltaQuantity );
            sideboardLineService.saveSideboardLine( sideboardLine );
        	logger.warn( "sideboardLine Created Or Updated !\n" + sideboardLine );
        	return new ResponseEntity<Void>( HttpStatus.CREATED );
    	} else {
    		return new ResponseEntity<Void>( HttpStatus.OK );
    	}
     }
    
    /**
     * Supprime une sideboardLine dans la base
     * 
     * @param sideboardLine
     * 			Le sideboardLine a supprimer
     * 
     * @return Un Status (succes ou erreur)
     */
    @RequestMapping(value="/deleteSideboardLine", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> deleteSideboardLine(@ RequestBody SideboardLine sideboardLine ) {
    	if (sideboardLineService.isSideboardLineExist(sideboardLine)) {
    		sideboardLineService.deleteSideboardLine( sideboardLine );
        	logger.warn("Delete sideboardLine success.\n ");
        	return new ResponseEntity<Void>(HttpStatus.OK);
    	} else {
    		logger.warn("Ce sideboardLine n'existe pas. Impossible de mettre a jour.\n " + sideboardLine);
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    	}
    }
}