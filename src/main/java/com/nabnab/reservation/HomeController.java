package com.nabnab.reservation;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeController {

    @FXML
    Button btn_dashboard;

    @FXML
    Button btn_reservations;
    @FXML
    Button btn_rooms;

    @FXML
    public VBox panelContent = null;

    @FXML
    private void initialize() {
        try {
            mouse_click_changedbackground(0);
            FXMLLoader fxmload = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Pane item = fxmload.load();
            panelContent.getChildren().add(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        btn_dashboard.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mouse_click_changedbackground(0);
                    panelContent.getChildren().clear();
                    FXMLLoader fxmload = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Pane item = fxmload.load();
                    panelContent.getChildren().add(item);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        btn_rooms.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mouse_click_changedbackground(2);
                    panelContent.getChildren().clear();
                    FXMLLoader fxmload = new FXMLLoader(getClass().getResource("rooms.fxml"));
                    Pane item = fxmload.load();
                    panelContent.getChildren().add(item);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        btn_reservations.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mouse_click_changedbackground(1);
                    panelContent.getChildren().clear();
                    FXMLLoader fxmload = new FXMLLoader(getClass().getResource("reservations.fxml"));
                    Pane item = fxmload.load();
                    panelContent.getChildren().add(item);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
    public void mouse_click_changedbackground(int index){
        btn_dashboard.setStyle("-fx-background-color :  #EBE8F9");
        btn_reservations.setStyle("-fx-background-color :  #EBE8F9");
        btn_rooms.setStyle("-fx-background-color :  #EBE8F9");
        if(index == 0)btn_dashboard.setStyle("-fx-background-color : #6B6B6B;");
        if(index == 1)btn_reservations.setStyle("-fx-background-color : #6B6B6B;");
        if(index == 2)btn_rooms.setStyle("-fx-background-color : #6B6B6B;");
        btn_dashboard.setStyle("-fx-background-radius :  15");
        btn_reservations.setStyle("-fx-background-radius :  15");
        btn_rooms.setStyle("-fx-background-radius :  15");
    }

}
