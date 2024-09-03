package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.InvokeResultEntry;
import net.minecraft.text.Text;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.createMultilineTranslation;

public class ResultEntry extends BaseResultProvider implements InvokeResultEntry {
    String searchKey;

    public ResultEntry(String s) {
        super();
        searchKey = findTranslationKey(s);
    }

    private String findTranslationKey(String s) {
        String trimmedS = s.substring(1).toLowerCase().replace(" ", "_");
        if (trimmedS.contains(":")) {
            String[] splitS = trimmedS.split(":");
            return "lore." + splitS[0] + "." + splitS[1];
        }
        return "lore.minecraft."+ trimmedS;
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
       return createMultilineTranslation(searchKey);
    }
}
