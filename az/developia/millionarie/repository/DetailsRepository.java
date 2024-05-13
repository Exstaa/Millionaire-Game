package az.developia.millionarie.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import az.developia.millionarie.database.Database;

public class DetailsRepository {
	private Connection conn;

	public Integer getHighScore() {
		Integer highScore = null;
		conn = Database.connect();
		try {
			PreparedStatement st = conn.prepareStatement("SELECT highscore from millionarie_details");
			ResultSet rs = st.executeQuery();
			rs.next();
			highScore = Integer.parseInt(rs.getString("highscore"));
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return highScore;

	}
	
	public void setHighScoreValue(Integer highScore) {
		conn = Database.connect();
		PreparedStatement st;
		try {
			st = conn.prepareStatement("UPDATE millionarie_details SET highscore = ? WHERE id = 1");
			st.setInt(1, highScore);
            st.executeUpdate();
            st.close();
            conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
