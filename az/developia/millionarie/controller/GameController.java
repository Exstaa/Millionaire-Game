package az.developia.millionarie.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import az.developia.millionarie.model.Question;
import az.developia.millionarie.repository.DetailsRepository;
import az.developia.millionarie.repository.QuestionRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController implements Initializable {
	public static Stage gameStage;

	@FXML
	private Label questionLabel;

	@FXML
	private Button variantButton1;

	@FXML
	private Button variantButton2;

	@FXML
	private Button variantButton3;

	@FXML
	private Button variantButton4;

	@FXML
	private Button scoreButton1;

	@FXML
	private Button scoreButton10;

	@FXML
	private Button scoreButton11;

	@FXML
	private Button scoreButton12;

	@FXML
	private Button scoreButton2;

	@FXML
	private Button scoreButton3;

	@FXML
	private Button scoreButton4;

	@FXML
	private Button scoreButton5;

	@FXML
	private Button scoreButton6;

	@FXML
	private Button scoreButton7;

	@FXML
	private Button scoreButton8;

	@FXML
	private Button scoreButton9;

	@FXML
	private Button helpButton;

	private ArrayList<Question> questions = new ArrayList<>();

	private QuestionRepository repo = new QuestionRepository();

	private Integer questionIndex = 0;

	private String[] gradients = { "-fx-background-color: linear-gradient(to right, #f00000, #dc281e);",
			"-fx-background-color:  linear-gradient(to right, #11998e, #38ef7d);",
			"-fx-background-color:  linear-gradient(to right, #eb5757, #000000);" };

	private Button[] scoreButtons;
  
	private DetailsRepository detailsRepo = new DetailsRepository();
	
	private Integer highScore;
	
	private MediaPlayer player;

	@FXML
	private void variantSelected(ActionEvent event) {
		Button clickedButton = (Button) event.getTarget();

		Question currentQuestion = questions.get(questionIndex);

		if (clickedButton.getText().equals(currentQuestion.getAnswer())) {
			File file = new File("resources/correct_answer.mp3");
			Media media = new Media(file.toURI().toString());
			player = new MediaPlayer(media);
			player.setVolume(0.1);
			player.play();
			clickedButton.setStyle(clickedButton.getStyle() + gradients[1]);

			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.0), event1 -> {
				scoreButtons[questionIndex].setStyle(scoreButtons[questionIndex].getStyle() + gradients[0]);
				questionIndex++;
				loadQuestion();
				clearButtonColors();
			}));
			timeline.setCycleCount(1);
			timeline.play();
		} else {
			player.pause();
			File file = new File("resources/wrong_answer.mp3");
			Media media = new Media(file.toURI().toString());
			player = new MediaPlayer(media);
			player.setVolume(0.1);
			player.play();
			clickedButton.setStyle(clickedButton.getStyle() + gradients[2]);
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.0), event1 -> {
				gameStage.hide();
				Stage gameOverStage = new Stage();
				String result = "";
				if (questionIndex > 2) {
					result = scoreButtons[questionIndex - 3].getText();
				} else {
					result = "0";
				}
				AnchorPane gameOverPane = new AnchorPane();
				Label txt = new Label("Your estimate income is: " + result);
				txt.setStyle("-fx-font-size: 30px;");
				gameOverPane.getChildren().add(txt);
				Scene gameOverScene = new Scene(gameOverPane, 400, 100);
				gameOverStage.setScene(gameOverScene);
				gameOverStage.show();
			}));
			timeline.setCycleCount(1);
			timeline.play();
		}
	}

	@FXML
	private void surrenderClicked(ActionEvent event) {
		Stage gameOverStage = new Stage();
		AnchorPane gameOverPane = new AnchorPane();
		String result = "";
		if (questionIndex != 0) {
			result = scoreButtons[questionIndex - 1].getText();
		} else {
			result = "0";
		}
		Label txt = new Label("Your estimate income is: " + result);
		txt.setStyle("-fx-font-size: 30px;");
		gameOverPane.getChildren().add(txt);
		Scene gameOverScene = new Scene(gameOverPane, 400, 100);
		gameOverStage.setScene(gameOverScene);
		if(Integer.parseInt(result) > highScore) {
			highScore = Integer.parseInt(result);
			detailsRepo.setHighScoreValue(highScore);
		}
		gameOverStage.setTitle("Game Over/Surrendered");
		gameOverStage.show();
		if(Integer.parseInt(result) > highScore) {
			highScore = Integer.parseInt(result);
			detailsRepo.setHighScoreValue(highScore);
		}
	}

	@FXML
	private void helpButtonClicked(ActionEvent event) {
		helpButton.setVisible(false);
		Button[] variantsButtons = new Button[] { variantButton1, variantButton2, variantButton3, variantButton4 };

		ArrayList<String> mixerList = new ArrayList<>();

		Question currentQuestion = questions.get(questionIndex);
		String[] variants = currentQuestion.getVariants();
		for (int i = 0; i < variants.length; i++) {
			if (!variants[i].equals(currentQuestion.getAnswer())) {
				mixerList.add(variants[i]);
			}
		}
		Collections.shuffle(mixerList);
		mixerList.remove(0);
		mixerList.remove(1);

		mixerList.add(currentQuestion.getAnswer());

		for (int i = 0; i < variantsButtons.length; i++) {
			Button button = variantsButtons[i];
			String variant = button.getText();
			if (!mixerList.contains(variant)) {
				button.setVisible(false);
			}
		}
	}

	private void clearButtonColors() {
		variantButton1.setVisible(true);
		variantButton2.setVisible(true);
		variantButton3.setVisible(true);
		variantButton4.setVisible(true);
		variantButton1.setStyle(variantButton1.getStyle() + gradients[0]);
		variantButton2.setStyle(variantButton1.getStyle() + gradients[0]);
		variantButton3.setStyle(variantButton1.getStyle() + gradients[0]);
		variantButton4.setStyle(variantButton1.getStyle() + gradients[0]);
	}

	private void loadQuestion() {
		if (questionIndex == questions.size()) {
			Stage gameOverStage = new Stage();
			AnchorPane gameOverPane = new AnchorPane();
			String result = "";
			if (questionIndex != 0) {
				result = scoreButtons[questionIndex - 1].getText();
			} else {
				result = "0";
			}
			Label txt = new Label("Your estimate income is: " + result);
			txt.setStyle("-fx-font-size: 30px;");
			gameOverPane.getChildren().add(txt);
			Scene gameOverScene = new Scene(gameOverPane, 400, 100);
			gameOverStage.setScene(gameOverScene);
			gameOverStage.setTitle("You won");
			gameOverStage.show();
			if(Integer.parseInt(result) > highScore) {
				highScore = Integer.parseInt(result);
				detailsRepo.setHighScoreValue(highScore);
			}
		} else {
			clearButtonColors();

			Question question = questions.get(questionIndex);
			String questionString = question.getQuestion();

			List<String> variants = Arrays.asList(question.getVariants());

			questionLabel.setText(questionString);

			Collections.shuffle(variants);

			variantButton1.setText(variants.get(0));
			variantButton2.setText(variants.get(1));
			variantButton3.setText(variants.get(2));
			variantButton4.setText(variants.get(3));
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		highScore = detailsRepo.getHighScore();
		scoreButtons = new Button[] { scoreButton1, scoreButton2, scoreButton3, scoreButton4, scoreButton5,
				scoreButton6, scoreButton7, scoreButton8, scoreButton9, scoreButton10, scoreButton11, scoreButton12 };
		questions = repo.getAllQuestions();
		File file = new File("resources/suspense.mp3");
		Media media = new Media(file.toURI().toString());
		player = new MediaPlayer(media);
		player.setVolume(0.1);
		player.play();
		loadQuestion();
	}
}
