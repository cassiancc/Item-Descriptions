package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.util.Identifier;

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;

public class LimelightItemDescriptions implements LimelightExtension {
    public static final Identifier ID = Identifier.of(MOD_ID, "item_descriptions");
    public static final LimelightItemDescriptions INSTANCE = new LimelightItemDescriptions();

    @Override
    public Identifier id() {
        return ID;
    }
}
