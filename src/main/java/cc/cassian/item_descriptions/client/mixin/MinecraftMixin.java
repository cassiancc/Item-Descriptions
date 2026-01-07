package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.descriptions.ItemDescriptions;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "reloadResourcePacks(ZLnet/minecraft/client/Minecraft$GameLoadCookie;)Ljava/util/concurrent/CompletableFuture;", at = @At(value = "RETURN"))
	private void renderEffects(boolean error, @Coerce Object gameLoadCookie, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
		ItemDescriptions.cachedDescriptions.clear();
	}
}
