package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Logger logger = LogManager.getLogger();

    private Model model;
    private TodoDAO todoDAO;

    List<CheckBox> checkBoxList;

    private static String userName;
    private Scene scene;

    private Text userNameText;

    Button addButton;

    @FXML
    private Pane pane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private void adatbazisProbaAction(javafx.event.ActionEvent actionEvent) {
        model.adatbazisProba();
    }

    @FXML
    private void changeDate(){
        syncCheckBoxes();
        buildScrollPane(Timestamp.valueOf(datePicker.getValue().atStartOfDay()));
    }

    public void login(Stage stage, Scene scene, String userName) {

        this.userName = userName;
        this.scene = scene;

        stage.setScene(scene);

        userNameText = (Text) scene.lookup("#user_name_text");
        userNameText.setText(userName);

        datePicker = (DatePicker) scene.lookup("#datePicker");
        datePicker.setValue(LocalDate.now());

        pane = (Pane) scene.lookup("#pane");

        if(userName.equals(Main.ADMIN_NAME))
            buildAdminFunctions();

        Date today = new Date();
        buildScrollPane(new Timestamp(today.getTime()));

    }

    private void buildAdminFunctions(){

        logger.info("buildAdminFunctions...");

        HBox hBox = new HBox();
        hBox.setLayoutY(500);
        hBox.setLayoutX(20);
        hBox.setSpacing(20);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setPrefHeight(60);
        hBox.getChildren().add(colorPicker);

        TextArea addDescriptionTextArea = new TextArea();
        addDescriptionTextArea.setPrefHeight(60);
        addDescriptionTextArea.setPrefWidth(430);
        hBox.getChildren().add(addDescriptionTextArea);

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefHeight(60);
        datePicker.setValue(LocalDate.now());
        hBox.getChildren().add(datePicker);

        TextArea addEmployeeTextArea = new TextArea();
        addEmployeeTextArea.setPrefWidth(60);
        addEmployeeTextArea.setPrefHeight(100);
        hBox.getChildren().add(addEmployeeTextArea);

        addButton = new Button();
        addButton.setPrefHeight(60);
        addButton.setText("Hozzáadás");
        hBox.getChildren().add(addButton);

        pane.getChildren().add(hBox);

    }

    private void buildScrollPane(Timestamp timestamp){

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutY(114);
        scrollPane.setPrefHeight(485);
        if(userName.equals(Main.ADMIN_NAME))
            scrollPane.setPrefHeight(385);
        scrollPane.setPrefWidth(1000);
        scrollPane.setStyle("-fx-background-color: #99d8ea; -fx-border-color: #429eb8;");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(990);
        anchorPane.setPrefHeight(500);
        anchorPane.setStyle("-fx-background-color: #99d8ea;");
        scrollPane.setContent(anchorPane);

        VBox vBox = new VBox();
        vBox.setPrefWidth(100);
        showTodo(vBox,timestamp);
        anchorPane.getChildren().add(vBox);

        pane.getChildren().removeAll();
        pane.getChildren().add(scrollPane);

    }

    private VBox showTodo(VBox vBox, Timestamp timestamp) {

        logger.info("showTodo...");
        logger.info("userName: " + userName);


        List<Todo> listTodo = TodoDAO.listTodo("id", userName, timestamp);
        logger.info(listTodo);

        checkBoxList = new ArrayList<>();

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

                vBox.getChildren().add(hBox);
            }

        return vBox;

    }

    public void syncCheckBoxes(){

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
