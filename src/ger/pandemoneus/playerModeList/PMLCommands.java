package ger.pandemoneus.playerModeList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command class.
 * 
 * @author Pandemoneus - https://github.com/Pandemoneus
 * 
 */
public final class PMLCommands implements CommandExecutor {

	private static String pluginName;
	
	private static String chatPrefix;

	/**
	 * Associates this object with a plugin
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public PMLCommands(PlayerModeList plugin) {
		pluginName = plugin.getPluginName();
		
		chatPrefix = new StringBuilder("" + ChatColor.WHITE).append("[").append(ChatColor.GOLD).append(pluginName).append(ChatColor.WHITE).append("] ").toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		final int n = args.length;
		
		if (hasPerm(sender, ".list")) {
			if (n == 0) {
				final Player[] players = Bukkit.getServer().getOnlinePlayers();
				if (players.length <= 0) {
					sendMessage(sender, new StringBuilder("There are no players online."));
				} else {
					for (Player p : players) {
						sendMessage(sender, determineGameModeChatString(p));
					}
				}
				return true;
			} else if (n >= 1) {
				Player player = Bukkit.getServer().getPlayer(args[0]);
				if (player != null) {
					sendMessage(sender, determineGameModeChatString(player));
				} else {
					sendMessage(sender, new StringBuilder("Could not find the player named '").append(args[0]).append("'."));
				}
				return true;
			} else {
				return false;
			}
		} else {
			sendMessage(sender, new StringBuilder("You do not have the permission to use this command."));
			return true;
		}
	}
	
	private static void sendMessage(CommandSender sender, StringBuilder message) {
		sender.sendMessage(new StringBuilder(chatPrefix).append(message).toString());
	}
	
	private static boolean hasPerm(CommandSender sender, String perm) {
		return (sender.hasPermission(pluginName.toLowerCase() + perm) || sender.isOp());
	}
	
	private static StringBuilder determineGameModeChatString(Player player) {
		final StringBuilder sb = new StringBuilder(player.getName()).append(": ");
		
		if (player.getGameMode() == GameMode.SURVIVAL) {
			sb.append(ChatColor.RED).append("SURVIVAL");
		} else {
			sb.append(ChatColor.GREEN).append("CREATIVE");
		}
		
		return sb;
	}
}
