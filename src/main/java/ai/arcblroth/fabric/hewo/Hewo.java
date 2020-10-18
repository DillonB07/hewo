package ai.arcblroth.fabric.hewo;

import ai.arcblroth.fabric.hewo.api.HewoAPI;
import ai.arcblroth.fabric.hewo.command.OwoCommand;
import ai.arcblroth.fabric.hewo.config.HewoConfig;
import maow.owo.OwO;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hewo implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("Hewo");
    public static final String MODID = "hewo";
    public static HewoConfig CONFIG;

    @Override
    public void onInitialize() {
        LOGGER.info(OwO.INSTANCE.owo("Loading furry speak..."));

        AutoConfig.register(HewoConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(HewoConfig.class).getConfig();

        HewoAPI.getInstance().addOwoProbabilityModifier(() -> CONFIG.hewoChance == 0 ? -1 : CONFIG.hewoChance / 100D);

        CommandRegistrationCallback.EVENT.register(((commandDispatcher, dedicated) -> OwoCommand.register(commandDispatcher)));
    }

}
