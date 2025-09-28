package controllers;

import Models.Customer;
import connectionDB.ConnectionOB;
import connectionDB.LoginDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Properties;

public class SignUp_From_Controller {
    public Label emailLabel;
    public Label accountNameLabel;
    private boolean passState=true;
    private boolean nicState=true;
    private boolean accountNameState=true;
    private boolean emailUniqueState=true;
    private boolean emailState=false;
    private String generatedOtp;
    public Label passLabel;
    public Label nicLabel;

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
        Stage stage2= (Stage) signInLink.getScene().getWindow();
        stage2.close();
        stage.show();
    }

    @FXML
    void signUpBtnAction(ActionEvent event) {
        String nic=txtNIC.getText().trim();
        String fName=txtFName.getText().trim();
        String lName=txtLName.getText().trim();
        String email=txtEmail.getText().trim();
        String aName=txtAName.getText().trim();
        String pass=txtPass.getText();
        String passCon=txtPassCon.getText();

        if (!nic.isBlank() && !fName.isBlank() && !lName.isBlank() && !email.isBlank() && !aName.isBlank() && !pass.isBlank() && !passCon.isBlank()){
            if((pass).equals(passCon)){
                if(nicState && passState && emailUniqueState && accountNameState){
                    String emailFormatCheck=txtEmail.getText().trim();
                    if(emailFormatCheck.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")){
                        sendOtp(emailFormatCheck);
                        if(emailState){
                            Connection connection=null;
                            String EncryptPass= org.mindrot.jbcrypt.BCrypt.hashpw(pass, org.mindrot.jbcrypt.BCrypt.gensalt(12));

                            try {
                                connection= ConnectionOB.getInstance().getConnection();
                                connection.setAutoCommit(false);
                                Customer customer=new Customer(nic,fName,lName,email,aName,EncryptPass);
                                boolean state=LoginDetailsController.addDetails(customer);
                                if(state){
                                    JOptionPane.showMessageDialog(null,"You have signed up successfully ✅");

                                }else{
                                    JOptionPane.showMessageDialog(null,"Oops! Something went wrong. Please try again..!");

                                }
                                connection.commit();
                                connection.setAutoCommit(true);
                                if(state){
                                    Stage stage=new Stage();
                                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login_Form.fxml"))));
                                    Stage stage2= (Stage) signUpBtn.getScene().getWindow();
                                    stage2.close();
                                    stage.show();

                                }
                            } catch (ClassNotFoundException | SQLException | IOException ex) {
                                try {
                                    connection.rollback();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                JOptionPane.showMessageDialog(null,ex.getMessage());
                            }
                        }else{
                        JOptionPane.showMessageDialog(null,"Check your Email Address and try again.!");

                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Invalid Email Address.!");

                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Password  &  NIC  &  Email  &  Account Name  must be Unique.!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Confirm password doesn’t match.");

            }
        }else{
            JOptionPane.showMessageDialog(null,"Please Fill in All Required Fields..!");
        }

    }

    @FXML
    void termsLinkAction(ActionEvent event) {

    }

    public void passCheckAction(KeyEvent keyEvent) {
        try {
           if(txtPass.getText().isEmpty()){
                   passLabel.setText("Enter a password for your account.");
                   passLabel.setStyle("-fx-text-fill: black;");
                   passState=false;

           }else {
               ArrayList<String> passArray= LoginDetailsController.getPassCheck();

                   for (String pass : passArray) {
                       String password = txtPass.getText();
                       if (org.mindrot.jbcrypt.BCrypt.checkpw(password, pass)) {
                           passLabel.setText("\"" + password + "\" is Already Used.!");
                           passLabel.setStyle("-fx-text-fill: red;");
                           passState = false;
                           return;
                       } else {
                           passLabel.setText("\"" + password + "\" is Available.!");
                           passLabel.setStyle("-fx-text-fill: green;");
                           passState = true;
                       }


               }
           }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    public void nicCheckAction(KeyEvent keyEvent) {
        try {
            if(txtNIC.getText().isEmpty()){
                nicLabel.setText("Enter your NIC Number.");
                nicLabel.setStyle("-fx-text-fill: black;");
                nicState=false;

            }else {
                ArrayList<String> nicArray = LoginDetailsController.getNicCheck();

                    for (String newNic : nicArray) {
                        String nic = txtNIC.getText();
                        if ((nic).equals(newNic)) {
                            nicLabel.setText("\"" + nic + "\" is Already Exists.!");
                            nicLabel.setStyle("-fx-text-fill: red;");
                            nicState = false;
                            return;
                        } else {
                            nicLabel.setText("\"" + nic + "\" is Available.!");
                            nicLabel.setStyle("-fx-text-fill: green;");
                            nicState = true;
                        }


                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    public void emailCheckAction(KeyEvent keyEvent) {
        try {

            if(txtEmail.getText().isEmpty()){
                emailLabel.setText("Enter your Email Address.");
                emailLabel.setStyle("-fx-text-fill: black;");
                emailUniqueState=false;

            }else {
                String email = txtEmail.getText().trim();

                ArrayList<String> emailArray = LoginDetailsController.getEmailCheck();
                if (email.matches("^[a-zA-Z0-9._%+-]+@")) {
                    txtEmail.setText(txtEmail.getText().trim() + "gmail.com");
                    email += "gmail.com";
                    signUpBtn.requestFocus();

                }

                    if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
                        email += "@gmail.com";

                        for (String emailCheck : emailArray) {
                            if ((email).equals(emailCheck)) {
                                emailLabel.setText("\"" + email + "\" is Already Used.!");
                                emailLabel.setStyle("-fx-text-fill: red;");
                                emailUniqueState = false;
                                return;
                            } else {
                                emailLabel.setText("\"" + email + "\" is Available.!");
                                emailLabel.setStyle("-fx-text-fill: green;");
                                emailUniqueState = true;

                            }
                        }


                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    public void accountNameCheckAction(KeyEvent keyEvent) {
        try {
            if(txtAName.getText().isEmpty()){
                accountNameLabel.setText("Enter a name for your account.");
                accountNameLabel.setStyle("-fx-text-fill: black;");
                accountNameState=false;

            }else {
                ArrayList<String> accountNameArray= LoginDetailsController.getAccountNameCheck();


                    for (String accountName : accountNameArray) {
                        String aName = txtAName.getText();
                        if (aName.equals(accountName)) {
                            accountNameLabel.setText("\"" + aName + "\" is Already Used.!");
                            accountNameLabel.setStyle("-fx-text-fill: red;");
                            accountNameState = false;
                            return;
                        } else {
                            accountNameLabel.setText("\"" + aName + "\" is Available.!");
                            accountNameLabel.setStyle("-fx-text-fill: green;");
                            accountNameState = true;
                        }
                    }


            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    private void sendOtp(String email) {
        generatedOtp = String.valueOf((int)(Math.random() * 900000) + 100000);
        System.out.println("Generated OTP: " + generatedOtp);
        sendEmail(txtEmail.getText(), generatedOtp);
        openOtpWindow();
    }
    private void openOtpWindow(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Verify OTP");
        dialog.setHeaderText("Enter the OTP sent to your email");
        dialog.setContentText("OTP:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(userInput -> {
            if (userInput.equals(generatedOtp)) {
                JOptionPane.showMessageDialog(null,"OTP Verified ✅");
                emailState=true;
            } else {
                JOptionPane.showMessageDialog(null,"Invalid OTP ❌");
                emailState=false;
            }
        });


    }
    private void sendEmail(String recipient, String otp) {
        String senderEmail = "dissanayakedilan2005@gmail.com";
        String senderPassword = "cenrkobrwnusrxnx";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);
            JOptionPane.showMessageDialog(null,"OTP Email Sent ✅");

        } catch (MessagingException ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage());

        }
    }
}
