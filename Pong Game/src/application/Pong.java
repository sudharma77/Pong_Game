/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package application ;

import java.util.Random;
//import java.awt.Canvas;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author hp
 */
public class Pong extends Application {
    
    
    private static final int width = 800;
    private static final int height = 600;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_R = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double playerOneYPos = height / 2;
    private double playerTwoYPos = height / 2;
    private double ballXPos = width / 2;
    private double ballYPos = height / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted ; 
    private int playerOneXPos = 0;
    private double playerTwoXPos = width - PLAYER_WIDTH;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("PONG GAME");
        Canvas canvas;
        canvas = new Canvas(width, height);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e ->run(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);
        
        //mouse control
        
        canvas.setOnMouseMoved(e ->playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e ->gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        t1.play();
    }
    
    private void run(GraphicsContext gc){
        //set the back ground 
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        
        //set text
        gc.setFill(Color.RED);
        gc.setFont(Font.font(25));
        
        if(gameStarted){
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            //computer
            if(ballXPos < width - width /4){
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
                
            }else{
                playerTwoYPos = ballYPos>(playerTwoYPos + PLAYER_HEIGHT / 2)?playerTwoYPos += 1: playerTwoYPos - 1;                
            }
            
            //ball draw
            
            gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
        }else{
            //set the start text
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("On Click", width / 2, height / 2);
            
            //reset the ball start position
            ballXPos = width / 2;
            ballYPos = height / 2;
            
            //reset soeed and direction
            
            ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
             ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;           
            
        }
        
        //ball stay in canvas
        if(ballYPos > height || ballYPos <0 ){
            ballYSpeed *=-1;
        }
        
        //computer get a point 
        
        if(ballXPos < playerOneXPos - PLAYER_WIDTH){
            scoreP2++;
            gameStarted = false;
            
        }
        
        //you get a point
        
        if(ballXPos > playerTwoXPos + PLAYER_WIDTH){
            scoreP1++;
            gameStarted = false;
            
        }
        
        //increase the ball speed
        
        if(((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||((ballXPos < playerOneXPos + PLAYER_WIDTH)
                &&  ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)){
            ballYSpeed +=1*Math.signum(ballYSpeed);
            ballXSpeed +=1*Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }
        
        // draw score
        gc.fillText(scoreP1+ "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
