package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.text.Text;

public class BaseResultProvider {
        public LimelightExtension extension() {
            return Extension.INSTANCE; //
        }

        public String entryId() {
            return "item-descriptions:items"; //
        }

        public Text text() {
            return Text.literal("item descriptions");
        }

}
