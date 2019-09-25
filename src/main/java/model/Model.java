package model;

import model.DAO.Todo;
import model.DAO.TodoDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Model {

    private static Logger logger = LogManager.getLogger();

    private TodoDAO dao;

    public Model() {
        dao = new TodoDAO();
    }

    public void adatbazisProba() {

        java.util.Date today = new java.util.Date();
        Todo todo = new Todo('r', "asd", new java.sql.Timestamp(today.getTime()), "Aladár", false);
        dao.saveTodo(todo);
    }

}