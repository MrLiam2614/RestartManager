package me.mrliam2614.restartmanager.commands;

import me.mrliam2614.restartmanager.RestartManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class cmd_Restart implements CommandExecutor, TabCompleter {
    private final RestartManager plugin;

    public cmd_Restart(RestartManager restartManager) {
        this.plugin = restartManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("restartmanager.admin")) {
            if (args[0].equalsIgnoreCase("stop")) {
                plugin.getRestarting().cancel();
                if (sender instanceof Player) {
                    plugin.getFacilitisAPI().msg.sendMessage((Player) sender, "&7[&6RestartManager&7] &cStopped Timer");
                    plugin.getFacilitisAPI().console.sendMessage(plugin, sender.getName() + "&cStopped Timer", "info");
                } else {
                    plugin.getFacilitisAPI().console.sendMessage(plugin, "&cStopped Timer", "info");
                }
            } else if (Integer.parseInt(args[0]) > 0) {
                if (sender instanceof Player) {
                    plugin.getFacilitisAPI().msg.sendMessage((Player) sender, "&7[&6RestartManager&7] &aStarted &cRestart &6Timer");
                    plugin.getFacilitisAPI().console.sendMessage(plugin, sender.getName() + " &aStarted &cRestart &6Timer", "info");
                } else {
                    plugin.getFacilitisAPI().console.sendMessage(plugin, " &aStarted &cRestart &6Timer", "info");
                }
                plugin.setRestart(Integer.parseInt(args[0]));
            } else {
                sender.sendMessage("Missing values");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("restartmanager.admin")) {
            if (sender.hasPermission("restartmanager.admin")) {
                commands.add("stop");
                commands.add("1");
                commands.add("2");
                commands.add("3");
                commands.add("4");
                commands.add("5");
                commands.add("6");
                commands.add("7");
                commands.add("8");
                commands.add("9");
            }
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
