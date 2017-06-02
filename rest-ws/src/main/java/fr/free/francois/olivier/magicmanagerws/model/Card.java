package fr.free.francois.olivier.magicmanagerws.model;

/**
 * Cette classe represente l'objet card. 
 * Une carte contient une edition et un type ainsi
 * que d'autres attributs.
 * 
 * Une carte pourra etre ajouter a un deck si elle est disponible.
 */
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Table(name="T_Cards")
public class Card {

	/**
	 * L'identifiant de la carte. Cet ID n'est pas modifiable
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCard;
	
	/**
	 * Le nom francais de la carte
	 */
	private String nameFr;
	
	/**
	 * Le nom anglais de la carte
	 */
	private String nameEn;
	
	/**
	 * La description francaise de la carte
	 */
	private String descFr;
	
	/**
	 * La description anglaise de la carte
	 */
	private String descEn;
	
	/**
	 * Le cout de mana de la carte
	 */
	private String color;
	
	/**
	 * La rarete de la carte
	 */
	private String rarity;
	
	/**
	 * La quantite de la carte
	 */
	private int quantity;
	
	/**
	 * Le nombre de cartes non utilise dans des decks
	 */
	private int disponibility;
	
	/**
	 * Le type de la carte
	 */
	@ManyToOne @JoinColumn(name="IdType")
	private Type type;
	
	/**
	 * L'edition de la carte
	 */
	@ManyToOne @JoinColumn(name="IdEdition")
	private Edition edition;
	
	/**
	 * La liste des decks qui contiennent cette carte
	 */
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pk.card", cascade=CascadeType.ALL)
	private Set<DeckLine> deckLines =  new HashSet<DeckLine>(0);
	
	/**
	 * La liste des sideboard qui contiennent cette carte
	 */
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pk.card", cascade=CascadeType.ALL)
	private Set<SideboardLine> sideboardLines =  new HashSet<SideboardLine>(0);
	
	/**
	 * L'url de l'image de la carte
	 */
	private String urlImage;
	
	/**
	 * Le prix de la carte
	 */
	private int price;
	
	/**
	 * La date d'ajout dans la base de la carte
	 */
	private Date date;

	/**
	 * Constructeur vide
	 */
	public Card() {}
	
	/**
	 * Constructeur avec parametres
	 * @param nameFr
	 * @param nameEn
	 * @param descFr
	 * @param descEn
	 * @param color
	 * @param rarity
	 * @param quantity
	 * @param disponibility
	 * @param type
	 * @param edition
	 * @param decks
	 * @param urlImage
	 * @param price
	 * @param date
	 */
	public Card(String nameFr, String nameEn, String descFr, String descEn, String color, String rarity, int quantity,
			int disponibility, Type type, Edition edition, Set<Deck> decks, String urlImage, int price, Date date) {
		super();
		this.setNameFr(nameFr);
		this.setNameEn(nameEn);
		this.setColor(color);
		this.setRarity(rarity);
		this.setQuantity(quantity);
		this.setDisponibility(disponibility);
		this.setType(type);
		this.setEdition(edition);
		this.setDate(date);
		this.setDescEn(descEn);
		this.setDescFr(descFr);
		this.setUrlImage(urlImage);
		this.setPrice(price);
	}

	/**
	 * Retourne la description francaise de la carte
	 * 
	 * @return Une chaine de caracteres
	 */
	public String getDescFr() {
		return descFr;
	}

	/**
	 * Met a jour la description francaise de la carte
	 * 
	 * @param descFr
	 * 				la nouvelle description francaise
	 */
	public void setDescFr(String descFr) {
		this.descFr = descFr;
	}

	/**
	 * Retourne la description anglaise de la carte
	 * 
	 * @return une chaine de caracteres
	 */
	public String getDescEn() {
		return descEn;
	}

