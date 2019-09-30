package controller;

import javafx.event.ActionEvent;
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
import java.time.temporal.ChronoUnit;
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
    private Pane pane_lgnscn;

    @FXML
    private DatePicker datePicker;

    private ColorPicker colorPicker;

    private TextArea addDescriptionTextArea, addEmployeeTextArea;

    private DatePicker deadlineDatePicker;

    @FXML
    private void changeDate() {

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

        if (userName.equals(Main.ADMIN_NAME))
            buildAdminFunctions();

        Date today = new Date();
        buildScrollPane(new Timestamp(today.getTime()));

    }

    public void back(@org.jetbrains.annotations.NotNull Stage stage, Scene scene) {

        this.scene = scene;

        stage.setScene(scene);

        pane_lgnscn = (Pane) scene.lookup("#pane_lgnscn");

    }

    private void buildAdminFunctions() {

        logger.info("buildAdminFunctions...");

        HBox hBox = new HBox();
        hBox.setLayoutY(500);
        hBox.setLayoutX(20);
        hBox.setSpacing(20);

        colorPicker = new ColorPicker();
        colorPicker.setPrefHeight(60);
        hBox.getChildren().add(colorPicker);

        addDescriptionTextArea = new TextArea();
        addDescriptionTextArea.setPrefHeight(60);
        addDescriptionTextArea.setPrefWidth(430);
        hBox.getChildren().add(addDescriptionTextArea);

        deadlineDatePicker = new DatePicker();
        deadlineDatePicker.setPrefHeight(60);
        deadlineDatePicker.setValue(LocalDate.now().plus(1, ChronoUnit.DAYS));
        hBox.getChildren().add(deadlineDatePicker);

        addEmployeeTextArea = new TextArea();
        addEmployeeTextArea.setPrefWidth(60);
        addEmployeeTextArea.setPrefHeight(100);
        hBox.getChildren().add(addEmployeeTextArea);

        addButton = new Button();
        addButton.setPrefHeight(60);
        addButton.setText("Hozzáadás");
        addButton.setOnAction(actionEvent -> addButtonAction());
        hBox.getChildren().add(addButton);

        pane.getChildren().add(hBox);

    }

    private void buildScrollPane(Timestamp timestamp) {
        buildScrollPane(timestamp, "id");
    }

    private void buildScrollPane(Timestamp timestamp, String attribute) {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutY(114);
        scrollPane.setPrefHeight(485);
        if (userName.equals(Main.ADMIN_NAME))
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
        showTodo(attribute, vBox, timestamp);
        anchorPane.getChildren().add(vBox);

        pane.getChildren().removeAll();
        pane.getChildren().add(scrollPane);

    }

    private VBox showTodo(String attribute, VBox vBox, Timestamp timestamp) {

        logger.info("showTodo...");
        logger.info("userName: " + userName);


        List<Todo> listTodo = TodoDAO.listTodo(attribute, userName, timestamp);
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
                try {
                    rectangle.setFill(Color.web(todo.getColor()));
                } catch (IllegalArgumentException e) {
                    rectangle.setFill(Color.WHITE);
                }
                rectangle.setStroke(Color.web("#429eb8"));
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
//                checkBoxList.add(checkBox);
//                checkBox.setId(String.valueOf(todo.getId()));
                checkBox.setOnAction(actionEvent -> syncCheckBoxes(todo));
                hBox.getChildren().add(checkBox);

                vBox.getChildren().add(hBox);
            }

        return vBox;

    }

    @FXML
    private void addButtonAction() {

        Date today = new Date();
        Todo todo = new Todo(new Timestamp(today.getTime()), colorPicker.getValue().toString().substring(2, 10), addDescriptionTextArea.getText(),
                Timestamp.valueOf(deadlineDatePicker.getValue().atStartOfDay()), addEmployeeTextArea.getText(), false);
        TodoDAO.saveTodo(todo);
        buildScrollPane(Timestamp.valueOf(datePicker.getValue().atStartOfDay()));
        colorPicker.setValue(Color.WHITE);
        addDescriptionTextArea.setText("");
        deadlineDatePicker.setValue(LocalDate.now().plus(1, ChronoUnit.DAYS));
        addEmployeeTextArea.setText("");
    }

    @FXML
    private void sortTodos(ActionEvent event) {
        int value = Integer.parseInt((String) ((MenuItem) event.getSource()).getUserData());
        switch (value) {
            case 2:
                buildScrollPane(Timestamp.valueOf(datePicker.getValue().atStartOfDay()), "description");
                break;
            case 3:
                buildScrollPane(Timestamp.valueOf(datePicker.getValue().atStartOfDay()), "color");
                break;
            case 4:
                buildScrollPane(Timestamp.valueOf(datePicker.getValue().atStartOfDay()), "deadline");
                break;
            case 5:
                buildScrollPane(Timestamp.valueOf(datePicker.getValue().atStartOfDay()), "employee");
                break;
            default:
                buildScrollPane(Timestamp.valueOf(datePicker.getValue().atStartOfDay()), "id");
        }
    }

    public void syncCheckBoxes(Todo todo) {

        logger.info("syncCheckBoxes...");
        TodoDAO.updateTodo("isDone",!todo.isDone(),"id",todo.getId());

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
