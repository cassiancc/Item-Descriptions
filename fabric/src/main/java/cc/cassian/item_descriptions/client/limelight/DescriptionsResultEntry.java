package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.InvokeResultEntry;
import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class DescriptionsResultEntry implements InvokeResultEntry {
    Text searchKey;

    public DescriptionsResultEntry(String s) {
        super();
        searchKey = findTranslationKey(s);
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
        var itemStack = createMultilineTranslation(findItemLoreKey(Registries.ITEM.get(Identifier.of(namespace, item)).getDefaultStack()));
        if (!Objects.requireNonNull(itemStack.getLiteralString()).isEmpty()) return itemStack;
        //Check to see if that namespaced identifier matches a mob. If so, return that item's lore key.
        //This seems to return a Pig if it isn't matched correctly, so that is ignored if "pig" isn't actually typed in.
        var mobRegistry = Registries.ENTITY_TYPE.get(Identifier.of(namespace, item)).getTranslationKey();
        if (Objects.equals(mobRegistry, "entity.minecraft.pig") ) {
            if (item.equals("pig")) return createMultilineTranslation(convertToLoreKey(mobRegistry));
        }
        else
            return createMultilineTranslation(convertToLoreKey(mobRegistry));
        // If a namespace match is not found, iterate through block and item registries for a name match.
        Text itemRegistry = iterateRegistry(Registries.ITEM, lowerS);
        if (itemRegistry != null)
            return itemRegistry;
        Text blockRegistry = iterateRegistry(Registries.BLOCK, lowerS);
        if (blockRegistry != null)
            return blockRegistry;
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
            if (lowerS.equals(I18n.translate(registryKey).toLowerCase())) {
                returnedKey[0] = createMultilineTranslation(convertToLoreKey(registryKey));
                return;
            };
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
