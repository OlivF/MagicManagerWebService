package fr.free.francois.olivier.magicmanagerws.services;

import fr.free.francois.olivier.magicmanagerws.model.Card;
import fr.free.francois.olivier.magicmanagerws.model.Deck;
import fr.free.francois.olivier.magicmanagerws.model.DeckLine;

/**
 * DeckLineService est l'interface de la classe DeckLineServiceImpl.
 * Cette interface est composee de plusieurs methodes :
 * - Enregistrer / Mettre a jour ou supprimer un deckline dans la base
 * - Supprimer un deckline dans la base
 * 
 * @author Olivier Francois
 */
public interface DeckLineService {

	/**
	 * Cherche une deckLine dans la base
	 * 
	 * @param id
	 * 				L'id de la deckLine a rechercher
	 */
	public DeckLine findByCardAndDeck(Card card, Deck deck);
	
	/**
	 * Enregistre une deckLine dans la base
	 * 
	 * @param deckLine
	 * 				La deckLine a enregistrer
	 */
	void saveDeckLine(DeckLine deckLine);
	
	/**
	 * Supprime une deckLine
	 * 
	 * @param deckLine
	 * 				le deckLine a supprimer
	 */
	void deleteDeckLine(DeckLine deckLine);
	
	/**
	 * Verifie si la deckLine existe et retourne un boolean (true ou false)
	 * 
	 * @param deckLine
	 * 				le deckLine
	 * 
	 * @return boolean
	 */
	boolean isDeckLineExist(DeckLine deckLine);
}
