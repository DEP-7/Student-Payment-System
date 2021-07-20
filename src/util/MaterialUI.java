/*
 * Copyright (c) Dhanushka Chandimal. All rights reserved.
 * Licensed under the MIT License. See License in the project root for license information.
 */

package util;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MaterialUI {
    public static void paintTextFields(Control... textFields) {
        for (Control txt : textFields) {
            AnchorPane pneTextContainer = (AnchorPane) txt.getParent();
            String floatedText = txt.getAccessibleText();
            Canvas canvas = new Canvas();
            GraphicsContext ctx = canvas.getGraphicsContext2D();

            pneTextContainer.getChildren().add(0, canvas);

            pneTextContainer.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                canvas.setWidth(newValue.getWidth());
                canvas.setHeight(newValue.getHeight());
                redrawTextFieldCanvas(canvas, ctx, floatedText, false);
            });

            txt.focusedProperty().addListener((observable, oldValue, newValue) -> {
                redrawTextFieldCanvas(canvas, ctx, floatedText, newValue);
            });

            pneTextContainer.setOnMouseEntered(event -> {
                pneTextContainer.setCursor(Cursor.TEXT);
            });

            pneTextContainer.setOnMouseClicked(event -> {
                txt.requestFocus();
            });
        }
    }

    private static void redrawTextFieldCanvas(Canvas canvas, GraphicsContext ctx, String floatedText, boolean focus) {
        ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ctx.setStroke(focus ? Color.valueOf("#C173FF") : Color.WHITE);
        ctx.strokeRoundRect(1, 10, canvas.getWidth() - 2, canvas.getHeight() - 11, 10, 10);
        ctx.setFill(Color.valueOf("#404040"));
        ctx.fillRect(12, 0, new Text(floatedText).getLayoutBounds().getWidth() + 8, 20);
        ctx.setFill(focus ? Color.valueOf("#C173FF") : Color.WHITE);
        ctx.fillText(floatedText, 16, 15);
    }
}
