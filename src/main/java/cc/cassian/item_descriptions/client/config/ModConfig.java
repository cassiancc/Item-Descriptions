package cc.cassian.item_descriptions.client.config;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.SerializedName;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import java.util.List;

public class ModConfig extends ReflectiveConfig {


    //General settings

    @SerializedName("item_descriptions")
    @Comment("Enable Item Descriptions")
    public final TrackedValue<Boolean> itemDescriptions = this.value(true);

    @SerializedName("display_always")
    @Comment("Always show Item Descriptions while hovering over an item in your inventory.")
    public final TrackedValue<Boolean> displayAlways = this.value(false);

    @SerializedName("spawn_eggs_show_entity")
    @Comment("Prioritize the Entity Description of the spawn egg over the generic spawn egg description.")
    public final TrackedValue<Boolean> spawnEggsShowEntity = this.value(true);

    @SerializedName("show_mod_name")
    @Comment("When Item Descriptions are being displayed, also display the mod name.")
    public final TrackedValue<Boolean> showModName = this.value(false);

    @Comment("Add Item Descriptions Info Recipes to Enchanted Books when using Reliable Recipe Viewer")
    public final TrackedValue<Boolean> addToRecipeViewers = this.value(false);


    /**
     * Hint settings.
     */
    public final Hint hint = new Hint();
    public static class Hint extends Section {

        @Comment("When descriptions on an item are not visible, show a tooltip with the default text 'Show more' and the keybind.")
        public final TrackedValue<Boolean> enabled = this.value(false);

        @SerializedName("show_keybinds")
        @Comment("Show keybinds in hint text.")
        public final TrackedValue<Boolean> showKeybinds = this.value(true);

        @Comment("This setting changes what colour code is used for hint tooltips.")
        public final TrackedValue<Integer> colour = this.value(11184810);

        @Comment("Italicize Hints")
        public final TrackedValue<Boolean> italics = this.value(false);

        @Comment("Force-uppercase hint keybinds.")
        public final TrackedValue<Boolean> uppercase = this.value(true);
    }

    /**
     * Style settings.
     */
    public final Style style = new Style();
    public static class Style extends Section {

        @Comment("This setting changes what colour code is used for block, item, and entity tooltips.")
        public final TrackedValue<Integer> colour = this.value(11184810);

        @Comment("This setting changes whether block, item, and entity tooltips are italicized.")
        public final TrackedValue<Boolean> italics = this.value(false);

        @Comment("This setting changes whether block, item, and entity tooltips are bolded.")
        public final TrackedValue<Boolean> bold = this.value(false);

        @Comment("Changes the minimum width of multi-line tooltip. Expands for longer item names. Ignored when ToolTipFix is present.")
        public final TrackedValue<Integer> length = this.value(160);

        @SerializedName("mod_name_colour")
        @Comment("Colour of the mod name tooltip")
        public final TrackedValue<Integer> modNameColour = this.value(5592575);

        @SerializedName("mod_name_italics")
        @Comment("Whether to italicize the mod name tooltip")
        public final TrackedValue<Boolean> modNameItalics = this.value(true);
    }

    //Keybinds
    public final Keybinds keybinds = new Keybinds();
    public static class Keybinds extends Section {

        @SerializedName("display_when_ctrl_is_held")
        @Comment("Show descriptions when the Ctrl key is held.")
        public final TrackedValue<Boolean> displayWhenCtrlIsHeld = this.value(true);

        @SerializedName("display_when_shift_is_held")
        @Comment("Show descriptions when the Shift key is held.")
        public final TrackedValue<Boolean> displayWhenShiftIsHeld = this.value(false);

        @SerializedName("display_when_alt_is_held")
        @Comment("Show descriptions when the Alt key is held.")
        public final TrackedValue<Boolean> displayWhenAltIsHeld = this.value(false);

        @Comment("Invert all Item Descriptions keybinds.")
        public final TrackedValue<Boolean> invert = this.value(false);
    }

    @SerializedName("block_descriptions")
    public final BlockDescriptions blockDescriptions = new BlockDescriptions();
    public static class BlockDescriptions extends Section {

        @Comment("This will show Block Descriptions in compatible mods. This can also be disabled in Jade/WTHIT's settings.")
        public final TrackedValue<Boolean> enable = this.value(true);

        @SerializedName("show_always")
        @Comment("Always show Block Descriptions, not just when a key is held.")
        public final TrackedValue<Boolean> showAlways = this.value(false);
    }

    @SerializedName("entity_descriptions")
    public final EntityDescriptions entityDescriptions = new EntityDescriptions();
    public static class EntityDescriptions extends Section {

        @Comment("This will show Entity Descriptions in compatible mods. This can also be disabled in Jade/WTHIT's settings.")
        public final TrackedValue<Boolean> enable = this.value(true);

        @SerializedName("show_always")
        @Comment("Always show Entity Descriptions, not just when a key is held.")
        public final TrackedValue<Boolean> showAlways = this.value(false);
    }

