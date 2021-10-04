package me.mrliam2614.restartmanager.restart;

import me.mrliam2614.restartmanager.RestartManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Manager extends BukkitRunnable {
    private final RestartManager plugin;
    private List<Integer> alertList;
    private int countdown;
    private Long time;
    private String message;

    public Manager(RestartManager plugin, int time) {
        this.plugin = plugin;
        this.time = Long.parseLong(String.valueOf(time)) * 60;
        this.countdown = plugin.getConfig().getInt("countdownFrom");
        this.alertList = plugin.getConfig().getIntegerList("allerts");
        this.message = plugin.getConfig().getString("message.restarting");
    }

    @Override
    public void run() {
        time = time - 1;
        //Is inside Countdown
        if (time <= countdown) {
            String msg = ChatColor.translateAlternateColorCodes('&', "&7[&6Restart&7] " + message.replace("{time}", getTime()));
            plugin.getServer().broadcastMessage(msg);
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
            }
        }
        //Is alert time
        if (alertList.contains(time)) {
            String msg = ChatColor.translateAlternateColorCodes('&', "&7[&6Restart&7] " + message.replace("{time}", getTime()));
            plugin.getServer().broadcastMessage(msg);
        }
        if (time == 0) {
            String msg = ChatColor.translateAlternateColorCodes('&', "&7[&6Restart&7] " + plugin.getConfig().getString("message.kickmessage"));
            plugin.getFacilitisAPI().console.sendMessage(plugin, "SERVER STOPPED", "info");
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + player.getName() + " " + msg);
            }
        }
        if(time < 0){
            if(time == -1){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
            }
            plugin.getRestarting().cancel();
        }
    }

    public Long remainingTime() {
        return time;
    }

    public String getTime() {
        Long time = remainingTime();

        int minutes = (int) (time / 60);
        int seconds = (int) (time - (minutes * 60));

        if (minutes > 0) {
            if (minutes < 10) {
                return minutes + ":" + seconds;
            }
            return "0" + minutes + ":" + seconds;
        } else {
            return seconds + "";
        }
    }
}
