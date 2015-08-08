package com.sanjay900.tetris.SETTINGS;

import java.sql.Connection;
import java.sql.SQLException;

import com.sanjay900.tetris.Core;

public abstract class Generic implements Runnable {

    protected Connection connection = null;
    
    protected Core plugin = Core.getInstance();
    
    @Override
    public void run() {
        try {
            connection = plugin.getDb().getConnection();
            genericRun();
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public abstract void genericRun() throws SQLException;
}