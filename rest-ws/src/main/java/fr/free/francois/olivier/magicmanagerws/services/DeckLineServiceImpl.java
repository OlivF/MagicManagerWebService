package fr.free.francois.olivier.magicmanagerws.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fr.free.francois.olivier.magicmanagerws.dao.HibernateUtil;
import fr.free.francois.olivier.magicmanagerws.model.Card;
import fr.free.francois.olivier.magicmanagerws.model.Deck;
import fr.free.francois.olivier.magicmanagerws.model.DeckLine;

@Repository
public class DeckLineServiceImpl implements DeckLineService {

	private static Logger logger  = LogManager.getLogger(DeckLineServiceImpl.class);
	
	@Override
	public DeckLine findByCardAndDeck(Card card, Deck deck) {
		DeckLine deckLine = null;
		try ( Session session = HibernateUtil.getSession() ) {
		
			Query<DeckLine> queryDeckLine = session.createNamedQuery("loadDeckLine", DeckLine.class);
			queryDeckLine.setParameter("card", card);
			queryDeckLine.setParameter("deck", deck);
			deckLine = queryDeckLine.getSingleResult();
		} catch (Exception e) {
			logger.warn(e);
			deckLine = null;
		}
		return deckLine;
	}
	
	@Override
	public void saveDeckLine(DeckLine deckLine) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			
			Card card = deckLine.getCard();
			card.getDeckLines().add(deckLine);
			session.saveOrUpdate(card);
			trans.commit();
		}
	}

	@Override
	public void deleteDeckLine(DeckLine deckLine) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.delete(deckLine);
			trans.commit();
		}		
	}
	
	@Override
	public boolean isDeckLineExist(DeckLine deckLine) {
		boolean isExist = true;
		DeckLine dl = null;
		try ( Session session = HibernateUtil.getSession() ) {
			Query<DeckLine> queryDeckLine = session.createNamedQuery("loadDeckLine", DeckLine.class);
			queryDeckLine.setParameter("card", deckLine.getCard());
			queryDeckLine.setParameter("deck", deckLine.getDeck());
			dl = queryDeckLine.getSingleResult();
			logger.warn(dl);
		} catch ( Exception e ) {
			logger.warn(e);
			isExist = false;
		}
		return isExist;
	}
	

}
