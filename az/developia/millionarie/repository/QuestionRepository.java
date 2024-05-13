package az.developia.millionarie.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import az.developia.millionarie.database.Database;
import az.developia.millionarie.model.Question;

public class QuestionRepository {
	public ArrayList<Question> getAllQuestions() {
		ArrayList<Question> list = new ArrayList<>();
		Connection conn = Database.connect();
		String query = "SELECT * FROM millionarie_questions";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Question question = new Question();
				question.setId(rs.getInt("id"));
				question.setQuestion(rs.getString("question"));
				question.setVariants(rs.getString("variants").split(","));
				question.setAnswer(rs.getString("answer"));
				
				list.add(question);
			}
			rs.close();
			conn.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
