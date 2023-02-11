package com.ut.rpg.core.managers;

import com.ut.rpg.core.hooks.databases.ItemDatabase;
import com.ut.rpg.core.hooks.databases.PlayerDataDatabase;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;

public final class DatabaseManager {

    private static DatabaseManager databaseManager = new DatabaseManager();
    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        return databaseManager;
    }
    @Getter @Setter private String address;
    @Getter @Setter private String port;
    @Getter @Setter private String database;
    @Getter @Setter private String username;
    @Getter @Setter private String password;

    public Connection getConnection()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + this.address + ":" + this.port + "/" + this.database + "?useSSL=false&serverTimezone=UTC", this.username, this.password);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
        } catch (SQLException var4) {
            var4.printStackTrace();
        }
        return null;
    }


}
