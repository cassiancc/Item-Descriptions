package cc.cassian.item_descriptions.client.limelight;

//? if 1.21.1 {
/*import cc.cassian.item_descriptions.client.config.ModConfig;
import io.wispforest.limelight.api.entry.InvokeResultEntry;
import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;
import static net.minecraft.client.resource.language.I18n.hasTranslation;
import static net.minecraft.client.resource.language.I18n.translate;

public class DescriptionsResultEntry implements InvokeResultEntry {
    Text searchKey;

    public DescriptionsResultEntry(String s) {
        super();
        searchKey = findTranslationKey(s);
    }

    public static Text createMultilineTranslation(String loreKey) {
        // Setup list to store (potentially multi-line) tooltip.
        StringBuilder lines = new StringBuilder();
        //Check if the key exists.
        if (!loreKey.isEmpty()) {
            // Translate the lore key.
            String translatedKey = translate(loreKey);
            // Check if the translated key exists.
            if (hasTranslation(loreKey)) {
                // Add the final tooltip.
                lines.append(translatedKey);
            }
        }
        return Text.literal(String.valueOf(lines));
    }

    private Text findTranslationKey(String s) {
        String lowerS = s.toLowerCase();
        // Take in user input, remove the #, and remove any spaces.
        String trimmedS = lowerS.replace(" ", "_");
        // Set up a default namespace and item, in case namespace isn't specified, e.g. minecraft:user_input
        String namespace = "minecraft";
        String item = trimmedS;
        // If a namespace is provided, change the namespace, e.g. user:input
        if (trimmedS.contains(":")) {
            String[] splitS = trimmedS.split(":");
            namespace = splitS[0];
            item = splitS[1];
        }
        //Check to see if that namespaced identifier matches an item. If so, return that item's lore key.
        if (ModConfig.get().itemDescriptions) {
            var itemStack = createMultilineTranslation(findItemLoreKey(Registries.ITEM.get(Identifier.of(namespace, item)).getDefaultStack()).toString());
            if (!Objects.requireNonNull(itemStack.getLiteralString()).isEmpty()) return itemStack;
        }
        //Check to see if that namespaced identifier matches a mob. If so, return that item's lore key.
        //This seems to return a Pig if it isn't matched correctly, so that is ignored if "pig" isn't actually typed in.
        if (ModConfig.get().entityDescriptions) {
            var mobRegistry = Registries.ENTITY_TYPE.get(Identifier.of(namespace, item)).getTranslationKey();
            if (Objects.equals(mobRegistry, "entity.minecraft.pig") ) {
                if (item.equals("pig")) return createMultilineTranslation(convertToLoreKey(mobRegistry).toString());
            }
            else
                return createMultilineTranslation(convertToLoreKey(mobRegistry).toString());
        }
        // If a namespace match is not found, iterate through block and item registries for a name match.
        if (ModConfig.get().itemDescriptions) {
            Text itemRegistry = iterateRegistry(Registries.ITEM, lowerS);
            if (itemRegistry != null)
                return itemRegistry;
        }
        if (ModConfig.get().blockDescriptions) {
            Text blockRegistry = iterateRegistry(Registries.BLOCK, lowerS);
            if (blockRegistry != null)
                return blockRegistry;
        }
        //If no match is found, return an empty string.
        return Text.literal("");


    }

    public Text iterateRegistry(DefaultedRegistry<?> registries, String lowerS) {
        final Text[] returnedKey = new Text[1];
        registries.stream().forEach(registryEntry -> {
            String registryKey;
            if (registryEntry instanceof Block block) {
                registryKey = block.getTranslationKey();
            }
            else if (registryEntry instanceof Item item) {
                registryKey = item.getTranslationKey();
            }
            else return;
            if (lowerS.equals(translate(registryKey).toLowerCase())) {
                returnedKey[0] = createMultilineTranslation(convertToLoreKey(registryKey).toString());
            }
        });
        return returnedKey[0];
    }

    @Override
    public void run() {
        MinecraftClient.getInstance().keyboard.setClipboard(searchKey.getLiteralString());
    }

    @Override
    public boolean closesScreen() {
        return false; //
    }

    public LimelightExtension extension() {
        return DescriptionsExtension.INSTANCE;
    }

    public String entryId() {
        return "item-descriptions:item_descriptions";
    }

    @Override
    public Text text() {
        return searchKey;
    }
}
*///?}