package me.billzo.billzovillagers;

import me.billzo.billzovillagers.utils.VillagerRegistrationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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

        livingEntity.setCustomName(new VillagerRegistrationUtil().getRandomFullName());
        livingEntity.getWorld()
            .spawnParticle(Particle.VILLAGER_HAPPY, livingEntity.getEyeLocation(), 15, 0.5, 0.5, 0.5);
      });
    });
  }
}