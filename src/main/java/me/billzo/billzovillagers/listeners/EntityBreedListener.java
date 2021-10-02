package me.billzo.billzovillagers.listeners;

import me.billzo.billzovillagers.BillzoVillagers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import java.util.Random;

public class EntityBreedListener implements Listener {
  @EventHandler
  public void onEntityBreed(EntityBreedEvent event) {
    LivingEntity child = event.getEntity();
    LivingEntity mother = event.getMother();
    LivingEntity father = event.getFather();

    if(!(child instanceof Villager))
      return;
    if(mother.getCustomName() == null || father.getCustomName() == null)
      return;

    String[] firstNames = BillzoVillagers.getPlugin().getConfig().getStringList("names.first").toArray(new String[0]);
    if(firstNames.length < 1)
      return;

    String childFirstName = firstNames[new Random().nextInt(firstNames.length)];

    String motherLastName = mother.getCustomName().substring(mother.getCustomName().lastIndexOf(" ") + 1);
    String fatherLastName = father.getCustomName().substring(father.getCustomName().lastIndexOf(" ") + 1);
    String childLastName;

    if(motherLastName.equals(fatherLastName)) {
      childLastName = String.format("%s-%s", motherLastName, motherLastName);
    } else {
      childLastName = new Random().nextInt(2) == 1 ? motherLastName : fatherLastName;
    }

    child.setCustomName(String.format("%s %s", childFirstName, childLastName));
  }
}