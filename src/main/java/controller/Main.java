package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.TodoDAO;

import java.io.IOException;

public class Main extends Application {

    private final String ADMIN_NAME = "admin";

    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller();

        Parent root = FXMLLoader.load(getClass().getResource("/JFX/loginScene.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("/JFX/employeeScene.fxml"));
        Scene scene = new Scene(root);
        Scene scene2 = new Scene(root2);

        primaryStage.setTitle("Hello World");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> TodoDAO.close());

        Button loginButton = (Button) scene.lookup("#login_button");
        loginButton.setOnAction(actionEvent -> {

            TextField userNameTextField = (TextField) scene.lookup("#user_name_text_field");
            TextField passwordTextField = (TextField) scene.lookup("#password_text_field");

            if(!userNameTextField.getText().equals(ADMIN_NAME) && !userNameTextField.getText().isEmpty())
                controller.login(primaryStage, scene2, userNameTextField.getText());


        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
