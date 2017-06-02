package fr.free.francois.olivier.magicmanagerws.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import fr.free.francois.olivier.magicmanagerws.dao.HibernateUtil;
import fr.free.francois.olivier.magicmanagerws.model.Card;

public class Demo {
	
	static BufferedReader keyboard = new BufferedReader(
				new InputStreamReader(System.in)
	);
			
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("READY.....");
		//Card c = null;
		try ( SessionFactory sessionFactory = HibernateUtil.getSessionFactory() ;
				  Session session = HibernateUtil.getSession() ) {
			
			/*Card card = session.load(Card.class, 7); 
			System.out.println(card);
			Set<Deck> decks = card.getDecks();
			
			for (Deck deck : decks) {
				System.out.println(deck);
			}
			
			Query<Card> query = session.createNamedQuery("loadCardById", Card.class);
			query.setParameter("idCard", 2);
			c = query.getSingleResult();
			System.out.println("--------------------");
			System.out.println(c);*/
			
			Card c = session.load(Card.class, 2);
			System.out.println(c);
			//c.setColor("BBBBBBB");
			System.out.println( "Press Enter Key" );
			keyboard.readLine();
			
			
			Query<Card> query = session.createNamedQuery("loadCardById", Card.class);
			query.setParameter("idCard", 2);
			c = query.getSingleResult();
			System.out.println(c);
			
			c = session.load(Card.class, 2);
			System.out.println(c);
			
			
			
			
			
			
		}
		
	}
}
