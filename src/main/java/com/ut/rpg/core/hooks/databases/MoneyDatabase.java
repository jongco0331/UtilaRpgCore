package com.ut.rpg.core.hooks.databases;

import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.managers.DatabaseManager;
import com.ut.rpg.core.managers.stat.StatManager;
import com.ut.rpg.core.objects.Account;
import com.ut.rpg.core.objects.PlayerData;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public final class MoneyDatabase {
    
    private MoneyDatabase() {}
    
    private static MoneyDatabase moneyDatabase = new MoneyDatabase();

    public static MoneyDatabase getInstance() {
        return moneyDatabase;
    }

    String table = "playerEconomy";

    String insertQuery = "INSERT INTO " + table + " values(?, ?, ?)";
    String existQuery = "SELECT * FROM " + table + " where uuid = ?";
    String dataQuery = "SELECT * FROM " + table + " where uuid = ?";
    String updateMoneyQuery = "UPDATE " + table + " SET money = ? WHERE uuid = ?";
    String updateCashQuery = "UPDATE " + table + " SET cash = ? WHERE uuid = ?";

    public void insertNewMoneyData(Player p) throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(insertQuery)){
                ps.setString(1, p.getUniqueId().toString());
                ps.setInt(2, 0);
                ps.setInt(3, 0);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public boolean isExists(Player p) throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(existQuery)){
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
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(existQuery)){
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

    public Account getOriginalData(Player p)  throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(dataQuery)){
                ps.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next())
                {
                    long money = resultSet.getInt("money");
                    long cash = resultSet.getInt("cash");
                    return new Account(money, cash);
                }
                return null;
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public Account getOriginalData(UUID uuid)  throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(dataQuery)){
                ps.setString(1, uuid.toString());
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next())
                {
                    long money = resultSet.getInt("money");
                    long cash = resultSet.getInt("cash");
                    return new Account(money, cash);
                }
                return null;
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public void upgradeAccountMoneyData(Player p, long money) throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(updateMoneyQuery)){
                ps.setString(2, p.getUniqueId().toString());
                ps.setLong(1, money);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public void upgradeAccountMoneyData(UUID uuid, long money) throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(updateMoneyQuery)){
                ps.setString(2, uuid.toString());
                ps.setLong(1, money);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public void upgradeCashMoneyData(Player p, long cash) throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(updateCashQuery)){
                ps.setString(2, p.getUniqueId().toString());
                ps.setLong(1, cash);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public void upgradeCashMoneyData(UUID uuid, long cash) throws DatabaseHookException {
        try {
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(updateCashQuery)){
                ps.setString(2, uuid.toString());
                ps.setLong(1, cash);
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

}
