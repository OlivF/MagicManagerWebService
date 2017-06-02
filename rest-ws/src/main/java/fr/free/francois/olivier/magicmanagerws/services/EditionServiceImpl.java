package fr.free.francois.olivier.magicmanagerws.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fr.free.francois.olivier.magicmanagerws.dao.HibernateUtil;
import fr.free.francois.olivier.magicmanagerws.model.Edition;

@Repository
public class EditionServiceImpl implements EditionService {

	@Override
	public Set<Edition> findAll() {
		Set<Edition> editions = new HashSet<Edition>();
		try ( Session session = HibernateUtil.getSession() ) {
			
			Query<Edition> queryEdition = session.createNamedQuery("loadEditions", Edition.class);
			ScrollableResults results = queryEdition.scroll( ScrollMode.FORWARD_ONLY );
			int cpt = 0;
			while (results.next()) {
				editions.add((Edition) results.get(0));
				if ( ++cpt % 200 == 0 ) {
					session.clear();
				}
			}
		}
		return editions;
	}

	@Override
	public Edition findById(int id) {
		Edition edition = null;
		try ( Session session = HibernateUtil.getSession() ) {
		
			Query<Edition> queryType = session.createNamedQuery("loadEditionById", Edition.class);
			queryType.setParameter("idEdition", id);
			edition = queryType.getSingleResult();
		} catch (Exception e) {
			edition = null;
		}
		return edition;
	}

	@Override
	public List<Edition> findByName(String name) {
		List<Edition> editions = new ArrayList<Edition>();
		try ( Session session = HibernateUtil.getSession() ) {
		
			Query<Edition> queryEdition = session.createNamedQuery("loadEditionsByName", Edition.class);
			queryEdition.setParameter("name", name);
			editions = queryEdition.getResultList();
		}
		return editions;
	}

	@Override
	public void saveEdition(Edition edition) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.save(edition);
			trans.commit();
		}
	}

	@Override
	public void updateEdition(Edition edition) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.saveOrUpdate(edition);
			trans.commit();
		}
	}

	@Override
	public void deleteEdition(Edition edition) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.delete(edition);
			trans.commit();
		}		
	}

	@Override
	public boolean isEditionExist(Edition edition) {
		boolean isExist = true;
		try ( Session session = HibernateUtil.getSession() ) {
		
			Query<Edition> queryEdition = session.createNamedQuery("loadEditionByNameFrAndEn", Edition.class);
			queryEdition.setParameter("nameFr", edition.getNameFr());
			queryEdition.setParameter("nameEn", edition.getNameEn());
			edition = queryEdition.getSingleResult();
		} catch ( Exception e ) {
			isExist = false;
		}
		return isExist;
	}

}
