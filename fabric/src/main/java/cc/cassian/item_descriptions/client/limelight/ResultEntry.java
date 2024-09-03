package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.InvokeResultEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ResultEntry extends BaseResultProvider implements InvokeResultEntry {
    @Override
    public void run() {
        MinecraftClient.getInstance().player.sendMessage(Text.literal("triggered!"));
    }

    @Override
    public boolean closesScreen() {
        return false; //


    }
}
