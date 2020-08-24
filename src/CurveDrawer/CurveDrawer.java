package CurveDrawer;

import InterpolationGenerator.QuadraticEquation;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class CurveDrawer {

    private ICurveGenerator curveGenerator;

    public CurveDrawer(ICurveGenerator curveGenerator) {

        this.curveGenerator = curveGenerator;

    }

    public void drawCurve(Point2D point1, Point2D point2, QuadraticEquation equation, Pane pane) {

        Point2D controlPoint = ControlPointGenerator.quadraticEquationControlPoint(point1, point2, equation);

        QuadCurve curve = curveGenerator.createCurve
                (
                point1.getX(), point1.getY(),
                controlPoint.getX(), controlPoint.getY(),
                point2.getX(), point2.getY()
                );


        BezierCurveVisualizer visualizer = new BezierCurveVisualizer(point1, controlPoint, point2, pane, 2000);

        visualizer.startVisualizing();

        Timeline addingCurveTimeLine = new Timeline(new KeyFrame(Duration.millis(2000), e -> pane.getChildren().add(curve)));

        addingCurveTimeLine.play();

    }

    public ICurveGenerator getCurveGenerator() {
        return curveGenerator;
    }

    public void setCurveGenerator(ICurveGenerator curveGenerator) {
        this.curveGenerator = curveGenerator;
    }

}
