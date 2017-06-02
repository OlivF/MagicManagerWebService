package fr.free.francois.olivier.magicmanagerws.services;

import java.util.List;
import java.util.Set;

import fr.free.francois.olivier.magicmanagerws.model.Card;

/**
 * CardService est l'interface de la classe CardServiceImpl.
 * Cette interface est composee de plusieurs methodes :
 * - Trouver toutes les cards
 * - Trouver une card par son id
 * - Trouver une card par son nom
 * - Enregistrer une card dans la base
 * - Mettre a jour une card dans la base
 * - Supprimer une card dans la base
 * - Verifier si une card existe dans la base en verifiant nameFr, nameEn et edition
 * 
 * @author Olivier Francois
 */
public interface CardService {

	/**
	 * Cherche toutes les cards dans la base
	 * 
	 * @return une liste de card
	 */
	Set<Card> findAll();
	
	/**
	 * Cherche une card par son id
	 * 
	 * @param id
	 * 				l'id de la card a rechercher
	 * 
	 * @return la card
	 */
	Card findById(int id);
	
	/**
	 * Chercher une card par son nom
	 * 
	 * @param name
	 * 				le nom de la card a rechercher
	 * 
	 * @return une liste de card
	 */
	List<Card> findByName(String name);
	
	/**
	 * Enregistre une card dans la base
	 * 
	 * @param card
	 * 				La card a enregistrer
	 */
	void saveCard(Card card);
	
	/**
	 * Mettre a jour une card dans la base
	 * 
	 * @param card
	 * 				La card a mettre a jour
	 */
	void updateCard(Card card);
	
	/**
	 * Supprime une card
	 * 
	 * @param card
	 * 				La card a supprimer
	 */
	void deleteCard(Card card);
	
	/**
	 * Verifie si la card existe puis retourne (true ou false)
	 * 
	 * @param card
	 * 				la card a chercher
	 * 
	 * @return boolean
	 */
	boolean isCardExist(Card card);
	
}
