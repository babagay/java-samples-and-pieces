package JavaCore.Module05FX;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

import static javafx.scene.paint.Color.*;

//https://www.tutorialspoint.com/javafx/javafx_application.htm
//https://dzone.com/articles/bye-bye-javafx-scene-builder
public class Snowman extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    ObservableList<Node> list;
    VBox rootNode;

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setAlwaysOnTop(true);

        list = FXCollections.observableArrayList();

        rootNode = new VBox();



        HBox hbox = new HBox();
        hbox.setSpacing(20);

        Button drawSnowmanButton = new Button("Draw");
        drawSnowmanButton.getStyleClass().add("button");
        drawSnowmanButton.setLayoutX(500);
        drawSnowmanButton.setLayoutY(100);

        drawSnowmanButton.setOnAction((ActionEvent event) -> {


//            list = null;
            list = FXCollections.observableArrayList();
//            rootNode.getChildren().removeAll();

            for (int i = 1; i < 3; i++) {

                Random random = new Random();

                int r = 50 +  random.nextInt() * 600;
                int X = 100 + random.nextInt() * 200;
                int Y = 150 + random.nextInt() * 400;

                Circle circle = new Circle(X, Y, r);
                circle.getStyleClass().add("circle");
                circle.setFill(  Color.AZURE );
                circle.setStroke(Color.web("0x0E82FF",1.0));
                circle.setStyle("-fx-border-width: 5px");



                list.add(circle);
            }
            list.add(hbox);
            rootNode.getChildren().clear();
            rootNode.getChildren().addAll(list);
        });




        hbox.getChildren().addAll(drawSnowmanButton);




        list.add(hbox);






        rootNode.getChildren().addAll(list);


        Scene sceneGraphic = new Scene(rootNode,600,600);

        rootNode.getStyleClass().add("root-node");

        sceneGraphic.getStylesheets().add(this.getClass().getResource( "./style.css").toExternalForm() );


        primaryStage.setScene(sceneGraphic);


        primaryStage.setTitle("Snowman");

        primaryStage.show();

    }
}
