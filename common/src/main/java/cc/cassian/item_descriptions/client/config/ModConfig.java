package cc.cassian.item_descriptions.client.config;

import cc.cassian.item_descriptions.client.ModClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static ModConfig INSTANCE = new ModConfig();
    //General settings
    public boolean itemDescriptions = true;
    public boolean displayAlways = false;
    //Style
    public String style_color = "Gray";
    public boolean style_italics = false;
    public boolean style_bold = false;
    public int style_length = 40;
    //Keybinds
    public boolean keybind_displayWhenControlIsHeld = true;
    public boolean keybind_displayWhenShiftIsHeld = false;
    public boolean keybind_displayWhenAltIsHeld = false;
    public boolean keybind_invert = false;
    //Block/Entity Descriptions
    public boolean blockDescriptions = true;
    public boolean displayBlockDescriptionsAlways = false;
    public boolean entityDescriptions = true;
    public boolean displayEntityDescriptionsAlways = false;
    public int compat_limelightLength = 15;
    //Developer settings
    public boolean developer_showUntranslated = false;
    public boolean developer_dontTranslate = false;
    public boolean developer_disableGenericStringDescriptions = true;
    public boolean developer_disableGenericTagDescriptions = false;


    public static void load() {
        if (!Files.exists(configPath())) {
            save();
            return;
        }

        try (var input = Files.newInputStream(configPath())) {
            INSTANCE = GSON.fromJson(new InputStreamReader(input, StandardCharsets.UTF_8), ModConfig.class);
        } catch (IOException e) {
            ModClient.LOGGER.warn("Unable to load config file!");
        }
    }

    public static void save() {
        try (var output = Files.newOutputStream(configPath()); var writer = new OutputStreamWriter(output, StandardCharsets.UTF_8)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            ModClient.LOGGER.warn("Unable to save config file!");
        }
    }

    public static ModConfig get() {
        if (INSTANCE == null) INSTANCE = new ModConfig();
        return INSTANCE;
    }

    @ExpectPlatform
    static Path configPath() {
        throw new AssertionError();
    }
}