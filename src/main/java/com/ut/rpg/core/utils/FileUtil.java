package com.ut.rpg.core.utils;

import com.ut.rpg.core.Main;

import java.io.File;

public class FileUtil {

    public static void createFile(String path)
    {
        File file = new File("plugins/UtilaRpgCore/" + path + ".yml");
        if(!file.exists()) {
            Main.getPlugin().saveResource(path + ".yml", false);
        }
    }

    public static void createFolder(String path)
    {
        File file = new File("plugins/UtilaRpgCore/" + path);
        if(!file.exists())
            file.mkdir();
    }

}
