package com.company;

public class Cabin {
    Passenger[] passengerList = new Passenger[3];


    public Passenger[] getPassengerList() {
        return passengerList;
    }

    public void initialisePassengerList() {
        for (int i = 0; i < passengerList.length; i++) {
            passengerList[i] = new Passenger("e", "e", 0);
        }
    }

//    public boolean isFullBoard() {
//        for (int i = 0; i < 3; i++) {
//            if (passengerList[i].firstName.equals("e")) {
//                return false;
//            }
//        }
//        return true;
//    }

    public boolean isFullBoard() {
        for (int i = 0; i < 3; i++) {
            if (!passengerList[i].firstName.equals("e")) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        for (Passenger passenger : passengerList) {
            if(!passenger.firstName.equals("e")) {
                return false;
            }
        }
        return true;
    }
}
