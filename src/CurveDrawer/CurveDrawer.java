package CurveDrawer;

import InterpolationGenerator.QuadraticEquation;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class CurveDrawer {

    private final double drawingTime = 10000;

    private ICurveGenerator curveGenerator;

    public CurveDrawer(ICurveGenerator curveGenerator) {

        this.curveGenerator = curveGenerator;

    }

    public void drawCurve(Point2D point1, Point2D point2, QuadraticEquation equation, Pane pane, double animationSpeed) {

        Point2D controlPoint = ControlPointGenerator.quadraticEquationControlPoint(point1, point2, equation);

        QuadCurve curve = curveGenerator.createCurve
                (
                point1.getX(), point1.getY(),
                controlPoint.getX(), controlPoint.getY(),
                point2.getX(), point2.getY()
                );


        double time = drawingTime * (1 - animationSpeed);

        time = time <= 0 ? 0.1 : time;

        BezierCurveVisualizer visualizer = new BezierCurveVisualizer(point1, controlPoint, point2, pane, time);

        visualizer.startVisualizing();

        Timeline addingCurveTimeLine = new Timeline(new KeyFrame(Duration.millis(time), e -> pane.getChildren().add(curve)));

        addingCurveTimeLine.play();

    }

    public ICurveGenerator getCurveGenerator() {
        return curveGenerator;
    }

    public void setCurveGenerator(ICurveGenerator curveGenerator) {
        this.curveGenerator = curveGenerator;
    }

}
