package de.pfannekuchen.juggernaut;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;

import net.kyori.adventure.text.Component;

/**
 * Simple events for the Juggernaut FFA Plugin
 * @author Pancake
  */
public class Events implements Listener {

	@EventHandler public void onPlayerBlockBreak(BlockBreakEvent e) { e.setCancelled(Game.shouldAllowInteraction(e.getPlayer())); }
	@EventHandler public void onPlayerBlockPlace(BlockPlaceEvent e) { e.setCancelled(Game.shouldAllowInteraction(e.getPlayer())); }
	@EventHandler public void onPlayerDamage(EntityDamageEvent e) { e.setCancelled(Game.shouldAllowInteraction(e.getEntity())); }
	@EventHandler public void onPlayerDrop(PlayerDropItemEvent e) { e.setCancelled(Game.shouldAllowInteraction(e.getPlayer())); }
	@EventHandler public void onPlayerAdvancement(PlayerAdvancementCriterionGrantEvent e) { e.setCancelled(true); }
	@EventHandler public void onPlayerPickup(EntityPickupItemEvent e) { e.setCancelled(!Game.isRunning); }
	@EventHandler public void onPlayerConsume(PlayerItemConsumeEvent e) { e.setCancelled(Game.shouldAllowInteraction(e.getPlayer())); }
	@EventHandler public void onInteractEvent(PlayerInteractEvent e) throws Exception { Game.onInteract(e.getPlayer(), e.getItem(), e.getAction()); }
	@EventHandler public void onInteractEvent2(PlayerInteractEvent e) { e.setCancelled(Game.shouldAllowInteraction(e.getPlayer())); }
	@EventHandler public void onRegenerateEvent(EntityRegainHealthEvent e) { 
		if (e.getRegainReason() == RegainReason.SATIATED || e.getRegainReason() == RegainReason.EATING || e.getRegainReason() == RegainReason.MAGIC_REGEN && e.getEntityType() == EntityType.PLAYER && Game.juggernaut != null) {
			if (e.getEntity().getName().equals(Game.juggernaut.getName())) e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		e.joinMessage(null);
		Game.onJoin(e.getPlayer());
	}
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		e.quitMessage(null);
		Game.onQuit(e.getPlayer());
	}
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
		e.deathMessage(null);
		Game.onDeath(e.getEntity());
	}

	@EventHandler
	public void onLateJoin(PlayerJoinEvent e) {
		Juggernaut.queuedPlayers.add(e.getPlayer().getUniqueId());
		new Thread(() -> {
			try {
				Thread.sleep(8000);
			} catch (Exception e2) {
				
			}
			if (Juggernaut.queuedPlayers.contains(e.getPlayer().getUniqueId()) && e.getPlayer().isOnline()) {
				Juggernaut.queuedPlayers.remove(e.getPlayer().getUniqueId());
				new BukkitRunnable() {
					@Override
					public void run() {
						e.getPlayer().kick(Component.text("Login Failed."));
					}
				}.runTask(Juggernaut.instance());
			}
		}).start();
	}
	
}