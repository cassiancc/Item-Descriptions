package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.text.Text;

public class ResultProvider {
        public LimelightExtension extension() {
            return LimelightItemDescriptions.INSTANCE; //
        }

        public String entryId() {
            return "item-descriptions:items"; //
        }

        public Text text() {
            return Text.literal("item descriptions");
        }

}
