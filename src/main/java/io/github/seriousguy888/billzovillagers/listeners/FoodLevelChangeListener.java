package io.github.seriousguy888.billzovillagers.listeners;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import io.github.seriousguy888.billzovillagers.config.MainConfig;
import io.github.seriousguy888.billzovillagers.utils.MeatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onFoodLevelChange(PlayerItemConsumeEvent event) {
        MainConfig mainConfig = BillzoVillagers.getPlugin().getMainConfig();
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (!(new MeatManager().isVillagerMeat(item))) {
            return;
        }

        event.setCancelled(true);
        item.setAmount(item.getAmount() - 1);

        int newFoodLevel = Math.min(player.getFoodLevel() + mainConfig.getFoodPoints(), 20);
        float newSaturationLevel = Math.min(player.getSaturation() + mainConfig.getSaturationPoints(), newFoodLevel);

        player.setFoodLevel(newFoodLevel);
        player.setSaturation(newSaturationLevel);
    }
}