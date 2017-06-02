package fr.free.francois.olivier.magicmanagerws.services;

import java.util.List;
import java.util.Set;

import fr.free.francois.olivier.magicmanagerws.model.Edition;

/**
 * EditionService est l'interface de la classe EditionServiceImpl.
 * Cette interface est compos�e de plusieurs methodes :
 * - Trouver toutes les editions
 * - Trouver une edition par son id
 * - Trouver une edition par son nom
 * - Enregistrer une edition dans la base
 * - Mettre a jour une edition dans la base
 * - Supprimer une edition dans la base
 * - Verifier si une edition existe dans la base
 * 
 * @author Olivier Francois
 */
public interface EditionService {

	/**
	 * Cherche toutes les editions dans la base
	 * 
	 * @return une liste d'editions
	 */
	Set<Edition> findAll();
	
	/**
	 * Cherche une edition par son id
	 * 
	 * @param id
	 * 				l'id de l'edition a rechercher
	 * 
	 * @return le type
	 */
	Edition findById(int id);
	
	/**
	 * Chercher une edition par son nom
	 * 
	 * @param name
	 * 				le nom de l'edition a rechercher
	 * 
	 * @return l'edition
	 */
	List<Edition> findByName(String name);
	
	/**
	 * Enregistre une edition dans la base
	 * 
	 * @param edition
	 * 				L'edition a enregistrer
	 */
	void saveEdition(Edition edition);
	
	/**
	 * Mettre a jour une edition dans la base
	 * 
	 * @param edition
	 * 				L'edition a mettre a jour
	 */
	void updateEdition(Edition edition);
	
	/**
	 * Supprime une edition
	 * 
	 * @param edition
	 * 				l'edition a supprimer
	 */
	void deleteEdition(Edition edition);
	
	/**
	 * Verifie si l'edition existe et retourne un boolean (true ou false)
	 * 
	 * @param edition
	 * 				l'edition a chercher
	 * 
	 * @return boolean
	 */
	boolean isEditionExist(Edition edition);
	
}
