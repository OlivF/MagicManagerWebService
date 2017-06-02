package fr.free.francois.olivier.magicmanagerws.services;

import fr.free.francois.olivier.magicmanagerws.model.Card;
import fr.free.francois.olivier.magicmanagerws.model.Deck;
import fr.free.francois.olivier.magicmanagerws.model.SideboardLine;

/**
 * SideboardLineService est l'interface de la classe SideboardLineServiceImpl.
 * Cette interface est composee de plusieurs methodes :
 * - Enregistrer / mettre a jour / ou supprimer
 *   un sideboardline dans la base
 * - Supprimer un sideboardline dans la base
 * 
 * @author Olivier Francois
 */
public interface SideboardLineService {

	/**
	 * Cherche une sideboardLine dans la base
	 * 
	 * @param id
	 * 				L'id de la sideboardLine a rechercher
	 */
	public SideboardLine findByCardAndDeck(Card card, Deck deck);
	
	/**
	 * Enregistre une sideboardLine dans la base
	 * 
	 * @param sideboardLine
	 * 				La sideboardLine a enregistrer
	 */
	void saveSideboardLine(SideboardLine sideboardLine);
	
	/**
	 * Supprime une sideboardLine
	 * 
	 * @param sideboardLine
	 * 				le sideboardLine a supprimer
	 */
	void deleteSideboardLine(SideboardLine sideboardLine);
	
	/**
	 * Verifie si la sideboardLine existe et retourne un boolean (true ou false)
	 * 
	 * @param sideboardLine
	 * 				le sideboardLine
	 * 
	 * @return boolean
	 */
	boolean isSideboardLineExist(SideboardLine sideboardLine);
}
