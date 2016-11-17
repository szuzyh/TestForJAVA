/**
 * Created by Leo on 2016/11/17.
 */
package helloworld;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HelloWorld extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        Label label1 = new Label();
//一个带文本元素的Label
      //  Label label2 = new Label("Search");
//一个带文本和图标的Label
        Image image = null;
        try {
            FileInputStream in=new FileInputStream("C:\\Users\\Leo\\IdeaProjects\\Github\\TestForJAVA\\src\\main\\resources\\ace.bmp");
            image=new Image(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
         Label label3 = new Label("",new ImageView(image) );
        StackPane root = new StackPane();
      //  root.getChildren().add(btn);
        root.getChildren().add(label3);
        Scene scene =new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}