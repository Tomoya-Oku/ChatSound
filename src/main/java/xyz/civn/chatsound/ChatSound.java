package xyz.civn.chatsound;

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
	FileConfiguration conf = this.getConfig();

	String prefix = GRAY + "[" + B + GREEN + "ChatSound" + R + GRAY + "] ";

	public static final ChatColor B        = ChatColor.BOLD;
	public static final ChatColor I        = ChatColor.ITALIC;
	public static final ChatColor M        = ChatColor.MAGIC;
	public static final ChatColor R        = ChatColor.RESET;
	public static final ChatColor S        = ChatColor.STRIKETHROUGH;
	public static final ChatColor U        = ChatColor.UNDERLINE;
	public static final ChatColor AQUA     = ChatColor.AQUA;
	public static final ChatColor BLACK    = ChatColor.BLACK;
	public static final ChatColor BLUE     = ChatColor.BLUE;
	public static final ChatColor D_AQUA   = ChatColor.DARK_AQUA;
	public static final ChatColor D_BLUE   = ChatColor.DARK_BLUE;
	public static final ChatColor D_GRAY   = ChatColor.DARK_GRAY;
	public static final ChatColor D_GREEN  = ChatColor.DARK_GREEN;
	public static final ChatColor D_PURPLE = ChatColor.DARK_PURPLE;
	public static final ChatColor D_RED    = ChatColor.DARK_RED;
	public static final ChatColor GOLD     = ChatColor.GOLD;
	public static final ChatColor GRAY     = ChatColor.GRAY;
	public static final ChatColor GREEN    = ChatColor.GREEN;
	public static final ChatColor L_PURPLE = ChatColor.LIGHT_PURPLE;
	public static final ChatColor RED      = ChatColor.RED;
	public static final ChatColor WHITE    = ChatColor.WHITE;
	public static final ChatColor YELOW    = ChatColor.YELLOW;

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
			if (args.length == 0)
			{
				sender.sendMessage(prefix);
				sender.sendMessage(GOLD + "Author: CIVN");
				sender.sendMessage(GOLD + "Version: 1.1");
				return true;
			}

			else if (args.length == 1 | args.length == 2)
			{
				if (args[0].equalsIgnoreCase("volume") | args[0].equalsIgnoreCase("v"))
				{
					if (args.length == 1)
					{
						sender.sendMessage(prefix + GOLD + "Volume: " + AQUA + conf.getDouble("Sound.volume"));
						return true;
					}

					Double vol = Double.parseDouble (args[1]);

					if (vol == null)
					{
						return false;
					}

					else if (vol >= 1.1)
					{
						sender.sendMessage(prefix + RED + "You must set volume 0~1.0!");
						return false;
					}

					conf.set("Sound.volume", vol);
					this.saveConfig();
					this.reloadConfig();

					sender.sendMessage(prefix + GOLD + "You set volume " + AQUA + conf.getDouble("Sound.volume") + GOLD + "!");
					return true;
				}

				else if (args[0].equalsIgnoreCase("pitch") | args[0].equalsIgnoreCase("p"))
				{
					if (args.length == 1)
					{
						sender.sendMessage(prefix + GOLD + "Pitch: " + AQUA + conf.getDouble("Sound.pitch"));
						return true;
					}

					Double pit = Double.parseDouble (args[1]);

					if (pit == null)
					{
						return false;
					}

					else if (pit >= 2.1)
					{
						sender.sendMessage(prefix + RED + "You must set pitch 0~2.0!");
						return false;
					}

					conf.set("Sound.pitch", pit);
					this.saveConfig();
					this.reloadConfig();

					sender.sendMessage(prefix + GOLD + "You set pitch " + AQUA + conf.getDouble("Sound.pitch") + GOLD + "!");
					return true;
				}

				else if (args[0].equalsIgnoreCase("sound") | args[0].equalsIgnoreCase("s"))
				{
					if (args.length == 1)
					{
						sender.sendMessage(prefix + GOLD + "Sound: " + AQUA + conf.getString("Sound.sound"));
						return true;
					}

					String snd = args[1];

					try
					{
						Sound.valueOf (snd);
					}

					catch (Exception e)
					{
						sender.sendMessage(args[1] + RED + " does not exist!");
						return false;
					}

					conf.set("Sound.sound", snd);
					this.saveConfig();
					this.reloadConfig();

					sender.sendMessage(prefix + GOLD + "You set sound " + AQUA + conf.getString("Sound.sound") + GOLD + "!");
					return true;
				}

				else if (args[0].equalsIgnoreCase("help") | args[0].equalsIgnoreCase("?"))
				{
					sender.sendMessage(prefix);
					sender.sendMessage(GOLD + "/chatsound <volume or pitch> <value>");
					return true;
				}
			}

			return false;

		}

		return false;

	}

	@EventHandler
	public void onPlayerChat (AsyncPlayerChatEvent event)
	{
		Player p = event.getPlayer();

		double vol = conf.getDouble("Sound.volume");
		float volume = (float) vol;

		double pit = conf.getDouble("Sound.pitch");
		float pitch = (float) pit;

		Sound sound = Sound.valueOf (conf.getString("Sound.sound"));

		if (sound == null)
		{
			p.sendMessage(prefix + RED + "Can't load a sound name in config!");
			return;
		}

		p.getWorld().playSound(p.getLocation(), sound, volume, pitch);
		return;
	}
}