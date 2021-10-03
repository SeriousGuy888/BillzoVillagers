package me.billzo.billzovillagers.utils;

import me.billzo.billzovillagers.BillzoVillagers;
import org.bukkit.entity.Villager;

import java.util.Random;

public class VillagerRegistrationUtil {
  private String[] firstNames = BillzoVillagers.getPlugin().getConfig().getStringList("names.first").toArray(new String[0]);
  private String[] lastNames = BillzoVillagers.getPlugin().getConfig().getStringList("names.last").toArray(new String[0]);
  private Random random = new Random();

  public void nameBredVillager(Villager child, Villager mother, Villager father) {
    if(mother.getCustomName() == null || father.getCustomName() == null)
      return;
    if(firstNames.length < 1)
      return;

    String motherLastName = getLastNameOfName(mother.getCustomName());
    String fatherLastName = getLastNameOfName(father.getCustomName());
    String childLastName;

    if(motherLastName.equals(fatherLastName) || random.nextInt(10) == 0)
      childLastName = String.format("%s-%s", motherLastName, fatherLastName);
    else
      childLastName = random.nextBoolean() ? motherLastName : fatherLastName;

    child.setCustomName(String.format("%s %s", getRandomFirstName(), childLastName));
  }

  private String getLastNameOfName(String name) {
    return name.substring(name.lastIndexOf(" ") + 1);
  }

  private String getRandomFirstName() {
    return firstNames[random.nextInt(firstNames.length)];
  }
  private String getRandomLastName() {
    return lastNames[random.nextInt(lastNames.length)];
  }
  public String getRandomFullName() {
    return String.format("%s %s", getRandomFirstName(), getRandomLastName());
  }
}
