package com.nabnab.reservation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ReservetionItem implements Initializable {

    @FXML
    Text txt_time ;

    public void getdata(String time){
        txt_time.setText(time);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
