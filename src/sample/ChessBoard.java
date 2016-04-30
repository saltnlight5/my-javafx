package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Drawing Chess Board using Canvas.
 */
public class ChessBoard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chess Board");
        StackPane stackPane = new StackPane();
        stackPane.setPrefWidth(600);
        stackPane.setPrefHeight(600);

        BoardCanvas canvas = new BoardCanvas(stackPane);
        stackPane.getChildren().add(canvas);

        primaryStage.setScene(new Scene(stackPane));
        primaryStage.show();
    }

    public static class BoardCanvas extends Canvas {

        public BoardCanvas(Pane parent) {
            // Bind canvas size to stack pane size.
            widthProperty().bind(parent.widthProperty());
            heightProperty().bind(parent.heightProperty());

            // Handle Canvas resizing.
            widthProperty().addListener(observable -> draw());
            heightProperty().addListener(observable -> draw());

            // Draw first time.
            draw();
        }

        public void draw() {
            double squareSize = getWidth() / (8 + (1/3)); // We want inset = 1/3 of square size
            double insetSize = squareSize / 3;

            GraphicsContext gc = getGraphicsContext2D();
            drawBoard(gc, getWidth(), getHeight(), insetSize);
        }

        private void drawBoard(GraphicsContext gc, double canvasWidth, double canvasHeight, double inset) {
            double minSize = Math.min(canvasWidth, canvasHeight);
            double width = minSize - inset * 2;
            double height = minSize - inset * 2;
            double square =  width / 8;
            double leftMargin = (canvasWidth - (square * 8)) / 2;
            double topMargin = (canvasHeight - (square * 8)) / 2;
            Color[] colors = {Color.LIGHTGRAY, Color.BLUE};

            // Clear the canvas first
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvasWidth, canvasHeight);

            // Draw a border outline of board.
            gc.setFill(Color.BLACK);
            gc.strokeRect(leftMargin, topMargin, width, height);

            // Draw the 64 square board with alternate colors. First top left square should be light color
            for (int row = 0; row < 8; row ++) {
                double colHeight = topMargin + (square * row);
                for (int col = 0; col < 8; col++) {
                    gc.setFill(colors[(col + (row % 2)) % 2]); // Alternate start color on each row
                    gc.fillRect(leftMargin + col * square, colHeight, square, square);
                }
            }
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }

}
