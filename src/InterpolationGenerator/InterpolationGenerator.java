package InterpolationGenerator;

import javafx.geometry.Point2D;

import java.util.Vector;


public class InterpolationGenerator {

    private Vector<Point2D> points; // this vector will hold quadratic graph points
    private Vector<QuadraticEquation> equations; // this vector will hold the quadratic graph equations


    public InterpolationGenerator() {
        points = null;
        equations = new Vector<>();
    }

    public InterpolationGenerator(Vector<Point2D> points) {

        this.points = points;

        equations = new Vector<>(points.size() - 1);

    }

    public void generateEquations() {

        if (points.size() < 3)
            throw new IllegalStateException("You need three points minimum to generate the interpolation equations.");

        equations.clear();

        Point2D fPoint = points.get(0);
        Point2D sPoint = points.get(1);

        double a, b, c;

        ThreeEquationSystemSolver solver = new ThreeEquationSystemSolver();
        QuadraticEquation lastEquation;

        a = 0;
        b = (fPoint.getY() - sPoint.getY()) / (fPoint.getX() - sPoint.getX());
        c = fPoint.getY() - fPoint.getX() * b;

        equations.add(new QuadraticEquation(a, b, c));

        for (int i = 1; i < points.size() - 1; i++) {

            fPoint = points.get(i);
            sPoint = points.get(i + 1);
            lastEquation = equations.lastElement();

            solver.setFirstEquation(Math.pow(fPoint.getX(), 2), fPoint.getX(), 1, fPoint.getY());
            solver.setSecondEquation(Math.pow(sPoint.getX(), 2), sPoint.getX(), 1, sPoint.getY());
            solver.setThirdEquation(2 * fPoint.getX(), 1, 0, 2 * fPoint.getX() * lastEquation.getA() + lastEquation.getB());

            solver.solve();

            a = solver.getFirstVariable();
            b = solver.getSecondVariable();
            c = solver.getThirdVariable();

            equations.add(new QuadraticEquation(a, b, c));

        }

    }

    public Vector<Point2D> getPoints() {
        return points;
    }

    public void setPoints(Vector<Point2D> points) {
        this.points = points;
    }

    public void addPoint(Point2D newPoint) {
        points.add(newPoint);
    }

    public void addPoint(Point2D newPoint, int index) {
        points.add(index, newPoint);
    }

    public Vector<QuadraticEquation> getEquations() {
        return equations;
    }


}
