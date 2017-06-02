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
 * Cette classe represente une edition.
 * Une edition est composee d'un nom francais,
 * d'un nom anglais et d'une image
 * 
 * @author Olivier
 *
 */
@Entity @Table(name="T_Editions")
public class Edition {
	
	/**
	 * L'identifiant de l'edition
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEdition;
	
	/**
	 * Le nom francais de l'edition
	 */
	private String nameFr;

	/**
	 * Le nom anglais de l'edition
	 */
	private String nameEn;
	
	/**
	 * L'url de l'image de l'edition
	 */
	private String urlImage;
	
	/**
	 * La liste des cartes de cette edition
	 */
	@JsonIgnore
	@OneToMany(targetEntity=Card.class, mappedBy="edition")
	private Set<Card> cards = new HashSet<Card>();
		
	/**
	 * Constructeur vide
	 */
	public Edition() {}

	/**
	 * Constructeur avec parametres
	 * @param nameFr
	 * @param nameEn
	 * @param urlImage
	 */
	public Edition(String nameFr, String nameEn, String urlImage) {
		super();
		this.setNameFr(nameFr);
		this.setNameEn(nameEn);
		this.setUrlImage(urlImage);
	}
	
	/**
	 * Retourne la liste des cartes de l'edition
	 * 
	 * @return une liste de card
	 */
	public Set<Card> getCards() {
		return cards;
	}

	public int getNbCards() {
		return this.cards.size();
	}
	
	/**
	 * Retourne le nom francais de l'edition
	 * 
	 * @return Une chaine de caracteres
	 */
	public String getNameFr() {
		return nameFr;
	}

	/**
	 * Met a jour le nom francais de l'edition
	 * 
	 * @param nameFr
	 * 			Le nouveau nom francais
	 */
	public void setNameFr(String nameFr) {
		this.nameFr = nameFr;
	}
	
	/**
	 * Retourne le nom anglais de l'edition
	 * 
	 * @return Une chaine de caractere
	 */
	public String getNameEn() {
		return nameEn;
	}

	/**
	 * Met a jour le nom anglais de l'edition
	 * 
	 * @param nameEn
	 * 			Le nouveau nom anglais
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * Retourne l'identifiant de l'edition
	 * 
	 * @return un entier
	 */
	public int getIdEdition() {
		return idEdition;
	}

	/**
	 * Retourne l'url de l'image de l'edition
	 * 
	 * @return Une chaine de caractere
	 */
	public String getUrlImage() {
		return urlImage;
	}

	/**
	 * Met a jour l'url de l'image de l'edition
	 * 
	 * @param urlImage
	 * 			La nouvelle url de l'image
	 */
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	@Override
	public String toString() {
		return nameFr + "/" + nameEn;
	}
	
}
