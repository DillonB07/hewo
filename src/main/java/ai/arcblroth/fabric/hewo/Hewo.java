package ai.arcblroth.fabric.hewo;

import ai.arcblroth.fabric.hewo.command.OwoCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hewo implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("Hewo");

    @Override
    public void onInitialize() {
        LOGGER.info(Owo.owo("Loading furry speak..."));
        CommandRegistrationCallback.EVENT.register(((commandDispatcher, dedicated) -> OwoCommand.register(commandDispatcher)));
    }

}
