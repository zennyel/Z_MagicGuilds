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
        String sql = "CREATE TABLE IF NOT EXISTS adventurer(uuid VARCHAR(36), rank CHAR, experience DOUBLE)";
        try(Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)){
            stm.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insert(Player player, Adventurer adventurer){
        String sql = "INSERT INTO adventurer(rank, experience) VALUES (?,?)";
        try(Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)){
            stm.setString(1, adventurer.getRank().toString());
            stm.setDouble(2, adventurer.getExperience());
            stm.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Adventurer get(Player player){
        String sql = "SELECT * FROM adventurer WHERE uuid = ?";
        try(Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()){
            if(rs.next()){
                String rank = rs.getString("rank");
                double experience = rs.getDouble("experience");
                return new Adventurer(Rank.valueOf(rank), experience);
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
