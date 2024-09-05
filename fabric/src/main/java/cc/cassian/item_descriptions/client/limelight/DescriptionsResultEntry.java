package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.InvokeResultEntry;
import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.client.MinecraftClient;
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
        // Take in user input, remove the #, and remove any spaces.
        String trimmedS = s.toLowerCase().replace(" ", "_");
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
        //If no match is found, return an empty string.
        return Text.literal("");


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
