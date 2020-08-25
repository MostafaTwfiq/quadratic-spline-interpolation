package VisualizerGUI;

import CurveDrawer.ICurveGenerator;
import CurveDrawer.PointsDrawer;
import VisualizerGUI.PointsView.PointElement;
import VisualizerGUI.PointsView.PointsPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;

import java.util.Vector;

public class Visualizer {

    AnchorPane parentPane;
    VisualizerGUIController controller;

    PointsPane pointsPane;

    public Visualizer() {

        pointsPane = new PointsPane();

        load();

    }

    private void load() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("VisualizerGUI.fxml"));
            controller = new VisualizerGUIController();
            loader.setController(controller);
            parentPane = loader.load();

            setupDrawPane();
            setupDrawBAction();
            setupClearBAction();

            controller.getPointsScrollPane().setContent(pointsPane);

        }catch (Exception e) {
            System.out.println("There is something wrong happened while loading the fxml.");
        }

    }

    private void setupDrawPane() {

        Pane drawPane = controller.getDrawPane();

        Label coordinateLabel = new Label();

        drawPane.getChildren().add(coordinateLabel);

        drawPane.setOnMouseMoved(e -> {

            coordinateLabel.setText("x: " + String.format("%.2f", e.getX()) + "\ny: " + String.format("%.2f",e.getY()));
            coordinateLabel.setLayoutX(e.getX() + 5);
            coordinateLabel.setLayoutY(e.getY() + 5);

        });

        drawPane.setOnMouseClicked(e -> {

            if (pointsPane.getChildren().size() != 0) {

                PointElement lastPoint = (PointElement) pointsPane.getChildren().get(pointsPane.getChildren().size() - 1);

                if (lastPoint.getXCoordinate() != e.getX() && lastPoint.getYCoordinate() != e.getY()) {
                    pointsPane.addPoint(e.getX(), e.getY());
                    drawPane.getChildren().add(new Rectangle(e.getX() - 4, e.getY() - 4, 8, 8));
                }

            } else {
                pointsPane.addPoint(e.getX(), e.getY());
                drawPane.getChildren().add(new Rectangle(e.getX() - 4, e.getY() - 4, 8, 8));
            }

        });

    }


    private void setupDrawBAction() {

        controller.getDrawB().setOnAction(e -> {

            if (pointsPane.getChildren().size() < 3) {
                System.out.println("Need three points minimum.");
            } else {

                Vector<Point2D> points = new Vector<>();
                for (Node point : pointsPane.getChildren())
                    points.add( ((PointElement)point).getPoint());

                PointsDrawer drawer = new PointsDrawer(controller.getDrawPane(), points, new ICurveGenerator() {

                    @Override
                    public QuadCurve createCurve(double v, double v1, double v2, double v3, double v4, double v5) {

                        QuadCurve curve = new QuadCurve(v, v1, v2,v3, v4, v5);

                        curve.setFill(Color.TRANSPARENT);
                        curve.setStroke(Color.BLACK);
                        curve.setStrokeWidth(2);

                        return curve;

                    }
                });

                clearDrawPaneCurves();

                drawer.drawAll();

            }

        });

    }

    private void setupClearBAction() {

        controller.getClearB().setOnAction( e-> {
            clearDrawPaneCurves();
        });
    }

    public AnchorPane getParentPane() {
        return parentPane;
    }

    private void clearDrawPaneCurves() {

        controller.getDrawPane().getChildren().removeIf(curve -> curve instanceof QuadCurve);

    }

    private void clearDrawPanePoints() {

        controller.getDrawPane().getChildren().removeAll();

        pointsPane.getChildren().removeAll();

    }

}
