package mate.academy.dao.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't add CinemaHall entity, error: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHall;
    }

    @Override
    public CinemaHall get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> sessionQuery = session.createQuery("from CinemaHall ch "
                    + "where ch.id = :id", CinemaHall.class);
            sessionQuery.setParameter("id", id);
            return sessionQuery.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't find CinemaHall by id: " + id + " , error: ", e);
        }

    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> getMovieQuery = session.createQuery(
                    "from CinemaHall ch", CinemaHall.class);
            return getMovieQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get CinemaHalls from DB. Error: ", e);
        }
    }
}
