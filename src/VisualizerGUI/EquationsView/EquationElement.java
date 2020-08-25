package VisualizerGUI.EquationsView;

import InterpolationGenerator.QuadraticEquation;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EquationElement extends VBox {

    QuadraticEquation equation;

    Point2D point1, point2;

    Label pointsL, equationL;

    public EquationElement(Point2D point1, Point2D point2, QuadraticEquation equation) {

        this.point1 = point1;
        this.point2 = point2;
        this.equation = equation;

        pointsL = setupLabel( "Point 1: (" + String.format("%.2f", point1.getX())
                            + "," + String.format("%.2f", point1.getY()) + ")\n"
                + "Point 2: (" + String.format("%.2f", point2.getX())
                + "," + String.format("%.2f", point2.getY()) + ")" );

        equationL = setupLabel("Equation: " + equation.toString());

        setPadding(new Insets(5, 5, 5, 5));

        setSpacing(5);

        getChildren().addAll(pointsL, equationL);

    }

    private Label setupLabel(String text) {

        Label label = new Label();

        label.setFont(new Font("Cambria", 15));

        label.setText(text);

        label.setStyle("-fx-text-fill: black; ");

        return label;

    }


}
