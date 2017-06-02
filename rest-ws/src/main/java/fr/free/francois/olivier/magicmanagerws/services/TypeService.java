package fr.free.francois.olivier.magicmanagerws.services;

import java.util.List;
import java.util.Set;

import fr.free.francois.olivier.magicmanagerws.model.Type;

/**
 * TypeService est l'interface de la classe TypeServiceImpl.
 * Cette interface est composee de plusieurs methodes :
 * - Trouver tous les types
 * - Trouver un type par son id
 * - Trouver un type par son nom
 * - Enregistrer un type dans la base
 * - Mettre a jour un type dans la base
 * - Supprimer un type dans la base
 * - Verifier si un type existe dans la base
 * 
 * @author Olivier Francois
 */
public interface TypeService {

	/**
	 * Cherche tous les types dans la base
	 * 
	 * @return une liste de type
	 */
	Set<Type> findAll();
	
	/**
	 * Cherche un type par son id
	 * 
	 * @param id
	 * 				l'id du type a rechercher
	 * 
	 * @return le type
	 */
	Type findById(int id);
	
	/**
	 * Cherche un type par son nom
	 * 
	 * @param name
	 * 				le nom du type a rechercher
	 * 
	 * @return le type
	 */
	List<Type> findByName(String name);
	
	/**
	 * Enregistre un type dans la base
	 * 
	 * @param type
	 * 				Le type a enregistrer
	 */
	Type saveType(Type type);
	
	/**
	 * Mettre a jour un type dans la base
	 * 
	 * @param type
	 * 				Le type � mettre � jour
	 */
	void updateType(Type type);
	
	/**
	 * Supprime un type
	 * 
	 * @param type
	 * 				le type a supprimer
	 */
	void deleteType(Type type);
	
	/**
	 * Verifie si le type existe puis retourne un boolean (true ou false)
	 * 
	 * @param type
	 * 				le type a chercher
	 * 
	 * @return boolean
	 */
	boolean isTypeExist(Type type);
	
}
