package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Cabin[] cabinList = new Cabin[12];
        initialiseCabinList(cabinList);
        while (true){
            cruiseMenu(cabinList);
        }

    }

    public static void initialiseCabinList(Cabin[] cabinList) {
        for(int i = 0; i < 12; i++){
            cabinList[i] = new Cabin();
            cabinList[i].initialisePassengerList();
        }
    }

    public static void cruiseMenu(Cabin[] cabinList){
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----Welcome to Cruise Ship passenger boarding menu----");

        System.out.println(" A : Add customer to cabin ");
        System.out.println(" V : View all cabins ");
        System.out.println(" E : Display Empty cabins ");
        System.out.println(" D : Delete customer from cabin ");
        System.out.println(" F : Find cabin from customer name ");
        System.out.println(" T : Display passenger expenses ");
        System.out.println(" S : Store program data into file ");
        System.out.println(" L : Load program data from file ");
        System.out.println(" O : View passengers ordered alphabetically by name ");
        System.out.println("-------------------------------------------------------");

        String input = scanner.next().toLowerCase(Locale.ROOT);

        switch (input){
            case "a": addCustomer(cabinList);
                break;
            case "v" : viewCabins(cabinList);
                break;
            case "e" : emptyCabins(cabinList);
                break;
            case "d" : deleteCustomer(cabinList);
                break;
            case "f" : findCustomerCabin(cabinList);
                break;
            case "t" : displayPassengersExpenses(cabinList);
                break;
            case "s" : storeData(cabinList);
                break;
            case "l" : loadData();
                break;
            case "o" : alphabeticalPassengerOrder(cabinList);
                break;
            default:
                System.out.println("Invalid input!!");
        }
    }

    private static boolean isShipFull(Cabin[] cabinArray) {
        for(Cabin cabin : cabinArray) {
            if(!cabin.isFullBoard()) {
                return false;
            }
        }
        return true;
    }

    private static void addCustomer(Cabin[] cabinArray) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            try{
                System.out.println("Enter the cabin No :");
                int cabinNo = scanner.nextInt();
                if (cabinNo == 12){
                        break;
                }
                if (0 <= cabinNo && cabinNo < 12 && !cabinArray[cabinNo].isFullBoard()){
                    int passengerNo = 1;
                    while (true) {
                        boolean endLoop = false;
                        System.out.println("Adding passenger " + passengerNo);
                        System.out.println("Enter first name : ");
                        String firstName = scanner.next();
                        System.out.println("Enter surname : ");
                        String surname = scanner.next();
                        System.out.println("Enter expenses : ");
                        double expenses = scanner.nextDouble();
                        for (int i = 0; i < 3; i++ ){
                            if(cabinArray[cabinNo].getPassengerList()[i].getFirstName().equals("e")) {
                                cabinArray[cabinNo].getPassengerList()[i].setFirstName(firstName);
                                cabinArray[cabinNo].getPassengerList()[i].setSurname(surname);
                                cabinArray[cabinNo].getPassengerList()[i].setExpenses(expenses);
                                passengerNo++;
                                System.out.println("Do you want to add another passenger into this same cabin " + cabinNo + "?");
                                System.out.println("Press 'Y' to add another passenger and press any other key to exit to the previous menu.");
                                scanner.nextLine();
                                String userInput = scanner.nextLine().toLowerCase(Locale.ROOT);
                                if(!userInput.equals("y")) {
                                    endLoop = true;
                                }
                                break;
                            }
                        }
                        if(endLoop) {
                            System.out.println("Leaving cabin " + cabinNo + "...");
                            break;
                        }
                    }
                } else if(cabinArray[cabinNo].isFullBoard()) {
                    System.out.println("Try another cabin.");
                } else {
                    System.out.println("Enter a number between 0 - 12");
                }
            } catch (InputMismatchException x) {
                System.out.println("Enter a number between 0 - 12");
                break;
            }
        }
    }

    public static void viewCabins(Cabin[] cabins){
        for(int i = 0; i < 12; i++){
            if(!cabins[i].isEmpty()){
                System.out.println("\nCabin " + i + " : \n");
                for (int j = 0; j < 3; j++){
                    Passenger passenger = cabins[i].getPassengerList()[j];
                    if(!passenger.getFirstName().equals("e")){
                        System.out.println("First Name = " + passenger.getFirstName());
                        System.out.println("Surname    = " + passenger.getSurname());
                        System.out.println("Expenses   = " + passenger.getExpenses() + "\n");
                    }
                }
            }else{
                System.out.println("Cabin no :" + i + " is empty");
            }
        }
    }

    private static void emptyCabins(Cabin[] cabins) {
        for(int i = 0; i < 12; i++) {
            if(!cabins[i].isFullBoard()) {
                for(int j = 0; j < 3; j++) {
                    if(cabins[i].getPassengerList()[j].getFirstName().equals("e")) {
                        System.out.println("Cabin " + i + " has empty seats.");
                        break;
                    }
                }
            }
        }
    }

    private static void deleteCustomer(Cabin[] cabins) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter passenger's name");
        String input = scanner.nextLine();
        for(int i = 0; i < 12; i++) {
            for (int j = 0; j < 3; j++) {
                if(cabins[i].getPassengerList()[j].getFirstName().toLowerCase(Locale.ROOT).equals(input.toLowerCase(Locale.ROOT))) {
                    cabins[i].getPassengerList()[j].setFirstName("e");
                    cabins[i].getPassengerList()[j].setSurname("e");
                    cabins[i].getPassengerList()[j].setExpenses(0);
                    System.out.println("Passenger deleted");
                    break;
                }
            }
        }
    }

    private static void findCustomerCabin(Cabin[] cabins) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter passenger's name");
        String input = scanner.nextLine();
        boolean isPassengerAvailable = false;
        for(int i = 0; i < 12; i++) {
            for (int j = 0; j < 3; j++) {
                if(cabins[i].getPassengerList()[j].getFirstName().toLowerCase(Locale.ROOT).equals(input.toLowerCase(Locale.ROOT))) {
                    System.out.println("Passenger found. Cabin number is " + i);
                    isPassengerAvailable = true;
                    break;
                }
            }
        }
        if(!isPassengerAvailable) {
            System.out.println("Invalid passenger name");
        }
    }

    private static void displayPassengersExpenses(Cabin[] cabins) {
        System.out.println("-------------------------------");
        double total = 0;
        for(int i = 0; i < 12; i++) {
            for(int j = 0; j < 3; j++) {
                if(!(cabins[i].getPassengerList()[j].getExpenses() == 0)) {
                    System.out.println(cabins[i].getPassengerList()[j].getFirstName() + "   -   " + cabins[i].getPassengerList()[j].getExpenses());
                    total += cabins[i].getPassengerList()[j].getExpenses();
                }
            }
        }
        System.out.println("----------------------------------------------------");
        System.out.println("*  Total   =   " + total);
        System.out.println("----------------------------------------------------");
    }

    private static void storeData(Cabin[] cabins) {
        try {
            FileWriter myWriter = new FileWriter("Data.txt");
            for (int i = 0; i < 12; i++) {
                myWriter.write("Cabin : " + i);
                myWriter.write(System.lineSeparator());
                for (int j = 0; j < cabins[j].passengerList.length; j++) {
                    myWriter.write("Passenger " + j + " : ");
                    myWriter.write("{FirstName : " + cabins[i].passengerList[j].getFirstName());
                    myWriter.write(", Surname : " + cabins[i].passengerList[j].getSurname());
                    myWriter.write(", Expenses : " + cabins[i].passengerList[j].getExpenses() + "}");
                    myWriter.write(System.lineSeparator());
                }
            }
            myWriter.close();
            System.out.println("Ship data saved!");
        } catch (IOException x ) {
            System.out.println("Error! " + x );
        }
        System.out.println("-------------------------------");
    }


    private static void loadData() {
        try {
            File file = new File("Data.txt");
            Scanner readFile = new Scanner(file);
            while (readFile.hasNext()){
                System.out.println(readFile.nextLine());
            }
            System.out.println("----------------------------------------------");
        } catch (IOException x) {
            System.out.println("Error! " + x);
        }
    }

    private static void alphabeticalPassengerOrder(Cabin[] cabins) {
        int totalCabinPassengers = cabins.length * 3;
        Passenger[] passengerArray = new Passenger[totalCabinPassengers];
        int index = 0;
        for (Cabin cabin : cabins) {
            for (int j = 0; j < 3; j++) {
                if (!cabin.getPassengerList()[j].getFirstName().equals("e")) {
                    passengerArray[index] = cabin.getPassengerList()[j];
                    index++;
                }
            }
        }
        Passenger tempPassenger = null;
        for (int i = 0; i < passengerArray.length; i++) {
            for (int j = i + 1 ; j < passengerArray.length; j++) {
                if(passengerArray[i] != null && passengerArray[j] != null) {
                    if (passengerArray[i].getFirstName().compareTo(passengerArray[j].getFirstName()) > 0){
                        tempPassenger = passengerArray[i];
                        passengerArray[i] = passengerArray[j];
                        passengerArray[j] = tempPassenger;
                    }
                }
            }
        }
        System.out.println("Alphabetic order : ");
        for (Passenger s : passengerArray) {
            if (s != null && !s.getFirstName().equals("e")) {
                System.out.println(s.getFirstName() + " ");
            }
        }
    }
}

