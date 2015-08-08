package com.sanjay900.tetris.SETTINGS;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.sanjay900.tetris.HIGHSCORE.PlayerHighScore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetScores extends Generic  {
	@Override
	public void genericRun() throws SQLException {
		String query = "SELECT * FROM `tetris`";
		PreparedStatement p = connection.prepareStatement(query);
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			UUID u = UUID.fromString(rs.getString("UUID"));
			PlayerHighScore hs = new PlayerHighScore(rs.getString("lastName"),u,rs.getInt("score"),rs.getInt("Tlines"));
			plugin.getHighScore().setScore(u,hs);
		}
	}
}
