package ai.arcblroth.fabric.hewo.config;

import ai.arcblroth.fabric.hewo.Hewo;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = Hewo.MODID)
public class HewoConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int hewoChance = 5;

    @ConfigEntry.Gui.Tooltip
    public boolean useAffixes = true;

    public List<String> owoPrefixes = new ArrayList<>(Hewo.DEFAULT_PREFIXES);
    public List<String> owoSuffixes = new ArrayList<>(Hewo.DEFAULT_SUFFIXES);

}
