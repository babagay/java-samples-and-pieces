package JavaCore.Module05FX;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import static io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe.subscribe;

/**
 * [Задание]
 * https://docs.google.com/document/d/16KrXuh3ludpjIDj5wdHBCL3CNUOUCAXrvF1WoETYkew/edit
 */
public class Snowman extends Application
{
    final int MIN_CIRCLE_RADIUS = 50;
    final int MAX_CIRCLE_RADIUS = 120;
    final int CIRCLES_COUNT = 2;

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
     * Массив промежуточного хранения кругов тела снеговика
     */
    ObservableList<Node> list;

    /**
     * Массив кругов глаз и носа
     */
    ObservableList<Node> helperCircleList;

    /**
     * Кнопка отрисовки кругов
     */
    Button drawSnowmanButton;

    Button paintCirclesButton;

    Button gradientCirclesButton;

    TextField circlesCountField;

    TextField minCircleRadiusField;

    TextField maxCircleRadiusField;

    Observable<ActionEvent> observableDraw;

    private ConnectableObservable<ActionEvent> actionEventDrawBtnConnectableObservable;

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setAlwaysOnTop( true );

        list = FXCollections.observableArrayList();

        rootNode = new VBox();

        groupCircles = new Group();

        // Контейнер контролов
        Parent controlPane = getControls();

        makeFlowFromDrawBtnAction();

        subscribeOnDrawBtnFlow();

        // subscribeOnDrawBtnFlow2(); // подписать второго наблюдателя сразу после первого

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
        grid.add( drawSnowmanButton, 0, 0 );

        paintCirclesButton = new Button( "Paint" );
        paintCirclesButton.setVisible( false );
        paintCirclesButton.getStyleClass().add( "button-paint" );
        grid.add( paintCirclesButton, 0, 1 );

        gradientCirclesButton = new Button( "Gradient" );
        gradientCirclesButton.setVisible( false );
        gradientCirclesButton.getStyleClass().add( "button-gradient" );
        grid.add( gradientCirclesButton, 0, 2 );

        Label circlesCountLabel = new Label( "Circles Count:" );
        grid.add( circlesCountLabel, 1, 1 );

        circlesCountField = new TextField();
        circlesCountField.textProperty().setValue( CIRCLES_COUNT + "" );
        grid.add( circlesCountField, 1, 2 );

        Label minCircleRadiusLabel = new Label( "Min Circle Radius:" );
        grid.add( minCircleRadiusLabel, 2, 1 );

        minCircleRadiusField = new TextField();
        minCircleRadiusField.textProperty().setValue( MIN_CIRCLE_RADIUS + "" );
        grid.add( minCircleRadiusField, 2, 2 );

        Label maxCircleRadiusLabel = new Label( "Max Circle Radius:" );
        grid.add( maxCircleRadiusLabel, 3, 1 );

        maxCircleRadiusField = new TextField();
        maxCircleRadiusField.textProperty().setValue( MAX_CIRCLE_RADIUS + "" );
        grid.add( maxCircleRadiusField, 3, 2 );

