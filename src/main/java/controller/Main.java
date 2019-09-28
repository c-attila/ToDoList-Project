package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DAO.TodoDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;

import java.io.IOException;

public class Main extends Application {

    public static final String ADMIN_NAME = "admin";
    public static final String PASSWD = "admin";

    private static Logger logger = LogManager.getLogger();

    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller();

        Parent root = FXMLLoader.load(getClass().getResource("/JFX/loginScene.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("/JFX/employeeScene.fxml"));
        Parent root3 = FXMLLoader.load(getClass().getResource("/JFX/adminScene.fxml"));
        Scene scene = new Scene(root);
        Scene scene2 = new Scene(root2);
        Scene scene3 = new Scene(root3);

        primaryStage.setTitle("ToDoList");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            TodoDAO.close();
            controller.syncCheckBoxes();
        });

        Button loginButton = (Button) scene.lookup("#login_button");
        loginButton.setOnAction(actionEvent -> {

            TextField userNameTextField = (TextField) scene.lookup("#user_name_text_field");
            TextField passwordTextField = (TextField) scene.lookup("#password_text_field");

            if(!userNameTextField.getText().equals(ADMIN_NAME) && !userNameTextField.getText().isEmpty()) {
                controller.login(primaryStage, scene2, userNameTextField.getText());
            }else{
                if(userNameTextField.getText().equals(ADMIN_NAME)){
                    if(passwordTextField.getText().equals(PASSWD)){
                        controller.login(primaryStage, scene3, userNameTextField.getText());
                    }else{
                        Text wrong_passwd_text = (Text) scene.lookup("#wrong_passwd_text");
                        wrong_passwd_text.setVisible(true);
                    }
                }
            }


        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
