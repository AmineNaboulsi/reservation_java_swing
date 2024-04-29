package com.nabnab.reservation;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DayFormat implements Initializable {

    @FXML
    Text txt_day;
    @FXML
    Text txt_day_num;
    @FXML
    Pane circle_day;

    MyListener ml;
    public static boolean checked= false;
    public void getdata(int s1 , String s2 ,String s3 ,MyListener ml){
        this.ml =ml;
        txt_day_num.setText(""+s1);
        txt_day.setText(s3);
        circle_day.setStyle("-fx-background-color :  #EDEDED ; -fx-background-radius : 50");
        txt_day_num.setStyle("-fx-text-alignment :   center ; -fx-fill : #000");
        circle_day.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ml.findrooms(s1);
                /*if(checked){
                    checked = false;
                    circle_day.setStyle("-fx-background-color :  #EDEDED ; -fx-background-radius : 50");
                    txt_day_num.setStyle("-fx-text-alignment :   center ; -fx-fill : #000");
                    txt_day.setStyle("-fx-text-alignment :   center ; -fx-fill : #b1b1b1");

                }else{
                    checked = true;
                    circle_day.setStyle("-fx-background-color :  #000 ; -fx-background-radius : 50");
                    txt_day_num.setStyle("-fx-text-alignment :   center ; -fx-fill : #fff");
                    txt_day.setStyle("-fx-text-alignment :   center ; -fx-fill : #000");

                }*/
            }
        });
    }
    public void clearall(){
        circle_day.setStyle("-fx-background-color :  #EDEDED ; -fx-background-radius : 50");
        txt_day_num.setStyle("-fx-text-alignment :   center ; -fx-fill : #000");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        circle_day.setOnMouseEntered(event -> circle_day.setCursor(javafx.scene.Cursor.HAND));
        circle_day.setOnMouseExited(event -> circle_day.setCursor(javafx.scene.Cursor.DEFAULT));

    }
}
