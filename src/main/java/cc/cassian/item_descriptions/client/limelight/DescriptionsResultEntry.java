package cc.cassian.item_descriptions.client.limelight;

//? if fabric {
//? if 1.21.1 || 1.21.5 {
/*import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.descriptions.EffectDescriptions;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import io.wispforest.limelight.api.entry.InvokeResultEntry;
import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

import static cc.cassian.item_descriptions.client.descriptions.ItemDescriptions.findLoreKey;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class DescriptionsResultEntry implements InvokeResultEntry {
    Component searchKey;

    public DescriptionsResultEntry(String s) {
        super();
        searchKey = findTranslationKey(s);
    }

    public static Component createMultilineTranslation(String loreKey) {
        // Setup list to store (potentially multi-line) tooltip.
        StringBuilder lines = new StringBuilder();
        //Check if the key exists.
        if (!loreKey.isEmpty()) {
            // Translate the lore key.
            String translatedKey = I18n.get(loreKey);
            // Check if the translated key exists.
            if (hasTranslation(loreKey)) {
                // Add the final tooltip.
                lines.append(translatedKey);
            }
        }
        return Component.literal(String.valueOf(lines));
    }

    private Component findTranslationKey(String s) {
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
        //Check to see if that namespaced Identifier matches an item. If so, return that item's lore key.
        if (ModClient.CONFIG.itemDescriptions.value()) {
            var itemStack = createMultilineTranslation(findLoreKey(BuiltInRegistries.ITEM
                    //? if >=1.21.2 {
                    .getValue
                     //?} else {
                    /^.get
                    ^///?}
                    (ModHelpers.of(namespace, item)).getDefaultInstance()).toString());
            if (!Objects.requireNonNull(itemStack.tryCollapseToString()).isEmpty()) return itemStack;
        }
        //Check to see if that namespaced Identifier matches a mob. If so, return that item's lore key.
        //This seems to return a Pig if it isn't matched correctly, so that is ignored if "pig" isn't actually typed in.
        if (ModClient.CONFIG.entityDescriptions.enable.value()) {
            var mobRegistry = BuiltInRegistries.ENTITY_TYPE
                    //? if >=1.21.2 {
                    .getValue
                     //?} else {
                    /^.get
                    ^///?}
                    (ModHelpers.of(namespace, item)).getDescriptionId();
            if (Objects.equals(mobRegistry, "entity.minecraft.pig") ) {
                if (item.equals("pig")) return createMultilineTranslation(DescriptionKey.ofTranslationKey(mobRegistry).toString());
            }
            else
                return createMultilineTranslation(DescriptionKey.ofTranslationKey(mobRegistry).toString());
        }
        // If a namespace match is not found, iterate through block and item registries for a name match.
        if (ModClient.CONFIG.itemDescriptions.value()) {
            Component itemRegistry = iterateRegistry(BuiltInRegistries.ITEM, lowerS);
            if (itemRegistry != null)
                return itemRegistry;
        }
        if (ModClient.CONFIG.blockDescriptions.enable.value()) {
            Component blockRegistry = iterateRegistry(BuiltInRegistries.BLOCK, lowerS);
            if (blockRegistry != null)
                return blockRegistry;
        }
        //If no match is found, return an empty string.
        return Component.literal("");


    }

    public Component iterateRegistry(DefaultedRegistry<?> registries, String lowerS) {
        final Component[] returnedKey = new Component[1];
        registries.stream().forEach(registryEntry -> {
            String registryKey;
            if (registryEntry instanceof Block block) {
                registryKey = block.getDescriptionId();
            }
            else if (registryEntry instanceof Item item) {
                registryKey = item.getDescriptionId();
            }
            else return;
            if (lowerS.equals(I18n.get(registryKey).toLowerCase())) {
                returnedKey[0] = createMultilineTranslation(DescriptionKey.ofTranslationKey(registryKey).toString());
            }
        });
        return returnedKey[0];
    }

    @Override
    public void run() {
        Minecraft.getInstance().keyboardHandler.setClipboard(searchKey.getString());
    }

    @Override
    public boolean closesScreen() {
        return false; //
    }

    public LimelightExtension extension() {
        return DescriptionsExtension.INSTANCE;
    }

    public String entryId() {
        return "item_descriptions:item_descriptions";
    }

    @Override
    public Component text() {
        return searchKey;
    }
}
*///?}
//?}