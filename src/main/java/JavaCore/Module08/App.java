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
 *
 *   // todo
 // [!] сначала все зависло, потом пошло само
 // [!] как сделать, чтобы поток освобождал ресурсы?
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
    
    Map<Integer,String> directionMap;
    
    public static void main(String[] args)
    {
        launch( args );
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        random = new Random();
        
        initDirectionMap();
    
         threadPool = Executors.newFixedThreadPool( 4 );
        
//        rootNode = new FlowPane( );
        rootNode = new Pane( );

        squaresGroup = new Group();

        initPositions();

        squaresList = FXCollections.observableArrayList();

        rootNode.getChildren().add( squaresGroup );

        compileScene( primaryStage );

        createSquares();


        environment = new Environment();
        environment.init();
    }
    
    private void initDirectionMap(){
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
    }

    private void compileScene(Stage stage)
    {
        Scene scene = new Scene( rootNode, 1200, 800 );

        stage.setScene( scene );

        stage.setTitle( "Auto Squares" );

        stage.show();
    
        stage.setOnCloseRequest( event -> threadPool.shutdownNow() );
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
                                    double width = getRandomNumber() + 10;
                                    double height = getRandomNumber();
                                    double x = Double.parseDouble( startPosition.get( t ).split( "/" )[0] );
                                    double y = Double.parseDouble( startPosition.get( t ).split( "/" )[1] );

                                    Rectangle figure = new Rectangle( x, y, width, height );
                                    figure.setFill( Color.web( getRandomColor(), 0.5 ) );
                                    figure.setStroke( Color.web( getRandomColor() ) );

                                    // test
                                    figure.setOnContextMenuRequested( event -> {
                                        System.out.println( figure.getX() + " " + figure.getY() );
                                        double _x = figure.getX();
                                        figure.setX( _x + 1 );
                                    } );

                                    addFigure( figure );
    
                                    actTheFigure( figure );
                                    
                                    
                                    
                                    

                                    //Path path = generateStraightPath(determinePathOpacity(),figure);
                                    //PathTransition transition = generatePathTransition(figure, path);
                                    //transition.play();
                                    //// transition.notify();

                                    // todo теперь надо сделать, чтобы в крайней точке пути генерился новый путь
                                    // Как отловить момент, когда фигура завершит первый шаг анимации? Хочу знать.
                                    // МОжно ожидать 2 сек (время анимации)
                                    // Создать процесс
                                    // Фьючерс
                                    /*
                                    ExecutorService threadPool = Executors.newFixedThreadPool( 4 );
                                    FutureTask<String> futureTask = new FutureTask<>( () -> {
                                        // Thread.sleep( 2000 );
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
                                    */


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
    
    private String blameOrDiamonds(){
        int t = getRandomInt( 0,100 );
        if ( t%2 == 0 )
            return "blame";
        return "diamonds";
    }
    
    /*
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
*/
    synchronized private void addFigure(Node figure)
    {
        squaresGroup.getChildren().add( figure );
    }
    
    String getRandomDirection(){
        int direction = getRandomInt( 0,3 );
        return directionMap.get( direction );
    }
    
    Map<String, Integer> generateVector(){
        
        int deltaX = 0;
        int deltaY = 0;
        int dX = 0;
        int dY = 0;
        int hop = getRandomInt( 1,200 );
        int direction = 0;
        String moveTo = getRandomDirection();
        
        switch ( moveTo ){
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
        
        Map<String,Integer> delta = new HashMap<>( 10 );
        
        delta.put( "x", deltaX );
        delta.put( "y", deltaY );
        delta.put( "dX", dX );
        delta.put( "dY", dY );
        delta.put( "hopValue", hop );
        delta.put( "direction", direction );
        
        return delta;
    }
    
    /**
     * итерация движения фигуры
     */
    void moveFigure (Rectangle figure) throws InterruptedException
    {
        Map<String, Integer> vector = generateVector();
        
        // Центр фигуры
        double fX = figure.getX();
        double fY = figure.getY();
        
        int pause = getRandomInt( 10, 50 );
        
        for ( int i = 0; i < vector.get( "hopValue" ); i++ ) {
            
            if ( environment.couldIGoThere( figure, vector ) == false ) break;
            
            // todo сообщить среде о намерении занять новый массив и освободить некую площадь
            // [!] не обязательно через подписку, можно через вызов потокобезопасного метода
            // [!] или через паттерн синхронизации
            // [!] как вариант, можно сообщать среде о своем местоположении
            //     и быть подписанным на ее сообщения
            //     Напр, она видит, что фигура пытается занять оккупированную зону,
            //     тогда среда должна кинуть мессагу: Фигура, стоп!
            //     Но как она узнает о намерении - через интервальный мониторинг или через подписку.
            
            // сдвинуть фигуру, изменив положение ее центра
            figure.setX( fX + vector.get( "dX" ) );
            figure.setY( fY + vector.get( "dY" ) );
            
            fX = figure.getX();
            fY = figure.getY();
            
            // сделать короткую паузу
            Thread.sleep( pause );
        }
        
        // сделать длинную паузу
        Thread.sleep( pause * 10 );
    }
    
   
    private void actTheFigure (Rectangle figure)
    {
        Thread t = new Thread( () -> {
            //                while(true) {
            environment.tickImpulse.subscribe(
                    v -> {
                        //System.out.println( "Received tick: " + v );
                    
                        moveFigure( figure );
                    },
                    e -> System.out.println( "Error: " + e ),
                    () -> System.out.println( "Completed" )
                    );
//                }
        } );
    
        threadPool.submit( t );
    }
    
    class Environment {
    
        // todo потокобезопасность
        int[][] movingZone;
        
        Observable<Long> tickImpulse;
    
        void init ()
        {
            movingZone = new int[1200][800];
            tickImpulse = Observable.interval( 100, TimeUnit.MILLISECONDS );
        }
        
        boolean couldIGoThere (Rectangle figure, Map<String,Integer> vector)
        {
            boolean allow = true;
    
            // координаты левого верхнего угла квадрата
            double fX = figure.getX();
            double fY = figure.getY();
        
            switch ( vector.get( "direction" ) ){
                case 0:  // North
                    if ( (fY) <= 1 ) return false;
                    break;
                case 1: // East
                    if ( (fX + figure.getWidth()) >= 1200 ) return false;
                    break;
                case 2: // South
                    if ( (fY + figure.getHeight()) >= 800 ) return false;
                    break;
                case 3: // West
                    if ( (fX) <= 1 ) return false;
                    break;
            }
            
            // todo
            // взять направление
            
            // вычислить отрезок
            
            // проверить, свобоен ли он
            
            if ( true ){
                // если да, заполнить часть movingZone единицами;
                // вычислить освобождаемый отрезок
                // и заполнить часть movingZone нулями
            } else {
                allow = false;
            }
            
            return allow;
        }
    }

    private int getRandomInt(int from, int to){
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
