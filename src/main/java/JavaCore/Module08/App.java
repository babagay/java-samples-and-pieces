package JavaCore.Module08;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Random;

public class App extends Application
{
    VBox rootNode;
    Group group;
    ObservableList<Node> list;

    public static void main(String[] args)
    {
            launch( args );
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        rootNode = new VBox();

        group = new Group();
        group.minHeight( 500 );
        group.minWidth( 700 );

        list = FXCollections.observableArrayList();

        rootNode.getChildren().add( group );

        compileScene( primaryStage );
    }

    private void compileScene(Stage stage)
    {
        Scene scene = new Scene( rootNode, 1200, 800 );

        stage.setScene( scene );



        final HashMap<String, Integer> params = new HashMap<>();
        params.put( "circlesCount", 12 );
        params.put( "minCircleRadius", 12 );
        params.put( "maxCircleRadius", 45 );
        createCircles(params);

        createSquares();

        stage.setTitle( "Auto Squares" );

        stage.show();


    }

    private void createSquares(){

        double x = 200.;
        double y = 300.;
        double width = 500.;
        double height = 20.;

        for ( int i = 1; i <= 4; i++ )
        {
            Random random = new Random();
            width = getRandomNumber();
            height = getRandomNumber();
            x = getRandomNumber();
            y = getRandomNumber();

            Rectangle figure = new Rectangle(x,y,width,height);
            figure.setFill( Color.web( getRandomColor(), 0.5 ));
            figure.setStroke( Color.BROWN );
            figure.xProperty().setValue ( 225 );

            list.add( figure );
        }



        group.getChildren().addAll( list );
    }

    private double getRandomNumber()
    {
        Random random = new Random();
        return random.nextInt(150 - 105) + 125.;
    }

    private String getRandomColor()
    {
        String s = "";
        for ( int i = 0; i < 3; i++ )
        {
            Random random = new Random();
            int d = random.nextInt( 98 - 10 ) + 10;
            s += d;
        }
        return s;
    }

    private void createCircles(final HashMap<String, Integer> params)
    {

        int radius, X, Y;
        int topCircleCenterX = 0;
        int topCircleCenterY = 0;

        for ( int i = 1; i <= params.get( "circlesCount" ); i++ )
        {
            Random random = new Random();

            int min = Integer.valueOf( params.get( "minCircleRadius" ) );
            int max = Integer.valueOf( params.get( "maxCircleRadius" ) );

            if ( i == 1 )
            {
                radius = params.get( "minCircleRadius" );
            }
            else if ( i == params.get( "circlesCount" ) )
            {
                radius = params.get( "maxCircleRadius" );
            }
            else
            {
                radius = random.nextInt( (params.get( "maxCircleRadius" ) - params.get( "minCircleRadius" )) + 1 ) + params.get( "minCircleRadius" );
            }

            if ( i == 1 )
            {
                X = random.nextInt( (params.get( "maxCircleRadius" ) - params.get( "minCircleRadius" )) + 1 ) + params.get( "minCircleRadius" );
                Y = random.nextInt( (params.get( "maxCircleRadius" ) - params.get( "minCircleRadius" )) + 1 ) + params.get( "minCircleRadius" );

                topCircleCenterX = X;
            }
            else
            {
                X = topCircleCenterX;
                Y = topCircleCenterY + radius;
            }

            topCircleCenterY = Y + radius;

            Circle circle = new Circle( X, Y, radius );
            circle.getStyleClass().add( "circle" );
            circle.setFill( Color.AZURE );
            circle.setStroke( Color.web( "0x0E82FF", 1.0 ) );
            circle.setStyle( "-fx-border-width: 5px" );

            list.add( circle );
        }
    }

}
