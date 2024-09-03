package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.InvokeResultEntry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.tooltipFixInstalled;

public class ResultEntry extends BaseResultProvider implements InvokeResultEntry {
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
//            return "lore." + splitS[0] + "." + splitS[1];
            namespace = splitS[0];
            item = splitS[1];
        }

        return Registries.ITEM.get(Identifier.of(namespace, item));




    }

    @Override
    public void run() {
//        MinecraftClient.getInstance().keyboard.setClipboard();
    }

    @Override
    public boolean closesScreen() {
        return false; //
    }

    @Override
    public Text text() {
        return createMultilineTranslation(findItemLoreKey(searchKey.getDefaultStack()));
//        return createMultilineTranslation(searchKey);
    }
}
