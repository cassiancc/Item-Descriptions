package cc.cassian.item_descriptions.client.limelight;

//? if fabric {
//? if 1.21.1 || 1.21.5 {
/*import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import io.wispforest.limelight.api.builtin.bangs.BangDefinition;
import io.wispforest.limelight.api.builtin.bangs.BangsProvider;
import io.wispforest.limelight.api.entry.ResultEntry;
import io.wispforest.limelight.api.entry.ResultGatherContext;
import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;

public class DescriptionsExtension implements LimelightExtension, BangsProvider {
    public static final ResourceLocation ID = ModHelpers.of("item_descriptions");
    public static final DescriptionsExtension INSTANCE = new DescriptionsExtension();

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public List<BangDefinition> bangs() {
        return List.of(new BangDefinition(
                "descriptions",
                Component.translatable("limelightExtension.item-descriptions.item_descriptions"),
                (ctx, entryConsumer) -> entryConsumer.accept(new DescriptionsResultEntry(ctx.searchText()))
        ));
    }

    @Override
    public void gatherEntries(ResultGatherContext ctx, Consumer<ResultEntry> entryConsumer) {
        var resultEntry = new DescriptionsResultEntry((ctx.searchText()));
        if (!Objects.requireNonNull(resultEntry.text().getString()).isEmpty()) {
            entryConsumer.accept(new DescriptionsResultEntry(ctx.searchText()));
        }
    }
}
*///?}
//?}