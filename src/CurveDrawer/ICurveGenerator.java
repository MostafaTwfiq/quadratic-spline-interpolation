package CurveDrawer;

import javafx.scene.shape.QuadCurve;

public interface ICurveGenerator {

    QuadCurve createCurve(double v, double v1, double v2, double v3, double v4, double v5);

}
