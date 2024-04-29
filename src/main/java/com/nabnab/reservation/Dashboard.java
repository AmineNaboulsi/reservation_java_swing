package com.nabnab.reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class Dashboard implements Initializable , MyListener{
    @FXML
    TableView<Room> table_rooms ;
    @FXML
    ScrollPane recyclerooms ;
    @FXML
    ComboBox comboBox_mois ;

    @FXML
    ScrollPane panel_mois ;

    ConnectionDB connectionDB ;

    @FXML private TableColumn<Room, String> roomname ;
    @FXML private TableColumn<Room, Integer> nbtables;
    @FXML private TableColumn<Room, Integer> nb_chairs ;
    @FXML private TableColumn<Room, Boolean> wifi ;
    @FXML private TableColumn<Room, Boolean> datashow;

    ArrayList<Room> li =new ArrayList<>();
    List<String> months;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionDB =new ConnectionDB();
        Boolean test = connectionDB.CreateConnection();
        //parseRoomList();



        months = Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        List<String> time_day_li = Arrays.asList(
                "8 AM", "9 AM", "10 AM", "11 AM", "12 AM", "2 PM",
                "3 PM", "4 PM", "5 PM", "6 PM"
        );
        VBox vb = new VBox();
        for (int i = 0; i < time_day_li.size(); i++) {
            try {
                FXMLLoader fxmload = new FXMLLoader(getClass().getResource("reservetion_item.fxml"));
                Pane item = null;
                item = fxmload.load();
                ReservetionItem item1 = fxmload.getController();
                item1.getdata(time_day_li.get(i));
                vb.getChildren().add(item);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        //time_day.setContent(vb);


        comboBox_mois.getItems().addAll(months);
        comboBox_mois.getSelectionModel().selectFirst();
        Label selectedDateLabel = new Label();

        // Listen for selection changes in the ComboBox
        comboBox_mois.setOnAction(event -> {
            getmoisdetails();
        });
        getmoisdetails();
        LocalDate currentDate = LocalDate.now();

        // Get the month number from the current date
        int monthNumber = currentDate.getMonthValue();
        comboBox_mois.getSelectionModel().select(monthNumber-1);
    }
    public void getmoisdetails(){
        String selectedMonthName = String.valueOf(comboBox_mois.getValue());

        // Get the corresponding English month name
        String selectedMonthUpperCase = selectedMonthName.toUpperCase(Locale.ENGLISH);

        // Get the number of days in the selected month
        Month month = Month.valueOf(selectedMonthUpperCase);
        int year = LocalDate.now().getYear(); // For simplicity, using the current year
        int daysInMonth = month.length(LocalDate.of(year, month, 1).isLeapYear());

        StringBuilder resultBuilder = new StringBuilder();
        HBox hb = new HBox();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String englishDayOfWeek = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("day_format.fxml"));
                AnchorPane item = fxmlLoader.load();
                DayFormat c =fxmlLoader.getController();
                c.getdata(day, selectedMonthName, englishDayOfWeek,this);
                hb.getChildren().add(item);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        panel_mois.setFocusTraversable(false);
        panel_mois.setContent(hb);

        parseRoomList();
    }
    private List<Room> parseRoomList(){
        if(connectionDB.getRooms() != null){
            li = connectionDB.getRoomsBYDate(LocalDate.now());
            VBox hbx = new VBox();
            try {
                for (int i = 0; i < li.size(); i++) {
                    FXMLLoader fxmload = new FXMLLoader(getClass().getResource("item_room.fxml"));
                    Pane item = fxmload.load();
                    controller_item c = fxmload.getController();
                    c.getdata(li.get(i) , i+1);
                    hbx.getChildren().add(item);
                }
                recyclerooms.setContent(hbx);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }else return new ArrayList<>();
    }
    private List<Room> parseRoomList(LocalDate date){
        if(connectionDB.getRooms() != null){
            li = connectionDB.getRoomsBYDate(date);

            VBox hbx = new VBox();
            try {
                if(li == null || li.size() == 0){
                    Image image = new Image(getClass().getResource("emptylist.png").toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(false); // Optionally, preserve ratio can be set to true if you want to maintain the aspect ratio
                    imageView.setFitWidth(374);        // Set the width of the ImageView
                    imageView.setFitHeight(280);  // Bind the ImageView's height to the VBox's height
                    hbx.setPadding(new Insets(20));
                    hbx.getChildren().add(imageView);
                    Label l = new Label("List de reservation vide.");
                    hbx.getChildren().add(l);
                    recyclerooms.setContent(hbx);
                }else{
                    for (int i = 0; i < li.size(); i++) {
                        FXMLLoader fxmload = new FXMLLoader(getClass().getResource("item_room.fxml"));
                        Pane item = fxmload.load();
                        controller_item c = fxmload.getController();
                        c.getdata(li.get(i) , i+1);
                        hbx.getChildren().add(item);
                    }
                    recyclerooms.setContent(hbx);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }else return new ArrayList<>();
    }

    @Override
    public void findrooms(int s1) {
        System.out.println(s1);
        int months_number = 1;
        for (int i = 0; i < months.size(); i++) {
            //System.out.println(months.get(i) +" "+comboBox_mois.getSelectionModel().getSelectedItem().toString());
            if(months.get(i).equalsIgnoreCase(comboBox_mois.getSelectionModel().getSelectedItem().toString())){
                months_number = i+1;
            }
        }
        String dateStr = String.format("2024-%02d-%02d", months_number, s1);
        LocalDate date = LocalDate.parse(dateStr);
        parseRoomList(date);
        System.out.println(dateStr);
    }
}
