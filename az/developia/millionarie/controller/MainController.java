package az.developia.millionarie.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import az.developia.millionarie.main.MainClass;
import az.developia.millionarie.repository.DetailsRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainController implements Initializable{

	@FXML
	private Button startButton;

	@FXML
	private MediaPlayer player;
	
	@FXML
	private Label highScoreLabel; 
	
	private DetailsRepository repo = new DetailsRepository();
	
	private Integer highScore;
	
	@FXML
	void startButtonClicked(ActionEvent event) {
		player.stop();
		try {
			Stage stage1 = new Stage();
			AnchorPane pane = FXMLLoader
					.load(getClass().getClassLoader().getResource("az/developia/millionarie/view/game.fxml"));
			Scene scene = new Scene(pane);
			stage1.setScene(scene);
			stage1.setTitle("Game");
			stage1.show();
			GameController.gameStage = stage1;
			MainClass.mainStage.hide();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    highScore = repo.getHighScore();
	    highScoreLabel.setText(highScore.toString());
        File file = new File("resources/soundtrack.mp3");		
	    Media media = new Media(file.toURI().toString());
	    player = new MediaPlayer(media);
	    player.setVolume(0.1);
	    player.play();
	}
}