    @SerializedName("enchantment_descriptions")
    public final EnchantmentDescriptions enchantmentDescriptions = new EnchantmentDescriptions();
    public static class EnchantmentDescriptions extends Section {

        @SerializedName("enable")
        @Comment("Enable enchantment descriptions.")
        public final TrackedValue<Boolean> enable = this.value(true);

        @SerializedName("display_always")
        @Comment("Always show Enchantment Descriptions, not just when a key is held.")
        public final TrackedValue<Boolean> displayAlways = this.value(false);

        @SerializedName("only_show_on_books")
        @Comment("By default, descriptions are shown on all enchanted items. Disable if this should only apply to books.")
        public final TrackedValue<Boolean> onlyShowOnBooks = this.value(false);

        @SerializedName("only_enchantment_descriptions_on_books")
        @Comment("Hide Item Descriptions on Enchanted Books, since they'll always have an Enchantment Description.")
        public final TrackedValue<Boolean> onlyEnchantmentDescriptionsOnBooks = this.value(true);

        @SerializedName("show_in_table")
        @Comment("Show Enchantment Descriptions in the Enchanting Table")
        public final TrackedValue<Boolean> enchantingTable = this.value(true);

        /**
         * Hides Item Descriptions when Enchantment Descriptions are visible.
         */
    //    public final TrackedValue<Boolean> hideItemDescriptionIfEnchantmentDescriptionPresent = this.value(false);

        @SerializedName("colour")
        @Comment("This setting changes what colour code is used for enchantment tooltips.")
        public final TrackedValue<Integer> colour = this.value(5592405);

        @Comment("Italicize Enchantment Descriptions")
        public final TrackedValue<Boolean> italics = this.value(false);

        @Comment("Add Enchantment Descriptions Info Recipes to Enchanted Books when using Reliable Recipe Viewer")
        public final TrackedValue<Boolean> addToRecipeViewers = this.value(true);
    }

    /**
     * Effect Descriptions
     */
    @SerializedName("effect_descriptions")
    public final EffectDescriptions effectDescriptions = new EffectDescriptions();
    public static class EffectDescriptions extends Section {

        @Comment("Enable descriptions for Status Effects.")
        public final TrackedValue<Boolean> enable = this.value(true);

        @SerializedName("display_always")
        @Comment("Always show Effect Descriptions, not just when a key is held.")
        public final TrackedValue<Boolean> displayAlways = this.value(false);

        @Comment("This setting changes what colour code is used for effect tooltips.")
        public final TrackedValue<Integer> colour = this.value(5592405);

        @SerializedName("only_show_effect_descriptions")
        @Comment("Replace Item Descriptions with Effect Descriptions when present.")
        public final TrackedValue<Boolean> onlyShowEffectDescriptions = this.value(false);

        @Comment("Add Effect Descriptions Info Recipes to Potions when using Reliable Recipe Viewer")
        public final TrackedValue<Boolean> addToRecipeViewers = this.value(true);
    }

    //Developer settings
    @SerializedName("developer_options")
    public final DeveloperOptions developerOptions = new DeveloperOptions();
    public static class DeveloperOptions extends Section {

        @SerializedName("show_all_potential_keys")
        @Comment("Replaces the description with a list of translation keys that can be used to match that item. Hold Alt to view their translations.")
        public final TrackedValue<Boolean> showAllPotentialKeys = this.value(false);

        @SerializedName("items_with_tooltips_to_hide")
        @Comment("List of items that have their built-in tooltips disabled.")
        public final TrackedValue<List<String>> itemsWithTooltipsToHide = this.value(ValueList.create(""));

        @SerializedName("show_untranslated")
        @Comment("Show lore tags on untranslated items. This includes items meant to have generic descriptions! Disable after testing.")
        public final TrackedValue<Boolean> showUntranslated = this.value(false);

        @SerializedName("disable_tag_descriptions")
        @Comment("This will disable descriptions for items based off the block or item tags when a more specific description is not present (e.g. planks, slabs, stairs, tools, etc.).")
        public final TrackedValue<Boolean> disableTagDescriptions = this.value(false);

        @SerializedName("force_enable_enchantment_descriptions")
        @Comment("Forces Enchantment Descriptions to be enabled, even while similar mods are installed.")
        public final TrackedValue<Boolean> forceEnableEnchantmentDescriptions = this.value(false);

        @SerializedName("generate_missing")
        @Comment("Creates placeholder en_us language files per-namespace in the minecraft/data/missing folder.")
        public final TrackedValue<Boolean> generateMissing = this.value(false);

        @SerializedName("force_enable_enchantment_descriptions")
        @Comment("Force enable Effect Descriptions, even when other mods are installed.")
        public final TrackedValue<Boolean> forceEnableEffectDescriptions = this.value(false);

        @SerializedName("hide_other_tooltips")
        @Comment("Hide tooltips from other mods.")
        public final TrackedValue<Boolean> hideOtherTooltips = this.value(false);

        @SerializedName("config_screen")
        @Comment("Override the backend config library. Available options are 'cloth-config' and 'yacl'")
        public final TrackedValue<String> configScreen = this.value("");
    }
}