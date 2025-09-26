package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login_From_Controller {

    @FXML
    private Hyperlink forgotPassLink;

    @FXML
    private Button signInBtn;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private TextField txtUName;

    @FXML
    private PasswordField txtUPass;

    @FXML
    void forgotPassLinkAction(ActionEvent event) {

    }

    @FXML
    void signInBtnAction(ActionEvent event)  {


    }

    @FXML
    void signUpLinkAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SignUp_From.fxml"))));
        stage.show();
    }

}
