package VisualizerGUI.PointsView;

import javafx.scene.layout.VBox;

public class PointsPane extends VBox {


    public PointsPane() {

        setSpacing(5);

    }


    public void addPoint(double x, double y) {

        getChildren().add(new PointElement(x, y, this));

    }

}
