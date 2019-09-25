package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Logger logger = LogManager.getLogger();

    private Model model;

    @FXML
    private void adatbazisProbaAction(javafx.event.ActionEvent actionEvent) {
        model.adatbazisProba();
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
    public void initialize(URL location, ResourceBundle resources) { model = new Model(); }
}
