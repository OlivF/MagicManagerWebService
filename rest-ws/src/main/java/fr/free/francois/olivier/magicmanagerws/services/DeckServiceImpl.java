package fr.free.francois.olivier.magicmanagerws.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fr.free.francois.olivier.magicmanagerws.dao.HibernateUtil;
import fr.free.francois.olivier.magicmanagerws.model.Deck;

@Repository
public class DeckServiceImpl implements DeckService {

	private static Logger logger  = LogManager.getLogger(DeckServiceImpl.class);
	
	@Override
	public Set<Deck> findAll() {
		Set<Deck> decks = new HashSet<>();
		try ( Session session = HibernateUtil.getSession() ) {
			
			Query<Deck> queryDeck = session.createNamedQuery("loadDecks", Deck.class);
			ScrollableResults results = queryDeck.scroll( ScrollMode.FORWARD_ONLY );
			int cpt = 0;
			while (results.next()) {
				decks.add((Deck) results.get(0));
				if ( ++cpt % 200 == 0 ) {
					session.clear();
				}
			}
		}
		return decks;
	}

	@Override
	public Deck findById(int id) {
		Deck deck = null;
		try ( Session session = HibernateUtil.getSession() ) {
			Query<Deck> queryDeck = session.createNamedQuery("loadDeckById", Deck.class);
			queryDeck.setParameter("idDeck", id);
			deck = queryDeck.getSingleResult();
		} catch (Exception e) {
			logger.warn(e);
			deck = null;
		}
		return deck;
	}

	@Override
	public List<Deck> findByName(String name) {
		List<Deck> editions = new ArrayList<>();
		try ( Session session = HibernateUtil.getSession() ) {
			Query<Deck> queryEdition = session.createNamedQuery("loadDecksByName", Deck.class);
			queryEdition.setParameter("name", "%" + name + "%");
			editions = queryEdition.getResultList();
		}
		return editions;
	}

	@Override
	public void saveDeck(Deck deck) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.save(deck);
			trans.commit();
		}
	}

	@Override
	public void updateDeck(Deck deck) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.saveOrUpdate(deck);
			trans.commit();
		}
	}

	@Override
	public void deleteDeck(Deck deck) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.delete(deck);
			trans.commit();
		}		
	}

	@Override
	public boolean isDeckExist(Deck deck) {
		boolean isExist = true;
		Deck d = null;
		try ( Session session = HibernateUtil.getSession() ) {
			Query<Deck> queryDeck = session.createNamedQuery("loadDeckByName", Deck.class);
			queryDeck.setParameter("name", deck.getName());
			d = queryDeck.getSingleResult();
			logger.warn(d);
		} catch ( Exception e ) {
			logger.warn(e);
			isExist = false;
		}
		return isExist;
	}

}
