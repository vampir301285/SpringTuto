package kap23_Threads_bei_JavaFX.Bsp2;

import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;


public class Controller
{ 
  @FXML
  protected TextField state;
  
  @FXML
  protected TextField message;

  @FXML
  protected TextField result;
  
  @FXML
  protected ProgressBar progress;
  
  @FXML
  protected Button start;
  
  private Task<String> task;
  
  class MyTask extends Task<String>
  {
    @Override
    protected String call() throws Exception
    {
      int len = 10;
      for(int i=0; i <= len; i++ )
      {
        updateMessage("Step " + i);
        updateProgress(i, len);
        delay();
      }
      
      Platform.runLater( () -> start.setDisable(false) );
      
      return "Done";
    }
  }

  public Controller()
  {
    
  }

  @FXML
  protected void handleStart(ActionEvent event)
  {
    start.setDisable(true);
    startTask();
  }
  
  @FXML
  protected void handleCancel(ActionEvent event)
  {
    task.cancel();
    start.setDisable(false);
  }


  private void startTask()
  {
    task = new MyTask();
    
    state.textProperty().bind( task.stateProperty().asString() );
    message.textProperty().bind( task.messageProperty() );
    result.textProperty().bind( task.valueProperty() );
    progress.progressProperty().bind( task.progressProperty() );
    
    task.setOnCancelled( e -> System.err.println("Task Canceled!") );
    
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }
  
  private void delay()
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep(1000);
    }
    catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }
}
