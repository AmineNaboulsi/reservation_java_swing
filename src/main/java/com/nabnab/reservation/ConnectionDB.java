package com.nabnab.reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConnectionDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public Connection connection;


    public ConnectionDB() {
    }

    public Boolean CreateConnection() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public Boolean Authentification(User user) {
        /*
        try {
            PreparedStatement ps = connection.prepareStatement("select count(username) from user where username = ? and password = ? ");
            ps.setString(1 , user.getUserName().trim());
            ps.setString(2 , user.getPassword().trim());

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                int row = resultSet.getInt(1);
                if(row == 1)
                    return true;

            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

         */
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call AuthenticateUser(?, ?, ?)}");
            cs.setString(1, user.getUserName());
            cs.setString(2, user.getPassword());
            cs.registerOutParameter(3, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(3);
        } catch (SQLException e) {
            return false;
        }
    }
    public void CloseConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Room> getRooms() {
        ArrayList<Room> li_rooms = new ArrayList<>();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call get_room}");
            ResultSet rs =  cs.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomname(rs.getString("room_name"));
                room.setNbtables(rs.getInt("table_nb"));
                room.setNb_chairs(rs.getInt("chair"));
                room.setWifi(rs.getBoolean("wifi"));
                room.setDatashow(rs.getBoolean("datashow"));
                li_rooms.add(room);

            }
            return li_rooms;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Room> getRoomsBYDate(LocalDate d) {
        ArrayList<Room> li_rooms = new ArrayList<>();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call get_roomByDate(?)}");
            cs.setDate(1,java.sql.Date.valueOf(d));
            ResultSet rs =  cs.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setRoomname(rs.getString("room_name"));
                room.setNbtables(rs.getInt("table_nb"));
                room.setNb_chairs(rs.getInt("chair"));
                room.setWifi(rs.getBoolean("wifi"));
                room.setDatashow(rs.getBoolean("datashow"));
                li_rooms.add(room);

            }
            return li_rooms;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public boolean UpdateRooms(Room R) {
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call update_room(?,?,?,?,?,?,?)}");
            cs.setInt(1, R.getId());
            cs.setString(2, R.getRoomname());
            cs.setInt(3, R.getNbtables());
            cs.setInt(4, R.getNb_chairs());
            cs.setBoolean(5, R.isDatashow());
            cs.setBoolean(6, R.isWifi());
            cs.registerOutParameter(7, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(7);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean AddRooms(Room R) {
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call add_room(?,?,?,?,?,?)}");

            cs.setString(1, R.getRoomname());
            cs.setInt(2, R.getNbtables());
            cs.setInt(3, R.getNb_chairs());
            cs.setBoolean(4, R.isDatashow());
            cs.setBoolean(5, R.isWifi());
            cs.registerOutParameter(6, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(6);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean RemoveRooms(int id) {
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall("{call remove_room(?,?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(2);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public Integer getIdRoom (String Name){

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM `room` WHERE room_name LIKE ?");
            ps.setString(1 , Name);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                int row = resultSet.getInt(1);
            return row;

            }
            return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }


    }
   public boolean addReservation(String roomName, LocalDate date_from) {
       Integer i = getIdRoom(roomName);
       if (i != -1) {
           CallableStatement cs = null;
           try {
               cs = connection.prepareCall("{call add_reservation(?, ?, ?)}");
               cs.setInt(1, i);
               cs.setDate(2, java.sql.Date.valueOf(date_from));
               cs.registerOutParameter(3, Types.BOOLEAN);
               cs.execute();
               return cs.getBoolean(3);

           } catch (SQLException e) {
               System.out.println(e.getMessage());
               return false;
           } finally {
               if (cs != null) {
                   try {
                       cs.close();
                   } catch (SQLException e) {
                       System.out.println(e.getMessage());
                   }
               }
           }
       }else{
           return false;
       }
   }

}

