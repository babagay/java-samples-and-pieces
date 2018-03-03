package JavaCore.Module08;

import io.reactivex.Observable;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Запустить 4 срэда
 * каждый поток:
 * - вычисляет занимаемые координаты (площадь)
 * - выбирает вектор
 * - выбирает размер скачка
 * - сообщает о перемещении (какой отрезок будет занят на следующем шаге)
 * - перемещается
 * - меняет массив занимаемых собой координат
 * - Другие оьъекты сообщают о своем желании занять определенные координаты.
 * если (после того, как сформирован отрезок, на который мы хотим переместиться) приходит сообщение что другой объект собирается занять одну из точек отрезка,
 * мы уступаем ему, т.е. запускаем заново процесс выбора вектора.
 * - поток может спрашивать и сам: занимает ли кто-то желаемый отрезок
 * <p>
 * Сделать сначала, чтобы квадраты просто ездили
 * Учитывать границы формы при выборе вектора
 * <p>
 * https://stackoverflow.com/questions/31856158/move-objects-on-screen-in-javafx
 * <p>
 * [known issues]
 * Когда ресурсов не хватает, возникает исключение. Нужна потокобезопасность каого-то метода?
 * В какой-то момент фигура останавливается и перестает реагировтаь на клик - замерзает
 */
public class App extends Application //implements EventHandler
{
    Pane rootNode;
    Group squaresGroup;
    ObservableList<Node> squaresList;
    List<String> startPosition;
    Random random;
    ExecutorService threadPool;
    Environment environment;

    private final static String DIRECTION_NORTH = "north";
    private final static String DIRECTION_EAST = "east";
    private final static String DIRECTION_SOUTH = "south";
    private final static String DIRECTION_WEST = "west";

    Map<Integer, String> directionMap;

    public static void main(String[] args)
    {
        launch( args );
    }

    @Override
    public void start(Stage primaryStage)
    {
        random = new Random();

        initDirectionMap();

        threadPool = Executors.newFixedThreadPool( 6 );

        rootNode = new Pane();

        squaresGroup = new Group();

        initPositions();

        squaresList = FXCollections.observableArrayList();

        rootNode.getChildren().add( squaresGroup );

        compileScene( primaryStage );

        createSquares( 4 );

        environment = new Environment();
        environment.init();
    }

    private void initDirectionMap()
    {
        directionMap = new HashMap<>( 4 );
        directionMap.put( 0, DIRECTION_NORTH );
        directionMap.put( 1, DIRECTION_EAST );
        directionMap.put( 2, DIRECTION_SOUTH );
        directionMap.put( 3, DIRECTION_WEST );
    }

    private void initPositions()
    {
        startPosition = new ArrayList<>();

        startPosition.add( "70/300" );
        startPosition.add( "700/600" );
        startPosition.add( "1000/500" );
        startPosition.add( "500/30" );
        startPosition.add( "100/30" );
        startPosition.add( "850/30" );
    }

    private void compileScene(Stage stage)
    {
        Scene scene = new Scene( rootNode, 1200, 800 );

        stage.setScene( scene );

        stage.setTitle( "Auto Squares" );

        stage.show();

        stage.setOnCloseRequest( event -> threadPool.shutdownNow() );
    }

