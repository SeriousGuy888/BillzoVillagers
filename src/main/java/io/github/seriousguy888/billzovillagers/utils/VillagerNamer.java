package io.github.seriousguy888.billzovillagers.utils;

import io.github.seriousguy888.billzovillagers.BillzoVillagers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Villager;

import java.util.Random;

public class VillagerNamer {
  private final FileConfiguration config = BillzoVillagers.getPlugin().getConfig();

  private final String[] firstNames = config.getStringList("names.first").toArray(new String[0]);
  private final String[] lastNames = config.getStringList("names.last").toArray(new String[0]);

  private final Random random = new Random();

  public void nameBredVillager(Villager child, Villager mother, Villager father) {
    if(mother.getCustomName() == null || father.getCustomName() == null)
      return;
    if(firstNames.length < 1)
      return;

    String motherLastName = getLastNameOfName(mother.getCustomName());
    String fatherLastName = getLastNameOfName(father.getCustomName());

    String childFirstName = getRandomFirstName();
    String childMiddleName = getRandomLastName(); // choose a random name from last names as a middle name
    String childLastName;

    if(motherLastName.equals(fatherLastName) || random.nextInt(10) == 0) {
      childLastName = String.format("%s-%s", motherLastName, fatherLastName);
    } else {
      childLastName = random.nextBoolean() ? motherLastName : fatherLastName;
    }

    // if percentage chance, give the child a middle name
    String childFullName = shouldUseMiddleName()
      ? String.format("%s %s %s", childFirstName, childMiddleName, childLastName)
      : String.format("%s %s", childFirstName, childLastName);
    child.setCustomName(childFullName);
  }

  private String getLastNameOfName(String name) {
    return name.substring(name.lastIndexOf(" ") + 1);
  }
  private boolean shouldUseMiddleName() {
    final boolean enableMiddleNames = config.getBoolean("naming.middle_names.enabled");
    final double middleNameChance = config.getDouble("naming.middle_names.chance_percentage");
    return enableMiddleNames && new Random().nextInt(100) <= middleNameChance;
  }

  private String getRandomFirstName() {
    return firstNames[random.nextInt(firstNames.length)];
  }
  private String getRandomLastName() {
    return lastNames[random.nextInt(lastNames.length)];
  }
  public String getRandomFullName() {
    return shouldUseMiddleName()
        ? String.format("%s %s %s", getRandomFirstName(), getRandomLastName(), getRandomLastName())
        : String.format("%s %s", getRandomFirstName(), getRandomLastName());
  }
}
