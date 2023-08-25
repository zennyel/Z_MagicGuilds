package com.zennyel.guilds.database;

import com.zennyel.GuildsPlugin;

import java.sql.*;

public class QuestsDAO extends AbstractDatabase{
    public QuestsDAO(GuildsPlugin instance) {
        super(instance);
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS quests(uuid VARCHAR(26), int completed)";
        try(Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)){
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertCompletedQuests(String uuid, int completed) {
        String sql = "INSERT INTO quests (uuid, completed) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);
            statement.setInt(2, completed);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCompletedQuests(String uuid) {
        String sql = "SELECT * FROM quests WHERE uuid = ?";
        try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("completed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public Connection getConnection() {
        return getConnection("adventurer.db");
    }





}
