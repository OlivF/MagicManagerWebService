package fr.free.francois.olivier.magicmanagerws.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fr.free.francois.olivier.magicmanagerws.dao.HibernateUtil;
import fr.free.francois.olivier.magicmanagerws.model.Card;

@Repository
public class CardServiceImpl implements CardService {

	@Override
	public Set<Card> findAll() {
		Set<Card> cards = new LinkedHashSet<Card>(0);
		try ( Session session = HibernateUtil.getSession() ) {
			
			Query<Card> queryCard = session.createNamedQuery("loadCards", Card.class);
			
			ScrollableResults results = queryCard.scroll( ScrollMode.FORWARD_ONLY );
			int cpt = 0;
			while (results.next()) {
				cards.add((Card) results.get(0));
				if ( ++cpt % 200 == 0 ) {
					session.clear();
				}
			}
		}
		return cards;
	}

	@Override
	public Card findById(int id) {
		Card card = null;
		try ( Session session = HibernateUtil.getSession() ) {
		
			Query<Card> queryCard = session.createNamedQuery("loadCardById", Card.class);
			queryCard.setParameter("idCard", id);
			card = queryCard.getSingleResult();
		} catch ( Exception e ) {
			card = null;
		}
		return card;
	}

	@Override
	public List<Card> findByName(String name) {
		List<Card> cards = new ArrayList<Card>();
		try ( Session session = HibernateUtil.getSession() ) {
		
			Query<Card> queryCard = session.createNamedQuery("loadCardsByName", Card.class);
			queryCard.setParameter("name", "%" + name + "%");
			cards = queryCard.getResultList();
		}
		return cards;
	}

	@Override
	public void saveCard(Card card) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.save(card);
			trans.commit();
		}
	}

	@Override
	public void updateCard(Card card) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.saveOrUpdate(card);
			trans.commit();
		}
	}

	@Override
	public void deleteCard(Card card) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.delete(card);
			trans.commit();
		}		
	}

	@Override
	public boolean isCardExist(Card card) {
		boolean isExist = true;
		try ( Session session = HibernateUtil.getSession() ) {
		
			Query<Card> queryCard = session.createNamedQuery("loadCardByNameAndEdition", Card.class);
			queryCard.setParameter("nameFr", card.getNameFr());
			queryCard.setParameter("nameEn", card.getNameEn());
			queryCard.setParameter("edition", card.getEdition());
			card = queryCard.getSingleResult();
		} catch ( Exception e ) {
			isExist = false;
		}
		return isExist;
	}

}
