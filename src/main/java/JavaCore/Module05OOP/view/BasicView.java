package JavaCore.Module05OOP.view;

import JavaCore.Module05OOP.AppState;
import JavaCore.Module05OOP.PlayerMP3.Extra.Digital;
import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.state.StateManager;
import com.gluonhq.particle.view.View;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javax.inject.Inject;

@ParticleView(name = "basic", isDefault = true)
public class BasicView implements View {

    @Inject private StateManager stateManager;

    private final VBox controls = new VBox(15);
    private Button button;

    @Override
    public void init() {
        Label label = new Label("Hello JavaFX World!");

        button = new Button();
        button.setOnAction(e -> label.setText("Hello JavaFX Universe!"));

        Button playButton = new Button();
        playButton.setText( "play" );
        playButton.setOnAction( e ->
        {
            Digital player = (Digital) AppState.getInstance().get( "player" );
            player.playSong();
        } );

        Button stopButton = new Button();
        stopButton.setText( "stop" );
        stopButton.setOnAction( e ->
        {
            Digital player = (Digital) AppState.getInstance().get( "player" );
            player.stopSong();
        } );

        Button pauseButton = new Button();
        pauseButton.setText( "||" );
        pauseButton.setOnAction( e ->
        {
            Digital player = (Digital) AppState.getInstance().get( "player" );
            player.pauseSong();
        } );

        controls.getChildren().addAll(/*label, button,*/ playButton, stopButton, pauseButton);
        controls.setAlignment(Pos.CENTER);

        stateManager.setPersistenceMode(StateManager.PersistenceMode.USER);
        addUser(stateManager.getProperty("UserName").orElse("").toString());
    }

    @Override
    public Node getContent() {
        return controls;
    }

    public void addUser(String userName) {
        button.setText(userName.isEmpty() ? "Change the World!" : userName + ", change the World!");

        stateManager.setProperty("UserName", userName);
    }

}

