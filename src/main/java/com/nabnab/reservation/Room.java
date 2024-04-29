package com.nabnab.reservation;

public class Room {
    private int id;
    private String roomname;
    private int nbtables;
    private int nb_chairs;
    private boolean wifi;
    private boolean datashow;

    public Room(){}
    public Room(int id, String roomname, int nbtables, int nb_chairs, boolean wifi, boolean datashow) {
        this.id = id;
        this.roomname = roomname;
        this.nbtables = nbtables;
        this.nb_chairs = nb_chairs;
        this.wifi = wifi;
        this.datashow = datashow;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomname='" + roomname + '\'' +
                ", nbtables=" + nbtables +
                ", nb_chairs=" + nb_chairs +
                ", wifi=" + wifi +
                ", datashow=" + datashow +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getRoomname() {
        return roomname;
    }

    public int getNbtables() {
        return nbtables;
    }

    public int getNb_chairs() {
        return nb_chairs;
    }

    public boolean isWifi() {
        return wifi;
    }

    public boolean isDatashow() {
        return datashow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public void setNbtables(int nbtables) {
        this.nbtables = nbtables;
    }

    public void setNb_chairs(int nb_chairs) {
        this.nb_chairs = nb_chairs;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public void setDatashow(boolean datashow) {
        this.datashow = datashow;
    }
}
