package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Date;

public class DashboardFormController {
    public TableView tblResults;
    public ImageView imgHoursHand;
    public ImageView imgSecondsHand;
    public ImageView imgMinutesHand;
    public Label lblTime;

    public void initialize(){
        startTimer();
        tblResults.getItems().add(new String("sdfsd"));
        tblResults.getItems().add(new String("sdfsd"));
        tblResults.getItems().add(new String("sdfsd"));
    }


    private void startTimer() {
        String time1 = String.format("%tT", new Date());
        lblTime.setText(time1);
        String[] arr1 = time1.split(":");

        //Set initial positions to start the clock
        double s = 6 * Double.parseDouble(arr1[2]);
        RotateTransition rs = new RotateTransition(Duration.millis(100), imgSecondsHand);
        rs.setFromAngle(s - 6);
        rs.setToAngle(s);
        rs.play();

        double m = 6 * Double.parseDouble(arr1[1]);
        RotateTransition rm = new RotateTransition(Duration.millis(10), imgMinutesHand);
        rm.setToAngle(m);
        rm.play();

        double h = 30 * (Double.parseDouble(arr1[0]) + Double.parseDouble(arr1[1]) / 60);
        RotateTransition rh = new RotateTransition(Duration.millis(100), imgHoursHand);
        rh.setFromAngle(h - 6);
        rh.setToAngle(h);
        rh.play();

        // Thread to run seconds hand
        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            String time = String.format("%tT", new Date());
            lblTime.setText(time);
            String[] arr = time.split(":");
            double secondsAngle = 6 * Double.parseDouble(arr[2]);
            RotateTransition rotateSecondHand = new RotateTransition(Duration.millis(100), imgSecondsHand);
            rotateSecondHand.setFromAngle(secondsAngle - 6);
            rotateSecondHand.setToAngle(secondsAngle);
            rotateSecondHand.play();
        }));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();

        // Thread to run minute and hour hand
        Thread t3 = new Thread(() -> {
            while (true) {
                String time = String.format("%tT", new Date());
                String[] arr = time.split(":");
                int sleepingTime = 61 - Integer.parseInt(arr[2]);
                try {
                    Thread.sleep(sleepingTime * 1000L); // TODO: Stop this thread when terminate the program
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    lblTime.setText(time);
                    double minutesAngle = 6 * Double.parseDouble(arr[1]) + 6;
                    RotateTransition rotateMinuteHand = new RotateTransition(Duration.millis(100), imgMinutesHand);
                    rotateMinuteHand.setFromAngle(minutesAngle - 6);
                    rotateMinuteHand.setToAngle(minutesAngle);
                    rotateMinuteHand.play();

                    double hourAngle = (30 * Double.parseDouble(arr[0]) + (Double.parseDouble(arr[1]) + 1)*0.5);
                    RotateTransition rotateHourHand = new RotateTransition(Duration.millis(100), imgHoursHand);
                    rotateHourHand.setFromAngle(hourAngle - 0.5);
                    rotateHourHand.setToAngle(hourAngle);
                    rotateHourHand.play();
                });
            }
        }, "Thread 1");
        t3.start();
    }
}
