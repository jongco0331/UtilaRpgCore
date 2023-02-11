package com.ut.rpg.core.managers;

import lombok.Getter;
import lombok.Setter;

public final class ConfigManager {

    private ConfigManager() {}

    private static ConfigManager configManager = new ConfigManager();

    public static ConfigManager getInstance() {
        return configManager;
    }

    @Getter @Setter
    private String text_head;

}
