package com.ut.rpg.core.loaders;

import com.ut.rpg.core.Main;
import com.ut.rpg.core.managers.DatabaseManager;
import com.ut.rpg.core.utils.FileUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBLoader implements IDataLoader {

    DatabaseManager manager = DatabaseManager.getInstance();

    private String queryItemTable = "CREATE TABLE itemDB ("
            +   "uuid int,"
            +   "stack blob,"
            +   "PRIMARY KEY(uuid))";
    private String queryPlayerDataTable = "CREATE TABLE playerData ("
            +   "uuid varchar(36),"
            +   "stat1 int,"
            +   "stat2 int,"
            +   "stat3 int,"
            +   "point int,"
            +   "level int,"
            +   "exp bigint,"
            +   "PRIMARY KEY(uuid))";
    private String queryEconomyTable = "CREATE TABLE playerEconomy ("
            +   "uuid varchar(36),"
            +   "money bigint,"
            +   "cash bigint,"
            +   "PRIMARY KEY(uuid))";

    @Override
    public void load() {
        try {
            Main sql = Main.getPlugin();
            FileUtil.createFile("config");
            sql.reloadConfig();
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            databaseManager.setAddress(sql.getConfig().getString("MySQL.address"));
            databaseManager.setPort(sql.getConfig().getString("MySQL.port"));
            databaseManager.setDatabase(sql.getConfig().getString("MySQL.database"));
            databaseManager.setUsername(sql.getConfig().getString("MySQL.username"));
            databaseManager.setPassword(sql.getConfig().getString("MySQL.password"));
            databaseManager.getConnection();

            DatabaseMetaData metaData = databaseManager.getConnection().getMetaData();

            if(!isTableExist(metaData, "itemDB").next())
            {
                Statement statement = databaseManager.getConnection().createStatement();
                statement.execute(queryItemTable);
                System.out.println("[JRpgCore] New Table \'itemDB\' has been defined");
            }
            if(!isTableExist(metaData, "playerData").next())
            {
                Statement statement = databaseManager.getConnection().createStatement();
                statement.execute(queryPlayerDataTable);
                System.out.println("[JRpgCore] New Table \'playerData\' has been defined");
            }
            if(!isTableExist(metaData, "playerEconomy").next())
            {
                Statement statement = databaseManager.getConnection().createStatement();
                statement.execute(queryEconomyTable);
                System.out.println("[JRpgCore] New Table \'playerEconomy\' has been defined");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {

    }
    private static ResultSet isTableExist(DatabaseMetaData dbmd, String tableName) throws SQLException {
        ResultSet rs = dbmd.getTables(null, "PUBLIC", tableName, null);
        return rs;
    }
}
