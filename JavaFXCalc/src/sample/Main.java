package sample;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;


public class Main extends Application {
    private enum Op { NOOP, ADD, SUBTRACT, MULTIPLY, DIVIDE }
    private Op curOp   = Op.NOOP;
    private Op stackOp = Op.NOOP;
    private DoubleProperty stackValue = new SimpleDoubleProperty();
    private DoubleProperty value = new SimpleDoubleProperty();


    @Override
    public void start(Stage primaryStage) throws Exception{
        File usersDB = new File("UsersDB.txt");
        File log = new File("Log.txt");
        FileRuler.NewFile(usersDB);
        FileRuler.NewFile(log);

        String user = null;
        GridPane root = new GridPane();
        Scene sceneCalc = new Scene(root, 320, 375);
        sceneCalc.getStylesheets().add(Main.class.getResource("CalcStyle.css").toExternalForm());
        TilePane btnRoot = new TilePane();
        primaryStage.setTitle("Calc (login screen)");
        TextField area = new TextField("0");
        area.setPrefWidth( sceneCalc.getWidth()-20);
        area.setAlignment(Pos.CENTER_RIGHT);
        area.setEditable(false);
        area.setFocusTraversable(false);
        area.textProperty().bind(value.asString());



        Button btn1 = new Button("1");
        btn1.setOnAction(event -> keyReflect(btn1));
        Button btn2 = new Button("2");
        btn2.setOnAction(event -> keyReflect(btn2));
        Button btn3 = new Button("3");
        btn3.setOnAction(event -> keyReflect(btn3));
        Button btn4 = new Button("4");
        btn4.setOnAction(event -> keyReflect(btn4));
        Button btn5 = new Button("5");
        btn5.setOnAction(event -> keyReflect(btn5));
        Button btn6 = new Button("6");
        btn6.setOnAction(event -> keyReflect(btn6));
        Button btn7 = new Button("7");
        btn7.setOnAction(event -> keyReflect(btn7));
        Button btn8 = new Button("8");
        btn8.setOnAction(event -> keyReflect(btn8));
        Button btn9 = new Button("9");
        btn9.setOnAction(event -> keyReflect(btn9));
        Button btn0 = new Button("0");
        btn0.setOnAction(event -> keyReflect(btn0));



        Button btnPlus = new Button("+");
        btnPlus.setOnAction(event -> operandKeyReflect(btnPlus));
        Button btnMinus = new Button("-");
        btnMinus.setOnAction(event -> operandKeyReflect(btnMinus));
        Button btnMulty = new Button("*");
        btnMulty.setOnAction(event -> operandKeyReflect(btnMulty));
        Button btnDiv = new Button("/");
        btnDiv.setOnAction(event -> operandKeyReflect(btnDiv));
        Button btnEq = new Button("=");

        Button btnDel = new Button("c");
        btnDel.setOnAction(event ->  value.set(0));


        root.setVgap(10);
        btnRoot.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.add(area, 0, 0);
        root.add(btnRoot, 0, 1);

        btnRoot.getChildren().addAll(
                btn1, btn2, btn3, btnDiv,
                btn4, btn5, btn6, btnMulty,
                btn7, btn8, btn9, btnMinus,
                btn0, btnEq, btnDel, btnPlus);

        GridPane loginRoot = new GridPane();
        Scene loginScreen = new Scene(loginRoot, 320, 375);
     //   loginRoot.setGridLinesVisible(true);
        loginRoot.setVgap(20);

        Button confirm = new Button("Confirm");
        confirm.setStyle("-fx-font-size: 16px;");
        Button clear = new Button("Clear");
        clear.setStyle("-fx-font-size: 16px;");
        Button registrationLink = new Button("registration");
        registrationLink.setStyle("-fx-font-size: 16px;");
        TextField login = new TextField();
        login.setStyle("-fx-max-width: 200; -fx-font-size: 16;");
        login.setPromptText("login");
        PasswordField password = new PasswordField();
        password.setPromptText("password");
        password.setStyle("-fx-max-width: 200; -fx-font-size: 16;");
        GridPane.setConstraints(login, 0, 0, 2, 1);
        GridPane.setConstraints(password, 0, 2, 2, 1);
        GridPane.setConstraints(registrationLink, 0, 4, 1, 1);
        GridPane.setConstraints(confirm, 1, 4, 1, 1, HPos.RIGHT, VPos.CENTER);

        Label loginCheck = new Label();
        Label passwordCheck = new Label();

        passwordCheck.setTextFill(Color.RED);


        GridPane.setConstraints(loginCheck, 0, 1, 1, 1);
        GridPane.setConstraints(passwordCheck, 1, 1, 1, 1);
        loginRoot.setAlignment(Pos.CENTER);
        loginRoot.getChildren().addAll(login, password, confirm, registrationLink, loginCheck, passwordCheck);


        GridPane regRoot = new GridPane();
        regRoot.setVgap(20);
        regRoot.setAlignment(Pos.CENTER);
        Scene regScreen = new Scene(regRoot, 320, 375);
        PasswordField passwordConfirm = new PasswordField();
        passwordConfirm.setPromptText("confirm your password");
        passwordConfirm.setStyle("-fx-max-width: 200; -fx-font-size: 16;");

        Button loginLink = new Button("login screen");
        loginLink.setStyle("-fx-font-size: 16px;");
        GridPane.setConstraints(loginLink, 0, 4, 1, 1);
        GridPane.setConstraints(passwordConfirm, 0, 3, 2, 1);



        registrationLink.setOnAction(event -> {
            primaryStage.setTitle("Calc (registration)");
            regRoot.getChildren().addAll(login, password, confirm, loginLink, passwordConfirm, loginCheck, passwordCheck);
            primaryStage.setScene(regScreen);
            loginRoot.getChildren().clear();
            loginCheck.setText("");
            passwordCheck.setText("");
        });

        loginLink.setOnAction(event -> {
            primaryStage.setTitle("Calc (login screen)");
            loginRoot.getChildren().addAll(login, password, confirm, registrationLink, loginCheck, passwordCheck);
            primaryStage.setScene(loginScreen);
            regRoot.getChildren().clear();
            loginCheck.setText("");
            passwordCheck.setText("");
        });

        confirm.setOnAction(event -> {
            if (primaryStage.getScene() == regScreen) {
                if(FileRuler.RegistrationLoginCheck(usersDB, login.getText())){
                    if (FileRuler.RegistrationPasswordCheck(password.getText(), passwordConfirm.getText())){
                        loginCheck.setTextFill(Color.GREEN);
                        loginCheck.setText("Success!");
                        FileRuler.FileWrite(usersDB, login.getText(), password.getText());
                        passwordCheck.setText("");
                    }else{
                        loginCheck.setText("");
                        passwordCheck.setText("Invalid password");
                    }
                }else{
                    loginCheck.setTextFill(Color.RED);
                    loginCheck.setText("Invalid login");
                }
            }else{
                if (FileRuler.LogIn(usersDB, login.getText(), password.getText())){
                    primaryStage.setScene(sceneCalc);
                }
                else {
                    GridPane.setConstraints(loginCheck, 0, 1, 2, 1);
                    loginCheck.setTextFill(Color.RED);
                    loginCheck.setText("Invalid login or password");
                }
            }
        });



        btnEq.setOnAction(event -> {
            try {
                equalKeyReflect(log, login.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        root.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case DIGIT1: keyReflect(btn1); break;
                case DIGIT2: keyReflect(btn2); break;
                case DIGIT3: keyReflect(btn3); break;
                case DIGIT4: keyReflect(btn4); break;
                case DIGIT5: keyReflect(btn5); break;
                case DIGIT6: keyReflect(btn6); break;
                case DIGIT7: keyReflect(btn7); break;
                case DIGIT8: keyReflect(btn8); break;
                case DIGIT9: keyReflect(btn9); break;
                case DIGIT0: keyReflect(btn0); break;
                case ADD: curOp = Op.ADD; break;
                case SUBTRACT: curOp = Op.SUBTRACT; break;
                case MULTIPLY: curOp = Op.MULTIPLY; break;
                case DIVIDE: curOp = Op.DIVIDE; break;
                case EQUALS:
                    try {
                        equalKeyReflect(log, login.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });

        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(sceneCalc);
        primaryStage.setScene(loginScreen);
        primaryStage.show();
    }


    void keyReflect(Button btn){
        if(curOp == Op.NOOP) {
            value.set(value.get() * 10 + Integer.parseInt(btn.getText()));
        }else{
            stackValue.set(value.get());
            value.set(Integer.parseInt(btn.getText()));
            stackOp = curOp;
            curOp = Op.NOOP;
        }
    }

    void operandKeyReflect(Button btn){
        switch (btn.getText()) {
            case "+": curOp = Op.ADD; break;
            case "-": curOp = Op.SUBTRACT; break;
            case "*": curOp = Op.MULTIPLY; break;
            case "/": curOp = Op.DIVIDE; break;
        }
    }

    private void equalKeyReflect(File file, String login) throws IOException{
        switch (stackOp) {
            case ADD:
                FileLog.Write(file, value.get(), stackValue.get(), "+");
                value.set(stackValue.get() + value.get());
                FileLog.Write(file, login, value.get());
            break;
            case SUBTRACT:
                FileLog.Write(file, value.get(), stackValue.get(), "-");
                value.set(stackValue.get() - value.get());
                FileLog.Write(file, login, value.get());
            break;
            case MULTIPLY:
                FileLog.Write(file, value.get(), stackValue.get(), "*");
                value.set(stackValue.get() * value.get());
                FileLog.Write(file, login, value.get());
            break;
            case DIVIDE:
                FileLog.Write(file, value.get(), stackValue.get(), "/");
                value.set(stackValue.get() / value.get());
                FileLog.Write(file, login, value.get());
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}