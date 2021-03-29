package ai.arcblroth.fabric.hewo.client;

import ai.arcblroth.fabric.hewo.config.HewoConfig;
import com.google.common.collect.ImmutableList;
import maow.owo.util.json.Substitution;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.gui.registry.api.GuiRegistryAccess;
import me.shedaniel.clothconfig2.impl.builders.StringListBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class HewoClient implements ClientModInitializer {

    private static final TranslatableText RESET_TEXT = new TranslatableText("text.cloth.reset_value");

    @Override
    public void onInitializeClient() {
        GuiRegistry registry = AutoConfig.getGuiRegistry(HewoConfig.class);
        registry.registerTypeProvider((i13n, field, config, defaults, registry2) -> {
            try {
                Object value = field.get(config);
                if(field.getGenericType() instanceof ParameterizedType) {
                    Type[] actualTypeArgs = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                    if(actualTypeArgs.length == 1) {
                        if (actualTypeArgs[0].equals(String.class)) {
                            List<String> actualValue = (List<String>) value;
                            StringListBuilder builder = new StringListBuilder(
                                    RESET_TEXT,
                                    new TranslatableText("text.autoconfig.hewo.option." + field.getName()),
                                    actualValue
                            );
                            builder.setSaveConsumer(newValue -> {
                                actualValue.clear();
                                actualValue.addAll(newValue);
                            });
                            return ImmutableList.of(builder.build());
                        } else if (actualTypeArgs[0].equals(Substitution.class)) {}
                    }
                }
                return ImmutableList.of();
            } catch (IllegalAccessException | ClassCastException e) {
                throw new RuntimeException(e);
            }
        }, List.class);
    }

}
