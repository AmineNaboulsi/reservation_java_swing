package com.nabnab.reservation;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


public class Rooms implements Initializable {

    @FXML
    TextField txt_roomname;
    @FXML
    TextField txt_nbchaires;
    @FXML
    TextField txt_nbtables;

    @FXML
    CheckBox chebox_wifi;
    @FXML
    CheckBox chebox_datatshow;
    @FXML
    TextField txt_roomid;
    @FXML
    Button btn_delete;

    @FXML Label status;
    @FXML TableView<Room> gridrooms;
    @FXML private TableColumn<Room, String> roomname ;
    @FXML private TableColumn<Room, Integer> nbtables;
    @FXML private TableColumn<Room, Integer> nb_chairs ;
    @FXML private TableColumn<Room, Boolean> wifi ;
    @FXML private TableColumn<Room, Boolean> datashow;

    @FXML private Button btn_update;
    ArrayList<Room> li =new ArrayList<>();
    ConnectionDB connectionDB = new ConnectionDB();

    public static Room selected ;
    private List<Room> parseRoomList(){
      if(connectionDB.getRooms() != null){
          li = connectionDB.getRooms();
          return connectionDB.getRooms();
        }else return new ArrayList<>();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionDB =new ConnectionDB();
        Boolean test = connectionDB.CreateConnection();
        try {
            // Set cell value factories using Callback
            roomname.setCellValueFactory(new PropertyValueFactory<>("roomname"));
            nbtables.setCellValueFactory(new PropertyValueFactory<>("nbtables"));
            nb_chairs.setCellValueFactory(new PropertyValueFactory<>("nb_chairs"));
            wifi.setCellValueFactory(new PropertyValueFactory<>("wifi"));
            datashow.setCellValueFactory(new PropertyValueFactory<>("datashow"));

            gridrooms.getItems().setAll(parseRoomList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        btn_delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (gridrooms.getSelectionModel().isEmpty()) {
                    status.setVisible(true);
                    status.setText("Select first");
                    status.setStyle("-fx-text-fill : #C81D3D");
                }
                else {
                    boolean transition = connectionDB.RemoveRooms(Integer.parseInt(txt_roomid.getText()));
                    if(transition){
                        status.setVisible(true);
                        status.setText("(1) Row removed");
                        status.setStyle("-fx-text-fill : #C81D3D");
                        setStatus();
                        gridrooms.getItems().setAll(parseRoomList());
                        reset();
                    }else{
                        status.setVisible(false);
                        status.setText("Delete problem");
                        status.setStyle("-fx-text-fill : #C81D3D");
                    }
                }
            }
        });
        gridrooms.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Room selectedRoom = newSelection;
                selected = selectedRoom;
                txt_roomname.setText(selectedRoom.getRoomname());
                txt_nbchaires.setText(""+selectedRoom.getNb_chairs());
                txt_nbtables.setText(""+selectedRoom.getNbtables());
                txt_roomid.setText(""+selectedRoom.getId());
                if(selectedRoom.isDatashow())
                    chebox_datatshow.setSelected(true);
                else chebox_datatshow.setSelected(false);

                if(selectedRoom.isWifi())
                    chebox_wifi.setSelected(true);
                else chebox_wifi.setSelected(false);

                btn_update.setText("Update");
                btn_update.setStyle("-fx-background-color :  #2F9AD1 ;-fx-text-fill : #fff");
            }
        });
        btn_update.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(btn_update.getText().equalsIgnoreCase("Update")){
                    if(txt_nbtables.getText().isEmpty()||
                            txt_nbtables.getText().isEmpty()||
                            txt_nbchaires.getText().isEmpty()||
                            txt_roomname.getText().isEmpty()||
                            txt_roomid.getText().isEmpty()){
                        status.setVisible(true);
                        status.setText("Filed Requeired");
                        status.setStyle("-fx-text-fill : #C81D3D");
                        setStatus();
                        return;
                    }
                    Room r = new Room();
                    r.setId(Integer.parseInt(txt_roomid.getText()));
                    r.setRoomname(txt_roomname.getText());
                    r.setNb_chairs(Integer.parseInt(txt_nbchaires.getText()));
                    r.setNbtables(Integer.parseInt(txt_nbtables.getText()));
                    r.setWifi(chebox_wifi.isSelected());
                    r.setDatashow(chebox_datatshow.isSelected());
                    Update(r);
                    reset();
                    refrech_click();

                }else{
                    if(txt_nbtables.getText().isEmpty()||
                            txt_nbchaires.getText().isEmpty()||
                            txt_roomname.getText().isEmpty()){
                        status.setVisible(true);
                        status.setText("Filed Requeired");
                        status.setStyle("-fx-text-fill : #C81D3D");
                        setStatus();
                        return;
                    }
                    Room r = new Room();
                    r.setRoomname(txt_roomname.getText());
                    r.setNb_chairs(Integer.parseInt(txt_nbchaires.getText()));
                    r.setNbtables(Integer.parseInt(txt_nbtables.getText()));
                    r.setWifi(chebox_wifi.isSelected());
                    r.setDatashow(chebox_datatshow.isSelected());
                    Add(r);
                }


            }
        });

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };


        TextFormatter<Integer> textFormatter = new TextFormatter<>(filter);
        txt_roomid.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(filter);
        txt_nbchaires.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(filter);
        txt_nbtables.setTextFormatter(textFormatter);
    }
    public void reset(){
        txt_roomid.setText("");
        txt_roomname.setText("");
        txt_nbtables.setText("");
        txt_nbchaires.setText("");
        chebox_wifi.setSelected(false);
        chebox_datatshow.setSelected(false);
        gridrooms.getSelectionModel().clearSelection();
        gridrooms.getItems().setAll(parseRoomList());

    }
    @FXML
    public void refrech_click(){
        try {
            btn_update.setText("Add");
            btn_update.setStyle("-fx-background-color : #000 ;-fx-text-fill : #fff");
            reset();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
    public void Update (Room R){
        boolean transation_update = connectionDB.UpdateRooms(R);
        status.setVisible(true);
        if(transation_update){
            System.out.println("Done");
            status.setText("(1) Row Updated");
            status.setStyle("-fx-text-fill : #188870");
            gridrooms.getItems().setAll(parseRoomList());
            reset();
        }else{
            status.setText("Update problem");
            status.setStyle("-fx-text-fill : #C81D3D");
        }
        setStatus();

    }
    public void Add (Room R){
        boolean transation_update = connectionDB.AddRooms(R);
        status.setVisible(true);
        if(transation_update){
            System.out.println("Done");
            status.setText("(1) Row Added");
            status.setStyle("-fx-text-fill : #188870");
            gridrooms.getItems().setAll(parseRoomList());
            reset();
        }else{
            status.setText("Add problem");
            status.setStyle("-fx-text-fill : #C81D3D");
        }
        setStatus();

    }
    public void setStatus(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    status.setVisible(false);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }}).start();
    }
    public boolean findbyname(String s){
        for (int i = 0; i < li.size(); i++) {
            if(li.get(i).getRoomname().trim().toLowerCase().equalsIgnoreCase(s.trim().toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
