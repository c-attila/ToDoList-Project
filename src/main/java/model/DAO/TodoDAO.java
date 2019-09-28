package model.DAO;

import com.mysql.jdbc.log.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

    public static void close() {
        try {
            em.close();
            emf.close();
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
        }
    }

}
