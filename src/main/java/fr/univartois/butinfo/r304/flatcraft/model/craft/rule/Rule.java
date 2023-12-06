package fr.univartois.butinfo.r304.flatcraft.model.craft.rule;

/**
 * Class representing a crafting rule.
 */
public class Rule {

    private String craftRule;
    private String product;
    private int quantity;

    /**
     * Creates a new Rule.
     *
     * @param rule The rule part of the craft rule.
     * @param product The product part of the craft rule.
     * @param quantity The quantity part of the craft rule.
     */
    public Rule(String rule, String product, int quantity) {
        this.craftRule = rule;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Gets the rule part of the craft rule.
     *
     * @return The rule part.
     */
    public String getRule() {
        return craftRule;
    }

    /**
     * Gets the product part of the craft rule.
     *
     * @return The product part.
     */
    public String getProduct() {
        return product;
    }

    /**
     * Gets the quantity part of the craft rule.
     *
     * @return The quantity part.
     */
    public int getQuantity() {
        return quantity;
    }

    // You can add more methods as needed based on your application requirements.
}
