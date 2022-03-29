package me.unease.speeduhc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;

public class SpeedUHC extends JavaPlugin implements Listener {

	/**
	 * Called when the server enables the plugin.
	 * Prepares the plugin.
	 */
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
	}
	
	/**
	 * Called when a player joins the world.
	 * Prepares the player. 
	 *
	 * @param event Player join event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.teleport(player.getWorld().getSpawnLocation());
		event.joinMessage(null);
		Bukkit.broadcast(Component.text("\u00A7b\u00bb \u00A7a" + player.getName() + "\u00A77 has joined the game."));
	}
	
	/**
	 * Called when a block breaks.
	 * Replaces ore drops.
	 * 
	 * @param event Block break event
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Material type = event.getBlock().getType();
		
		event.setDropItems(false);
		if (type == Material.DIAMOND_ORE) {
			event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 2));
		} else if (type == Material.IRON_ORE) {
			event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 2));
		} else if (type == Material.GOLD_ORE) {
			event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 2));
		} else if (type == Material.COAL_ORE) {
			event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.TORCH, 4));
		} else {
			event.setDropItems(true);
		}
		
		
		
		
	}
}
