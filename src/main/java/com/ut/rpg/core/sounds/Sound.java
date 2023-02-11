package com.ut.rpg.core.sounds;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class Sound {

    private org.bukkit.Sound sound;
    private float volume;
    private float pitch;

    public Sound(org.bukkit.Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static void playSound(Player p, Sound sound)
    {
        p.playSound(p.getLocation(), sound.sound, (float) sound.volume, (float) sound.pitch);
    }
}