        return grid;
    }

    /**
     * Генератор глаз и носа
     */
    private void createHelperCircles(final HashMap<String, Integer> params)
    {
        helperCircleList = FXCollections.observableArrayList();

        Circle topCircle = (Circle) list.get( 0 );

        double X = topCircle.getCenterX();
        double Y = topCircle.getCenterY();
        double R = topCircle.getRadius();

        double minR = R / 100 * 10;
        double maxR = R / 100 * 20;

        Random random = new Random();

        double eyeFactor = random.nextInt( (int) ((maxR - minR) + 1.) ) + minR;
        double eyeFactorPositive = eyeFactor;
        double extraShift = eyeFactor * 0.7;
        double noseShiftFactor = 3;
        double eyeShiftFactor = 2;

        R = eyeFactor;

        if ( random.nextBoolean() )
        {
            eyeFactor = -eyeFactor;
        }

        Circle leftEye = new Circle( X + eyeFactorPositive * eyeShiftFactor, Y - eyeFactor - extraShift, R );
        leftEye.getStyleClass().add( "circle-eye-left" );
        leftEye.setFill( Color.AZURE );
        leftEye.setStroke( Color.web( "0xeb6f1b", 1.0 ) );
        leftEye.setStyle( "-fx-border-width: 2px" );

        eyeFactor = random.nextInt( (int) ((maxR - minR) + 1.) ) + minR;

        if ( random.nextBoolean() )
        {
            eyeFactor = -eyeFactor;
            R *= 1.2;
        }
        else
        {
            R *= 0.8;
        }

        Circle rightEye = new Circle( X - eyeFactorPositive * eyeShiftFactor, Y - eyeFactor - extraShift, R );
        rightEye.getStyleClass().add( "circle-eye-right" );
        rightEye.setFill( Color.AZURE );
        rightEye.setStroke( Color.web( "0xd000d9", 1.0 ) );
        rightEye.setStyle( "-fx-border-width: 2px" );

        R *= 0.9;

        Circle nose = new Circle( X - eyeFactor * eyeShiftFactor, Y + eyeFactorPositive * noseShiftFactor, R );
        nose.setFill( Color.BLACK );
        nose.setStroke( Color.web( "0xd000d9", 0.5 ) );
        nose.setStyle( "-fx-border-width: 2px" );

        helperCircleList.add( leftEye );
        helperCircleList.add( rightEye );
        helperCircleList.add( nose );
    }

    private void makeFlowFromDrawBtnAction()
    {
        observableDraw = Observable.create(
                emitter -> drawSnowmanButton.setOnAction(
                        // ловим клик и отправляем в поток
                        event -> emitter.onNext(event)
                )
        );

// OK
//        observableDraw = Observable.fromPublisher(o -> drawSnowmanButton.setOnAction(
//                event -> o.onNext(event)
//        ));

        replayDrawBtnFlow();
    }

    private void replayDrawBtnFlow()
    {
        actionEventDrawBtnConnectableObservable = observableDraw.replay(1);

        // begin emitting items to subscribers. Мы можем сначала подписать получателей данных, а потом отдать команду connect()
        actionEventDrawBtnConnectableObservable.connect();
    }

    private void subscribeOnDrawBtnFlow()
    {
        actionEventDrawBtnConnectableObservable.subscribe(
                event -> drawBtnActionHandler(event)
        );
    }

    private void subscribeOnDrawBtnFlow2()
    {
        actionEventDrawBtnConnectableObservable.subscribe(
                event -> System.out.println(event)
        );
    }

    /**
     * Привязка обработчика клика к Draw-кнопке обычным способом.
     * Для реализации того же самого через Rx2 использовны методы:
     *      makeFlowFromDrawBtnAction()
     *      subscribeOnDrawBtnFlow()
     * Метод replayDrawBtnFlow() дополнительный и нужен для возможности
     * множественной подписки на один поток. Например, добавлен второй подписчик:
     *      subscribeOnDrawBtnFlow2()
     */
    private void bindActionToDrawBtn()
    {
        drawSnowmanButton.setOnAction( (ActionEvent event) -> drawBtnActionHandler(event));
    }

    private void drawBtnActionHandler(ActionEvent event)
    {
        final HashMap<String, Integer> params = new HashMap();
        params.put( "circlesCount", CIRCLES_COUNT );
        params.put( "minCircleRadius", MIN_CIRCLE_RADIUS );
        params.put( "maxCircleRadius", MAX_CIRCLE_RADIUS );

        fetchDetailsFromFieldsAndSetParams( params );

        createCircles( params );

        createHelperCircles( params );

        // Добавляем круги в группу
        groupCircles.getChildren().clear();
        groupCircles.getChildren().addAll( list );
        groupCircles.getChildren().addAll( helperCircleList );

        paintCirclesButton.setVisible( true );
        gradientCirclesButton.setVisible( true );
        bindActionToPaintBtn();
        bindActionToGradientBtn();
    }

    private void fetchDetailsFromFieldsAndSetParams(final HashMap<String, Integer> params)
    {
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
            e.printStackTrace();
        }
    }

    private void createCircles(final HashMap<String, Integer> params)
    {
        // Массив кругов
        list = FXCollections.observableArrayList();

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

            System.out.println( circle );
            list.add( circle );
        }
    }

    private void bindActionToPaintBtn()
    {
        paintCirclesButton.setOnAction( (ActionEvent event) -> {

            // можно подписаться позже, тогда подписчик получит все накопленные события
            // Но, если ограничить размер буфера, напр, до 1 в replayDrawBtnFlow(), в replay(),
            // подписчик получит одно событие
            subscribeOnDrawBtnFlow2();

            for ( Node circle :
                    list )
            {
                Circle item = (Circle) circle;
                item.setFill( Color.BISQUE );
            }
        } );
    }

    // [!] вычислять градинт можно пропорционально радиусу каждого круга
    private void bindActionToGradientBtn()
    {
        gradientCirclesButton.setOnAction( (ActionEvent event) -> {

            double radius;
            double centerX;
            double centerY;

            double startX;
            double startY;
            double endX;
            double endY;
            boolean proportional = false;
            CycleMethod cycleMethod = CycleMethod.NO_CYCLE;
            Stop[] stops;

            int greyStart = 0;
            int greyEnd = 255;

            int greyLevelStart;
            int greyLevelEnd;

            int deltaGrayLevels = greyEnd / list.size();
            int previousLevel = 0;

            int counter = 1;

            LinearGradient lg;

            ObservableList<Node> listReversed = FXCollections.observableArrayList( list );
            Collections.reverse( listReversed );

            for ( Node item :
                    listReversed )
            {
                Circle circle = (Circle) item;

                radius = circle.getRadius();
                centerX = circle.getCenterX();
                centerY = circle.getCenterY();

                startX = endX = centerX;
                startY = centerY + radius;
                endY = centerY - radius;

                if ( counter == 1 )
                {
                    greyLevelStart = greyStart;
                    greyLevelEnd = deltaGrayLevels;
                }
                else if ( counter == list.size() )
                {
                    greyLevelStart = previousLevel;
                    greyLevelEnd = greyEnd;
                }
                else
                {
                    greyLevelStart = previousLevel;
                    greyLevelEnd = previousLevel + deltaGrayLevels;
                }

                previousLevel += deltaGrayLevels;
                counter++;

                stops = new Stop[]{new Stop( 0, Color.grayRgb( greyLevelStart, 1.0 ) ), new Stop( 1, Color.grayRgb( greyLevelEnd, 1 ) )};

                lg = new LinearGradient( startX, startY, endX, endY, proportional, cycleMethod, stops );

                circle.setFill( lg );
            }
        } );
    }

    private void compileScene(Stage stage)
    {
        Scene sceneGraphic = new Scene( rootNode, 600, 600 );

        rootNode.getStyleClass().add( "root-node" );

        try {
            sceneGraphic.getStylesheets().add( this.getClass().getResource( "style.css" ).toExternalForm() );
        } catch (NullPointerException e) {
            System.out.println("No file style.css");
        }

        stage.setScene( sceneGraphic );

        stage.setTitle( "Snowman" );

        stage.show();
    }

}
