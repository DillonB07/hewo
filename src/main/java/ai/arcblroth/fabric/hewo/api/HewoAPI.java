package ai.arcblroth.fabric.hewo.api;

import ai.arcblroth.fabric.hewo.HewoAPIImpl;

import java.util.function.Supplier;

public interface HewoAPI {

    static HewoAPI getInstance() {
        return HewoAPIImpl.HEWO;
    }

    /**
     * Adds a modifier that specifies the probability
     * that a message will be owoified. A message will
     * be owoified based on the highest probability
     * from all registered modifiers. For example, if
     * one modifier returns 0.4 and another returns 0.6,
     * than the overall probability for owofication
     * will be 0.6.
     * @param probabilityModifier
     */
    void addOwoProbabilityModifier(Supplier<Double> probabilityModifier);

}
