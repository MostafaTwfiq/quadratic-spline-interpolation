package VisualizerGUI.PointsView;

import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PointsPane extends VBox {

    Pane drawPane;

    public PointsPane(Pane drawPane) {

        this.drawPane = drawPane;

        setSpacing(5);

    }


    public void addPoint(double x, double y, Rectangle pointRec) {

        getChildren().add(new PointElement(x, y, this, pointRec));

    }

    protected void movePoint(double oldX, double oldY, double newX, double newY, Rectangle pointRec) {

        Path path = new Path();

        //Moving to the starting point
        MoveTo moveTo = new MoveTo(oldX, oldY);

        //Creating 1st line
        LineTo line1 = new LineTo(newX, newY);

        //Adding all the elements to the path
        path.getElements().addAll(moveTo, line1);

        //Creating the path transition
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the transition
        pathTransition.setDuration(Duration.millis(1000));

        //Setting the node for the transition
        pathTransition.setNode(pointRec);

        //Setting the path for the transition
        pathTransition.setPath(path);

        //Playing the animation
        pathTransition.play();

    }

    protected void deletePoint(PointElement pointElement) {

        drawPane.getChildren().remove(pointElement.getPointRec());

        getChildren().remove(pointElement);

    }

}
