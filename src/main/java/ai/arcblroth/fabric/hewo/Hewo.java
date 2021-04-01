package ai.arcblroth.fabric.hewo;

import ai.arcblroth.fabric.hewo.api.HewoAPI;
import ai.arcblroth.fabric.hewo.command.OwoCommand;
import ai.arcblroth.fabric.hewo.config.HewoConfig;
import com.google.common.collect.ImmutableList;
import maow.owo.OwO;
import maow.owo.util.IOUtil;
import maow.owo.util.json.Defaults;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.function.BiFunction;

public class Hewo implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("Hewo");
    public static final ImmutableList<String> DEFAULT_PREFIXES;
    public static final ImmutableList<String> DEFAULT_SUFFIXES;
    public static final String MODID = "hewo";
    public static HewoConfig CONFIG;

    static {
        // Load Owo's defaults
        Defaults defaults = IOUtil.readResourceToDefaults("defaults.json");
        if (defaults != null) {
            DEFAULT_PREFIXES = ImmutableList.copyOf(defaults.getPrefixes());
            DEFAULT_SUFFIXES = ImmutableList.copyOf(defaults.getSuffixes());
        } else {
            DEFAULT_PREFIXES = ImmutableList.of();
            DEFAULT_SUFFIXES = ImmutableList.of();
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info(OwO.INSTANCE.owo("Loading furry speak..."));

        AutoConfig.register(HewoConfig.class, GsonConfigSerializer::new);
        ConfigHolder<HewoConfig> configHolder = AutoConfig.getConfigHolder(HewoConfig.class);
        BiFunction<ConfigHolder<HewoConfig>, HewoConfig, ActionResult> serializeListener = (holder, config) -> {
            // TODO: this is not thread safe
            HashSet<String> prefixes = new HashSet<>(OwO.INSTANCE.getPrefixes());
            HashSet<String> suffixes = new HashSet<>(OwO.INSTANCE.getSuffixes());
            prefixes.addAll(config.owoPrefixes);
            suffixes.addAll(config.owoSuffixes);
            OwO.INSTANCE.getPrefixes().clear();
            OwO.INSTANCE.getSuffixes().clear();
            OwO.INSTANCE.getPrefixes().addAll(prefixes);
            OwO.INSTANCE.getSuffixes().addAll(suffixes);
            return ActionResult.SUCCESS;
        };
        configHolder.registerSaveListener(serializeListener::apply);

        CONFIG = AutoConfig.getConfigHolder(HewoConfig.class).getConfig();
        serializeListener.apply(configHolder, CONFIG);
        HewoAPI.getInstance().addOwoProbabilityModifier(() -> CONFIG.hewoChance == 0 ? -1 : CONFIG.hewoChance / 100D);

        CommandRegistrationCallback.EVENT.register(((commandDispatcher, dedicated) -> OwoCommand.register(commandDispatcher)));
    }

}
