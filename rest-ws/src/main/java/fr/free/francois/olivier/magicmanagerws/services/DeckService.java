package fr.free.francois.olivier.magicmanagerws.services;

import java.util.List;
import java.util.Set;

import fr.free.francois.olivier.magicmanagerws.model.Deck;

/**
 * DeckService est l'interface de la classe DeckServiceImpl.
 * Cette interface est composee de plusieurs methodes :
 * - Trouver tous les decks
 * - Trouver un deck par son id
 * - Trouver un deck par son nom
 * - Enregistrer un deck dans la base
 * - Mettre a jour un deck dans la base
 * - Supprimer un deck dans la base
 * - Verifier si un deck existe dans la base
 * 
 * @author Olivier Francois
 */
public interface DeckService {

	/**
	 * Cherche tous les decks dans la base
	 * 
	 * @return une liste de decks
	 */
	Set<Deck> findAll();
	
	/**
	 * Cherche un deck par son id
	 * 
	 * @param id
	 * 				l'id du deck a rechercher
	 * 
	 * @return le type
	 */
	Deck findById(int id);
	
	/**
	 * Chercher un deck par son nom
	 * 
	 * @param name
	 * 				le nom du deck a rechercher
	 * 
	 * @return la liste des decks
	 */
	List<Deck> findByName(String name);
	
	/**
	 * Enregistre un deck dans la base
	 * 
	 * @param deck
	 * 				Le deck a enregistrer
	 */
	void saveDeck(Deck deck);
	
	/**
	 * Mettre a jour un deck dans la base
	 * 
	 * @param deck
	 * 				Le deck a mettre a jour
	 */
	void updateDeck(Deck deck);
	
	/**
	 * Supprime un deck
	 * 
	 * @param deck
	 * 				le deck a supprimer
	 */
	void deleteDeck(Deck deck);
	
	/**
	 * Verifie si le deck existe et retourne un boolean (true ou false)
	 * 
	 * @param deck
	 * 				le deck
	 * 
	 * @return boolean
	 */
	boolean isDeckExist(Deck deck);
	
}
