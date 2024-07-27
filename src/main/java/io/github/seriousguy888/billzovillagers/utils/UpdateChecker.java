package io.github.seriousguy888.billzovillagers.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.seriousguy888.billzovillagers.Main;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class UpdateChecker {
    private final Main plugin;
    private final String githubRepo;

    private boolean updateAvailable = false;
    private String latestVersion;
    private String latestReleasePageURL;

    public UpdateChecker(Main plugin, String githubRepo) {
        this.plugin = plugin;
        this.githubRepo = githubRepo;
    }

    /**
     * Sends a GET request to the plugin's github repo, and checks if there is a release
     * with a higher version number than the currently installed version. If there is, log
     * that to the server console, and set updateAvailable to true.
     */
    public void checkForUpdates() {
        Logger logger = plugin.getLogger();
        String url = "https://api.github.com/repos/" + githubRepo + "/releases/latest";

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            logger.info("Checking for updates...");
            try {
                // create and send http request to the github api
                HttpClient httpClient = HttpClient.newBuilder().build();
                HttpRequest request = HttpRequest
                        .newBuilder()
                        .uri(URI.create(url))
                        .header("content-type", "application/json")
                        .GET()
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                // parse json response
                Gson gson = new Gson();
                JsonObject releaseJsonObject = gson.fromJson(response.body(), JsonObject.class);

                // get the latest release version on github and the version currently installed on the server
                String installedVersion = Main.getPlugin().getDescription().getVersion();
                latestVersion = releaseJsonObject.get("tag_name").getAsString();
                latestReleasePageURL = "https://github.com/" + githubRepo + "/releases/" + latestVersion;
                logger.info("Latest release found on GitHub: " + latestVersion);

                // check if the latest found version is newer than the current version
                if (compareVersions(latestVersion, installedVersion) > 0) {
                    logger.info("Newer version available! " + latestReleasePageURL);
                    updateAvailable = true;
                } else {
                    logger.info("Up to date.");
                }
            } catch (IOException | InterruptedException | NullPointerException ex) {
                logger.warning("Skipped update checking because an error occurred " +
                        "while fetching latest version from GitHub.\n" + ex);
            }
        });

    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public String getLatestReleasePageURL() {
        return latestReleasePageURL;
    }

    // stolen from https://www.baeldung.com/java-comparing-versions
    private int compareVersions(String version1, String version2) {
        int comparisonResult = 0;

        version1 = version1.replaceAll("[^0-9.]", "");
        version2 = version2.replaceAll("[^0-9.]", "");

        String[] version1Splits = version1.split("\\.");
        String[] version2Splits = version2.split("\\.");
        int maxLengthOfVersionSplits = Math.max(version1Splits.length, version2Splits.length);

        for (int i = 0; i < maxLengthOfVersionSplits; i++) {
            Integer v1 = i < version1Splits.length ? Integer.parseInt(version1Splits[i]) : 0;
            Integer v2 = i < version2Splits.length ? Integer.parseInt(version2Splits[i]) : 0;
            int compare = v1.compareTo(v2);
            if (compare != 0) {
                comparisonResult = compare;
                break;
            }
        }
        return comparisonResult;
    }
}
