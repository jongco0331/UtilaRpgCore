package com.ut.rpg.core.hooks.databases;

import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.managers.DatabaseManager;
import com.ut.rpg.core.managers.PlayerManager;
import com.ut.rpg.core.managers.stat.StatManager;
import com.ut.rpg.core.objects.PlayerData;
import com.ut.rpg.core.objects.stat.StatData;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public final class PlayerDataDatabase {

    private static PlayerDataDatabase playerDataDatabase = new PlayerDataDatabase();

    private PlayerDataDatabase() {}

    public static PlayerDataDatabase getInstance() {
        return playerDataDatabase;
    }

    String table = "playerdata";

    public void insertNewData(Player p) throws DatabaseHookException {
        try {
            String query = "INSERT INTO " + table + " values(?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(1, p.getUniqueId().toString());
                ps.setInt(2, 0);
                ps.setInt(3, 0);
                ps.setInt(4, 0);
                StatManager statManager = StatManager.getInstance();
                ps.setInt(5, statManager.getStartPoint());
                ps.setInt(6, statManager.getStartLevel());
                ps.setInt(7, 0);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public boolean isExists(Player p) throws DatabaseHookException {
        try {
            String query = "SELECT * FROM " + table + " where uuid = ?";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next())
                    return true;
                return false;
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public boolean isExists(UUID uuid) throws DatabaseHookException {
        try {
            String query = "SELECT * FROM " + table + " where uuid = ?";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(1, uuid.toString());
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next())
                    return true;
                return false;
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public PlayerData getOriginalData(Player p)  throws DatabaseHookException {
        try {
            String query = "SELECT * FROM " + table + " where uuid = ?";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next())
                {
                    int point = resultSet.getInt("point");
                    int stat1 = resultSet.getInt("stat1");
                    int stat2 = resultSet.getInt("stat2");
                    int stat3 = resultSet.getInt("stat3");
                    int level = resultSet.getInt("level");
                    long exp = resultSet.getLong("exp");
                    return new PlayerData(point, stat1, stat2, stat3, level, exp);
                }
                return null;
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public void upgradeStatData(Player p, String data, int point, int value) throws DatabaseHookException {
        try {
            String query = "UPDATE " + table + " SET point = ?, " + data + " = ? WHERE uuid = ?";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(3, p.getUniqueId().toString());
                ps.setInt(1, point);
                ps.setInt(2, value);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public void upgradeStatData(UUID uuid, String data, int point, int value) throws DatabaseHookException {
        try {
            String query = "UPDATE " + table + " SET point = ?, " + data + " = ? WHERE uuid = ?";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(3, uuid.toString());
                ps.setInt(1, point);
                ps.setInt(2, value);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public void upgradeExpData(Player p, int level, long exp) throws DatabaseHookException {
        try {
            String query = "UPDATE " + table + " SET level = ?, exp = ? WHERE uuid = ?";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(3, p.getUniqueId().toString());
                ps.setInt(1, level);
                ps.setLong(2, exp);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }


    public void upgradeExpData(UUID uuid, int level, long exp) throws DatabaseHookException {
        try {
            String query = "UPDATE " + table + " SET level = ?, exp = ? WHERE uuid = ?";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setString(3, uuid.toString());
                ps.setInt(1, level);
                ps.setLong(2, exp);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

}
