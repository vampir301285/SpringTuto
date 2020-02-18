package kap23_Threads_bei_JavaFX.Bsp1;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;



public class Controller
{ 
  @FXML
  protected TextField eingabe;

  @FXML
  protected ListView<String> ausgabe;

  private volatile boolean isRunning = false;

  public Controller()
  {
    ObservableList<String> list = FXCollections.observableArrayList();
    Platform.runLater(() -> ausgabe.setItems(list) );
  }

  @FXML
  protected void handleStart(ActionEvent event)
  {
    System.out.println(Thread.currentThread());
    int tasks = 1;
    String str = eingabe.getText();
    if (str != null)
    {
      try
      {
        tasks = Integer.parseInt(str);
      }
      catch (NumberFormatException exce) {  }
    }

    isRunning = true;
    for (int i = 0; i < tasks; i++)
    {
      Thread th = new Thread(() -> searchTask());
      th.start();
    }
  }

  @FXML
  protected void handleStop(ActionEvent event)
  {
    isRunning = false;
  }

  //Wird nebenläufig von den Threads ausgeführt
  private void searchTask()
  {
    try
    {
      while (isRunning)
      {
        int rd = ThreadLocalRandom.current().nextInt(1000);
        String str = Thread.currentThread().getName() + " : " + rd;
        TimeUnit.MILLISECONDS.sleep(1000 + rd );
        
        Platform.runLater( () -> ausgabe.getItems().add(str) );
      }
    }
    catch (InterruptedException e)
    {
      // Task wird beendet
    }
  }
}
