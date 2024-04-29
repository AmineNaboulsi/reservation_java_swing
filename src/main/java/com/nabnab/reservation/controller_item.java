package com.nabnab.reservation;

import com.mysql.cj.result.Row;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class controller_item implements Initializable {

    @FXML
    public Text txt;
    @FXML
    public Label txtnum;
    @FXML public Pane cercleiswifi;
    @FXML public Pane cercleisdatashow;
    @FXML public Text txt_nbtables;
    @FXML public Text txt_nbchaise;
    @FXML public Text txt_nom;
    @FXML public Pane detailspanel;
    @FXML Pane ppnel ;
    @FXML Pane menuicon ;



    public void getdata(Room r , int index){
        txt.setText(r.getRoomname());
        txt_nom.setText(r.getRoomname());
        txt_nbtables.setText(""+r.getNbtables());
        txt_nbchaise.setText(""+r.getNb_chairs());
        if(r.isWifi())cercleiswifi.setStyle("-fx-background-color :  #3FE231 ; -fx-background-radius :  50");
        else cercleiswifi.setStyle("-fx-background-color :  #B54747 ; -fx-background-radius :  50");

        if(r.isDatashow())cercleisdatashow.setStyle("-fx-background-color :  #3FE231 ; -fx-background-radius :  50");
        else cercleisdatashow.setStyle("-fx-background-color :  #B54747 ; -fx-background-radius :  50");
        txtnum.setText(""+index);
        System.out.println(r.getRoomname());
        detailspanel.setLayoutX(7);
        detailspanel.setLayoutY(8);
        ppnel.setPrefWidth(260);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuicon.setOnMouseEntered(event -> menuicon.setCursor(javafx.scene.Cursor.HAND));
        menuicon.setOnMouseExited(event -> menuicon.setCursor(javafx.scene.Cursor.DEFAULT));

    }

    @FXML
    private void menuclick(MouseEvent mouseEvent) {
        if(ppnel.getPrefWidth() == 260){
            animateLayoutChange(225 , 8 , 400);
            animateWidthChange(470 , 400);
        }
        else{
            animateWidthChange(260 , 400);
            animateLayoutChange(7 , 8 , 400);
        }
    }
    public void animateWidthChange(double newWidth , int speed)  {
        // Create a Timeline for the animation
        Timeline timeline = new Timeline();

        // Define a KeyFrame for the animation
        KeyValue keyValue = new KeyValue(ppnel.prefWidthProperty(), newWidth);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(speed), keyValue); // Animation duration: 500 milliseconds

        // Add the KeyFrame to the Timeline
        timeline.getKeyFrames().add(keyFrame);

        // Play the animation
        timeline.play();
    }

    public void animateLayoutChange(double newX, double newY , int speed) {
        // Create a Timeline for the animation
        Timeline timeline = new Timeline();

        // Define KeyFrames for X and Y changes
        KeyValue keyValueX = new KeyValue(detailspanel.layoutXProperty(), newX);
        KeyValue keyValueY = new KeyValue(detailspanel.layoutYProperty(), newY);

        // Define KeyFrames for the animations
        KeyFrame keyFrameX = new KeyFrame(Duration.millis(speed), keyValueX); // Animation duration: 500 milliseconds
        KeyFrame keyFrameY = new KeyFrame(Duration.millis(speed), keyValueY); // Animation duration: 500 milliseconds

        // Add KeyFrames to the Timeline
        timeline.getKeyFrames().addAll(keyFrameX, keyFrameY);

        // Play the animation
        timeline.play();
    }

}
