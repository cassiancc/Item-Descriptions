package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;

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

    public DescriptionKey(Identifier identifier) {
        this.type = "lore";
        this.namespace = identifier.getNamespace();
        this.path = identifier.getPath();
        this.suffix = "";
    }

    public DescriptionKey(String type, Identifier identifier) {
        this.type = type;
        this.namespace = identifier.getNamespace();
        this.path = identifier.getPath();
        this.suffix = "";
    }

    public String getNamespace() {
        return namespace;
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

    public boolean isEmpty() {
        return type.isEmpty() && namespace.isEmpty() && path.isEmpty() && suffix.isEmpty();
    }

    public String combineDotSeperated(List<String> args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            if (!arg.isEmpty())
                sb.append(".").append(arg);
        }
        return sb.toString();
    }

    public String asTagTranslation() {
        return combineDotSeperated(List.of("tag", namespace, path, "description"));
    }

    public String asLoreTranslation() {
        return combineDotSeperated(List.of("tag", namespace, path, "description"));
    }

    @Override
    public String toString() {
        if (Objects.equals(type, "tag")) {
            return asTagTranslation();
        }
        else return asLoreTranslation();
    }

    public Text toText() {
        var newAdd = toString();
        //? if >1.20 {
        return Text.translatableWithFallback(newAdd, newAdd);
        //?} else {
        /*if (ModHelpers.hasTranslation(newAdd)) {
            return Text.translatable(newAdd);
        }
        else return Text.literal(newAdd);
        *///?}
    }

    public boolean hasTranslation() {
        if (ModConfig.get().developer_showUntranslated) return true;
        return I18n.hasTranslation(toString());
    }

    public DescriptionKey ofItem(String translationKey) {

    }
}
