package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Room {
    private static Integer temperatureInRoom = 22;
    private static Air_conditioning air_conditioning;
    private static Remote_controller remote_controller;

    public static void main(String[] args) throws IOException {

        air_conditioning = new Air_conditioning();
        air_conditioning.start();
        remote_controller = new Remote_controller(air_conditioning);
        remote_controller.start();
    }

    static class Remote_controller extends Thread{
        private Air_conditioning air_conditioning;

        public Remote_controller(Air_conditioning air_conditioning) {
            this.air_conditioning = air_conditioning;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String com;
            String[] commands = new String[]{"turnOn", "turnOff",
                    "upDesiredTemperature", "downDesiredTemperature", "exit"};

            while (true){
                for (String str : commands) {
                    System.out.println(str);
                }
                com = scanner.nextLine();
                if (com.equals("turnOn")){
                    air_conditioning.turnOn();
                } else if (com.equals("turnOff")){
                    air_conditioning.turnOff();
                } else if (com.equals("upDesiredTemperature")){
                    air_conditioning.upDesiredTemperature();
                } else if (com.equals("downDesiredTemperature")){
                    air_conditioning.downDesiredTemperature();
                } else if (com.equals("exit")){
                    air_conditioning.close();
                    break;
                } else {
                    System.out.println("command not found");
                }

            }

        }
    }

    static class Air_conditioning extends Thread{
        private final int TEMPERATURE_MIN = 10;
        private final int TEMPERATURE_MAX = 30;
        private int temperatureFromSensor;
        private int temperatureDesired;
        private boolean works;
        private boolean close;

        public Air_conditioning() {
            this.temperatureFromSensor = temperatureInRoom; // FIXME: 07.11.2021
            this.temperatureDesired = temperatureFromSensor;
            this.works = false;
            this.close = false;
        }

        public void turnOn(){
            works = true;
        }

        public void turnOff(){
            works = false;
        }

        public void upDesiredTemperature(){
            if (temperatureDesired < TEMPERATURE_MAX)
                temperatureDesired++;
        }

        public void downDesiredTemperature(){
            if (temperatureDesired > TEMPERATURE_MIN)
                temperatureDesired--;
        }

        public void close(){
            close = true;
        }

        @Override
        public void run() {
            while (!close) {
                if (works) {
                    temperatureFromSensor = temperatureInRoom; // FIXME: 07.11.2021
                    System.out.println("temperature from sensor = " + temperatureFromSensor);
                    System.out.println("temperature desired = " + temperatureDesired);
                    if (temperatureFromSensor < temperatureDesired) {
                        temperatureInRoom++;// FIXME: 07.11.2021
                    } else if (temperatureFromSensor > temperatureDesired) {
                        temperatureInRoom--;// FIXME: 07.11.2021
                    }
                }

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}