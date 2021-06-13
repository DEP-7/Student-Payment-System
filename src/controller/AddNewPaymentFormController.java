package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.MaterialUI;

public class AddNewPaymentFormController {
    public TextField txtNIC;

    public void initialize() {
        MaterialUI.paintTextFields(txtNIC);
    }

}
