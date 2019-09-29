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

}