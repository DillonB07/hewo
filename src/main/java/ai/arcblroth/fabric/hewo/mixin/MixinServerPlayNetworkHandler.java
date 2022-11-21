package ai.arcblroth.fabric.hewo.mixin;

import ai.arcblroth.fabric.hewo.Hewo;
import ai.arcblroth.fabric.hewo.HewoAPIImpl;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {

    @Shadow @Final
    private MinecraftServer server;

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "handleDecoratedMessage", at = @At(value = "HEAD"), cancellable = true)
    private void owoifyChatMessage(SignedMessage message, CallbackInfo ci) {
        if(HewoAPIImpl.HEWO.shouldOwo()) {
            TextContent textContent = message.getContent().getContent();
            if (textContent instanceof LiteralTextContent literalTextContent) {
                String owofied = Hewo.owoify(literalTextContent.string());
                Text newText = Text.translatable("chat.type.text", this.player.getDisplayName(), owofied);
                server.getPlayerManager().broadcast(newText, false);
                checkForSpam();
                ci.cancel();
            }
        }
    }

    @Shadow
    private void checkForSpam() {}

}