    private void createSquares(int count)
    {
        for ( int i = 0; i < count; i++ )
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
                                    createFigure( t );
                                }
                                finally
                                {
                                    latch.countDown();
                                }
                            } );
                            latch.await();
                            return null;
                        }
                    };
                }
            };
            service.start();
        }
    }

    private void createFigure(int index)
    {
        double width = getRandomNumber() + 10;
        double height = getRandomNumber();
        double x = Double.parseDouble( startPosition.get( index ).split( "/" )[0] );
        double y = Double.parseDouble( startPosition.get( index ).split( "/" )[1] );

        MyRectangle figure = new MyRectangle( x, y, width, height, "Figure-" + index );
        figure.setFill( Color.web( getRandomColor(), 0.5 ) );
        figure.setStroke( Color.web( getRandomColor() ) );

        // debug
        figure.setOnContextMenuRequested( event -> {
            System.out.println( figure.getName() + " [" + figure.getX() + ": " + figure.getY() + "]" );
            // double _x = figure.getX();
            // figure.setX( _x + 1 );
        } );

        addFigure( figure );

        actTheFigure( figure );
    }

    private String blameOrDiamonds()
    {
        int t = getRandomInt( 0, 100 );
        if ( t % 2 == 0 )
        {
            return "blame";
        }
        return "diamonds";
    }

    synchronized private void addFigure(Node figure)
    {
        squaresGroup.getChildren().add( figure );
    }

    String getRandomDirection()
    {
        int direction = getRandomInt( 0, 3 );
        return directionMap.get( direction );
    }

    Map<String, Integer> generateVector(MyRectangle figure)
    {
        int deltaX = 0;
        int deltaY = 0;
        int dX = 0;
        int dY = 0;
        int hop = getRandomInt( 1, 200 );
        int direction = 0;
        String moveTo = getRandomDirection();

        switch ( moveTo )
        {
            case DIRECTION_NORTH:
                deltaY = -hop;
                dY = -1;
                break;
            case DIRECTION_EAST:
                deltaX = hop;
                direction = 1;
                dX = 1;
                break;
            case DIRECTION_SOUTH:
                deltaY = hop;
                direction = 2;
                dY = 1;
                break;
            case DIRECTION_WEST:
                deltaX = -hop;
                direction = 3;
                dX = -1;
                break;
        }

        Map<String, Integer> vector = new HashMap<>( 10 );

        vector.put( "x", deltaX );
        vector.put( "y", deltaY );
        vector.put( "dX", dX );
        vector.put( "dY", dY );
        vector.put( "hopValue", hop );
        vector.put( "direction", direction );

        figure.setVector( vector );

        return vector;
    }

    /**
     * итерация движения фигуры
     */
    void moveFigure(MyRectangle figure) throws Exception
    {
        generateVector(figure);

        if ( figure.vector == null )
            throw new Exception("Vector is not set");

        // Центр фигуры
        double fX = figure.getX();
        double fY = figure.getY();

        int pause = getRandomInt( 10, 50 );

        for ( int i = 0; i < figure.vector.get( "hopValue" ); i++ )
        {
            if ( environment.couldIGoThere( figure ) == false ) break;

            // todo сообщить среде о намерении занять новый массив и освободить некую площадь
            // [!] не обязательно через подписку, можно через вызов потокобезопасного метода
            // [!] или через паттерн синхронизации
            // [!] как вариант, можно сообщать среде о своем местоположении
            //     и быть подписанным на ее сообщения
            //     Напр, она видит, что фигура пытается занять оккупированную зону,
            //     тогда среда должна кинуть мессагу: Фигура, стоп!
            //     Но как она узнает о намерении - через интервальный мониторинг или через подписку.

            // сдвинуть фигуру, изменив положение ее центра
            figure.setX( fX + figure.vector.get( "dX" ) );
            figure.setY( fY + figure.vector.get( "dY" ) );

            fX = figure.getX();
            fY = figure.getY();

            // сделать короткую паузу
            Thread.sleep( pause );
        }

        // сделать длинную паузу
        Thread.sleep( pause * 10 );
    }

    private void actTheFigure(MyRectangle figure)
    {
        Thread t = new Thread( () -> {
            // while(true) {
            environment.tickImpulse.subscribe(
                    v -> {
                        moveFigure( figure );
                    },
                    e -> System.out.println( "Error: " + e ),
                    () -> System.out.println( "Completed" )
            );
            // }
        } );

        threadPool.submit( t );
    }

    class Environment
    {
        int[][] movingZone;

        Observable<Long> tickImpulse;

        void init()
        {
            movingZone = new int[1200][800];
            tickImpulse = Observable.interval( 100, TimeUnit.MILLISECONDS );
        }

        synchronized boolean couldIGoThere(MyRectangle figure)
        {
            boolean allow = true;

            // Координаты отрезка, который хочет занять фигура. Первая строка - начало, вторая - конец
            int[][] bounds = new int[2][2];

            // Координаты отрезка, который фигура готова освободить.
            int[][] boundsLeave = new int[2][2];

            // координаты левого верхнего угла квадрата
            double fX = figure.getX();
            double fY = figure.getY();

            switch ( figure.vector.get( "direction" ) )
            {
                case 0:  // North
                    if ( (fY) <= 1 ) return false;
                    bounds[0][0] = (int) fX;
                    bounds[0][1] = (int) fY + 1;
                    bounds[1][0] = (int) ((int) fX + figure.getWidth());
                    bounds[1][1] = (int) fY + 1;
                    boundsLeave[0][0] = (int) fX;
                    boundsLeave[0][1] = (int) ((int) fY + figure.getHeight()) - 1;
                    boundsLeave[1][0] = (int) ((int) fX + figure.getWidth());
                    boundsLeave[1][1] = (int) ((int) fY + figure.getHeight()) - 1;
                    break;
                case 1: // East
                    if ( (fX + figure.getWidth()) >= 1200 ) return false;
                    // todo
                    // bounds
                    // boundsLeave
                    break;
                case 2: // South
                    if ( (fY + figure.getHeight()) >= 800 ) return false;
                    // todo
                    // bounds
                    // boundsLeave
                    break;
                case 3: // West
                    if ( (fX) <= 1 ) return false;
                    // todo
                    // bounds
                    // boundsLeave
                    break;
            }

            if ( isTargetAreaFree(bounds) )
            {
                occupyArea( bounds );
                releaseArea( boundsLeave );
            }
            else
            {
                allow = false;
            }

            return allow;
        }

        // todo
        // bound зависит от direction? Передать direction
        boolean isTargetAreaFree(int[][] bounds)
        {
            boolean free = true;

            //movingZone

            int bound = 0;

            for ( int i = 0; i < bound; i++ )
            {

            }

            return free;
        }

        // todo
        //  заполнить часть movingZone единицами
        void occupyArea(int[][] bounds)
        {
            //movingZone
        }

        // todo
        // заполнить часть movingZone нулями
        void releaseArea(int[][] boundsLeave)
        {
            //movingZone
        }
    }

    private int getRandomInt(int from, int to)
    {
        return random.nextInt( to - from ) + from;
    }

    // figure size
    private double getRandomNumber()
    {
        return random.nextInt( 200 - 130 ) + 130.;
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


}
