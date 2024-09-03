package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.InvokeResultEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.createMultilineTranslation;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.createTooltip;

public class ResultEntry extends BaseResultProvider implements InvokeResultEntry {
    String searchText;

    public ResultEntry(String s) {
        super();
        searchText = s.substring(1).toLowerCase().replace(" ", "_");
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

       return createMultilineTranslation("lore.minecraft."+searchText);
    }
}
