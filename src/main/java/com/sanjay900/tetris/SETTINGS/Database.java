package com.sanjay900.tetris.SETTINGS;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.sanjay900.eyrePlugin.EyrePlugin;
import com.sanjay900.tetris.Core;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;

@Getter
public class Database {

	private Core plugin = Core.getInstance();	
	private HikariDataSource connectionPool;

	public Database(final EyrePlugin plugin) throws SQLException {
		connectionPool = new HikariDataSource();
		connectionPool.setMaximumPoolSize(5);
		connectionPool.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		connectionPool.addDataSourceProperty("serverName", "localhost");
		connectionPool.addDataSourceProperty("port", "3306");
		connectionPool.addDataSourceProperty("databaseName", "tetris");
		connectionPool.addDataSourceProperty("user", "root");
		connectionPool.addDataSourceProperty("password", "q5QNpfzfTutUgw7M");
	}
	
	public Connection getConnection() {
		try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void disable() {
		if (connectionPool != null) {
			connectionPool.shutdown();
		}
	}
}