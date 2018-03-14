package JavaCore.Module08;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
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
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * [known issues]
 * Размер фигур меняется, они могут даже схлопываться.
 * Иной раз фигура полностью замерзает. Похоже, отваливается поток, т.к. перестает обрабатываться клик на фигуре
 *          и пространство воспринимается другими фигурами как свободное.
 * Но бывает, что все фигуры останавливаются, хотя и продолжают реагировать на клик -
 *      может, не происходит освобождение зоны? Кажется, остаются артефакты в массиве.
 * Error: java.lang.ArrayIndexOutOfBoundsException: -1
 * Error: java.lang.ArrayIndexOutOfBoundsException: 1218
 * Координаты фигуры могут быть отрицательными
 *
 * Можно заблокировать ресайз окна
 *
 * https://github.com/ReactiveX/RxJava/issues/5561
 * https://praveer09.github.io/technology/2016/02/29/rxjava-part-3-multithreading/
 * https://stackoverflow.com/questions/31856158/move-objects-on-screen-in-javafx
 *
 * Platform.runLater( () -> System.out.println( "Выполнить действие в основном потоке прилоения" ) );
 *
 * todo
 * Сделать пул потоков
 * и в каждом потоке бесконечный цикл, в каждой итерации которого moveIteration().
 * Убрать tickImpulse
 * По нажатию на Х очищать пул
 * Убедиться, что количество потоков, которые создаются = 4
 */
public class App extends Application //implements EventHandler
{
    static final int AREA_WIDTH = 1200;
    static final int AREA_HEIGHT = 800;
    
    Pane rootNode;
    Group squaresGroup;
    ObservableList<Node> squaresList;
    List<String> startPosition;
    Random random;
    ExecutorService threadPool; // @deprecated
    Environment environment;
    
    private final static String DIRECTION_NORTH = "north";
    private final static String DIRECTION_EAST = "east";
    private final static String DIRECTION_SOUTH = "south";
    private final static String DIRECTION_WEST = "west";
    
    private final static int TO_THE_NORTH = 0;
    private final static int TO_THE_EAST = 1;
    private final static int TO_THE_SOUTH = 2;
    private final static int TO_THE_WEST = 3;
    
    public static final int FIGURES_COUNT = 4;
    public static final int NUMBER_OF_THREADS = 4;
    
    Map<Integer, String> directionMap;
    
    public static void main (String[] args)
    {
        launch( args );
    }
    
    @Override
    public void start (Stage primaryStage)
    {
        random = new Random();
        
        initDirectionMap();
        
        threadPool = Executors.newFixedThreadPool( NUMBER_OF_THREADS );
        
        rootNode = new Pane();
        
        squaresGroup = new Group();
        
        initPositions();
        
        squaresList = FXCollections.observableArrayList();
        
        rootNode.getChildren().add( squaresGroup );
        
        compileScene( primaryStage );
        
        createSquares( FIGURES_COUNT );
        
        environment = new Environment();
        environment.init();
    }
    
    private void initDirectionMap ()
    {
        directionMap = new HashMap<>( 4 );
        directionMap.put( TO_THE_NORTH, DIRECTION_NORTH );
        directionMap.put( TO_THE_EAST, DIRECTION_EAST );
        directionMap.put( TO_THE_SOUTH, DIRECTION_SOUTH );
        directionMap.put( TO_THE_WEST, DIRECTION_WEST );
    }
    
    private void initPositions ()
    {
        startPosition = new ArrayList<>();
        
        startPosition.add( "70/300" );
        startPosition.add( "700/600" );
        startPosition.add( "1000/500" );
        startPosition.add( "500/30" );
        startPosition.add( "100/30" );
        startPosition.add( "850/30" );
    }
    
    private void compileScene (Stage stage)
    {
        Scene scene = new Scene( rootNode, AREA_WIDTH, AREA_HEIGHT );
        
        stage.setScene( scene );
        
        stage.setTitle( "Auto Squares" );
        
        stage.show();
        
        stage.setOnCloseRequest( event -> threadPool.shutdownNow() );
    }
    
