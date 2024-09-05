package cc.cassian.item_descriptions.client.limelight;

import blue.endless.jankson.annotation.Nullable;
import io.wispforest.limelight.api.builtin.bangs.BangDefinition;
import io.wispforest.limelight.api.builtin.bangs.BangsProvider;
import io.wispforest.limelight.api.entry.ResultEntryGatherer;
import io.wispforest.limelight.api.entry.ResultGatherContext;
import io.wispforest.limelight.api.extension.LimelightExtension;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;

public class Extension implements LimelightExtension, BangsProvider {
    public static final Identifier ID = Identifier.of(MOD_ID, "item_descriptions");
    public static final Extension INSTANCE = new Extension();

    @Override
    public Identifier id() {
        return ID;
    }

//    public void gatherEntries(ResultGatherContext ctx, Consumer<io.wispforest.limelight.api.entry.ResultEntry> entryConsumer) {
//        var resultEntry = new ResultEntry((ctx.searchText()));
//        if (!Objects.requireNonNull(resultEntry.text().getLiteralString()).isEmpty()) {
//            entryConsumer.accept(new ResultEntry(ctx.searchText()));
//        }
//    }

    @Override
    public List<BangDefinition> bangs() {
        return List.of(new BangDefinition(
                "descriptions",
                Text.translatable("limelightExtension.item-descriptions.item_descriptions"),
                (ctx, entryConsumer) -> {
                    entryConsumer.accept(new ResultEntry(ctx.searchText()));
                }
        ));
    }

    @Override
    public @Nullable ResultEntryGatherer checkExclusiveGatherer(ResultGatherContext ctx) {
//        if (!ctx.searchText().startsWith("#")) return null;
        var resultEntry = new ResultEntry((ctx.searchText()));
        if (!Objects.requireNonNull(resultEntry.text().getLiteralString()).isEmpty()) {
            return (ctx1, entryConsumer) -> entryConsumer.accept(new ResultEntry(ctx.searchText()));
        }
        return null;
    }
}
