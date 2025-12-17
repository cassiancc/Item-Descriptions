package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DescriptionKey {
    private final String namespace;
    private final String path;
    private final String type;
    private String suffix;

    public DescriptionKey(String type, String namespace, String path) {
        this.type = type;
        this.namespace = namespace;
        this.path = path;
        this.suffix = "";
    }

    public DescriptionKey(String type, String namespace, String path, String suffix) {
        this.type = type;
        this.namespace = namespace;
        this.path = path;
        this.suffix = suffix;
    }

    public DescriptionKey(Identifier resourceLocation) {
        this.type = "lore";
        this.namespace = resourceLocation.getNamespace();
        this.path = resourceLocation.getPath();
        this.suffix = "";
    }

    public DescriptionKey(String resourceLocation) {
        var id = resourceLocation.split("\\.");
        if (id.length>2) {
            this.type = id[0];
            this.namespace = id[1];
            this.path = id[2];
        } else {
            this.type = id[0];
            this.namespace = "minecraft";
            this.path = id[1];
        }
        this.suffix = "";
    }

    public DescriptionKey(String type, Identifier resourceLocation) {
        this.type = type;
        this.namespace = resourceLocation.getNamespace();
        this.path = resourceLocation.getPath();
        this.suffix = "";
    }

    /**
     * Check if a lore key exists or if a generic tooltip should be used.
     */
    public static DescriptionKey checkLoreKey(DescriptionKey loreKey) {
        //Check if the tooltip translation key exists. If so, use the provided tooltip.
        if (loreKey != null && loreKey.hasTranslation()) return loreKey;
        else return empty();
    }

    /**
     * Convert block/item/entity translation keys to lore translation keys.
     */
    public static @NotNull DescriptionKey ofTranslationKey(String translationKey) {
        DescriptionKey loreKey;
        //Find the translation key for blocks.
        if (translationKey.contains("block.")) loreKey = new DescriptionKey(translationKey);
        //Find the translation key for items.
        else if ((translationKey.contains("item."))) loreKey = new DescriptionKey(translationKey);
        //Find the translation key for entities.
        else if ((translationKey.contains("entity."))) {
            //Entity descriptions use a different format as to avoiding colliding with items of the same name.
            loreKey = new DescriptionKey(translationKey);
            //Tropical fish have 20 different variants and their description should be the same.
            if (translationKey.contains("tropical_fish")) {
                loreKey = new DescriptionKey("entity", "minecraft", "tropical_fish");
            }
            //In case an entity tooltip is misconfigured, try checking for an "old style" key.
            else if (loreKey.hasTranslation()) return loreKey;
        }
        else return empty();
        return loreKey;
    }

    public String getNamespace() {
        return namespace;
    }

    public int getNamespacePrecision() {
        return switch (namespace) {
            case "c", "forge" -> 0;
            case "minecraft" -> 1;
            case "item_descriptions" -> 3;
            default -> 2;
        };
    }


    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Sets the suffix of the translation key, if the translation would exist.
     */
    public void setSafeSuffix(String suffix) {
        var oldSuffix = this.suffix;
        this.suffix = suffix;
        if (!hasTranslation()) {
            this.suffix = oldSuffix;
        }
    }

    public static DescriptionKey empty() {
        return new DescriptionKey("", "", "");
    }

    public DescriptionKey orElse(DescriptionKey other) {
        return this.isEmpty() ? other : this;
    }

    public static boolean isMorePrecise(DescriptionKey currentKey, DescriptionKey newKey) {
        if (currentKey.getNamespace().equals(newKey.getNamespace()))
            return currentKey.toString().length() < newKey.toString().length();
        else
            return currentKey.getNamespacePrecision() < newKey.getNamespacePrecision();
    }


    public boolean isEmpty() {
        return type.isEmpty() && namespace.isEmpty() && path.isEmpty() && suffix.isEmpty();
    }

    public String combineDotSeperated(List<String> args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            arg = ModHelpers.toTranslationKey(arg);
            if (!arg.isEmpty())
                sb.append(".").append(arg);
        }
        return sb.toString().replaceFirst(".", "");
    }

    /**
     * Convert to a translation key string suffixed with .description,
     * the primary and most specific description Item Descriptions uses.
     */
    public String asDescriptionTranslation() {
        return combineDotSeperated(List.of(type, namespace, path, "description", suffix));
    }

    /**
     * Convert to a translation key string suffixed with .desc,
     * similar to mods like Enchantment Descriptions and Better Than Adventure.
     */
    public String asDescTranslation() {
        return combineDotSeperated(List.of(type, namespace, path, "desc", suffix));
    }

    /**
     * Convert to a translation key string prefixed with "lore."
     * a secondary, less specific description Item Descriptions uses for blocks, items,
     * and entities that share descriptions.
     */
    public String asLoreTranslation() {
        return combineDotSeperated(List.of("lore", namespace, path, suffix));
    }

    @Override
    public String toString() {
        if (type.equals("tag") || ModHelpers.hasTranslation(asDescriptionTranslation())) {
            return asDescriptionTranslation();
        } else if (type.equals("item") && ModHelpers.hasTranslation(asLoreTranslation())) {
            return asLoreTranslation();
        } else if (!type.equals("item") && ModHelpers.hasTranslation(asDescTranslation())) {
            return asDescTranslation();
        }
        else return asLoreTranslation();
    }

    public MutableComponent toText() {
        var newAdd = toString();
        return ModHelpers.translatableWithFallback(newAdd, newAdd);
    }

    public boolean hasTranslation() {
        if (ModClient.CONFIG.developerOptions.showUntranslated.value()) return true;
        return I18n.exists(toString());
    }

    public boolean hasEmptyTranslation() {
        return hasTranslation() && I18n.get(toString()).trim().isEmpty();
    }
}
