package com.ut.rpg.core.hooks.databases;

import com.ut.rpg.core.exceptions.DatabaseHookException;
import com.ut.rpg.core.managers.DatabaseManager;
import com.ut.rpg.core.utils.ItemUtil;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public final class ItemDatabase {

    private static ItemDatabase itemDatabase = new ItemDatabase();

    private ItemDatabase() {}

    public static ItemDatabase getInstance() {
        return itemDatabase;
    }

    String table = "itemdb";

    public void put(Object key, ItemStack type) throws DatabaseHookException {
        try {
            ItemStack stack = type;
            String query = "INSERT INTO " + table + " values(?, ?)";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.setObject(1, key);
                ps.setString(2, ItemUtil.itemToBinary(stack));
                ps.executeUpdate();
            }

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseHookException();
        }
    }

    public boolean isExist(String key, Object name) throws DatabaseHookException {
        try {
            String query = "SELECT * FROM " + table + " where " + key + " = '" + name + "'";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    return true;
                return false;
            }
        } catch(Exception e) {throw new DatabaseHookException();
        }
    }

    public ItemStack get(String key, Object value) throws DatabaseHookException
    {
        try {
            String query = "SELECT * FROM " + table + " where " + key + " = '" + value + "'";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    return ItemUtil.binaryToItem(rs.getString(2));
                return null;
            }
        } catch(Exception e)
        {
            throw new DatabaseHookException();
        }
    }

    public HashMap<Integer, ItemStack> getAllItem() throws DatabaseHookException
    {
        try {
            String query = "SELECT * FROM " + table;
            HashMap<Integer, ItemStack> map = new HashMap<>();
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                    map.put(rs.getInt(1), ItemUtil.binaryToItem(rs.getString(2)));
                return map;
            }
        } catch(Exception e)
        {
            throw new DatabaseHookException();
        }
    }

    public void remove(Object where, Object value1) throws DatabaseHookException
    {
        try {
            String query = "DELETE FROM " + table + " where " + where + " = '" + value1 + "'";
            try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement ps =
                    conn.prepareStatement(query)){
                ps.executeUpdate();
            }
        } catch(Exception e)
        {
            throw new DatabaseHookException();
        }
    }


}
