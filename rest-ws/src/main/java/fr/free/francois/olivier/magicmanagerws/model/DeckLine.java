package fr.free.francois.olivier.magicmanagerws.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Cette classe represente la liaison entre une carte
 * et un deck.
 * 
 * @author Olivier
 *
 */
@Entity @Table(name="T_DeckLine")
@AssociationOverrides({
	@AssociationOverride(name="pk.card", 
			joinColumns = @JoinColumn(name="IdCard")),
	@AssociationOverride(name="pk.deck",
			joinColumns = @JoinColumn(name="IdDeck"))
})
public class DeckLine implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DeckLineId pk = new DeckLineId();

	
	/**
	 * La quantite de la carte
	 */
	private int quantity;

	/**
	 * Constructeur vide
	 */
	public DeckLine() {}
	
	@EmbeddedId
	public DeckLineId getPk() {
		return pk;
	}

	public void setPk(DeckLineId pk) {
		this.pk = pk;
	}

	/**
	 * Retourne la carte de la deckLine
	 * 
	 * @return une card
	 */
	@Transient
	public Card getCard() {
		return getPk().getCard();
	}

	/**
	 * Met a jour la carte de la deckLine
	 * 
	 * @param card
	 * 			La nouvelle card
	 */
	public void setCard(Card card) {
		getPk().setCard(card);
	}

	/**
	 * Retourne le deck de la deckLine
	 * 
	 * @return un deck 		
	 */
	@Transient
	public Deck getDeck() {
		return getPk().getDeck();
	}

	/**
	 * Met a jour le deck de la deckLine
	 * 
	 * @param deck
	 * 			Le nouveau deck
	 */
	public void setDeck(Deck deck) {
		getPk().setDeck(deck);
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
	 * 			La nouvelle quantite de la carte
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return quantity + "x " + getPk().getCard().getNameFr();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result + quantity;
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
		DeckLine other = (DeckLine) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	
}
