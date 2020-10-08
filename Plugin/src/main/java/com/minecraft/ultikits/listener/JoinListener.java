package com.minecraft.ultikits.listener;

import com.minecraft.ultikits.checker.updatechecker.VersionChecker;
import com.minecraft.ultikits.enums.ConfigsEnum;
import com.minecraft.ultikits.tasks.SideBarTask;
import com.minecraft.ultikits.ultitools.UltiTools;
//import net.minecraft.server.v1_15_R1.IChatBaseComponent;
//import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
//import net.minecraft.server.v1_15_R1.PlayerConnection;
//import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

import static com.minecraft.ultikits.checker.updatechecker.VersionChecker.*;


public class JoinListener implements Listener {

    File file = new File(ConfigsEnum.JOIN_WELCOME.toString());
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    List<String> welcomeMessage = config.getStringList("welcome_message");
    String opJoinMessage = config.getString("op_join");
    String opQuitMessage = config.getString("op_quit");
    String playerJoinMessage = config.getString("player_join");
    String playerQuitMessage = config.getString("player_quit");
    int sendMessageDelay = config.getInt("send_message_delay");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String vanillaJoinMessage = event.getJoinMessage() == null ? "" : event.getJoinMessage();
        event.setJoinMessage(null);
        if (player.isOp()) {
            if (VersionChecker.isOutDate) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage(ChatColor.RED + String.format("[UltiTools] 工具插件最新版为%s，你的版本是%s！请下载最新版本！", version, current_version));
                        player.sendMessage(ChatColor.RED + "[UltiTools] 你知道吗？现在UltiTools可以自动更新啦！在配置文件中打开自动更新，更新再也不用麻烦！");
                    }
                }.runTaskLaterAsynchronously(UltiTools.getInstance(), 80L);
            }
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(player, opJoinMessage == null ? vanillaJoinMessage : opJoinMessage));
        } else {
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(player, playerJoinMessage == null ? vanillaJoinMessage : playerJoinMessage));
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                for (String each : welcomeMessage) {
                    player.sendMessage(PlaceholderAPI.setPlaceholders(player, each));
                }
            }

        }.runTaskLater(UltiTools.getInstance(), sendMessageDelay * 20L);
    }

    @EventHandler
    public void onJoinCreateEmailData(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        File folder = new File(ConfigsEnum.PLAYER_EMAIL.toString());
        File file = new File(folder, player.getName() + ".yml");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String vanillaQuitMessage = event.getQuitMessage() == null ? "" : event.getQuitMessage();
        event.setQuitMessage(null);
        if (event.getPlayer().isOp()) {
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(player, opQuitMessage == null ? vanillaQuitMessage : opQuitMessage));
        } else {
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(player, playerQuitMessage == null ? vanillaQuitMessage : playerQuitMessage));
        }
    }

    @EventHandler
    public void onJoinSaveIP(PlayerLoginEvent event) {
        if (!UltiTools.isProVersion) {
            return;
        }
        Player player = event.getPlayer();
        InetAddress ipAddress = event.getAddress();
        String ip = ipAddress.getHostAddress().replaceAll("\\.", "_");
        System.out.println(ip);
        File file = new File(ConfigsEnum.LOGIN.toString());
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.get("ip." + ip + ".players") == null) {
            config.set("ip." + ip + ".players", Collections.singletonList(player.getUniqueId().toString()));
        } else {
            List<String> playerList = config.getStringList("ip." + ip + ".players");
            if (playerList.contains(player.getUniqueId().toString())) {
                return;
            }
            int playerCount = playerList.size();
            int playerLimit = config.getInt("playerLimitForOneIP");
            if (playerCount < playerLimit) {
                playerList.add(player.getUniqueId().toString());
                config.set("ip." + ip + ".players", playerList);
            } else {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "此IP已达到注册上限，无法登入！");
                return;
            }
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
