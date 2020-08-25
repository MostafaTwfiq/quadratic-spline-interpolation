package VisualizerGUI.PointsView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.util.regex.Pattern;

public class PointElement extends HBox {

    final String Digits     = "(\\p{Digit}+)";
    final String HexDigits  = "(\\p{XDigit}+)";
    // an exponent is 'e' or 'E' followed by an optionally
    // signed decimal integer.
    final String Exp        = "[eE][+-]?"+Digits;
    final String fpRegex    =
            ("[\\x00-\\x20]*"+ // Optional leading "whitespace"
                    "[+-]?(" +         // Optional sign character
                    "NaN|" +           // "NaN" string
                    "Infinity|" +      // "Infinity" string

                    // A decimal floating-point string representing a finite positive
                    // number without a leading sign has at most five basic pieces:
                    // Digits . Digits ExponentPart FloatTypeSuffix
                    //
                    // Since this method allows integer-only strings as input
                    // in addition to strings of floating-point literals, the
                    // two sub-patterns below are simplifications of the grammar
                    // productions from the Java Language Specification, 2nd
                    // edition, section 3.10.2.

                    // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

                    // . Digits ExponentPart_opt FloatTypeSuffix_opt
                    "(\\.("+Digits+")("+Exp+")?)|"+

                    // Hexadecimal strings
                    "((" +
                    // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                    "(0[xX]" + HexDigits + "(\\.)?)|" +

                    // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                    ")[pP][+-]?" + Digits + "))" +
                    "[fFdD]?))" +
                    "[\\x00-\\x20]*");// Optional trailing "whitespace"

    TextField xTextField, yTextField;

    Label xLabel, yLabel;

    ImageView deleteImage;

    String currentXText, currentYText;

    VBox parentPane;

    public PointElement(double x, double y, VBox parentPane) {

        this.parentPane = parentPane;

        currentXText = String.format("%f", x);
        currentYText = String.format("%f", y);

        xTextField = setupTextField("Enter X coordinate.");
        xTextField.setText(currentXText);
        setupXTextField();

        yTextField = setupTextField("Enter Y coordinate.");
        yTextField.setText(currentYText);
        setupYTextField();

        xLabel = setupLabel("X: ");
        yLabel = setupLabel("Y: ");

        setupDeleteImage();

        setPadding(new Insets(5, 5, 5, 5));
        setSpacing(5);

        setPrefHeight(30);

        getChildren().addAll(xLabel, xTextField, yLabel, yTextField, deleteImage);

    }


    private Label setupLabel(String text) {

        Label label = new Label();

        label.setFont(new Font("Cambria", 15));

        label.setText(text);

        label.setStyle("-fx-text-fill: black; ");

        return label;

    }

    private TextField setupTextField(String promptText) {

        TextField textField = new TextField();

        textField.setPromptText(promptText);

        textField.setStyle(getTextFieldStyle());

        textField.setPrefWidth(80);

        return textField;
    }

    private String getTextFieldStyle() {
        return "-fx-background-color: white; " +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 0.3px; ";
    }

    private void setupXTextField() {

        xTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (oldPropertyValue && !Pattern.matches(fpRegex, xTextField.getText())) {
                    xTextField.setText(currentXText);
                } else {
                    currentXText = xTextField.getText();
                }
            }

        });

    }

    private void setupYTextField() {

        yTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (oldPropertyValue && !Pattern.matches(fpRegex, yTextField.getText())) {
                    yTextField.setText(currentYText);
                } else {
                    currentYText = yTextField.getText();
                }
            }

        });

    }

    private void setupDeleteImage() {

        try {

            FileInputStream imageStream = new FileInputStream("resources/deleteIcon.png");

            Image image = new Image(imageStream);
            deleteImage = new ImageView(image);
            deleteImage.setFitHeight(20);
            deleteImage.setFitWidth(20);

            deleteImage.setCursor(Cursor.HAND);

            deleteImage.setOnMouseClicked(e -> {
                parentPane.getChildren().remove(this);
            });

        } catch (Exception e) {
            System.out.println("Can't load the delete image in PointElement class.");
        }

    }

    public double getXCoordinate() {
        return Double.parseDouble(xTextField.getText());
    }

    public double getYCoordinate() {
        return Double.parseDouble(yTextField.getText());
    }

    public Point2D getPoint() {
        return new Point2D(getXCoordinate(), getYCoordinate());
    }

}
