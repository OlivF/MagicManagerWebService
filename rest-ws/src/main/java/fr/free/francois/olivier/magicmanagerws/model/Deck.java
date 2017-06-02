package fr.free.francois.olivier.magicmanagerws.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Cette represente un deck.
 * Un deck a un nom et une couleur, 
 * contient une liste de cartes et
 * possede une reserve qui contient elle aussi des cartes
 * @author Olivier
 *
 */
@Entity @Table(name="T_Decks")
public class Deck {

	/**
	 * L'identifiant du deck.
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDeck;
	
	/**
	 * Le nom du deck
	 */
	private String name;
	
	/**
	 * La couleur du deck
	 */
	private String color;
	
	/**
	 * Les sideboardLines associees au deck
	 */
	//@OneToMany(targetEntity=SideboardLine.class, mappedBy="deck")
	//private Set<SideboardLine> sideboardLines = new HashSet<SideboardLine>();
	
	/**
	 * Les cartes associees au deck
	 */
	@JsonIgnore
	@OneToMany(mappedBy ="pk.deck", cascade = CascadeType.ALL)
	private Set<DeckLine> deckLines =  new HashSet<DeckLine>();
	
	/**
	 * Les cartes associees au deck
	 */
	@JsonIgnore
	@OneToMany(mappedBy ="pk.deck", cascade = CascadeType.ALL)
	private Set<SideboardLine> sideboardLines =  new HashSet<SideboardLine>();
	
	/*@JsonIgnore
	@ManyToMany @JoinTable(name="T_DeckLine", 
			joinColumns = {@JoinColumn(name="IdDeck")},
			inverseJoinColumns = {@JoinColumn(name="IdCard")}
	)
	private Set<Card> cards =  new HashSet<Card>();
	*/
	
	/**
	 * Constructeur vide
	 */
	public Deck() {}
	
	/**
	 * Constructeur avec parametres
	 * @param name
	 * @param color
	 */
	public Deck(String name, String color) {
		super();
		this.setName(name);
		this.setColor(color);
	}
	
	public Set<DeckLine> getDeckLines() {
		return deckLines;
	}

	public void setDeckLines(Set<DeckLine> deckLines) {
		this.deckLines = deckLines;
	}
	
	public Set<SideboardLine> getSideboardLines() {
		return sideboardLines;
	}

	public void setSideboardLines(Set<SideboardLine> sideboardLines) {
		this.sideboardLines = sideboardLines;
	}

	/**
	 * Retourne une liste de deckLines
	 * 
	 * @return Une liste de deckLines
	 */
	/*public Set<DeckLine> getDeckLines() {
		return deckLines;
	}*/

	/**
	 * Met a jour la liste de deckLines
	 * 
	 * @param deckLines
	 * 				La nouvelle liste de deckLines
	 */
	/*public void setDeckLines(Set<DeckLine> deckLines) {
		this.deckLines = deckLines;
	}*/

	/**
	 * Retourne le sideboard du deck
	 * 
	 * @return Un sideboard
	 */
	//public Set<SideboardLine> getSideboardLines() {
		//return sideboardLines;
	//}

	/**
	 * Met a jour le sideboard du deck
	 * 
	 * @param sideboard
	 * 				Le nouveau sideboard du deck
	 */
	//public void setSideboardLines(Set<SideboardLine> sideboardLines) {
		//this.sideboardLines = sideboardLines;
	//}

	/**
	 * Retourne le nom du deck
	 * 
	 * @return Une chaine de caractere
	 */
	public String getName() {
		return name;
	}

	/**
	 * Met a jour le nom du deck
	 * 
	 * @param name
	 * 			Le nouveau nom du deck
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retourne la couleur du deck
	 * 
	 * @return Une chaine de caracteres
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Met a jour la couleur du deck
	 * 
	 * @param color
	 * 			La nouvelle couleur du deck
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Retourne l'id du deck
	 * 
	 * @return un entier
	 */
	public int getIdDeck() {
		return idDeck;
	}
	
	/**
	 * Retourne le nombre de carte du deck
	 * 
	 * @return un entier
	 */
	public int getNbCards() {
		int size = 0;
		for (DeckLine deckLine : deckLines) {
			size += deckLine.getQuantity();
		}
		return size;
	}

	@Override
	public String toString() {
		return "[Deck " + idDeck + "] >> " + name + " (" + color + ") - " + this.getNbCards() + " cartes";
	}
	
}
