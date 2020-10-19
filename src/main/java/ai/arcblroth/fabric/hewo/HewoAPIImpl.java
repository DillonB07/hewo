package ai.arcblroth.fabric.hewo;

import ai.arcblroth.fabric.hewo.api.HewoAPI;
import maow.owo.OwO;
import maow.owo.util.json.Substitution;

import java.util.ArrayList;
import java.util.List;
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

    public List<Substitution> getSubstitutions() {
        return OwO.INSTANCE.getSubstitutions();
    }

    public List<String> getPrefixes() {
        return OwO.INSTANCE.getPrefixes();
    }

    public List<String> getSuffixes() {
        return OwO.INSTANCE.getSuffixes();
    }

    public void addSubstitution(Substitution substitution) {
        OwO.INSTANCE.addSubstitution(substitution);
    }

    public void addSubstitution(String original, String substitute, boolean byItself) {
        OwO.INSTANCE.addSubstitution(original, substitute, byItself);
    }

    public void addPrefix(String prefix) {
        OwO.INSTANCE.addPrefix(prefix);
    }

    public void addSuffix(String suffix) {
        OwO.INSTANCE.addPrefix(suffix);
    }

}
