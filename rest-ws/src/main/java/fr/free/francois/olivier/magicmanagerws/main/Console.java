package fr.free.francois.olivier.magicmanagerws.main;

import java.util.Set;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import fr.free.francois.olivier.magicmanagerws.dao.HibernateUtil;
import fr.free.francois.olivier.magicmanagerws.model.Card;
import fr.free.francois.olivier.magicmanagerws.model.Deck;
import fr.free.francois.olivier.magicmanagerws.model.DeckLine;
import fr.free.francois.olivier.magicmanagerws.model.Edition;
import fr.free.francois.olivier.magicmanagerws.model.Type;

public class Console {
	
	public static void main(String[] args) {
		try ( SessionFactory sessionFactory = HibernateUtil.getSessionFactory() ;
				  Session session = HibernateUtil.getSession() ) {
			
			/* Pour r�cup�rer toutes les cartes */
			/* Utiliser la m�thode pour charger par paquets afin de ne pas p�ter la m�moire */
			Query<Card> queryCards = session.createNamedQuery("loadCards", Card.class);
			ScrollableResults results = queryCards.scroll( ScrollMode.FORWARD_ONLY );
			int cpt = 0;
			while (results.next()) {
				Card card = (Card) results.get(0);
				System.out.println( card );
				if ( ++cpt % 200 == 0 ) {
					session.clear();
				}
			}
			
			/* Afficher un deck */
			System.out.println(" -----------------------------------------------");
			Query<Deck> queryDeck = session.createNamedQuery("loadDeckById", Deck.class);
			queryDeck.setParameter("idDeck", 1);
			Deck d = queryDeck.getSingleResult();
			System.out.println(d);
		
			Set<DeckLine> deckLines = d.getDeckLines();
			for (DeckLine deckLine : deckLines) {
				System.out.println("\t"+ deckLine);
			}
			
			/*Sideboard s = d.getSideboard();
			System.out.println("\nSideboard " + s.getIdSideboard() + " >> " + s);
			Set<SideboardLine> sideboardLines = s.getSideboardLines();
			for (SideboardLine sideboardLine : sideboardLines) {
				System.out.println("\t"+ sideboardLine);
			}
			System.out.println(" -----------------------------------------------");*/
			
			Query<Type> queryType = session.createNamedQuery("loadCardsByType", Type.class);
			queryType.setParameter("idType", 1);
			Type t = queryType.getSingleResult();
			System.out.println("Type " + t.getIdType() + " >> " + t);
			
			Set<Card> cards = t.getCards();
			for (Card card : cards) {
				System.out.println("\t" + card.getQuantity() + "x " + card.getNameFr() + " (" + card.getDisponibility() + " disponibles)");
			}
			
			System.out.println(" -----------------------------------------------");
			
			Query<Edition> queryEdition = session.createNamedQuery("loadCardsByEdition", Edition.class);
			queryEdition.setParameter("idEdition", 1);
			Edition e = queryEdition.getSingleResult();
			System.out.println("Edition " + e.getIdEdition() + " >> " + e);
			
			cards = e.getCards();
			for (Card card : cards) {
				System.out.println("\t" + card.getQuantity() + "x " + card.getNameFr() + " (" + card.getDisponibility() + " disponibles)");
			}
			
		}
	}
}
