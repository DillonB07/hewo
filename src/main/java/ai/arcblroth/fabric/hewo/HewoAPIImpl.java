package ai.arcblroth.fabric.hewo;

import ai.arcblroth.fabric.hewo.api.HewoAPI;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class HewoAPIImpl implements HewoAPI {

    public static final HewoAPIImpl HEWO = new HewoAPIImpl();
    private ArrayList<Supplier<Double>> chances = new ArrayList<>();

    private HewoAPIImpl() {
    }

    @Override
    public void addOwoProbabilityModifier(Supplier<Double> probabilityModifier) {
        chances.add(Objects.requireNonNull(probabilityModifier));
    }

    public boolean shouldOwo() {
        return ThreadLocalRandom.current().nextDouble() < chances.stream().map(Supplier::get).max(Double::compareTo).orElse(-1D);
    }

    public Map<String, String> getSubstitutions() {
        return ImmutableMap.copyOf(Hewo.apiSubstitutions);
    }

    public List<String> getPrefixes() {
        return ImmutableList.copyOf(Hewo.apiPrefixes);
    }

    public List<String> getSuffixes() {
        return ImmutableList.copyOf(Hewo.apiSuffixes);
    }

    public void addSubstitution(String original, String substitute) {
        Hewo.rebuildOwO(() -> Hewo.apiSubstitutions.put(original, substitute));
    }

    public void addSubstitution(String original, String substitute, boolean byItself) {
        addSubstitution(original, substitute);
    }

    public void addPrefix(String prefix) {
        Hewo.rebuildOwO(() -> Hewo.apiPrefixes.add(prefix));
    }

    public void addSuffix(String suffix) {
        Hewo.rebuildOwO(() -> Hewo.apiSuffixes.add(suffix));
    }

}
