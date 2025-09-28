package controllers;

import Models.Customer;
import connectionDB.LoginDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
        JOptionPane.showMessageDialog(null,"Working On Progress..!");
    }

    @FXML
    void signInBtnAction(ActionEvent event)  {
        String nameORmail=txtUName.getText().trim();
        String pass=txtUPass.getText();
        if(!nameORmail.isBlank()&&!pass.isEmpty()){
            try {

                ArrayList<Customer> emailArray= LoginDetailsController.getLoginDetails();



                for (Customer customer:emailArray) {
                    if (nameORmail.equals(customer.getEmail()) || nameORmail.equals(customer.getAccountName())) {

                        if (BCrypt.checkpw(pass,customer.getPassword())) {
                            JOptionPane.showMessageDialog(null, "Login Successful!");
                            txtUName.setText("");
                            txtUPass.setText("");
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong Password!");
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Account Name or Email not valid!");


            }catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }

        }else{
            JOptionPane.showMessageDialog(null,"All Fields Required..!");
        }

    }

    @FXML
    void signUpLinkAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SignUp_From.fxml"))));
        Stage stage2= (Stage) signUpLink.getScene().getWindow();
        stage2.close();
        stage.show();
    }

    public void txtUNameAction(KeyEvent keyEvent) {
        String email=txtUName.getText();
        if (email.matches("^[a-zA-Z0-9._%+-]+@")) {
            txtUName.setText(email + "gmail.com");

            txtUPass.requestFocus();

        }
    }
}
