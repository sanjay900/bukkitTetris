package com.sanjay900.tetris.SETTINGS;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.sanjay900.tetris.HIGHSCORE.PlayerHighScore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SetScore extends Generic  {
	private PlayerHighScore score;
	@Override
	public void genericRun() throws SQLException {
		String query = "INSERT INTO tetris (UUID, lastName, score, Tlines) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE UUID=VALUES(UUID), lastName=VALUES(lastName), score=VALUES(score), Tlines=VALUES(Tlines)";
		PreparedStatement p = connection.prepareStatement(query);
		p.setString(1, score.getUuid().toString());
		p.setString(2, score.getPlayer());
		p.setInt(3, score.getScore());
		p.setInt(4, score.getLines());
		ResultSet rs = p.executeQuery();
		while (rs.next()) {
			UUID u = UUID.fromString(rs.getString("UUID"));
			PlayerHighScore hs = new PlayerHighScore(rs.getString("lastName"),u,rs.getInt("score"),rs.getInt("Tlines"));
			plugin.getHighScore().setScore(u,hs);
		}
	}
}
