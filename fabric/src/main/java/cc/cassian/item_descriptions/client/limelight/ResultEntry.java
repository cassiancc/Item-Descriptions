package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.InvokeResultEntry;
import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class ResultEntry implements InvokeResultEntry {
    Item searchKey;

    public ResultEntry(String s) {
        super();
        searchKey = findTranslationKey(s);
    }

    private Item findTranslationKey(String s) {
        String trimmedS = s.substring(1).toLowerCase().replace(" ", "_");
        String namespace = "minecraft";
        String item = trimmedS;
        if (trimmedS.contains(":")) {
            String[] splitS = trimmedS.split(":");
            namespace = splitS[0];
            item = splitS[1];
        }
        return Registries.ITEM.get(Identifier.of(namespace, item));

    }

    @Override
    public void run() {
        MinecraftClient.getInstance().keyboard.setClipboard(findItemLoreKey(searchKey.getDefaultStack()));
    }

    @Override
    public boolean closesScreen() {
        return false; //
    }

    public LimelightExtension extension() {
        return Extension.INSTANCE;
    }

    public String entryId() {
        return "item-descriptions:item_descriptions";
    }

    @Override
    public Text text() {
        return createMultilineTranslation(findItemLoreKey(searchKey.getDefaultStack()));
    }
}
