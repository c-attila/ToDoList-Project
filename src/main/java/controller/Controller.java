package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import model.DAO.Todo;
import model.DAO.TodoDAO;
import model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Logger logger = LogManager.getLogger();

    private Model model;
    private TodoDAO todoDAO;

    @FXML
    private VBox list_VBox;

    @FXML
    private void adatbazisProbaAction(javafx.event.ActionEvent actionEvent) {
        model.adatbazisProba();
    }


    public void showTodos(String attribute, String user, java.sql.Timestamp date){

        List<Todo> listTodo = todoDAO.listTodo(attribute, user, date);

        for(Todo todo : listTodo) {

            logger.info(todo);

        }

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = new Model();
        todoDAO = new TodoDAO();

    }
}
