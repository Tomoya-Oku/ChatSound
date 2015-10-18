package org.civn.chatsound;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatSound extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("chatsound"))
		{
			sender.sendMessage(ChatColor.GOLD + "[ChatSound]");
			sender.sendMessage(ChatColor.GOLD + "Author: CIVN");
			sender.sendMessage(ChatColor.GOLD + "Version: 1.0");
			return true;
		}

		return false;

	}

	@EventHandler
	public void onPlayerChat (AsyncPlayerChatEvent event)
	{
		FileConfiguration conf=getConfig();

		double vol = conf.getDouble("volume");
		float volume = (float) vol;

		double pit = conf.getDouble("pitch");
		float pitch = (float) pit;

		Player p = event.getPlayer();

		p.getWorld().playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, volume, pitch);
	}
}