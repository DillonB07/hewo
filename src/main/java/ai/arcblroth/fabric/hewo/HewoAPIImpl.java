package ai.arcblroth.fabric.hewo;

import ai.arcblroth.fabric.hewo.api.HewoAPI;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class HewoAPIImpl implements HewoAPI {

    public static final HewoAPIImpl HEWO = new HewoAPIImpl();
    private ArrayList<Supplier<Double>> chances = new ArrayList<>();

    private HewoAPIImpl() {}

    @Override
    public void addOwoProbabilityModifier(Supplier<Double> probabilityModifier) {
        chances.add(Objects.requireNonNull(probabilityModifier));
    }

    public boolean shouldOwo() {
        return ThreadLocalRandom.current().nextDouble() < chances.stream().map(Supplier::get).max(Double::compareTo).orElse(-1D);
    }

}
