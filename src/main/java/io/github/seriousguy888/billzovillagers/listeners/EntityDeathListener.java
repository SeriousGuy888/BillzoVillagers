package io.github.seriousguy888.billzovillagers.listeners;

import io.github.seriousguy888.billzovillagers.utils.MeatManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class EntityDeathListener implements Listener {
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    LivingEntity victim = event.getEntity();
    Player killer = victim.getKiller();

    if((victim instanceof Villager) || (victim instanceof WanderingTrader)) {
      boolean wanderingTrader = victim instanceof WanderingTrader;
      ItemStack itemStack = new MeatManager().getVillagerMeat(wanderingTrader);

      int amount;
      int randomBound = 3;
      if(killer != null) { // if a player kills, check for looting and apply accordingly
        ItemStack weapon = killer.getInventory().getItemInMainHand();
        int lootingLvl = weapon.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
        randomBound += lootingLvl; // https://minecraft.fandom.com/wiki/Looting#Usage
      }

      amount = new Random().nextInt(randomBound) + 1;
      itemStack.setAmount(amount);
      victim.getWorld().dropItem(victim.getLocation(), itemStack);
    }
  }
}