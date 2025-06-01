package cc.cassian.item_descriptions.client.config;

import dev.architectury.injectables.annotations.ExpectPlatform;
import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import java.nio.file.Path;
import java.util.List;

public class ModConfig extends WrappedConfig {


    //General settings

    @Comment("Enable Item Descriptions")
    public boolean itemDescriptions = true;

    @Comment("Always show Item Descriptions while hovering over an item in your inventory.")
    public boolean displayAlways = false;

    @Comment("Prioritize the Entity Description of the spawn egg over the generic spawn egg description.")
    public boolean spawnEggsShowEntity = true;

    @Comment("When Item Descriptions are being displayed, also display the mod name.")
    public boolean showModName = false;


    /**
     * Hint settings.
     */
    public Hint hint = new Hint();
    public static class Hint implements Section {

        @Comment("When descriptions on an item are not visible, show a tooltip with the default text 'Show more' and the keybind.")
        public boolean enabled = false;

        @Comment("Show keybinds in hint text.")
        public boolean showKeybind = true;

        @Comment("This setting changes what Minecraft colour is used for hint tooltips, either by colour code or name.")
        public String color = "Gray";

        @Comment("Italicize Hints")
        public boolean italics = false;

        @Comment("Force-uppercase hint keybinds.")
        public boolean uppercase = true;
    }

    /**
     * Style settings.
     */
    public Style style = new Style();
    public static class Style implements Section {

        @Comment("This setting changes what Minecraft colour is used for block, item, and entity tooltips, either by colour code or name.")
        public String color = "Gray";

        @Comment("This setting changes whether block, item, and entity tooltips are italicized.")
        public boolean italics = false;

        @Comment("This setting changes whether block, item, and entity tooltips are bolded.")
        public boolean bold = false;

        @Comment("Changes the minimum width of multi-line tooltip. Expands for longer item names. Ignored when ToolTipFix is present.")
        public int length = 160;

        @Comment("Color of the mod name tooltip")
        public String modNameColor = "Blue";
    }

    //Keybinds
    public Keybinds keybinds = new Keybinds();
    public static class Keybinds implements Section {

        @Comment("Show descriptions when the Ctrl key is held.")
        public boolean displayWhenControlIsHeld = true;

        @Comment("Show descriptions when the Shift key is held.")
        public boolean displayWhenShiftIsHeld = false;

        @Comment("Show descriptions when the Alt key is held.")
        public boolean displayWhenAltIsHeld = false;

        @Comment("Invert all Item Descriptions keybinds.")
        public boolean invert = false;
    }

    public BlockDescriptions blockDescriptions = new BlockDescriptions();
    public static class BlockDescriptions implements Section {

        @Comment("This will show Block Descriptions in compatible mods. This can also be disabled in Jade/WTHIT's settings.")
        public boolean enable = true;

        @Comment("Always show Block Descriptions, not just when a key is held.")
        public boolean showAlways = false;
    }

    public EntityDescriptions entityDescriptions = new EntityDescriptions();
    public static class EntityDescriptions implements Section {

        @Comment("This will show Entity Descriptions in compatible mods. This can also be disabled in Jade/WTHIT's settings.")
        public boolean enable = true;

        @Comment("Always show Entity Descriptions, not just when a key is held.")
        public boolean showAlways = false;
    }

    public EnchantmentDescriptions enchantmentDescriptions = new EnchantmentDescriptions();
    public static class EnchantmentDescriptions implements Section {

        @Comment("Enable enchantment descriptions.")
        public boolean enable = true;

        @Comment("Always show Enchantment Descriptions, not just when a key is held.")
        public boolean displayAlways = false;

        @Comment("By default, descriptions are shown on all enchanted items. Disable if this should only apply to books.")
        public boolean onlyShowOnBooks = false;
        /**
         * Hides Item Descriptions when Enchantment Descriptions are visible.
         */
    //    public boolean hideItemDescriptionIfEnchantmentDescriptionPresent = false;

        @Comment("This setting changes what Minecraft colour is used for enchantment tooltips, either by colour code or name.")
        public String color = "Dark Gray";

        @Comment("Italicize Enchantment Descriptions")
        public boolean italics = false;
    }

    //Developer settings
    public DeveloperOptions developer = new DeveloperOptions();
    public static class DeveloperOptions implements Section {

        @Comment("Replaces the description with a list of translation keys that can be used to match that item. Hold Alt to view their translations.")
        public boolean showAllPotentialKeys = false;

        @Comment("List of items that have their built-in tooltips disabled.")
        public List<String> items_with_tooltips_to_hide = ValueList.create("");

         @Comment("Show lore tags on untranslated items. This includes items meant to have generic descriptions! Disable after testing.")
        public boolean showUntranslated = false;

        @Comment("This will disable descriptions for items based off the block or item tags when a more specific description is not present (e.g. planks, slabs, stairs, tools, etc.).")
        public boolean disableTagDescriptions = false;

        @Comment("Forces Enchantment Descriptions to be enabled, even while similar mods are installed.")
        public boolean forceEnableEnchantmentDescriptions = false;

        @Comment("Creates placeholder en_us language files per-namespace in the minecraft/data/missing folder.")
        public boolean generateMissing = false;

        @Comment("Force enable Effect Descriptions, even when other mods are installed.")
        public boolean forceEnableEffectDescriptions = false;

        @Comment("Hide tooltips from other mods.")
        public boolean hideOtherTooltips = false;

    }

    /**
     * Effect Descriptions
     */
    public EffectDescriptions effectDescriptions = new EffectDescriptions();
    public static class EffectDescriptions implements Section {

        @Comment("Enable descriptions for Status Effects.")
        public boolean enable = true;

        @Comment("Always show Effect Descriptions, not just when a key is held.")
        public boolean displayAlways = false;

        @Comment("This setting changes what Minecraft colour is used for effect tooltips, either by colour code or name.")
        public String color = "Dark Gray";

        @Comment("Replace Item Descriptions with Effect Descriptions when present.")
        public boolean onlyShowEffectDescriptions = false;
    }

    @ExpectPlatform
    public static Path configPath() {
        throw new AssertionError();
    }
}