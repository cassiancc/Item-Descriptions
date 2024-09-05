package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.LimelightEntrypoint;
import io.wispforest.limelight.api.extension.LimelightExtension;

import java.util.function.Consumer;

public class LimelightIntegration implements LimelightEntrypoint {
    @Override
    public void registerExtensions(Consumer<LimelightExtension> extensionRegistry) {
        // Register extensions here!
        extensionRegistry.accept(DescriptionsExtension.INSTANCE);
    }
}
