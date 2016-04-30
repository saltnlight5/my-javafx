package sample;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
            double squareSize = getWidth() / (8 + (1/3)); // We want padding = 1/3 of square size
            double padding = squareSize / 3;

            GraphicsContext gc = getGraphicsContext2D();
            drawBoard(gc, getWidth(), getHeight(), padding);
        }

        private void drawBoard(GraphicsContext gc, double canvasWidth, double canvasHeight, double padding) {
            double smallerSide = Math.min(canvasWidth, canvasHeight);
            double boardWidth = smallerSide - padding * 2;
            double boardHeight = boardWidth;
            double square =  boardWidth / 8;
            double paddingX = (canvasWidth - (square * 8)) / 2;
            double paddingY = (canvasHeight - (square * 8)) / 2;
            Color[] colors = {Color.LIGHTGRAY, Color.BLUE};

            // Clear the canvas first
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvasWidth, canvasHeight);

            // Draw a border outline of board.
            gc.setFill(Color.BLACK);
            gc.strokeRect(paddingX, paddingY, boardWidth, boardHeight);

            // Draw the board coordinates, it should stick to the side of the board even when board is resized.
            gc.setFont(Font.font("Courier New", square * 0.25));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            double coordCenter = square / 2;
            double coordX = paddingX - 10;
            for (int i = 0; i < 8; i++) {
                gc.fillText(Integer.toString(8 - i),  coordX, paddingY + (i * square) + coordCenter);
            }

            double coordY = paddingY + boardHeight + 10;
            for (int i = 0; i < 8; i++) {
                // ASCII 65 => 'A'
                gc.fillText(Character.toString((char)(65 +  i)),  paddingX + (i * square) + coordCenter, coordY);
            }

            // Draw the 64 square board with alternate colors. First top left square should be light color
            for (int row = 0; row < 8; row ++) {
                double colHeight = paddingY + (square * row);
                for (int col = 0; col < 8; col++) {
                    gc.setFill(colors[(col + (row % 2)) % 2]); // Alternate start color on each row
                    gc.fillRect(paddingX + col * square, colHeight, square, square);
                }
            }

            // Draw chess pieces using Unicode
            String blackPawn = Character.toString((char) 0x265F);
            String blackRook = Character.toString((char) 0x265C);
            String blackKnight = Character.toString((char) 0x265E);
            String blackBishop = Character.toString((char) 0x265D);
            String blackQueen = Character.toString((char) 0x265B);
            String blackKing = Character.toString((char) 0x265A);
            String whitePawn = Character.toString((char) 0x2659);
            String whiteRook = Character.toString((char) 0x2656);
            String whiteKnight = Character.toString((char) 0x2658);
            String whiteBishop = Character.toString((char) 0x2657);
            String whiteQueen = Character.toString((char) 0x2655);
            String whiteKing = Character.toString((char) 0x2654);

            gc.setFont(Font.font("Courier New", square * 0.75));
            gc.setFill(Color.BLACK);

            // Draw Black Pieces
            gc.fillText(blackRook, paddingX + (0 * square) + (square / 2), paddingY + (square / 2));
            gc.fillText(blackKnight, paddingX + (1 * square) + (square / 2), paddingY + (square / 2));
            gc.fillText(blackBishop, paddingX + (2 * square) + (square / 2), paddingY + (square / 2));
            gc.fillText(blackQueen, paddingX + (3 * square) + (square / 2), paddingY + (square / 2));
            gc.fillText(blackKing, paddingX + (4 * square) + (square / 2), paddingY + (square / 2));
            gc.fillText(blackBishop, paddingX + (5 * square) + (square / 2), paddingY + (square / 2));
            gc.fillText(blackKnight, paddingX + (6 * square) + (square / 2), paddingY + (square / 2));
            gc.fillText(blackRook, paddingX + (7 * square) + (square / 2), paddingY + (square / 2));
            for (int i = 0; i < 8; i++) {
                gc.fillText(blackPawn, paddingX + (i * square) + (square / 2), paddingY + square + (square / 2));
            }

            // Draw White Pieces
            gc.fillText(whiteRook, paddingX + (0 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            gc.fillText(whiteKnight, paddingX + (1 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            gc.fillText(whiteBishop, paddingX + (2 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            gc.fillText(whiteQueen, paddingX + (3 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            gc.fillText(whiteKing, paddingX + (4 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            gc.fillText(whiteBishop, paddingX + (5 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            gc.fillText(whiteKnight, paddingX + (6 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            gc.fillText(whiteRook, paddingX + (7 * square) + (square / 2), paddingY + (7 * square) + (square / 2));
            for (int i = 0; i < 8; i++) {
                gc.fillText(whitePawn, paddingX + (i * square) + (square / 2), paddingY + (6 * square) + (square / 2));
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
