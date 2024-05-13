package az.developia.millionarie.main;

import java.io.IOException;

import az.developia.millionarie.database.Database;
import az.developia.millionarie.repository.QuestionRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainClass extends Application {
	public static Stage mainStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage1) {
		try {
			mainStage = stage1;
			AnchorPane pane = FXMLLoader
					.load(getClass().getClassLoader().getResource("az/developia/millionarie/view/main.fxml"));
			Scene scene = new Scene(pane);
			stage1.setScene(scene);
			stage1.setTitle("Millionaire App");
			stage1.show();
			
			QuestionRepository repo = new QuestionRepository();
			System.out.println(repo.getAllQuestions());
			
			Database.connect();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void hide(Stage mainStage) {
		mainStage.hide();
	}
}
