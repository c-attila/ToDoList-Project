package model;

import controller.Controller;
import model.DAO.Todo;
import model.DAO.TodoDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;

public class Model {

    private static Logger logger = LogManager.getLogger();

    private TodoDAO dao;
    private Controller controller;

    public Model() {
        dao = new TodoDAO();
    }

    public void adatbazisProba() {

        java.util.Date today = new java.util.Date();
        Todo todo = new Todo(new java.sql.Timestamp(today.getTime()), "r", "asd", new java.sql.Timestamp(today.getTime()), "Alad�r", false);
        dao.saveTodo(todo);

        System.out.println(TodoDAO.listTodo("addingDate", "admin", new java.sql.Timestamp(today.getTime())));
        TodoDAO.updateTodo("color", "'red'", "id", 3);
    }
}