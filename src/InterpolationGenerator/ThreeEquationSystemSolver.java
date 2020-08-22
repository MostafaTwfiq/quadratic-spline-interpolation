package InterpolationGenerator;

public class ThreeEquationSystemSolver {

    // | a1     b1      c1 |         | firstVariable  |        | d1 |
    // | a2     b2      c2 |    *    | secondVariable |    =   | d2 |
    // | a3     b3      c3 |         | thirdVariable  |        | d3 |

    private double a1;
    private double a2;
    private double a3;
    private double b1;
    private double b2;
    private double b3;
    private double c1;
    private double c2;
    private double c3;
    private double d1;
    private double d2;
    private double d3;
    private double firstVariable;
    private double secondVariable;
    private double thirdVariable;


    public void setFirstEquation(double a, double b, double c, double d) {

        a1 = a;
        b1 = b;
        c1 = c;
        d1 = d;

    }


    public void setSecondEquation(double a, double b, double c, double d) {

        a2 = a;
        b2 = b;
        c2 = c;
        d2 = d;

    }


    public void setThirdEquation(double a, double b, double c, double d) {

        a3 = a;
        b3 = b;
        c3 = c;
        d3 = d;

    }



    /**
     * From Cramer's Rule:
     *
     *
     *
     *                          | a1     b1      c1 |
     * Delta =                  | a2     b2      c2 |
     *                          | a3     b3      c3 |
     *
     *
     *                          | d1     b1      c1 |
     * FirstVariable =          | d2     b2      c2 |   ÷   Delta
     *                          | d3     b3      c3 |
     *
     *
     *                          | a1     d1      c1 |
     * SecondVariable =         | a2     d2      c2 |   ÷   Delta
     *                          | a3     d3      c3 |
     *
     *
     *                          | a1     b1      d1 |
     * ThirstVariable =         | a2     b2      d2 |   ÷   Delta
     *                          | a3     b3      d3 |
     *
     */

    public void solve() {

        double delta = a1 * (b2 * c3 - b3 * c2) - b1 * (a2 * c3 - a3 * c2) + c1 * (a2 * b3 - a3 * b2);

        firstVariable = ( d1 * (b2 * c3 - b3 * c2) - b1 * (d2 * c3 - d3 * c2) + c1 * (d2 * b3 - d3 * b2) ) / delta;

        secondVariable = ( a1 * (d2 * c3 - d3 * c2) - d1 * (a2 * c3 - a3 * c2) + c1 * (a2 * d3 - a3 * d2) ) / delta;

        thirdVariable = ( a1 * (b2 * d3 - b3 * d2) - b1 * (a2 * d3 - a3 * d2) + d1 * (a2 * b3 - a3 * b2) ) / delta;

    }


    public double getFirstVariable() {
        return firstVariable;
    }

    public double getSecondVariable() {
        return secondVariable;
    }

    public double getThirdVariable() {
        return thirdVariable;
    }


}
