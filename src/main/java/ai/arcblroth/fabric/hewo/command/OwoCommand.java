package ai.arcblroth.fabric.hewo.command;

import ai.arcblroth.fabric.hewo.Hewo;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.SharedConstants;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class OwoCommand {

    private static final SimpleCommandExceptionType INVALID_MESSAGE_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("multiplayer.disconnect.illegal_characters"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // TODO: send decorated signed messages to players with hewo installed client-side
        dispatcher.register(CommandManager.literal("owo").then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((commandContext) -> {
            String message = StringUtils.normalizeSpace(StringArgumentType.getString(commandContext, "message"));

            for (int i = 0; i < message.length(); ++i) {
                if (!SharedConstants.isValidChar(message.charAt(i))) {
                    throw INVALID_MESSAGE_EXCEPTION.create();
                }
            }

            message = Hewo.owoify(message);

            Entity entity = commandContext.getSource().getEntity();
            if (entity == null) {
                return 0;
            }

            Text text = Text.translatable("chat.type.text", entity.getDisplayName(), message);
            commandContext.getSource().getServer().getPlayerManager().broadcast(text, false);

            return 1;
        })));
    }
}