	/**
	 * Met a jour la description anglaise de la carte
	 * 
	 * @param descEn
	 * 				la nouvelle description anglaise
	 */
	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}

	/**
	 * Retourne l'url de l'image de la carte
	 * 
	 * @return Une chaine de caractere
	 */
	public String getUrlImage() {
		return urlImage;
	}

	/**
	 * Met a jour l'url de l'image de la carte
	 * 
	 * @param urlImage
	 * 				la nouvelle url de l'image
	 */
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	/**
	 * Retourne le prix de la carte
	 * 
	 * @return un entier
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Met a jour le prix de la carte
	 * 
	 * @param price
	 * 				le nouveau prix
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Retourne la liste des deckLines associees
	 * 
	 * @return Une liste de deckLines
	 */
	public Set<DeckLine> getDeckLines() {
		return deckLines;
	}
	
	/**
	 * Retourne la liste des sideboardLines associees
	 * 
	 * @return Une liste de sideboardLines
	 */
	public Set<SideboardLine> getSideboardLines() {
		return sideboardLines;
	}

	/**
	 * Met a jour la liste des deckLines associees
	 * 
	 * @param deckLines
	 * 			Une liste de deckLine
	 */
	public void setDeckLines(Set<DeckLine> deckLines) {
		this.deckLines = deckLines;
	}
	
	/**
	 * Met a jour la liste des sideboardLines associees
	 * 
	 * @param sideboardLines
	 * 			Une liste de sideboardLine
	 */
	public void setSideboardLines(Set<SideboardLine> sideboardLines) {
		this.sideboardLines = sideboardLines;
	}

	/**
	 * Retourne le nom francais de la carte
	 * 
	 * @return Une chaine de caractere
	 */
	public String getNameFr() {
		return nameFr;
	}

	/**
	 * Met a jour le nom francais de la carte
	 * 
	 * @param nameFr
	 * 				Le nouveau nom francais
	 */
	public void setNameFr(String nameFr) {
		this.nameFr = nameFr;
	}

	/**
	 * Retourne le nom anglais de la carte
	 * 
	 * @return Une chaine de caractere
	 */
	public String getNameEn() {
		return nameEn;
	}

	/**
	 * Met a jour le nom anglais de la carte
	 * 
	 * @param nameEn
	 * 			Le nouveau nom anglais
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * Retourne le cout de mana de la carte
	 * 
	 * @return Une chaine de caractere
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Met a jour le cout de mana de la carte
	 * 
	 * @param color
	 * 				Le nouveau cout de mana
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Retourne la rarete de la carte
	 * 
	 * @return Une chaine de caractere
	 */
	public String getRarity() {
		return rarity;
	}

	/**
	 * Met a jour la rarete de la carte
	 * 
	 * @param rarity
	 * 			La nouvelle rarete de la carte
	 */
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	/**
	 * Retourne la quantite de la carte
	 * 
	 * @return un entier
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Met a jour la quantite de la carte
	 * 
	 * @param quantity
	 * 			La nouvelle quantite
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Retourne le nombre de carte disponible
	 * 
	 * @return un entier
	 */
	public int getDisponibility() {
		return disponibility;
	}

	/**
	 * Met a jour le nombre de cartes disponibles
	 * 
	 * @param disponibility
	 * 				le nouveau nombre de carte disponibles
	 */
	public void setDisponibility(int disponibility) {
		this.disponibility = disponibility;
	}
	
	/**
	 * Retourne le type de la carte
	 * 
	 * @return un type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Met a jour le type de la carte
	 * 
	 * @param type
	 * 			Le nouveau type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Retourne l'edition de la carte
	 * 
	 * @return une edition
	 */
	public Edition getEdition() {
		return edition;
	}

	/**
	 * Met a jour l'edition de la carte
	 * 
	 * @param edition
	 * 				La nouvelle edition de la carte
	 */
	public void setEdition(Edition edition) {
		this.edition = edition;
	}

	/**
	 * Retourne la date de creation de la carte
	 * 
	 * @return Une date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Met a jour la date de la carte
	 * 
	 * @param date
	 * 			La nouvelle date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Retourne l'identifiant de la carte
	 * 
	 * @return un entier
	 */
	public int getIdCard() {
		return idCard;
	}

	@Override
	public String toString() {
		return "[Card=" + idCard + "] " + nameFr + " / " + nameEn + " / " + color + " / "
				+ rarity + " / quantity=" + quantity + ", disponibility=" + disponibility + " / " + type
				+ " / " + edition + " / " + date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCard;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (idCard != other.idCard)
			return false;
		return true;
	}
	
	
	
}
