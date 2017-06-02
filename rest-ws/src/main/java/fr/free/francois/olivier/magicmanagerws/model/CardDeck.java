package fr.free.francois.olivier.magicmanagerws.model;

public class CardDeck {

	private int idCard;
	private int idDeck;
	private int quantity;
	
	public CardDeck() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CardDeck(int idCard, int idDeck, int quantity) {
		super();
		this.idCard = idCard;
		this.idDeck = idDeck;
		this.quantity = quantity;
	}
	public int getIdCard() {
		return idCard;
	}
	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}
	public int getIdDeck() {
		return idDeck;
	}
	public void setIdDeck(int idDeck) {
		this.idDeck = idDeck;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CardDeck [idCard=" + idCard + ", idDeck=" + idDeck + ", quantity=" + quantity + "]";
	}
	
	
	
	
	
}
