package com.minecraft.ultikits.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configs {

    private static final Map<ConfigsEnum, Map<OfflinePlayer, YamlConfiguration>> configs = new HashMap<>();
    private static final Map<ConfigsEnum, Map<OfflinePlayer, File>> files = new HashMap<>();

    private Configs() {
    }

    public static YamlConfiguration getConfig(ConfigsEnum configPath, OfflinePlayer player) {
        if (!configs.containsKey(configPath)) {
            configs.put(configPath, new HashMap<>());
        }
        if (!configs.get(configPath).containsKey(player)) {
            File file = getFile(configPath, player);
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            configs.get(configPath).put(player, configuration);
        }
        return configs.get(configPath).get(player);
    }

    public static File getFile(ConfigsEnum configPath, OfflinePlayer player){
        if (!files.containsKey(configPath)) {
            files.put(configPath, new HashMap<>());
        }
        if (!files.get(configPath).containsKey(player)) {
            File file = new File(configPath.toString(), player.getName() + ".yml");
            files.get(configPath).put(player, file);
        }
        return files.get(configPath).get(player);
    }

    public static void saveConfig(ConfigsEnum configPath, OfflinePlayer player) {
        if (!configs.containsKey(configPath) || !configs.get(configPath).containsKey(player)) return;
        File file = files.get(configPath).get(player);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        file = new File(configPath.toString(), player.getName() + ".yml");
        configuration = YamlConfiguration.loadConfiguration(file);
        configs.get(configPath).put(player, configuration);
        files.get(configPath).put(player, file);
    }
}
