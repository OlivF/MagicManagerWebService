package fr.free.francois.olivier.magicmanagerws.controllers;

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
import fr.free.francois.olivier.magicmanagerws.model.Type;
import fr.free.francois.olivier.magicmanagerws.services.TypeService;

/**
 * Cette classe est le controller REST pour les types.
 * Il permet grâce à l'url définie de pouvoir faire des requetes sur les types :
 * - afficher tous les types (format json)
 * - afficher un type (format json)
 * - afficher tous les types en cherchant par leur nom (format json)
 * - afficher toutes les cartes d'un type (format json)
 * - verifier si un type existe dans la base
 * - ajouter un type
 * - modifier un type
 * - supprimer un type
 * 
 * @author Olivier
 *
 */

@RestController
@RequestMapping(value="/api")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * Le Logger pour gérer les logs
     */
    private static Logger logger  = LogManager.getLogger(TypeController.class);
    
    /**
     * Recupere tous les types au format json
     * 
     * url ex : /api/types
     * 
     * @return Les informations sur les types
     */
    @RequestMapping(value="/types", method=RequestMethod.GET)
    public ResponseEntity<Set<Type>> getTypes() {
    	Set<Type> types = typeService.findAll();
    	logger.warn("Tous les types : " + types );
    	if ( types.isEmpty() ) {
    		return new ResponseEntity<Set<Type>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<Set<Type>>(types, HttpStatus.OK);
    }
    
    /**
     * Recupere les informations sur le type au format json
     * url ex : /api/type/1
     *  
     * @param id
     * 			id du type a rechercher
     * 
     * @return Les informations sur le type
     */
    @RequestMapping(value="/type/{id}", method=RequestMethod.GET)
    public ResponseEntity<Type> getType(@PathVariable int id){
    	logger.warn("Type id = " + id);
    	Type type = typeService.findById(id);
    	if ( type instanceof Type ) {
    		logger.warn("Type id = " + id + " => " + type );
        	return new ResponseEntity<Type>(type, HttpStatus.OK);
    	} else {
    		logger.warn( "Type Not Found" );
        	return new ResponseEntity<Type>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Recupere les types au format json
     * url ex : /api/type/Rituel
     *  
     * @param name
     * 			name du type a rechercher
     * 
     * @return Les types
     */
    @RequestMapping(value="/typeByName/{name}", method=RequestMethod.GET)
    public ResponseEntity<List<Type>> getTypeByName(@PathVariable String name){
    	List<Type> types = typeService.findByName(name);
    	logger.warn("Tous les types avec name like" + name + " : " + types );
    	if ( types.isEmpty() ) {
    		return new ResponseEntity<List<Type>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<Type>>(types, HttpStatus.OK);
    }
    
    /**
     * Recupere toutes les cartes d'un type
     * 
     * url ex : /cards/type/1
     * 
     * @param id
     * 				id du type a rechercher
     * 
     * @return la liste de toutes les cartes au format json
     */
    @RequestMapping(value="/cards/type/{id}", method=RequestMethod.GET)
    public ResponseEntity<Set<Card>> getCardsByIdType(@PathVariable int id) {
    	
    	Type type = typeService.findById(id);
    	
    	if ( type instanceof Type ) {
    		logger.warn("Type id = " + id + " => " + type );
    		Set<Card> cards = type.getCards();
    		logger.warn("Toutes les cartes avec type " + type.getNameFr() + " : " + cards );
        	if ( cards.isEmpty() ) {
        		return new ResponseEntity<Set<Card>>(HttpStatus.NO_CONTENT);
        	}
        	return new ResponseEntity<Set<Card>>(cards, HttpStatus.OK);
    		
    	} else {
    		logger.warn( "Type Not Found" );
        	return new ResponseEntity<Set<Card>>(HttpStatus.NOT_FOUND);
    	}
    }
    
    /**
     * Ajoute un type dans la base
     * 
     * @param type
     * 			Le type a ajouter dans la base
     * 
     * @return Un status (succes ou erreur)
     */
    @RequestMapping(value="/createType", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> addType( @RequestBody Type type, UriComponentsBuilder ucBuilder ) {
    	if(typeService.isTypeExist(type)) {
    		logger.warn( "Un type avec le même nom français, anglais existe déjà.");
    		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    	}
    	
    	typeService.saveType( type );
    	logger.warn( "Type Created !\n" + type );
		
    	/* Pour que l'url vers l'edition creee soit afficher dans le header 
    	 * de retour de la requete */
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(ucBuilder.path("/api/type/{id}").buildAndExpand(type.getIdType()).toUri());
    	return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
     }
    
    /**
     * Met a jour un type dans la base
     * 
     * @param type
     * 			Le type a mettre a jour
     * 
     * @return Un Status (succes ou erreur)
     * 
     */
    @RequestMapping(value="/updateType", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Type> updateType( @RequestBody Type type ) {
    	Type currentType = typeService.findById(type.getIdType());
    	if (currentType instanceof Type) {
    		typeService.updateType( type );
        	logger.warn("Update type success.\n " + type);
        	return new ResponseEntity<Type>(type, HttpStatus.OK);
    	} else {
    		logger.warn("Ce type n'existe pas. Impossible de mettre a jour.\n " + type);
    		return new ResponseEntity<Type>(type, HttpStatus.NOT_FOUND);
    	}
    
    }
    
    /**
     * Supprime un type dans la base
     * 
     * @param type
     * 			Le type a supprimer
     * 
     * @return Un Status (succes ou erreur)
     */
    @RequestMapping(value="/deleteType", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> deleteType(@ RequestBody Type type ) {
    	Type currentType = typeService.findById(type.getIdType());
    	if (currentType instanceof Type) {
    		typeService.deleteType( type );
        	logger.warn("Type supprimé avec success.\n " + type);
        	return new ResponseEntity<Void>(HttpStatus.OK);
    	} else {
    		logger.warn("Ce type n'existe pas. Impossible de supprimer.\n " + type);
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    	}
    	
    }
}
