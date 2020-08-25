package VisualizerGUI;

import CurveDrawer.ICurveGenerator;
import CurveDrawer.PointsDrawer;
import InterpolationGenerator.QuadraticEquation;
import VisualizerGUI.EquationsView.EquationsPane;
import VisualizerGUI.PointsView.PointElement;
import VisualizerGUI.PointsView.PointsPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;

import java.util.Vector;

public class Visualizer {

    AnchorPane parentPane;
    VisualizerGUIController controller;

    PointsPane pointsPane;

    EquationsPane equationsPane;

    PointsDrawer drawer;

    Vector<Point2D> points;


    public Visualizer() {

        load();

        points = new Vector<>();

        drawer = new PointsDrawer(controller.getDrawPane(), points, new ICurveGenerator() {

            @Override
            public QuadCurve createCurve(double v, double v1, double v2, double v3, double v4, double v5) {

                QuadCurve curve = new QuadCurve(v, v1, v2,v3, v4, v5);

                curve.setFill(Color.TRANSPARENT);
                curve.setStroke(Color.BLACK);
                curve.setStrokeWidth(2);

                return curve;

            }
        });


    }

    private void load() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("VisualizerGUI.fxml"));
            controller = new VisualizerGUIController();
            loader.setController(controller);
            parentPane = loader.load();

            pointsPane = new PointsPane(controller.getDrawPane());

            equationsPane = new EquationsPane();

            setupDrawPane();
            setupDrawAllBAction();
            setupDrawNextBAction();
            setupClearCurvesBAction();
            setupClearPointsBAction();
            setupCheckBox();
            setupSpeedSlider();

            controller.getPointsScrollPane().setContent(pointsPane);

            controller.getEquationsScrollPane().setContent(equationsPane);

        }catch (Exception e) {
            System.out.println("There is something wrong happened while loading the fxml.");
        }

    }

    private void setupDrawPane() {

        Pane drawPane = controller.getDrawPane();

        Label coordinateLabel = new Label();

        drawPane.getChildren().add(coordinateLabel);

        drawPane.setOnMouseMoved(e -> {

            coordinateLabel.setText("x: " + String.format("%.2f", e.getX()) +
                                    "\ny: " + String.format("%.2f",e.getY()));

            coordinateLabel.setLayoutX(e.getX() + 5);
            coordinateLabel.setLayoutY(e.getY() + 5);

        });

        drawPane.setOnMouseClicked(e -> {

            if (pointsPane.getChildren().size() != 0) {

                PointElement lastPoint = (PointElement) pointsPane.getChildren().get(pointsPane.getChildren().size() - 1);

                if (lastPoint.getXCoordinate() != e.getX() && lastPoint.getYCoordinate() != e.getY()) {
                    Rectangle pointRec = new Rectangle(e.getX() - 4, e.getY() - 4, 8, 8);
                    pointsPane.addPoint(e.getX(), e.getY(), pointRec);
                    drawPane.getChildren().add(pointRec);
                }

            } else {
                Rectangle pointRec = new Rectangle(e.getX() - 4, e.getY() - 4, 8, 8);
                pointsPane.addPoint(e.getX(), e.getY(), pointRec);
                drawPane.getChildren().add(pointRec);
            }

        });

    }


    private void setupDrawAllBAction() {

        controller.getDrawAllB().setOnAction(e -> {

            if (pointsPane.getChildren().size() < 3) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Need three points minimum.");
                alert.showAndWait();
            } else {

                points.clear();
                for (Node point : pointsPane.getChildren())
                    points.add( ((PointElement)point).getPoint());

                clearDrawPaneCurves();

                drawer.resetCounter();
                drawer.drawAll(controller.getSpeedSlider().getValue() / 100);

                Vector<QuadraticEquation> equationsV = drawer.getEquations();
                equationsPane.getChildren().clear();
                for (int i = 0; i < equationsV.size(); i++)
                    equationsPane.addEquation(points.get(i), points.get(i + 1), equationsV.get(i));

            }

        });

    }


    private void setupDrawNextBAction() {

        controller.getDrawNextB().setOnAction(e -> {

            if (pointsPane.getChildren().size() < 3) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Need three points minimum.");
                alert.showAndWait();
            } else {

                points.clear();
                for (Node point : pointsPane.getChildren())
                    points.add( ((PointElement)point).getPoint());


                drawer.drawNext(controller.getSpeedSlider().getValue() / 100);

                Vector<QuadraticEquation> equationsV = drawer.getEquations();
                equationsPane.getChildren().clear();
                for (int i = 0; i < equationsV.size(); i++)
                    equationsPane.addEquation(points.get(i), points.get(i + 1), equationsV.get(i));

            }

        });

    }

    private void setupClearCurvesBAction() {

        controller.getClearCurvesB().setOnAction( e-> {
            clearDrawPaneCurves();
        });
    }

    private void setupClearPointsBAction() {

        controller.getClearPointsB().setOnAction( e-> {
            clearDrawPaneCurves();
            clearDrawPanePoints();
        });

    }

    public AnchorPane getParentPane() {
        return parentPane;
    }

    private void clearDrawPaneCurves() {

        controller.getDrawPane().getChildren().removeIf(curve -> curve instanceof QuadCurve);

        drawer.resetCounter();

    }

    private void clearDrawPanePoints() {

        controller.getDrawPane().getChildren().clear();

        pointsPane.getChildren().clear();

        drawer.resetCounter();

        equationsPane.getChildren().clear();

    }


    private void setupCheckBox() {

        controller.getEnableCheckBox().setOnMouseClicked(e -> {

            if (!controller.getEnableCheckBox().isSelected())
                controller.getSpeedSlider().setValue(100);
            else
                controller.getSpeedSlider().setValue(50);

        });

    }

    private void setupSpeedSlider() {

        controller.getSpeedSlider().setValue(50);

        controller.getSpeedSlider().valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){

                controller.getEnableCheckBox().setSelected(newValue.doubleValue() < 100);

            }
        });

    }

}
