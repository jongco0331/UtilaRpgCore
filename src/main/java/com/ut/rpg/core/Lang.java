package com.ut.rpg.core;

import com.ut.rpg.core.utils.FileUtil;
import com.ut.rpg.core.utils.StringUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Lang {

    public static String LOADING_DATA;
    public static String ERROR_LOADING;
    public static String LOADING_SUCCESS;
    public static String NEED_STATPOINT;
    public static String UP_STAT1;
    public static String UP_STAT2;
    public static String UP_STAT3;
    public static String CRITICAL_MESSAGE;
    public static String AVOIDING_MESSAGE;
    public static String LEVEL_UP;
    public static String GET_EXP_ACTIONBAR;
    public static String PRINT_MONEY;
    public static String PRINT_CASH;

    public static void update()
    {
        FileUtil.createFile("message");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File("plugins/UtilaRpgCore/message.yml"));
        LOADING_DATA = StringUtil.colorize(yaml.getString("LOADING_DATA"));
        ERROR_LOADING = StringUtil.colorize(yaml.getString("ERROR_LOADING"));
        LOADING_SUCCESS = StringUtil.colorize(yaml.getString("LOADING_SUCCESS"));
        NEED_STATPOINT = StringUtil.colorize(yaml.getString("NEED_STATPOINT"));
        UP_STAT1 = StringUtil.colorize(yaml.getString("UP_STAT1"));
        UP_STAT2 = StringUtil.colorize(yaml.getString("UP_STAT2"));
        UP_STAT3 = StringUtil.colorize(yaml.getString("UP_STAT3"));
        CRITICAL_MESSAGE = StringUtil.colorize(yaml.getString("CRITICAL_MESSAGE"));
        AVOIDING_MESSAGE = StringUtil.colorize(yaml.getString("AVOIDING_MESSAGE"));
        LEVEL_UP = StringUtil.colorize(yaml.getString("LEVEL_UP"));
        GET_EXP_ACTIONBAR = StringUtil.colorize(yaml.getString("GET_EXP_ACTIONBAR"));
        PRINT_MONEY = StringUtil.colorize(yaml.getString("PRINT_MONEY"));
        PRINT_CASH = StringUtil.colorize(yaml.getString("PRINT_CASH"));
    }

}
