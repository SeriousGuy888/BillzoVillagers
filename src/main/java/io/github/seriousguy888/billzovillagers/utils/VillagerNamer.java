package io.github.seriousguy888.billzovillagers.utils;

import io.github.seriousguy888.billzovillagers.Main;
import io.github.seriousguy888.billzovillagers.config.MainConfig;
import io.github.seriousguy888.billzovillagers.config.NameListConfig;
import org.bukkit.entity.Villager;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VillagerNamer {
    private final MainConfig mainConfig = Main.getPlugin().getMainConfig();
    private final Random random = new Random();

    private final List<String>
            firstNames,
            lastNames,
            middleNames;

    public VillagerNamer() {
        NameListConfig nameListConfig = Main.getPlugin().getNameListConfig();
        firstNames = nameListConfig.getFirstNames();
        lastNames = nameListConfig.getLastNames();
        middleNames = Stream
                .concat(firstNames.stream(), lastNames.stream())
                .collect(Collectors.toList());
    }

    public void nameBredVillager(Villager child, Villager mother, Villager father) {
        if (mother.getCustomName() == null || father.getCustomName() == null)
            return;
        if (firstNames.isEmpty())
            return;

        String motherLastName = getLastNameOfName(mother.getCustomName());
        String fatherLastName = getLastNameOfName(father.getCustomName());

        String childFirstName = getRandomFirstName();
        String childLastName;

        if (/*motherLastName.equals(fatherLastName) ||*/ random.nextInt(10) == 0) {
            childLastName = String.format("%s-%s", motherLastName, fatherLastName);
        } else if (random.nextInt(20) == 0) {
            // Small chance for the child to choose a completely different last name
            childLastName = getRandomLastName();
        } else {
            childLastName = random.nextBoolean() ? motherLastName : fatherLastName;
        }

        // if percentage chance, give the child a middle name
        String childFullName = shouldUseMiddleName()
                ? String.format("%s %s %s", childFirstName, getRandomMiddleName(), childLastName)
                : String.format("%s %s", childFirstName, childLastName);
        child.setCustomName(childFullName);
    }

    private String getLastNameOfName(String name) {
        return name.substring(name.lastIndexOf(" ") + 1);
    }

    private boolean shouldUseMiddleName() {
        return (mainConfig.getMiddleNamesEnabled() &&
                random.nextInt(100) <= mainConfig.getMiddleNamesChancePercentage());
    }

    private String getRandomFirstName() {
        return firstNames.get(random.nextInt(firstNames.size()));
    }

    private String getRandomLastName() {
        return lastNames.get(random.nextInt(lastNames.size()));
    }

    private String getRandomMiddleName() {
        return middleNames.get(random.nextInt(middleNames.size()));
    }

    public String getRandomFullName() {
        return shouldUseMiddleName()
                ? String.format("%s %s %s", getRandomFirstName(), getRandomMiddleName(), getRandomLastName())
                : String.format("%s %s", getRandomFirstName(), getRandomLastName());
    }
}
