package InterpolationGenerator;

public class LinearEquation {

    double a;
    double b;


    public LinearEquation(double a, double b) {

        this.a = a;
        this.b = b;

    }


    public double calculateAtPoint(double x) {

        return a * x + b;

    }


    public String toString() {
        return a + " X " + " + " + b;
    }


    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }


}
