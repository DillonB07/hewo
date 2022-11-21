package ai.arcblroth.fabric.hewo.api;

import ai.arcblroth.fabric.hewo.HewoAPIImpl;

import java.util.List;
import java.util.Map;
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
    public Map<String, String> getSubstitutions();

    /**
     * Gets all prefixes registered on the owofier.
     */
    public List<String> getPrefixes();

    /**
     * Gets all suffixes registered on the owofier.
     */
    public List<String> getSuffixes();

    /**
     * Adds a substitution to the owofier.
     */
    public void addSubstitution(String original, String substitute);

    /**
     * Adds a substitution it to the owoifer.
     * @deprecated - artifact of the OwO v1 API
     */
    @Deprecated(forRemoval = true)
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
