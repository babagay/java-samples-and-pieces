package JavaCore.Module08;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

//        applyAnimation( squaresGroup );
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


                                    Path path = generateStraightPath(determinePathOpacity(),figure);
                                    PathTransition transition = generatePathTransition(figure, path);
                                    transition.play();
                                    // transition.notify();

                                    // todo теперь надо сделать, чтобы в крайней точке пути генерился новый путь
                                    // Как отловить момент, когда фигура завершит первый шаг анимации? Хочу знать.
                                    // Создать процесс
                                    // Фьючерс
                                    ExecutorService threadPool = Executors.newFixedThreadPool( 4 );
                                    FutureTask<String> futureTask = new FutureTask<>( () -> {
                                        Thread.sleep( 2000 );
                                        return "hello " + Thread.currentThread().getName();
                                    } );

                                    threadPool.execute( futureTask );

                                    try
                                    {
                                        System.out.println( futureTask.get() );
                                    }
                                    catch ( Exception e )
                                    {
                                        e.printStackTrace();
                                    }


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

    private PathTransition generatePathTransition(final Shape shape, final Path path)
    {
        double duration = (double) getRandomInt( 2,10 );
        double delay = (double) getRandomInt( 1,4 );

        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration( Duration.seconds(duration));
        pathTransition.setDelay(Duration.seconds(delay));
        pathTransition.setPath(path);
        pathTransition.setNode(shape);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount( Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        return pathTransition;
    }

    private Path generateStraightPath(double pathOpacity, Rectangle node)
    {
        Path path = new Path();

        int x = (int)node.getX();
        int y = (int)node.getY();

        double w = node.getWidth();
        double h = node.getHeight();

        int startX = (int) (x + w/2);
        int startY = (int) (y + h/2);

        int delta = getRandomInt( 0,800 );

        int endPointX = startX;
        int endPointY = startY;

        if ( blameOrDiamonds().equals( "blame" ) ) endPointX = startX + delta; else endPointY = startY + delta;

        path.getElements().add(new MoveTo(startX,startY));
        path.getElements().add(new QuadCurveTo(startX,startY,endPointX,endPointY));

        path.setOpacity(pathOpacity);

        return path;
    }

    private String blameOrDiamonds(){
        int t = getRandomInt( 0,100 );
        if ( t%2 == 0 )
            return "blame";
        return "diamonds";
    }

    private Path generateCurvyPath(final double pathOpacity)
    {
        int x = getRandomInt( 0,500 );
        int y = getRandomInt( 0,500 );

        final Path path = new Path();
        path.getElements().add(new MoveTo(x,y));
        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        path.setOpacity(pathOpacity);
        return path;
    }

    private double determinePathOpacity()
    {
        final Parameters params = getParameters();
        final List<String> parameters = params.getRaw();
        double pathOpacity = 0.0;
        if (!parameters.isEmpty())
        {
            try
            {
                pathOpacity = Double.valueOf(parameters.get(0));
            }
            catch (NumberFormatException nfe)
            {
                pathOpacity = 0.0;
            }
        }
        return pathOpacity;
    }

    private void applyAnimation(final Group group)
    {
        final Circle circle = new Circle(20, 20, 15);
        circle.setFill(Color.DARKRED);

        final Path path = generateCurvyPath(determinePathOpacity());
        group.getChildren().add(path);
        group.getChildren().add(circle);
        group.getChildren().add(new Circle(20, 20, 5));
        group.getChildren().add(new Circle(380, 240, 5));
        final PathTransition transition = generatePathTransition(circle, path);
        transition.play();
    }

    synchronized private void addFigure(Node figure)
    {
        squaresGroup.getChildren().add( figure );
    }

    private int getRandomInt(int from, int to){
        return random.nextInt( to - from ) + from;
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
