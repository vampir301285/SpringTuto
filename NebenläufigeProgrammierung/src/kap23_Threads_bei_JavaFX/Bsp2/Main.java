package kap23_Threads_bei_JavaFX.Bsp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application
{
  @Override
  public void start(Stage primaryStage)
  {
    try
    {
      Parent root = FXMLLoader.load(getClass().getResource("TaskBsp.fxml"));
      Scene scene = new Scene(root);

      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
