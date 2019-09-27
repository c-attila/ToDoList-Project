package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DAO.Todo;
import model.DAO.TodoDAO;
import model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Logger logger = LogManager.getLogger();

    private Model model;
    private TodoDAO todoDAO;

    List<CheckBox> checkBoxList;

    private String userName;

    @FXML
    private VBox list_VBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private void adatbazisProbaAction(javafx.event.ActionEvent actionEvent) {
        model.adatbazisProba();
    }

    @FXML
    private void showTodoAction() {
        LocalDate date = datePicker.getValue();
        List<Todo> listTodo = TodoDAO.listTodo("id", userName, Timestamp.valueOf(date.atStartOfDay()));
        logger.info(listTodo);
        showTodos(listTodo);
    }


    public void login(Stage stage, Scene scene, String userName) {

        this.userName = userName;

        stage.setScene(scene);

        Text userNameText = (Text) scene.lookup("#user_name_text");
        userNameText.setText(userName);

    }

    private void showTodos(List<Todo> listTodo) {

        int i = 0;
        if (listTodo != null)
            for (Todo todo : listTodo) {

                HBox hBox = new HBox();
                if (i == 0) {
                    hBox.setStyle("-fx-padding: 8;");
                    i = 1;
                } else {
                    hBox.setStyle("-fx-padding: 8; -fx-background-color: #429eb8;");
                    i = 0;
                }
                hBox.setSpacing(20);
                hBox.setAlignment(Pos.CENTER_LEFT);

                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(25);
                rectangle.setWidth(25);
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.rgb(153, 216, 234));
                hBox.getChildren().add(rectangle);

                Text text = new Text();
                text.setText(todo.getId() + "." + todo.getDescription());
                text.setStyle("-fx-font: 16 arial;");
                text.setWrappingWidth(620);
                hBox.getChildren().add(text);

                Text text2 = new Text();
                text2.setText(todo.getDeadline().toString());
                text2.setStyle("-fx-font: 20 arial;");
                text2.setWrappingWidth(100);
                hBox.getChildren().add(text2);

                Text text3 = new Text();
                text3.setText(todo.getEmployee());
                text3.setStyle("-fx-font: 20 arial;");
                text3.setWrappingWidth(100);
                hBox.getChildren().add(text3);

                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(todo.isDone());
                checkBoxList.add(checkBox);
                hBox.getChildren().add(checkBox);

                list_VBox.getChildren().add(hBox);
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
