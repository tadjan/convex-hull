package io.tadjan.algs.ch;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class ConvexHullVisualizer extends Application {

    private static final int NO_OF_RANDOM_POINTS = 25;

    private Pane canvas;

    private Set<Point> points;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();

        canvas = new Pane();
        borderPane.setCenter(canvas);
        BorderPane.setMargin(canvas, new Insets(5));

        canvas.setOnMouseClicked(this::addPoint);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clear());

        Button randomizeButton = new Button("Randomize!");
        randomizeButton.setOnAction(e -> generateRandomPoints());

        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL, 5, 5, clearButton, randomizeButton);
        buttonPane.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(buttonPane);
        BorderPane.setMargin(buttonPane, new Insets(5));

        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Convex Hull Demo");
        primaryStage.show();

        generateRandomPoints();
    }

    private void clear() {
        points.clear();
        repaint();
    }

    private void addPoint(MouseEvent event) {
        points.add(new Point(event.getX(), event.getY()));
        repaint();
    }

    private void removePoint(Point point) {
        points.remove(point);
        repaint();
    }

    private void generateRandomPoints() {
        double canvasWidth = canvas.getLayoutBounds().getWidth();
        double canvasHeight = canvas.getLayoutBounds().getHeight();

        points = new HashSet<>();
        for (int i = 0; i < NO_OF_RANDOM_POINTS; i++) {
            double x = canvasWidth * Math.random();
            double y = canvasHeight * (1.0 - Math.random());
            points.add(new Point(x, y));
        }
        repaint();
    }

    private void repaint() {
        canvas.getChildren().clear();

        Deque<Point> hull = ConvexHull.getHull(points);

        points.stream()
                .filter(point -> !hull.contains(point))
                .forEach(point -> drawCircle(point, Color.BLACK));

        Polygon polygon = new Polygon();
        polygon.setFill(null);
        polygon.setStroke(Color.RED);
        hull.forEach(point -> {
            Circle circle = drawCircle(point, Color.RED);
            polygon.getPoints().addAll(circle.getCenterX(), circle.getCenterY());
        });
        canvas.getChildren().add(polygon);
        polygon.toBack();
    }

    private Circle drawCircle(Point point, Color color) {
        double x = point.getX();
        double y = point.getY();
        Circle circle = new Circle(x, y, 5, color);
        circle.setOnMouseClicked(event -> {
            event.consume();
            removePoint(point);
        });
        canvas.getChildren().add(circle);
        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
