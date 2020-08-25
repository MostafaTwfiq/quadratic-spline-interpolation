package VisualizerGUI.EquationsView;

import InterpolationGenerator.QuadraticEquation;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;

public class EquationsPane extends VBox {

    public EquationsPane() {

        setSpacing(5);

    }



    public void addEquation(Point2D point1, Point2D point2, QuadraticEquation equation) {

        getChildren().addAll(new EquationElement(point1, point2, equation));

    }

}
