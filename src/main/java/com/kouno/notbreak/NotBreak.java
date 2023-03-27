package com.kouno.notbreak;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class NotBreak extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EntityType entityType = event.getEntityType();
        if (Arrays.asList(EntityType.ARMOR_STAND, EntityType.ITEM_FRAME).contains(entityType)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND || event.getEntityType() == EntityType.ITEM_FRAME) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                if (player.getGameMode() == GameMode.ADVENTURE) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof org.bukkit.block.BlockState) {
            org.bukkit.block.BlockState blockState = (org.bukkit.block.BlockState) holder;
            if (Arrays.asList(Material.ARMOR_STAND, Material.ITEM_FRAME).contains(blockState.getType())) {
                Player player = (Player) event.getWhoClicked();
                if (player.getGameMode() == GameMode.ADVENTURE) {
                    event.setCancelled(true);
                    ItemStack cursor = player.getItemOnCursor();
                    if (event.getSlot() == event.getRawSlot() && cursor.getType() != Material.AIR) {
                        player.setItemOnCursor(null);
                    }
                }
            }
        }
    }
}