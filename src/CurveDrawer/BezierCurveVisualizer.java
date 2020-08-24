package CurveDrawer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.Vector;

public class BezierCurveVisualizer {

    private Point2D startPoint, controlPoint, endPoint;

    private Pane pane;

    private double timeInMillis, speedRate;

    private Timeline visualizingTimeLine;

    private Line drawingLine;

    private double linesWidth;

    private final int numberOfCycles = 200;

    private double t = 0;


    public BezierCurveVisualizer(Point2D startPoint, Point2D controlPoint, Point2D endPoint,
                                 Pane pane, double timeInMillis) {

        this.startPoint = startPoint;
        this.controlPoint = controlPoint;
        this.endPoint = endPoint;

        this.pane = pane;

        this.timeInMillis = timeInMillis;

        speedRate = 1;

        visualizingTimeLine = new Timeline();

        linesWidth = 2;

    }

    public BezierCurveVisualizer(Point2D startPoint, Point2D controlPoint, Point2D endPoint, Pane pane, double timeInMillis, double speedRate) {

        this.startPoint = startPoint;
        this.controlPoint = controlPoint;
        this.endPoint = endPoint;

        this.pane = pane;

        this.timeInMillis = timeInMillis;

        this.speedRate = speedRate;

        visualizingTimeLine = new Timeline();

        linesWidth = 2;

    }


    public void startVisualizing() {

        Vector<Line> lines = new Vector<>();

        t = 0;

        Line fLine = createLine(startPoint, controlPoint);
        Line sLine = createLine(controlPoint, endPoint);
        pane.getChildren().addAll(fLine, sLine);

        visualizingTimeLine = new Timeline(new KeyFrame(Duration.millis(timeInMillis / numberOfCycles), e -> {

            Point2D drawingLineFPoint = interpolatorGetCurrentPoint(startPoint, controlPoint, t);
            Point2D drawingLineSPoint = interpolatorGetCurrentPoint(controlPoint, endPoint, t);

            drawingLine = createDrawingLine(drawingLineFPoint, drawingLineSPoint);

            lines.add(drawingLine);
            pane.getChildren().add(drawingLine);

            t += 1.0 / numberOfCycles;

        }));

        visualizingTimeLine.setCycleCount(numberOfCycles + 1);

        visualizingTimeLine.setRate(speedRate);

        visualizingTimeLine.setOnFinished(e -> {

            for (var v : lines)
              pane.getChildren().remove(v);

            pane.getChildren().remove(fLine);
            pane.getChildren().remove(sLine);

        });

        visualizingTimeLine.play();

    }


    public void pauseVisualizing() {

        visualizingTimeLine.pause();

    }

    public void resumeVisualizing() {

        visualizingTimeLine.play();

    }

    public void stopVisualizing() {

        visualizingTimeLine.stop();

    }

    private Point2D interpolatorGetCurrentPoint(Point2D point1, Point2D point2, double t) {

        double x = point1.getX() + (point2.getX() - point1.getX()) * t;
        double y = point1.getY() + (point2.getY() - point1.getY()) * t;

        return new Point2D(x, y);

    }


    private Line createDrawingLine(Point2D point1, Point2D point2) {

        Line drawingLine = new Line();
        drawingLine.setStroke(Color.YELLOWGREEN);
        drawingLine.setStrokeWidth(linesWidth / 20);

        drawingLine.setStartX(point1.getX());
        drawingLine.setStartY(point1.getY());

        drawingLine.setEndX(point2.getX());
        drawingLine.setEndY(point2.getY());

        return drawingLine;

    }


    private Line createLine(Point2D point1, Point2D point2) {

        Line line = new Line();

        line.setStroke(Color.SILVER);

        line.setStrokeWidth(linesWidth);

        line.setStartX(point1.getX());
        line.setStartY(point1.getY());

        line.setEndX(point2.getX());
        line.setEndY(point2.getY());

        return line;

    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D getControlPoint() {
        return controlPoint;
    }

    public void setControlPoint(Point2D controlPoint) {
        this.controlPoint = controlPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    public double getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(double timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public double getSpeedRate() {
        return speedRate;
    }

    public void setSpeedRate(double speedRate) {
        this.speedRate = speedRate;
    }

    public double getLinesWidth() {
        return linesWidth;
    }

    public void setLinesWidth(double linesWidth) {
        this.linesWidth = linesWidth;
    }

}
