package model.DAO;

import com.mysql.jdbc.log.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
import org.hibernate.service.spi.ServiceException;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

public class TodoDAO {

    private static Logger logger = LogManager.getLogger();

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public TodoDAO() {
        try {
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
        } catch (ServiceException e) {
            logger.warn("Connection couldn't be established. Exception: " + e);
        }
    }

    public static void saveTodo(Todo todo) {
        logger.info("saveTodo...");
        try {
            em.getTransaction().begin();
            em.persist(todo);
            em.getTransaction().commit();
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
        } catch (JDBCConnectionException e) {
            logger.warn("Connection not found. Exception: " + e);
        }
    }

    public static List<Todo> listTodo(String attribute, String user, java.sql.Timestamp addingDate) {

        logger.info("listTodo: " + attribute + " " + user + " " + addingDate);

        TypedQuery<Todo> query;
        try {
            if (user.equals("admin"))
                query = em.createQuery("SELECT td FROM Todo td " +
                        "WHERE EXTRACT(DAY FROM td.addingDate) = EXTRACT(DAY FROM \'" + addingDate.toString().split(" ")[0] + "\')" +
                        " ORDER BY td." + attribute, Todo.class);
            else
                query = em.createQuery("SELECT td FROM Todo td " +
                        "WHERE EXTRACT(DAY FROM td.addingDate) = EXTRACT(DAY FROM \'" + addingDate.toString().split(" ")[0] + "\')" +
                        " AND td.employee = '" + user + "'" +
                        " ORDER BY td." + attribute, Todo.class);
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
            return null;
        }

        logger.info(query.getResultList());
        return query.getResultList();
    }

    @Transactional
    public static <T> void updateTodo(String attributeToChange, T valueToChange, String byAttribute, T byValue) {
        try {
            Session session = HibernateUtility.getHibernateSession();
            session.getTransaction().begin();

            Query query = session.createQuery("UPDATE Todo td " +
                    "SET td." + attributeToChange + " = " + valueToChange + " " +
                    "WHERE td." + byAttribute + " = " + byValue);

            query.executeUpdate();
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
        }
    }

    public static void close() {
        try {
            em.close();
            emf.close();
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
        }
    }

}
