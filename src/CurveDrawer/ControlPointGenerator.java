package CurveDrawer;

import InterpolationGenerator.LinearEquation;
import InterpolationGenerator.QuadraticEquation;
import javafx.geometry.Point2D;

public class ControlPointGenerator {

    public static Point2D quadraticEquationControlPoint(Point2D startPoint, Point2D endPoint, QuadraticEquation equation) {

        Point2D controlPoint = startPoint.midpoint(endPoint);

        if (equation.getA() != 0) {

            double m1 = equation.getA() * 2 * startPoint.getX() + equation.getB();
            double m2 = equation.getA() * 2 * endPoint.getX() + equation.getB();

            LinearEquation linearEquation1 = new LinearEquation(m1, startPoint.getY() - startPoint.getX() * m1);
            LinearEquation linearEquation2 = new LinearEquation(m2, endPoint.getY() - endPoint.getX() * m2);

            double x = (linearEquation1.getB() - linearEquation2.getB()) / (linearEquation2.getA() - linearEquation1.getA());
            double y = linearEquation1.getA() * x + linearEquation1.getB();

            controlPoint = new Point2D(x, y);

        }

        return controlPoint;

    }

    private static double getX(Point2D sp, Point2D ep, QuadraticEquation e) {
        return (ep.getY() - sp.getY() + 2 * e.getA() * (Math.pow(sp.getX(), 2) - Math.pow(ep.getX(), 2)) + e.getB() * (sp.getX() - ep.getX())) / (2 * e.getA() * (sp.getX() - ep.getX()));
    }

    private static double getY(Point2D sp, Point2D ep, QuadraticEquation e) {
        return (2 * e.getA() * sp.getX() + e.getB()) * (getX(sp, ep, e) - sp.getX()) + sp.getY();
    }

}
