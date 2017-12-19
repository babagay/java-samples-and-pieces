package JavaCore.Module05FX;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.Observable;

import static javafx.scene.paint.Color.*;

//https://www.tutorialspoint.com/javafx/javafx_application.htm
public class Snowman extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

//        Node node = new Node();

        primaryStage.setWidth(600.0);
        primaryStage.setHeight(600.0);

        primaryStage.setAlwaysOnTop(true);

        // Scene basicScene = primaryStage.getScene();

        Pane rootNode = new Pane();
        //rootNode.setStyle("-fx-background-color: #ccc");

        Scene sceneGraphic = new Scene(rootNode);
        Stop[] stops = new Stop[] { new Stop(0, ROSYBROWN), new Stop(1, RED)};
        LinearGradient lg2 = new LinearGradient(125, 0, 225, 0, false, CycleMethod.NO_CYCLE, stops);


        sceneGraphic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                 event.getSource();
//                circle.relocate(20, 20);
            }
        });

        ObservableList<Node> list = FXCollections.observableArrayList();

        for (int i = 1; i < 3; i++) {

            int r = i * 10;

            Circle circle = new Circle(r+50, r+(i*20), r);
            // circle.setStyle("-fx-border: red;"); // todo
            // circle.setFill(lg2); // OK



            list.add(circle);
        }

        rootNode.getChildren().addAll(list);
        rootNode.applyCss();
        rootNode.layout();


        Parent region = new Region();
        Scene sceneControls = new Scene(region);

        Button button = new Button("Hello");
        //region.getChildren();

        primaryStage.setScene(sceneGraphic);
        //primaryStage.setScene(sceneControls);


        primaryStage.setTitle("Snowman");

        primaryStage.show();

    }
}
