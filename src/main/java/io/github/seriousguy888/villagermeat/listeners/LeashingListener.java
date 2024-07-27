package io.github.seriousguy888.villagermeat.listeners;

import io.github.seriousguy888.villagermeat.Main;
import io.github.seriousguy888.villagermeat.config.MainConfig;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LeashingListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        MainConfig mainConfig = Main.getPlugin().getMainConfig();

        HumanEntity player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ItemStack heldItem = player.getInventory().getItemInMainHand();


        // Return if entity is not a living entity or if it's already leashed
        if (!(entity instanceof LivingEntity livingEntity) || livingEntity.isLeashed()) {
            return;
        }

        // Return if this isn't one of the mobs configured to be leashable
        if (!((livingEntity instanceof Villager && mainConfig.canLeashVillager()) ||
                (livingEntity instanceof ZombieVillager && mainConfig.canLeashZombieVillager()) ||
                (livingEntity instanceof WanderingTrader && mainConfig.canLeashWanderingTrader())
        )) return;


        // Return if player is not holding a lead
        if (!heldItem.isSimilar(new ItemStack(Material.LEAD))) {
            return;
        }

        event.setCancelled(true); // Cancel event (to prevent opening the trading menu, for example)
        livingEntity.setLeashHolder(player); // Attach the entity to the player

        // Remove a lead from the player's hand
        if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            heldItem.setAmount(heldItem.getAmount() - 1);
            player.getInventory().setItemInMainHand(heldItem);
        }
    }
}