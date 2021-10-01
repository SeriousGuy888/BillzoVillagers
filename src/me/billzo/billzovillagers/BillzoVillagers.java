package me.billzo.billzovillagers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BillzoVillagers extends JavaPlugin {
  FileConfiguration config = getConfig();

  @Override
  public void onEnable() {
    config.addDefault("leashing.villager", true);
    config.addDefault("names.first", new String[]{"Aaliyah", "Abigail", "Adalyn", "Aiden", "Alexander", "Amelia", "Aria", "Ariana", "Asher", "Aubrey", "Aurora", "Ava", "Avery", "Barry", "Benjamin", "Berry", "Billzo", "Birch", "Blackberry", "Blackcherry", "Blueberry", "Blunt", "Bovine", "Caden", "Caleb", "Cameron", "Camilla", "Carry", "Carson", "Carter", "Cary", "Charlie", "Charlotte", "Chloe", "Chris", "Chunk", "Cocaniny", "Colton", "Cranberry", "Dairy", "Daniel", "Druggie", "Earring", "Elena", "Eliana", "Elijah", "Ella", "Ellie", "Emilia", "Emily", "Emma", "Ethan", "Evelyn", "Everly", "Ezra", "Fairy", "Ferry", "Fynchy", "Gabriel", "Gary", "Gerry", "Gianna", "Glary", "Glory", "Goldie", "Googly", "Grayson", "Hairy", "Hannah", "Harper", "Harry", "Henry", "Holdy", "Hrrm", "Hurrm", "Hurrrm", "Isaac", "Isabella", "Isaiah", "Isla", "Jack", "Jackie", "Jackson", "Jackson", "Jakob", "James", "Jasper", "Jayce", "Jayden", "Jerry", "Josiah", "Julian", "Karen", "Kerosene", "Kinsley", "Laim", "Larry", "Layla", "Lazy", "Leah", "Leo", "Levi", "Liam", "Lila", "Lily", "Lincoln", "Logan", "Luca", "Lucas", "Luke", "Luna", "Madelyn", "Madison", "Mary", "Mason", "Matteo", "Matthew", "Aary", "Maverick", "Maya", "Merry", "Mia", "Michael", "Mila", "Miles", "Most popular baby names of 2020", "Muhammad", "Noah", "Nora", "Nova", "Oliver", "Olivia", "Owen", "Paisley", "Parry", "Penelope", "Peyton", "Quantum", "Quarry", "Raspberry", "Riley", "Ryan", "Samuel", "Sarah", "Scarlett", "Scary", "Sebastian", "Sherry", "Smith", "Sophia", "Sorry", "Terry", "Toby", "Very", "Walrus", "Wary", "Weedy", "William", "Wyatt", "Yakson", "Yeti", "Zoe"});
    config.addDefault("names.last", new String[]{"Trumpet", "Snowman", "Toothbrush", "Smith", "Duck", "Ass", "Bass", "Bypass", "Class", "Crass", "Grass", "Mass", "Pass", "Gas", "Jackass", "Nervegas", "Trespass", "Pompeii", "Trump", "Biden", "Sandals", "Blunt", "Elchapo", "Pie", "Garfield", "Inbred", "Hrrm", "Hrrrm", "Hurm", "Hurrm", "Herrm", "Hurrrm", "Hrm", "Hrrrmson", "Claus", "Candycane", "Golem", "Stolen", "Goldfish", "Ingot", "Baron", "Christ", "Yeti", "Rubik", "Cube", "Death", "Nuke", "Sheppard", "Liam", "Bee", "Ironforests", "Jasper", "Bovine", "Thorium", "Plutonium", "Uranium", "Hexagon", "Matteoville", "Dragonson", "Bluntson", "Puzic", "Aardvark", "Weeeeed", "Druggledealerson", "Caterpillar", "Jasperlandtwo", "Holden", "Tobytopia", "Youtubson", "Netherite", "Moolah", "Hmm", "Hm", "Hmmm", "H'mm", "Jakob", "Porkchop", "Honag", "Ohio", "Newrhodes", "RandumPerson314"});
    config.options().copyDefaults(true);
    saveConfig();

    new TaskNameVillagers(this).runTaskTimer(this, 0L, 200L);

    getServer().getPluginManager().registerEvents(new Listeners(this), this);
    this.getCommand("villager").setExecutor(new CommandVillager());
  }

  @Override
  public void onDisable() {
    System.out.println("f");
  }
}