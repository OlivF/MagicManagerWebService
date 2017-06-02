package fr.free.francois.olivier.magicmanagerws.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Olivier
 *
 */
@Embeddable
public class DeckLineId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Card card;
	private Deck deck;
	
	@JsonIgnore
	@ManyToOne
	public Card getCard() {
		return card;
	}
	
	public void setCard(Card card) {
		this.card = card;
	}
	
	@JsonIgnore
	@ManyToOne
	public Deck getDeck() {
		return deck;
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	
	public boolean equals (Object o) {
		if (this==o) return true;
		if(o==null || getClass() != o.getClass()) return false;
		
		DeckLineId that = (DeckLineId) o;
		
		if(card!= null ? !card.equals(that.card):that.card!=null) return false;
		if(deck!= null ? !deck.equals(that.deck):that.deck!=null) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result;
		result=(card!=null ? card.hashCode():0);
		result=31*result+(deck!=null ? deck.hashCode():0);
		return result;
		
	}
}
