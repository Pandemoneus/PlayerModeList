package ger.pandemoneus.playerModeList;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PlayerModeList plug-in.
 * 
 * Lists which player is in which mode.
 * 
 * @author Pandemoneus - https://github.com/Pandemoneus
 * 
 */
public class PlayerModeList extends JavaPlugin {
	/*
	 * Plug-in related stuff
	 */
	private String version;
	private String pluginName;
	
	private PMLCommands cmdExecutor;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDisable() { 
		// cancel all scheduled triggers
		getServer().getScheduler().cancelTasks(this);
			
		Log.info(new StringBuilder(pluginName).append(" disabled").toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable() {
		// set the name and version of this plug-in according to the plugin.yml file
		PluginDescriptionFile pdfFile = getDescription();
		version = pdfFile.getVersion();
		pluginName = pdfFile.getName();
		
		// set the owner of the logger
		Log.setLoggingPlugin(this);
		Log.info(new StringBuilder(pluginName).append(" v").append(version).append(" enabled").toString());
		
		// associate all commands to our custom command executor
		cmdExecutor = new PMLCommands(this);
		getCommand("playermodelist").setExecutor(cmdExecutor);
		getCommand("pml").setExecutor(cmdExecutor);
	}

	/**
	 * Returns the version of the plug-in.
	 * 
	 * @return the version of the plug-in
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Returns the name of the plug-in.
	 * 
	 * @return the name of the plug-in
	 */
	public String getPluginName() {
		return pluginName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return pluginName;
	}
}
