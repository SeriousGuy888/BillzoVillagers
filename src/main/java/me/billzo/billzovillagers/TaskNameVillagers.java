package me.billzo.billzovillagers;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class TaskNameVillagers extends BukkitRunnable {
  @Override
  public void run() {
    Bukkit.getWorlds().forEach(world -> {
      List<LivingEntity> livingEntities = world.getLivingEntities();
      livingEntities.forEach(livingEntity -> {
        if(!(livingEntity instanceof Villager))
          return;
        if(livingEntity.getCustomName() != null)
          return;


        String[] firstNames = BillzoVillagers.getPlugin().getConfig().getStringList("names.first").toArray(new String[0]);
        String[] lastNames = BillzoVillagers.getPlugin().getConfig().getStringList("names.last").toArray(new String[0]);
        if(firstNames.length < 1 || lastNames.length < 1)
          return;

        Random generator = new Random();
        livingEntity.setCustomName(
            firstNames[generator.nextInt(firstNames.length)] + " " +
                lastNames[generator.nextInt(lastNames.length)]);
        livingEntity.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, livingEntity.getEyeLocation(), 15, 0.5, 0.5, 0.5);
      });
    });
  }
}