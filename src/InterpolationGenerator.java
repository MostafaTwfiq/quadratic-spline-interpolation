import javafx.scene.effect.Light;

import java.util.Vector;
import javafx.scene.effect.Light.Point;

public class InterpolationGenerator {

    private Vector<Point> points; // this vector will hold quadratic graph points
    private Vector<QuadraticEquation> equations; // this vector will hold the quadratic graph equations


    public InterpolationGenerator() {
        points = null;
        equations = new Vector<>();
    }

    public InterpolationGenerator(Vector<Point> points) {

        this.points = points;

        equations = new Vector<>(points.size() - 1);

    }

    public void generateEquations() {

        if (points.size() < 3)
            throw new IllegalStateException("You need three points minimum to generate the interpolation equations.");

        equations.clear();

        Point fPoint = points.get(0);
        Point sPoint = points.get(1);

        double a, b, c;

        ThreeEquationSystemSolver solver = new ThreeEquationSystemSolver();
        QuadraticEquation lastEquation;

        a = 0;
        b = (fPoint.getY() - sPoint.getY()) / (fPoint.getX() - sPoint.getX());
        c = fPoint.getY() - fPoint.getX() * b;

        equations.add(new QuadraticEquation(a, b, c));

        for (int i = 1; i < points.size() - 2; i++) {

            fPoint = points.get(i);
            sPoint = points.get(i + 1);
            lastEquation = equations.lastElement();

            solver.setFirstEquation(Math.pow(fPoint.getX(), 2), fPoint.getX(), 1, fPoint.getY());
            solver.setSecondEquation(Math.pow(sPoint.getX(), 2), sPoint.getX(), 1, sPoint.getY());
            solver.setThirdEquation(2 * fPoint.getX(), 1, 0, 2 * fPoint.getX() * lastEquation.getA() + lastEquation.getB());

            a = solver.getFirstVariable();
            b = solver.getSecondVariable();
            c = solver.getThirdVariable();

            equations.add(new QuadraticEquation(a, b, c));

        }

    }

    public Vector<Point> getPoints() {
        return points;
    }

    public void setPoints(Vector<Point> points) {
        this.points = points;
    }

    public Vector<QuadraticEquation> getEquations() {
        return equations;
    }


}
