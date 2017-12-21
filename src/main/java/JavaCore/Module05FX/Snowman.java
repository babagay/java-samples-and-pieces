package JavaCore.Module05FX;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import scala.util.parsing.combinator.testing.Str;


import java.util.HashMap;
import java.util.Random;

//https://www.tutorialspoint.com/javafx/javafx_application.htm
//https://dzone.com/articles/bye-bye-javafx-scene-builder
public class Snowman extends Application
{

    public static void main(String[] args)
    {
        launch( args );
    }

    /**
     * Родительский узел
     */
    VBox rootNode;

    /**
     * Контейнер для кругов
     */
    Group groupCircles;

    /**
     * Массив промежуточного хранения кругов
     */
    ObservableList<Node> list;

    /**
     * Кнопка отрисовки кругов
     */
    Button drawSnowmanButton;

    TextField circlesCountField;
    TextField minCircleRadiusField;
    TextField maxCircleRadiusField;

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setAlwaysOnTop( true );

        list = FXCollections.observableArrayList();

        rootNode = new VBox();

        groupCircles = new Group();

        // Контейнер контролов
        Parent controlPane = getControls();

        bindActionToDrawBtn();

        rootNode.getChildren().add( controlPane );
        rootNode.getChildren().add( groupCircles );

        compileScene( primaryStage );
    }

    private Parent getControls()
    {
        GridPane grid = new GridPane();
        grid.setAlignment( Pos.CENTER );
        grid.setHgap( 10 );
        grid.setVgap( 10 );
        grid.setPadding( new Insets( 25, 25, 25, 25 ) );

        drawSnowmanButton = new Button( "Draw" );
        drawSnowmanButton.getStyleClass().add( "button" );
        drawSnowmanButton.setLayoutX( 500 );
        drawSnowmanButton.setLayoutY( 100 );

        // Кладем кнопку в контейнер управляющих элементов
        grid.add( drawSnowmanButton, 0, 1 );

        Label circlesCountLabel = new Label( "Circles Count:" );
        grid.add( circlesCountLabel, 1, 1 );

        circlesCountField = new TextField();
        grid.add( circlesCountField, 1, 2 );

        Label minCircleRadiusLabel = new Label( "Min Circle Radius:" );
        grid.add( minCircleRadiusLabel, 2, 1 );

        minCircleRadiusField = new TextField();
        grid.add( minCircleRadiusField, 2, 2 );

        Label maxCircleRadiusLabel = new Label( "Max Circle Radius:" );
        grid.add( maxCircleRadiusLabel, 3, 1 );

        maxCircleRadiusField = new TextField();
        grid.add( maxCircleRadiusField, 3, 2 );

        return grid;
    }


    //todo раположить окружности вертикально
    private void bindActionToDrawBtn()
    {
        TextField circlesCountField = this.circlesCountField;
        TextField minCircleRadiusField = this.minCircleRadiusField;
        TextField maxCircleRadiusField = this.maxCircleRadiusField;

        final HashMap<String, Integer> params = new HashMap();
        params.put( "circlesCount", 2 );
        params.put( "minCircleRadius", 200 );
        params.put( "maxCircleRadius", 250 );

        drawSnowmanButton.setOnAction( (ActionEvent event) -> {

            try
            {
                params.put( "circlesCount", Integer.valueOf(
                        String.valueOf( circlesCountField.getCharacters() )
                        )
                );

                params.put( "minCircleRadius", Integer.valueOf(
                        String.valueOf( minCircleRadiusField.getCharacters() )
                        )
                );

                params.put( "maxCircleRadius", Integer.valueOf(
                        String.valueOf( maxCircleRadiusField.getCharacters() )
                        )
                );
            }
            catch ( Exception e )
            {
            }


            int radius, X, Y;

            // Массив кругов
            list = FXCollections.observableArrayList();

            for ( int i = 1; i <= params.get( "circlesCount" ); i++ )
            {

                Random random = new Random();

                int min = 100;
                int max = 300;

                if ( i == 1 )
                {
                    radius = params.get( "minCircleRadius" );
                }
                else if ( i == 2 )
                {
                    radius = params.get( "maxCircleRadius" );
                }
                else
                {
                    radius = random.nextInt( (max - min) + 1 ) + min;
                }

                X = random.nextInt( (max - min) + 1 ) + min;
                Y = random.nextInt( (max - min) + 1 ) + min;

                Circle circle = new Circle( X, Y, radius );
                circle.getStyleClass().add( "circle" );
                circle.setFill( Color.AZURE );
                circle.setStroke( Color.web( "0x0E82FF", 1.0 ) );
                circle.setStyle( "-fx-border-width: 5px" );

                System.out.println( circle );
                list.add( circle );
            }

            // ДОбавляем круги в группу
            groupCircles.getChildren().clear();
            groupCircles.getChildren().addAll( list );
        } );
    }

    private void compileScene(Stage stage)
    {
        Scene sceneGraphic = new Scene( rootNode, 600, 600 );

        rootNode.getStyleClass().add( "root-node" );

        sceneGraphic.getStylesheets().add( this.getClass().getResource( "./style.css" ).toExternalForm() );

        stage.setScene( sceneGraphic );

        stage.setTitle( "Snowman" );

        stage.show();
    }

}
