package fr.free.francois.olivier.magicmanagerws.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Cette classe represente un type.
 * Un type est composé d'un nom francais
 * et d'un nom anglais
 * @author Olivier
 *
 */
@Entity @Table(name="T_Types")
public class Type {
	
	/**
	 * L'identifiant du type
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idType;
	
	/**
	 * Le nom francais du type
	 */
	private String nameFr;
	
	/**
	 * Le nom anglais du type
	 */
	private String nameEn;
	
	/**
	 * La liste des cartes de ce type
	 */
	@JsonIgnore
	@OneToMany(targetEntity=Card.class, mappedBy="type")
	private Set<Card> cards = new HashSet<Card>();
	
	/**
	 * Constructeur vide
	 */
	public Type() {}

	/**
	 * Constructeur avec parametres
	 * @param nameFr
	 * @param nameEn
	 */
	public Type(String nameFr, String nameEn) {
		super();
		this.setNameFr(nameFr);
		this.setNameEn(nameEn);
	}

	/**
	 * Retourne l'identifiant du type
	 * 
	 * @return un entier
	 */
	public int getIdType() {
		return idType;
	}

	/**
	 * Retourne la liste des cartes qui ont ce type
	 * 
	 * @return Une liste de cartes
	 */
	public Set<Card> getCards() {
		return cards;
	}
	
	public int getNbCards() {
		return this.cards.size();
	}
	
	/**
	 * Retourne le nom francais du type
	 * 
	 * @return Une chaine de caracteres
	 */
	public String getNameFr() {
		return nameFr;
	}
	
	/**
	 * Met a jour le nom francais du type
	 * 
	 * @param nameFr
	 * 			Le nouveau nom francais
	 */
	public void setNameFr(String nameFr) {
		this.nameFr = nameFr;
	}
	
	/**
	 * Retourne le nom anglais du type
	 * 
	 * @return Une chaine de caracteres
	 */
	public String getNameEn() {
		return nameEn;
	}
	
	/**
	 * Met a jour le nom anglais du type
	 * 
	 * @param nameEn
	 * 			Le nouveau nom anglais
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@Override
	public String toString() {
		return nameFr +"/" + nameEn;
	}
	
}
