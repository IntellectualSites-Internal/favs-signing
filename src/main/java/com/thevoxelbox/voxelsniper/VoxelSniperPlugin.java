package com.thevoxelbox.voxelsniper;

import com.thevoxelbox.voxelsniper.command.CommandRegistry;
import com.thevoxelbox.voxelsniper.listener.PlayerInteractListener;
import com.thevoxelbox.voxelsniper.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit extension point.
 */
public class VoxelSniperPlugin extends JavaPlugin {

	private VoxelSniperConfig voxelSniperConfig;
	private BrushRegistry brushRegistry;
	private SniperRegistry sniperRegistry;

	@Override
	public void onEnable() {
		this.voxelSniperConfig = loadConfig();
		this.brushRegistry = loadBrushRegistry();
		this.sniperRegistry = new SniperRegistry(this);
		loadCommands();
		loadListeners();
	}

	private VoxelSniperConfig loadConfig() {
		saveDefaultConfig();
		FileConfiguration config = getConfig();
		return new VoxelSniperConfig(config);
	}

	private BrushRegistry loadBrushRegistry() {
		BrushRegistry brushRegistry = new BrushRegistry();
		BrushRegistrar brushRegistrar = new BrushRegistrar(brushRegistry);
		brushRegistrar.registerBrushes();
		return brushRegistry;
	}

	private void loadCommands() {
		CommandRegistry commandRegistry = new CommandRegistry(this);
		CommandRegistrar commandRegistrar = new CommandRegistrar(this, commandRegistry);
		commandRegistrar.registerCommands();
	}

	private void loadListeners() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		pluginManager.registerEvents(new PlayerInteractListener(this), this);
	}

	/**
	 * Returns object for accessing global VoxelSniper options.
	 *
	 * @return {@link VoxelSniperConfig} object for accessing global VoxelSniper options.
	 */
	public VoxelSniperConfig getVoxelSniperConfig() {
		return this.voxelSniperConfig;
	}

	/**
	 * Returns {@link BrushRegistry} for current instance.
	 *
	 * @return Brush Manager for current instance.
	 */
	public BrushRegistry getBrushRegistry() {
		return this.brushRegistry;
	}

	/**
	 * Returns {@link SniperRegistry} for current instance.
	 *
	 * @return SniperRegistry
	 */
	public SniperRegistry getSniperRegistry() {
		return this.sniperRegistry;
	}
}