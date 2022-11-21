package ai.arcblroth.fabric.hewo;

import ai.arcblroth.fabric.hewo.api.HewoAPI;
import ai.arcblroth.fabric.hewo.command.OwoCommand;
import ai.arcblroth.fabric.hewo.config.HewoConfig;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import dev.maow.owo.api.OwO;
import dev.maow.owo.api.OwO.TranslateMode;
import dev.maow.owo.util.Options;
import dev.maow.owo.util.OwOFactory;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Hewo implements ModInitializer {

    public static final String MODID = "hewo";
    private static final Logger LOGGER = LogManager.getLogger("Hewo");

    public static final ImmutableMap<String, String> DEFAULT_SUBSTITUTIONS;
    public static final ImmutableList<String> DEFAULT_PREFIXES;
    public static final ImmutableList<String> DEFAULT_SUFFIXES;
    
    private static OwO OWO;
    private static HewoConfig CONFIG;

    protected static List<String> configPrefixes = new ArrayList<>();
    protected static List<String> configSuffixes = new ArrayList<>();

    protected static Map<String, String> apiSubstitutions = new HashMap<>();
    protected static List<String> apiPrefixes = new ArrayList<>();
    protected static List<String> apiSuffixes = new ArrayList<>();

    static {
        // Load Owo's defaults
        Options defaults = Options.defaults();
        if (defaults != null) {
            DEFAULT_SUBSTITUTIONS = ImmutableMap.copyOf(defaults.getSubstitutions());
            DEFAULT_PREFIXES = ImmutableList.copyOf(defaults.getPrefixes());
            DEFAULT_SUFFIXES = ImmutableList.copyOf(defaults.getSuffixes());
        } else {
            DEFAULT_SUBSTITUTIONS = ImmutableMap.of();
            DEFAULT_PREFIXES = ImmutableList.of();
            DEFAULT_SUFFIXES = ImmutableList.of();
        }

        rebuildOwO(null);
    }

    @Override
    public void onInitialize() {
        LOGGER.info(OWO.translate("Loading furry speak..."));

        AutoConfig.register(HewoConfig.class, GsonConfigSerializer::new);
        ConfigHolder<HewoConfig> configHolder = AutoConfig.getConfigHolder(HewoConfig.class);
        BiFunction<ConfigHolder<HewoConfig>, HewoConfig, ActionResult> serializeListener = (holder, config) -> {
            rebuildOwO(() -> {
                configPrefixes.clear();
                configPrefixes.addAll(config.owoPrefixes);
                configSuffixes.clear();
                configSuffixes.addAll(config.owoSuffixes);
            });
            return ActionResult.SUCCESS;
        };
        configHolder.registerLoadListener(serializeListener::apply);
        configHolder.registerSaveListener(serializeListener::apply);

        CONFIG = AutoConfig.getConfigHolder(HewoConfig.class).getConfig();
        serializeListener.apply(null, CONFIG);
        HewoAPI.getInstance().addOwoProbabilityModifier(() -> CONFIG.hewoChance == 0 ? -1 : CONFIG.hewoChance / 100D);

        CommandRegistrationCallback.EVENT.register(((commandDispatcher, registry, env) -> OwoCommand.register(commandDispatcher)));
    }

    protected static synchronized void rebuildOwO(Runnable reconfigure) {
        if (reconfigure != null) {
            reconfigure.run();
        }

        HashMap<String, String> mergedSubstitutions = new HashMap<>();
        mergedSubstitutions.putAll(DEFAULT_SUBSTITUTIONS);
        mergedSubstitutions.putAll(apiSubstitutions);

        OWO = OwOFactory.INSTANCE.create(new Options(
            256,
            mergedSubstitutions,
            ImmutableList.copyOf(Iterables.concat(configPrefixes, apiPrefixes)),
            ImmutableList.copyOf(Iterables.concat(configSuffixes, apiSuffixes))
        ));
    }

    public static String owoify(String text) {
        return OWO.translate(CONFIG.useAffixes ? TranslateMode.ALL : TranslateMode.PLAIN, text);
    }
}
