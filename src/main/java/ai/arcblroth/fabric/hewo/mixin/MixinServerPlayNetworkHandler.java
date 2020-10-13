package ai.arcblroth.fabric.hewo.mixin;

import ai.arcblroth.fabric.hewo.Owo;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {

    @ModifyArg(method = "onGameMessage", at = @At(value = "INVOKE", target = "net/minecraft/server/PlayerManager.broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"), index = 0)
    private Text owoifyChatMessage(Text text) {
        if(text instanceof TranslatableText) {
            TranslatableText translatableText = (TranslatableText) text;
            Object[] args = translatableText.getArgs();
            if(args.length >= 2) {
                args[1] = Owo.owo(args[1].toString());
            }
        }
        return text;
    }

}
