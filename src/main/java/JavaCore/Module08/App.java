package JavaCore.Module08;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Запустить 4 срэда
 * каждый поток:
 *  - вычисляет занимаемые координаты (площадь)
 *  - выбирает вектор
 *  - выбирает размер скачка
 *  - сообщает о перемещении (какой отрезок будет занят на следующем шаге)
 *  - перемещается
 *  - меняет массив занимаемых собой координат
 *  - Другие оьъекты сообщают о своем желании занять определенные координаты.
 *          если (после того, как сформирован отрезок, на который мы хотим переместиться) приходит сообщение что другой объект собирается занять одну из точек отрезка,
 *          мы уступаем ему, т.е. запускаем заново процесс выбора вектора.
 *  - поток может спрашивать и сам: занимает ли кто-то желаемый отрезок
 *
 *  Сделать сначала, чтобы квадраты просто ездили
 *  Учитывать границы формы при выборе вектора
 *
 *  https://stackoverflow.com/questions/31856158/move-objects-on-screen-in-javafx
 */
public class App extends Application //implements EventHandler
{
    Pane rootNode;
    Group squaresGroup;
    ObservableList<Node> squaresList;
    List<String> startPosition;
    Random random;

    public static void main(String[] args)
    {
        launch( args );
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        random = new Random();

//        rootNode = new FlowPane( );
        rootNode = new Pane( );

        squaresGroup = new Group();

        initPositions();

        squaresList = FXCollections.observableArrayList();

        rootNode.getChildren().add( squaresGroup );

        compileScene( primaryStage );

        createSquares();
    }

    private void initPositions()
    {
        startPosition = new ArrayList<>();

        startPosition.add( "70/300" );
        startPosition.add( "700/600" );
        startPosition.add( "1000/500" );
        startPosition.add( "500/30" );
    }

    private void compileScene(Stage stage)
    {
        Scene scene = new Scene( rootNode, 1200, 800 );

        stage.setScene( scene );

        stage.setTitle( "Auto Squares" );

        stage.show();
    }

    private void createSquares()
    {


        for ( int i = 0; i < 4; i++ )
        {
            int t = i;

            Service<Void> service = new Service<Void>()
            {
                @Override
                protected Task<Void> createTask()
                {
                    return new Task<Void>()
                    {
                        @Override
                        protected Void call() throws Exception
                        {
                            //Background work
                            final CountDownLatch latch = new CountDownLatch( 1 );
                            Platform.runLater( () -> {
                                try
                                {
                                    //FX Stuff done here
                                    double width = getRandomNumber() + 10;
                                    double height = getRandomNumber();
                                    double x = Double.parseDouble( startPosition.get( t ).split( "/" )[0] );
                                    double y = Double.parseDouble( startPosition.get( t ).split( "/" )[1] );

                                    Rectangle figure = new Rectangle( x, y, width, height );
                                    figure.setFill( Color.web( getRandomColor(), 0.5 ) );
                                    figure.setStroke( Color.web( getRandomColor() ) );

                                    figure.setOnContextMenuRequested( event -> {
                                        System.out.println( figure.getX() + " " + figure.getY() );
                                        double _x = figure.getX();
                                        figure.setX( _x + 1 );
                                    } );

                                    addFigure( figure );

                                }
                                finally
                                {
                                    latch.countDown();
                                }
                            } );
                            latch.await();
                            //Keep with the background work
                            return null;
                        }
                    };
                }
            };
            service.start();
        }


    }

    synchronized private void addFigure(Node figure)
    {
        squaresGroup.getChildren().add( figure );
    }

    private double getRandomNumber()
    {
        return random.nextInt( 120 - 80 ) + 80.;
    }

    private String getRandomColor()
    {
        String s = "";
        for ( int i = 0; i < 3; i++ )
        {
            int d = random.nextInt( 98 - 10 ) + 10;
            s += d;
        }
        return s;
    }


//    @Override
//    public void handle(Event event)
//    {
//        System.out.println("dscsdc");
//        KeyEvent event2 = (KeyEvent) event;
//        if ( event2.getCode() == KeyCode.DOWN) {
//            System.out.println("DOWN");
//        }
//    }
//
//    @FXML
//    public void buttonPressed(KeyEvent e)
//    {
//        if(e.getCode().toString().equals("ENTER"))
//        {
//            //do something
//            System.out.println("Enter");
//        }
//    }
}
