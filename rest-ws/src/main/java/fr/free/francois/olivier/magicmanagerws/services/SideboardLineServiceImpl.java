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
import fr.free.francois.olivier.magicmanagerws.model.SideboardLine;

@Repository
public class SideboardLineServiceImpl implements SideboardLineService {

	private static Logger logger  = LogManager.getLogger(SideboardLineServiceImpl.class);
	
	@Override
	public SideboardLine findByCardAndDeck(Card card, Deck deck) {
		SideboardLine sideboardLine = null;
		try ( Session session = HibernateUtil.getSession() ) {
			Query<SideboardLine> querySideboardLine = session.createNamedQuery("loadSideboardLine", SideboardLine.class);
			querySideboardLine.setParameter("card", card);
			querySideboardLine.setParameter("deck", deck);
			sideboardLine = querySideboardLine.getSingleResult();
		} catch (Exception e) {
			sideboardLine = null;
			logger.warn(e);
		}
		return sideboardLine;
	}
	
	@Override
	public void saveSideboardLine(SideboardLine sideboardLine) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			Card card = sideboardLine.getCard();
			card.getSideboardLines().add(sideboardLine);
			session.saveOrUpdate(card);
			trans.commit();
		}
	}

	@Override
	public void deleteSideboardLine(SideboardLine sideboardLine) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.delete(sideboardLine);
			trans.commit();
		}		
	}
	
	@Override
	public boolean isSideboardLineExist(SideboardLine sideboardLine) {
		boolean isExist = true;
		SideboardLine sl = null;
		try ( Session session = HibernateUtil.getSession() ) {
			Query<SideboardLine> querySideboardLine = session.createNamedQuery("loadSideboardLine", SideboardLine.class);
			querySideboardLine.setParameter("card", sideboardLine.getCard());
			querySideboardLine.setParameter("deck", sideboardLine.getDeck());
			sl = querySideboardLine.getSingleResult();
			logger.warn(sl);
		} catch ( Exception e ) {
			isExist = false;
			logger.warn(e);
		}
		return isExist;
	}
	

}
