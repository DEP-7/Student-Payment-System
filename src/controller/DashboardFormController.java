package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.DashboardTM;
import model.Receipt;
import service.ReceiptService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DashboardFormController {
    private final ReceiptService receiptService = new ReceiptService();
    public TableView<DashboardTM> tblResult;
    public ImageView imgHoursHand;
    public ImageView imgSecondsHand;
    public ImageView imgMinutesHand;
    public Label lblTotalIncome;
    public Label lblTotalPayments;
    public Label lblTotalRegistrations;

    public void initialize() {
        //startTimer();

        tblResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("courseId"));
        tblResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("newRegistrations"));
        tblResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("numberOfPayments"));
        tblResult.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("totalIncome"));

        loadAllDetails();
    }

    private void loadAllDetails() {
        tblResult.getItems().clear();

        ArrayList<String> courseID = new ArrayList<>();
        ArrayList<Integer> newRegistrations = new ArrayList<>();
        ArrayList<Integer> noOfPayments = new ArrayList<>();
        ArrayList<BigDecimal> totalIncome = new ArrayList<>();

        for (Receipt receipt : receiptService.searchReceiptsByKeyword("", LocalDate.now(), LocalDate.now().plusDays(1))) {
            if (courseID.contains(receipt.getStudent().getCourse().getCourseID())) {
                int index = courseID.indexOf(receipt.getStudent().getCourse().getCourseID());
                if (receipt.getPaymentDescription().equals("Registration Fee")) {
                    newRegistrations.set(index, newRegistrations.get(index) + 1);
                }
                noOfPayments.set(index, noOfPayments.get(index) + 1);
                totalIncome.set(index, totalIncome.get(index).add(receipt.getAmountReceived()));
            } else {
                courseID.add(receipt.getStudent().getCourse().getCourseID());
                if (receipt.getPaymentDescription().equals("Registration Fee")) {
                    newRegistrations.add(1);
                } else {
                    newRegistrations.add(1);
                }
                noOfPayments.add(1);
                totalIncome.add(receipt.getAmountReceived());
            }
        }

        int numberOfRegistrations = 0;
        int numberOfPayments = 0;
        BigDecimal income = new BigDecimal(0);

        for (int i = 0; i < courseID.size(); i++) {
            numberOfRegistrations += newRegistrations.get(i);
            numberOfPayments += noOfPayments.get(i);
            income = income.add(totalIncome.get(i));
            tblResult.getItems().add(new DashboardTM(courseID.get(i), newRegistrations.get(i), noOfPayments.get(i), totalIncome.get(i)));
        }

        lblTotalIncome.setText(String.format("%,.0f",income));
        lblTotalPayments.setText(numberOfPayments + "");
        lblTotalRegistrations.setText(numberOfRegistrations + "");
    }


    private void startTimer() {
        String time1 = String.format("%tT", new Date());
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
                    double minutesAngle = 6 * Double.parseDouble(arr[1]) + 6;
                    RotateTransition rotateMinuteHand = new RotateTransition(Duration.millis(100), imgMinutesHand);
                    rotateMinuteHand.setFromAngle(minutesAngle - 6);
                    rotateMinuteHand.setToAngle(minutesAngle);
                    rotateMinuteHand.play();

                    double hourAngle = (30 * Double.parseDouble(arr[0]) + (Double.parseDouble(arr[1]) + 1) * 0.5);
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
