package sample;

import InterpolationGenerator.InterpolationGenerator;
import InterpolationGenerator.QuadraticEquation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);


        // Equations should be:
        // firstEquations   =   0.0                     (X ^ 2)  +  22.704              X  +     0.0
        // secondEquation   =   0.8887999999999999      (X ^ 2)  +  4.928000000000047   X  +     88.87999999999971
        // thirdEquation    =   -0.13560000000000855    (X ^ 2)  +  35.66000000000029   X  +     -141.61000000000232
        // fourthEquation   =   1.6048000000000138      (X ^ 2)  +  -33.95600000000152  X  +     554.5500000000139
        // fifthEquation    =   0.2088888888890061      (X ^ 2)  +  28.859999999993725  X  +     -152.1299999999172


        Vector<Point2D> points = new Vector<>();
        points.add(new Point2D(0, 0));
        points.add(new Point2D(10, 227.04));
        points.add(new Point2D(15, 362.78));
        points.add(new Point2D(20, 517.35));
        points.add(new Point2D(22.5, 602.97));
        points.add(new Point2D(30, 901.67));

        InterpolationGenerator generator = new InterpolationGenerator(points);

        generator.generateEquations();

        Vector<QuadraticEquation> equations = generator.getEquations();

        for (var e : equations)
            System.out.println(e);

    }
}
