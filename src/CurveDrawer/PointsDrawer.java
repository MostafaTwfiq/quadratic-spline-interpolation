package CurveDrawer;


import InterpolationGenerator.QuadraticEquation;
import InterpolationGenerator.InterpolationGenerator;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import java.util.Vector;

public class PointsDrawer {

    private Pane pane;
    private Vector<Point2D> points;
    private Vector<QuadraticEquation> equations;
    private CurveDrawer curveDrawer;
    private int currentEquationIndex;


    public PointsDrawer(Pane pane, Vector<Point2D> points, ICurveGenerator curveDrawer) {

        this.pane = pane;

        this.points = points;

        this.curveDrawer = new CurveDrawer(curveDrawer);

        currentEquationIndex = 0;

    }


    public void drawNext(double animationSpeed) {

        calculateEquations();

        if (equations == null || currentEquationIndex >= equations.size())
            return;

        QuadraticEquation equation = equations.get(currentEquationIndex);

        Point2D point1 = points.get(currentEquationIndex);
        Point2D point2 = points.get(currentEquationIndex + 1);

        curveDrawer.drawCurve(point1, point2, equation, pane, animationSpeed);

        currentEquationIndex++;

    }

    public void drawAll(double animationSpeed) {

        calculateEquations();

        if (equations == null)
            return;

        for (int i = currentEquationIndex; i < equations.size(); i++) {

            QuadraticEquation equation = equations.get(i);

            Point2D point1 = points.get(i);
            Point2D point2 = points.get(i + 1);

            curveDrawer.drawCurve(point1, point2, equation, pane, animationSpeed);

        }

        currentEquationIndex = equations.size();

    }

    private void calculateEquations() {

        if (points.size() < 3) {
            equations = null;
            return;
        }

        InterpolationGenerator interpolationGenerator = new InterpolationGenerator(points);
        interpolationGenerator.generateEquations();

        equations = interpolationGenerator.getEquations();

    }

    public boolean hasNextToDraw() {
        return currentEquationIndex < equations.size();
    }

    public void resetCounter() {
        currentEquationIndex = 0;
    }


    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Vector<Point2D> getPoints() {
        return points;
    }

    public void setPoints(Vector<Point2D> points) {
        this.points = points;


        currentEquationIndex = 0;

        calculateEquations();

    }

    public Vector<QuadraticEquation> getEquations() {
        return equations;
    }

}
