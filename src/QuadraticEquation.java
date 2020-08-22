public class QuadraticEquation {



    private double a, b, c;

    // a (X ^ 2) + b X + c


    /** This constructor will take the equation constants.
     *
     * Ex: lets assume the function is a function in x,
     * then the equation will be as following: a (X ^ 2) + b X + c.
     *
     * @param a the quadratic term constant
     * @param b the linear term constant
     * @param c the equation constant
     */

    public QuadraticEquation(double a, double b, double c) {

        this.a = a;
        this.b = b;
        this.c = c;

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

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }


    /** This function will calculate the function at the passed point.
     *
     * @param x the function variable value.
     * @return it will return the calculated function value at the given point
     */

    public double calculateAtPoint(double x) {

        return a * Math.pow(x, 2) + b * x + c;

    }


    public String toString() {
        return a + " (X ^ 2) " + " + " + b + " X " + " + " + c;
    }


}
