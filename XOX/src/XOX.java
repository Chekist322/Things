import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class XOX extends Application{

    Button button;

    static public void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PISOS");
        button = new Button();
        button.setText("BIG_PISOS");

        StackPane lay = new StackPane();
        lay.getChildren().add(button);

        Scene scene = new Scene(lay, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}