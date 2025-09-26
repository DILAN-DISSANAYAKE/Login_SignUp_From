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

public class SignUp_From_Controller {

    @FXML
    private Hyperlink signInLink;

    @FXML
    private Button signUpBtn;

    @FXML
    private Hyperlink termsLink;

    @FXML
    private TextField txtAName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFName;

    @FXML
    private TextField txtLName;

    @FXML
    private TextField txtNIC;

    @FXML
    private PasswordField txtPass;

    @FXML
    private PasswordField txtPassCon;

    @FXML
    void signInLinkAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login_Form.fxml"))));
        stage.show();
    }

    @FXML
    void signUpBtnAction(ActionEvent event) {

    }

    @FXML
    void termsLinkAction(ActionEvent event) {

    }

}
