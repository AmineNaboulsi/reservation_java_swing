package com.nabnab.reservation;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class LoginController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;

    @FXML
    private Label txt_error;
    @FXML
    private Label welcomeText2;


    public ConnectionDB con =new ConnectionDB();
    boolean CoonectionDone = false;


    @FXML
    private void initialize() {

        welcomeText2.setOnMouseEntered(event -> welcomeText2.setCursor(javafx.scene.Cursor.HAND));
        welcomeText2.setOnMouseExited(event -> welcomeText2.setCursor(javafx.scene.Cursor.DEFAULT));

        welcomeText2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    String url = "https://www.youtube.com/watch?v=JhjFzDCbP00";
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI(url));
                    } else {
                        // Handle case where Desktop API is not supported
                        System.out.println("Desktop API is not supported.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        CoonectionDone =  con.CreateConnection();
        if(CoonectionDone){
            welcomeText.setText("Connected");
            welcomeText.setStyle("-fx-text-fill : #188870");
            System.out.println("Connected");
        }
        else{
            welcomeText.setText("Offline");
            welcomeText.setStyle("-fx-text-fill : #C81D3D");
            System.out.println("Offline");
        }
    }

    @FXML
    protected void Login_In() {
        if(CoonectionDone){
            welcomeText.setText("Connected");
            welcomeText.setStyle("-fx-text-fill : #188870");
            System.out.println("Connected");
            if(CoonectionDone){
                if(txt_username.getText().equalsIgnoreCase("") ||
                        txt_password.getText().equalsIgnoreCase("")){
                    txt_error.setText("Required Field (UserName , Paasword)");
                    txt_error.setStyle("-fx-text-fill : #C81D3D");
                    return;
                }
                Boolean auth = con.Authentification(new User(
                        txt_username.getText().trim(),
                        txt_password.getText().trim()));
                if(auth){
                    txt_error.setText("Authentification succssfully");
                    txt_error.setStyle("-fx-text-fill : #188870");
//                    con.CloseConnection();
                    try {
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Réservations");
                        Scene scene = new Scene(root, 900, 600);
                        stage.setResizable(false);
                        stage.setScene(scene);

                        stage.show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    txt_error.setText("Authentification error");
                    txt_error.setStyle("-fx-text-fill : #C81D3D");
                }
            }
        }
        else{
            welcomeText.setText("Offline");
            welcomeText.setStyle("-fx-text-fill : #C81D3D");
            System.out.println("Connection not working");
            txt_error.setText("Connection Failed");
            txt_error.setStyle("-fx-text-fill : #C81D3D");
        }
    }


}