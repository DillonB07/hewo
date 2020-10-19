package ai.arcblroth.fabric.hewo.api;

import ai.arcblroth.fabric.hewo.HewoAPIImpl;
import maow.owo.util.json.Substitution;

import java.util.List;
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
     */
    void addOwoProbabilityModifier(Supplier<Double> probabilityModifier);

    /**
     * Gets all {@link maow.owo.util.json.Substitution}s
     * registered on the owofier.
     */
    public List<Substitution> getSubstitutions();

    /**
     * Gets all prefixes registered on the owofier.
     */
    public List<String> getPrefixes();

    /**
     * Gets all suffixes registered on the owofier.
     */
    public List<String> getSuffixes();

    /**
     * Adds a {@link maow.owo.util.json.Substitution}
     * to the owofier.
     */
    public void addSubstitution(Substitution substitution);

    /**
     * Builds a {@link maow.owo.util.json.Substitution}
     * and adds it to the owoifer.
     */
    public void addSubstitution(String original, String substitute, boolean byItself);

    /**
     * Adds a prefix to the owofier.
     */
    public void addPrefix(String prefix);

    /**
     * Adds a sufix to the owofier.
     */
    public void addSuffix(String suffix);

}
