package cc.cassian.item_descriptions.client.config;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModLists;
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
import java.util.List;

public class ModConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static ModConfig INSTANCE = new ModConfig();
    //General settings
    public boolean itemDescriptions = true;
    public boolean displayAlways = false;
    /**
     * When Item Descriptions are being displayed, also display the mod name.
     */
    public boolean showModName = false;
    // Hint
    public boolean hint_enabled = false;
    public boolean hint_showKeybind = true;
    public String hint_color = "Gray";
    public boolean hint_italics = false;
    //Style
    public String style_color = "Gray";
    public boolean style_italics = false;
    public boolean style_bold = false;
    public int style_length = 160;
    public String style_modNameColor = "Blue";
    //Keybinds
    public boolean keybind_displayWhenControlIsHeld = true;
    public boolean keybind_displayWhenShiftIsHeld = false;
    public boolean keybind_displayWhenAltIsHeld = false;
    public boolean keybind_invert = false;
    // Block/Entity Descriptions
    /**
     * This will show Block Descriptions in compatible mods. This can also be disabled in Jade/WTHIT's settings.
     */
    public boolean blockDescriptions = true;
    /**
     * Always show Block Descriptions, not just when a key is held.
     */
    public boolean displayBlockDescriptionsAlways = false;
    /**
     * This will show Entity Descriptions in compatible mods. This can also be disabled in Jade/WTHIT's settings.
     */
    public boolean entityDescriptions = true;
    /**
     * Always show Entity Descriptions, not just when a key is held.
     */
    public boolean displayEntityDescriptionsAlways = false;
    //Enchantment Descriptions
    public boolean enchantmentDescriptions = true;
    /**
     * Always show Enchantment Descriptions, not just when a key is held.
     */
    public boolean displayEnchantmentDescriptionsAlways = false;
    /**
     * By default, descriptions are shown on all enchanted items. Disable if this should only apply to books.
     */
    public boolean displayEnchantmentDescriptionsOnlyOnBooks = false;
    /**
     * Hides Item Descriptions when Enchantment Descriptions are visible.
     */
//    public boolean hideItemDescriptionIfEnchantmentDescriptionPresent = false;
    /**
     * This setting changes what Minecraft colour is used for enchantment tooltips, either by colour code or name.
     */
    public String enchantmentDescriptions_color = "Dark Gray";
    /**
     * Italicize Enchantment Descriptions
     */
    public boolean enchantmentDescriptions_italics = false;
    //Developer settings
    /**
     * Replaces the description with a list of translation keys that can be used to match that item. Hold Alt to view their translations.
     */
    public boolean developer_showAllPotentialKeys = false;
    /**
     * List of items that have their built-in tooltips disabled.
     */
    public List<String> developer_items_with_tooltips_to_hide = List.of("");
    /**
     * Show lore tags on untranslated items. This includes items meant to have generic descriptions! Disable after testing.
     */
    public boolean developer_showUntranslated = false;
    /**
     * This will disable descriptions for items based off the
     * block or item tags when a more specific description is not present
     * (e.g. planks, slabs, stairs, tools, etc.).
     */
    public boolean developer_disableTagDescriptions = false;
    /**
     * Forces Enchantment Descriptions to be enabled, even while similar mods are installed.
     */
    public boolean developer_forceEnableEnchantmentDescriptions = false;
    /**
     * Creates placeholder en_us language files per-namespace in the minecraft/data/missing folder.
     */
    public boolean developer_generateMissing = false;

    // Effect Descriptions
    /**
     * Enable descriptions for Status Effects.
     */
    public boolean effectDescriptions = true;
    /**
     * Always show Effect Descriptions, not just when a key is held.
     */
    public boolean display_effect_descriptions_always = false;
    public String effect_descriptions_color = "Dark Gray";
    /**
     * Replace Item Descriptions with Effect Descriptions when present.
     */
    public boolean display_effect_descriptions_only = false;
    /**
     * Force enable Effect Descriptions, even when other mods are installed.
     */
    public boolean developer_force_enable_effect_descriptions = false;
    public boolean developer_hide_other_tooltips = false;

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
        ModLists.loadLists();
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