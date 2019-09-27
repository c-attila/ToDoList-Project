package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
import java.util.ArrayList;
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

    @FXML
    private void showTodosAction(){
        java.util.Date today = new java.util.Date();
        Todo todo = new Todo(new java.sql.Timestamp(today.getTime()), 'r', "It if sometimes furnished unwilling as addi", new java.sql.Timestamp(today.getTime()), "Aladár", false);
        Todo todo2 = new Todo(new java.sql.Timestamp(today.getTime()), 'r',
                "asd dasdasdasdasdsadasdsadsadsadsadsadsadsadasdsadsadsadsadscadasdsacsadsadsadsdsad" +
                "dsadsadsadsadsadsadsadsdsadsdadsad" +
                "dsadasdasdasdasd", new java.sql.Timestamp(today.getTime()), "Aladár", false);
        Todo todo3 = new Todo(new java.sql.Timestamp(today.getTime()), 'r', " but advantage now him. Remark easily garret nor nay. Civil those mrs enjoy shy fat merry. You greatest jointure saw horrible. He pr", new java.sql.Timestamp(today.getTime()), "Aladár", false);
        Todo todo4 = new Todo(new java.sql.Timestamp(today.getTime()), 'r', "se was justice new winding. In finished on he speaking suitable advanced if. Boy happiness sportsmen say prevaile", new java.sql.Timestamp(today.getTime()), "Aladár", false);
        Todo todo5 = new Todo(new java.sql.Timestamp(today.getTime()), 'r', "antage now him. Remark easily garret nor nay. Civil those mrs enjoy shy fat merry. You greate", new java.sql.Timestamp(today.getTime()), "Aladár", false);
        Todo todo6 = new Todo(new java.sql.Timestamp(today.getTime()), 'r', "It if sometimes furnished unwilling as addi", new java.sql.Timestamp(today.getTime()), "Aladár", false);


        List<Todo> listTodo = new ArrayList<>();
        listTodo.add(todo);
        listTodo.add(todo2);
        listTodo.add(todo3);
        listTodo.add(todo4);
        listTodo.add(todo5);
        listTodo.add(todo6);
        showTodos(listTodo);
    }


    public void login(Stage stage, Scene scene, String userName){

        stage.setScene(scene);

        Text userNameText = (Text) scene.lookup("#user_name_text");
        userNameText.setText(userName);

        java.util.Date today = new java.util.Date();
//        List<Todo> listTodo = TodoDAO.listTodo("id",userName,new java.sql.Timestamp(today.getTime()));

    }

    private void showTodos(List<Todo> listTodo){

        int i = 0;
        for(Todo todo : listTodo) {

            HBox hBox = new HBox();
            if(i == 0) {
                hBox.setStyle("-fx-padding: 8;");
                i = 1;
            }else {
                hBox.setStyle("-fx-padding: 8; -fx-background-color: #429eb8;");
                i = 0;
            }
            hBox.setSpacing(20);
            hBox.setAlignment(Pos.CENTER_LEFT);

            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(25);
            rectangle.setWidth(25);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.rgb(153,216,234));
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
