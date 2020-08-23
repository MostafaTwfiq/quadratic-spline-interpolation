package CurveDrawer;

import InterpolationGenerator.QuadraticEquation;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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


        Rectangle rec = new Rectangle(10, 10);

        rec.setTranslateX(point1.getX() - 5);
        rec.setTranslateY(point1.getY() - 5);
        rec.setFill(Color.RED);

        pane.getChildren().add(rec);

        Path path = new Path();
        path.getElements().add(new MoveTo(point1.getX(), point1.getY()));
        path.getElements().add(new QuadCurveTo(
                controlPoint.getX(), controlPoint.getY(),
                point2.getX(), point2.getY()));
        PathTransition pt = new PathTransition(Duration.millis(2000), path);

        pt.setNode(rec);

        pt.setOnFinished(e -> {
            pane.getChildren().remove(rec);
            pane.getChildren().add(curve);
        });

        pt.play();

    }

    public ICurveGenerator getCurveGenerator() {
        return curveGenerator;
    }

    public void setCurveGenerator(ICurveGenerator curveGenerator) {
        this.curveGenerator = curveGenerator;
    }

}
