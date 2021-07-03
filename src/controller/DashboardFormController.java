package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.DashboardTM;
import model.Receipt;
import service.ReceiptServiceRedisImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardFormController {
    private final ReceiptServiceRedisImpl receiptService = new ReceiptServiceRedisImpl();
    public TableView<DashboardTM> tblResult;
    public ImageView imgHoursHand;
    public ImageView imgSecondsHand;
    public ImageView imgMinutesHand;
    public Label lblTotalIncome;
    public Label lblTotalPayments;
    public Label lblTotalRegistrations;

    public void initialize() {
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

        lblTotalIncome.setText(String.format("%,.0f", income));
        lblTotalPayments.setText(numberOfPayments + "");
        lblTotalRegistrations.setText(numberOfRegistrations + "");
    }
}
