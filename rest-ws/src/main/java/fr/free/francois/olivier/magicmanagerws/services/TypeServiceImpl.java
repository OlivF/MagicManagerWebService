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
import fr.free.francois.olivier.magicmanagerws.model.Type;

@Repository
public class TypeServiceImpl implements TypeService {

	private static Logger logger  = LogManager.getLogger(TypeServiceImpl.class);
	
	@Override
	public Set<Type> findAll() {
		Set<Type> types = new HashSet<>();
		try ( Session session = HibernateUtil.getSession() ) {
			session.clear();
			Query<Type> queryType = session.createNamedQuery("loadTypes", Type.class);
			ScrollableResults results = queryType.scroll( ScrollMode.FORWARD_ONLY );
			int cpt = 0;
			while (results.next()) {
				types.add((Type) results.get(0));
				if ( ++cpt % 200 == 0 ) {
					session.clear();
				}
			}
		}
		return types;
	}

	@Override
	public Type findById(int id) {
		Type type = null;
		try ( Session session = HibernateUtil.getSession() ) {
			Query<Type> queryType = session.createNamedQuery("loadTypeById", Type.class);
			queryType.setParameter("idType", id);
			type = queryType.getSingleResult();
		} catch (Exception e) {
			type = null;
			logger.warn(e);
		}
		return type;
	}

	@Override
	public List<Type> findByName(String name) {
		List<Type> types = new ArrayList<>();
		try ( Session session = HibernateUtil.getSession() ) {
			Query<Type> queryType = session.createNamedQuery("loadTypesByName", Type.class);
			queryType.setParameter("name", "%" + name + "%");
			types = queryType.getResultList();
		}
		return types;
	}

	@Override
	public Type saveType(Type type) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.save(type);
			trans.commit();
		}
		return type;
	}

	@Override
	public void updateType(Type type) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.saveOrUpdate(type);
			trans.commit();
		}
	}

	@Override
	public void deleteType(Type type) {
		try ( Session session = HibernateUtil.getSession() ) {
			Transaction trans = session.beginTransaction();
			session.delete(type);
			trans.commit();
		}		
	}

	@Override
	public boolean isTypeExist(Type type) {
		boolean isExist = true;
		Type t = null;
		try ( Session session = HibernateUtil.getSession() ) {
			Query<Type> queryType = session.createNamedQuery("loadTypeByNameFrAndEn", Type.class);
			queryType.setParameter("nameFr", type.getNameFr());
			queryType.setParameter("nameEn", type.getNameEn());
			t = queryType.getSingleResult();
			logger.warn(t);
		} catch ( Exception e ) {
			isExist = false;
			logger.warn(e);
		}
		return isExist;
	}

}
