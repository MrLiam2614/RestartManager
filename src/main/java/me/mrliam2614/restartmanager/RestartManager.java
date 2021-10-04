package me.mrliam2614.restartmanager;

import me.mrliam2614.FacilitisAPI.FacilitisAPI;
import me.mrliam2614.restartmanager.commands.cmd_Restart;
import me.mrliam2614.restartmanager.restart.Manager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class RestartManager extends JavaPlugin {
    private FacilitisAPI facilitisAPI;
    private BukkitTask restarting;

    public void onEnable() {
        saveDefaultConfig();
        facilitisAPI = FacilitisAPI.getInstance();
        getFacilitisAPI().messages.EnableMessage(this);
        getCommand("restartmanager").setExecutor(new cmd_Restart(this));
        int restartTime = getConfig().getInt("autorestart");

        if (restartTime <= 0) {
            getFacilitisAPI().console.sendMessage(this, "&6Auto Restart &7not &cactive", "info");
        } else {
            getFacilitisAPI().console.sendMessage(this, "&6Auto Restart &aActive", "info");
            setRestart(restartTime);
        }
    }

    public void onDisable() {
        getFacilitisAPI().messages.DisableMessage(this);
    }

    public FacilitisAPI getFacilitisAPI() {
        return facilitisAPI;
    }

    public void setRestart(int restartTime) {
        if (restarting != null) {
            restarting.cancel();
        }
        restarting = new Manager(this, restartTime).runTaskTimer(this, 20, 20);
    }

    public BukkitTask getRestarting() {
        return restarting;
    }
}