    private void createSquares (int count)
    {
        for ( int i = 0; i < count; i++ ) {
            int t = i;
            
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask ()
                {
                    return new Task<Void>() {
                        @Override
                        protected Void call () throws Exception
                        {
                            final CountDownLatch latch = new CountDownLatch( 1 );
                            
                            Platform.runLater( () -> {
                                try {
                                    createFigure( t );
                                }
                                finally {
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
    
    private void createFigure (int index)
    {
        double width = getRandomInt( 130, 220 );
        double height = getRandomInt( 120, 200 );
        double x = Double.parseDouble( startPosition.get( index ).split( "/" )[0] );
        double y = Double.parseDouble( startPosition.get( index ).split( "/" )[1] );
        
        MyRectangle figure = new MyRectangle( x, y, width, height, "Figure-" + index );
        figure.setFill( Color.web( getRandomColor(), 0.5 ) );
        figure.setStroke( Color.web( getRandomColor() ) );
        
        // get extra info
        figure.setOnContextMenuRequested( event -> System.out.println(
                figure.getName() + " [" + figure.getX() + ": " + figure.getY() + "]" ) );
        
        placeOnCanvas( figure );
        
        actTheFigure( figure );
    }
    
    private String blameOrDiamonds ()
    {
        int t = getRandomInt( 0, 100 );
        if ( t % 2 == 0 ) {
            return "blame";
        }
        return "diamonds";
    }
    
    synchronized private void placeOnCanvas (Node figure)
    {
        if ( figure != null ) { squaresGroup.getChildren().add( figure ); }
    }
    
    int getRandomDirection ()
    {
        int direction = getRandomInt( 0, 8 );
        
        if ( direction > 3 ) {
            if ( direction == 4 ) { direction = 0; }
            else if ( direction == 5 ) { direction = 1; }
            else if ( direction == 6 ) { direction = 2; }
            else if ( direction == 7 ) { direction = 3; }
            else if ( direction == 8 ) direction = 3;
        }
        
        return direction;
    }
    
    Map<String, Integer> generateVector (MyRectangle figure)
    {
        int dX = 0;
        int dY = 0;
        int hop = getRandomInt( 2, 200 );
        int direction = getRandomDirection();
        
        switch ( getRandomDirection() ) {
            case TO_THE_NORTH:
                dY = -1;
                break;
            case TO_THE_EAST:
                dX = 1;
                break;
            case TO_THE_SOUTH:
                dY = 1;
                break;
            case TO_THE_WEST:
                dX = -1;
                break;
        }
        
        Map<String, Integer> vector = new HashMap<>( 6 );
        
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
    void moveFigure (MyRectangle figure) throws Exception
    {
        generateVector( figure );
        
        if ( figure.vector == null ) { throw new Exception( "Vector is not set" ); }
        
        // Центр фигуры
        double fX = figure.getX();
        double fY = figure.getY();
        double x,y;
        
        int pause = getRandomInt( 10, 50 );
        
        boolean breakLoop = false;
        
        for ( int i = 0; i < figure.vector.get( "hopValue" ); i++ )
        {
            if ( environment.couldIGoThere( figure ) == false ) break;
            
            // сдвинуть фигуру, изменив положение ее левого верхнего угла
            x = fX + figure.vector.get( "dX" );
            if ( x < 0 ) x = 0;
            if ( x >= AREA_WIDTH ) x = AREA_WIDTH - 1;
            
            y = fY + figure.vector.get( "dY" );
            if ( y < 0 ) y = 0;
            if ( y >= AREA_HEIGHT ) y = AREA_HEIGHT - 1;
            
            switch ( figure.vector.get( "direction" ) ){
                case TO_THE_NORTH:
                    if ( y == 0 ) breakLoop = true;
                    break;
                case TO_THE_EAST:
                    if ( x == AREA_WIDTH - 1 ) breakLoop = true;
                    break;
                case TO_THE_SOUTH:
                    if ( y == AREA_HEIGHT - 1 ) breakLoop = true;
                    break;
                case TO_THE_WEST:
                    if ( x == 0 ) breakLoop = true;
                    break;
            }
            
            if ( breakLoop ) break;
            
            figure.setX( x );
            figure.setY( y );
            
            fX = figure.getX();
            fY = figure.getY();
            
            // сделать короткую паузу
            Thread.sleep( pause );
        }
        
        // сделать длинную паузу
        // Thread.sleep( 450 );
        Thread.sleep( pause * 10 );
    }
    
    private void actTheFigure (MyRectangle figure)
    {
        environment.tickImpulse
                .subscribeOn( Schedulers.computation() ) // создать отдельный поток
                .subscribe(
                        v -> moveFigure( figure ),
                        e -> System.out.println( "Error: " + e ),
                        () -> System.out.println( "Completed" )
                          );
    }
    
    class Environment {
        
        final static
        int tickPeriod = 600;
        
        volatile int[][] movingZone;
        
        Observable<Long> tickImpulse;
        
        void init ()
        {
            movingZone = new int[AREA_WIDTH][AREA_HEIGHT];
            tickImpulse = Observable.interval( tickPeriod, TimeUnit.MILLISECONDS );
        }
        
        synchronized boolean couldIGoThere (MyRectangle figure)
        {
            if ( doesBoundaryReached( figure ) ) return false;
            
            boolean allow = true;
            
            // Координаты отрезка, который хочет занять фигура. Первая строка массива - начало, вторая - конец
            int[][] bounds = getBoundsByDirection( figure );
            
            // Координаты отрезка, который фигура готова освободить
            int[][] boundsLeave = getBoundsLeaveByDirection( figure );
            
            if ( isTargetAreaFree( bounds ) ) {
                markAreasAsOccupied( bounds );
                markAreaAsReleased( boundsLeave );
            }
            else {
                allow = false;
            }
            
            return allow;
        }
        
        boolean doesBoundaryReached (MyRectangle figure)
        {
            boolean boundaryReached = false;
            
            // координаты левого верхнего угла квадрата
            double fX = figure.getX();
            double fY = figure.getY();
            
            switch ( figure.vector.get( "direction" ) ) {
                case TO_THE_NORTH:
                    if ( ( fY ) <= 1 ) boundaryReached = true;
                    break;
                case TO_THE_EAST:
                    if ( ( fX + figure.getWidth() ) >= AREA_WIDTH ) boundaryReached = true;
                    break;
                case TO_THE_SOUTH:
                    if ( ( fY + figure.getHeight() ) >= AREA_HEIGHT ) boundaryReached = true;
                    break;
                case TO_THE_WEST:
                    if ( ( fX ) <= 1 ) boundaryReached = true;
                    break;
            }
            
            return boundaryReached;
        }
        
        int[][] getBoundsByDirection (MyRectangle figure)
        {
            int[][] bounds = new int[2][2];
            
            // координаты левого верхнего угла квадрата
            double fX = figure.getX();
            double fY = figure.getY();
            
            switch ( figure.vector.get( "direction" ) ) {
                case TO_THE_NORTH:
                    bounds[0][0] = (int) fX;
                    bounds[0][1] = (int) fY - 1;
                    bounds[1][0] = (int) ( (int) fX + figure.getWidth() );
                    bounds[1][1] = (int) fY - 1;
                    break;
                case TO_THE_EAST:
                    bounds[0][0] = (int) ( (int) fX + figure.getWidth() ) + 1;
                    bounds[0][1] = (int) fY;
                    bounds[1][0] = (int) ( (int) fX + figure.getWidth() ) + 1;
                    bounds[1][1] = (int) ( (int) fY + figure.getHeight() );
                    break;
                case TO_THE_SOUTH:
                    bounds[0][0] = (int) fX;
                    bounds[0][1] = (int) ( (int) fY + figure.getHeight() ) + 1;
                    bounds[1][0] = (int) ( (int) fX + figure.getWidth() );
                    bounds[1][1] = (int) ( (int) fY + figure.getHeight() ) + 1;
                    break;
                case TO_THE_WEST:
                    bounds[0][0] = (int) fX - 1;
                    bounds[0][1] = (int) fY;
                    bounds[1][0] = (int) fX - 1;
                    bounds[1][1] = (int) ( (int) fY + figure.getHeight() );
                    break;
            }
            
            return bounds;
        }
        
        int[][] getBoundsLeaveByDirection (MyRectangle figure)
        {
            int[][] boundsLeave = new int[2][2];
            
            // координаты левого верхнего угла квадрата
            double fX = figure.getX();
            double fY = figure.getY();
            
            switch ( figure.vector.get( "direction" ) ) {
                case TO_THE_NORTH:
                    boundsLeave[0][0] = (int) fX;
                    boundsLeave[0][1] = (int) ( (int) fY + figure.getHeight() );
                    boundsLeave[1][0] = (int) ( (int) fX + figure.getWidth() );
                    boundsLeave[1][1] = (int) ( (int) fY + figure.getHeight() );
                    break;
                case TO_THE_EAST:
                    boundsLeave[0][0] = (int) fX;
                    boundsLeave[0][1] = (int) fY;
                    boundsLeave[1][0] = (int) fX;
                    boundsLeave[1][1] = (int) ( (int) fY + figure.getHeight() );
                    break;
                case TO_THE_SOUTH:
                    boundsLeave[0][0] = (int) fX;
                    boundsLeave[0][1] = (int) fY;
                    boundsLeave[1][0] = (int) ( (int) fX + figure.getWidth() );
                    boundsLeave[1][1] = (int) fY;
                    break;
                case TO_THE_WEST:
                    boundsLeave[0][0] = (int) ( (int) fX + figure.getWidth() );
                    boundsLeave[0][1] = (int) fY;
                    boundsLeave[1][0] = (int) ( (int) fX + figure.getWidth() );
                    boundsLeave[1][1] = (int) ( (int) fY + figure.getHeight() );
                    break;
            }
            
            return boundsLeave;
        }
        
        boolean isTargetAreaFree (int[][] bounds)
        {
            boolean free = true;
            
            int xStart = bounds[0][0];
            int yStart = bounds[0][1];
            int xEnd = bounds[1][0];
            int yEnd = bounds[1][1];
            
            if ( xStart >= AREA_WIDTH ) xStart = AREA_WIDTH - 1;
            if ( xEnd >= AREA_WIDTH ) xEnd = AREA_WIDTH - 1;
            if ( yStart >= AREA_HEIGHT ) yStart = AREA_HEIGHT - 1;
            if ( yEnd >= AREA_HEIGHT ) yEnd = AREA_HEIGHT - 1;
    
            if ( xStart < 0 ) xStart = 0;
            if ( xEnd < 0 ) xEnd = 0;
            if ( yStart < 0 ) yStart = 0;
            if ( yEnd < 0 ) yEnd = 0;
            
            int start, end;
    
    
            try {
                if ( xStart == xEnd ) {
                    // горизонтальное движение, вектор расположен вертикально
                    if ( yEnd > yStart ) {
                        start = yStart;
                        end = yEnd;
                    }
                    else {
                        start = yEnd;
                        end = yStart;
                    }
                    
                    if ( end >= AREA_HEIGHT ) end = AREA_HEIGHT;
                    
                    for ( int i = start; i <= end && i < AREA_HEIGHT; i++ ) {
                        if ( movingZone[xStart][i] == 1 ) {
                            free = false;
                            break;
                        }
                    }
                }
                else {
                    // вертикальное движение, вектор расположен горизонтально
                    if ( xEnd > xStart ) {
                        start = xStart;
                        end = xEnd;
                    }
                    else {
                        start = xEnd;
                        end = xStart;
                    }
                    
                    if ( end >= AREA_WIDTH ) end = AREA_WIDTH;
                    
                    for ( int i = start; i < end && i < AREA_WIDTH; i++ ) {
                        if ( movingZone[i][yStart] == 1 ) {
                            free = false;
                            break;
                        }
                    }
                }
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
    
    
            return free;
        }
        
        // занять часть пространства
        void markAreasAsOccupied (int[][] bounds)
        {
            fillMovingZonePart( bounds, 1 );
        }
        
        // освободить часть пространства
        void markAreaAsReleased (int[][] boundsLeave)
        {
            fillMovingZonePart( boundsLeave, 0 );
        }
        
        /**
         * заполнить часть movingZone нулями или единицами
         *
         * @param bounds
         * @param sign   0|1
         */
        synchronized void fillMovingZonePart (int[][] bounds, int sign)
        {
            int xStart = bounds[0][0];
            int yStart = bounds[0][1];
            int xEnd = bounds[1][0];
            int yEnd = bounds[1][1];
            
            int start, end;
            
            if ( xStart == xEnd ) {
                if ( yEnd > yStart ) {
                    start = yStart;
                    end = yEnd;
                }
                else {
                    start = yEnd;
                    end = yStart;
                }
                for ( int i = start; i <= end && i < AREA_HEIGHT; i++ ) {
                    movingZone[xStart][i] = sign;
                }
            }
            else {
                if ( xEnd > xStart ) {
                    start = xStart;
                    end = xEnd;
                }
                else {
                    start = xEnd;
                    end = xStart;
                }
                for ( int i = start; i <= end && i < AREA_WIDTH; i++ ) {
                    movingZone[i][yStart] = sign;
                }
            }
        }
    }
    
    private int getRandomInt (int from, int to)
    {
        return random.nextInt( to - from ) + from;
    }
    
    private String getRandomColor ()
    {
        String s = "";
        for ( int i = 0; i < 3; i++ ) {
            int d = random.nextInt( 98 - 10 ) + 10;
            s += d;
        }
        return s;
    }
    
    
}
