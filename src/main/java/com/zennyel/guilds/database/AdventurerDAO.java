package com.zennyel.guilds.database;

import com.zennyel.GuildsPlugin;
import com.zennyel.guilds.guild.Adventurer;
import com.zennyel.guilds.guild.Rank;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdventurerDAO extends AbstractDatabase{


    public AdventurerDAO(GuildsPlugin instance) {
        super(instance);
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS adventurer(uuid VARCHAR(36), rank CHAR, fame INTEGER, experience DOUBLE)";
        try(Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)){
            stm.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insert(Player player, Adventurer adventurer) {
        String sql = "INSERT INTO adventurer(uuid, rank, experience, fame) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, player.getUniqueId().toString());
            stm.setString(2, adventurer.getRank().toString());
            stm.setDouble(3, adventurer.getExperience());
            stm.setDouble(4, adventurer.getFame());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Adventurer get(Player player){
        String sql = "SELECT * FROM adventurer WHERE uuid = ?";
        try(Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()){
            if(rs.next()){
                String rank = rs.getString("rank");
                double experience = rs.getDouble("experience");
                int fame = rs.getInt("fame");
                return new Adventurer(Rank.valueOf(rank), experience, fame);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public Connection getConnection() {
        return getConnection("adventurer.db");
    }

}
