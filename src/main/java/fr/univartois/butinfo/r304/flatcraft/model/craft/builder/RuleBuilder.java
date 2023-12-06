package fr.univartois.butinfo.r304.flatcraft.model.craft.builder;

import fr.univartois.butinfo.r304.flatcraft.model.craft.rule.Rule;

public class RuleBuilder {

    private String rule;
    private String product;
    private int quantity;

    /**
     * Adds the rule part to the builder.
     *
     * @param rule The rule part to add.
     * @return The RuleBuilder instance for method chaining.
     */
    public RuleBuilder addRule(String rule) {
        this.rule = rule;
        return this;
    }

    /**
     * Adds the product part to the builder.
     *
     * @param product The product part to add.
     * @return The RuleBuilder instance for method chaining.
     */
    public RuleBuilder addProduct(String product) {
        this.product = product;
        return this;
    }

    /**
     * Adds the quantity part to the builder.
     *
     * @param quantity The quantity part to add.
     * @return The RuleBuilder instance for method chaining.
     */
    public RuleBuilder addQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Builds the rule using the collected parts.
     *
     * @return The constructed Rule object.
     */
    public Rule build() {
        return new Rule(rule, product, quantity);
    }

    /**
     * Builds the smelting rule using the collected parts.
     *
     * @return The constructed Rule object.
     */
    public Rule buildSmeltingRule() {
        // construire les règles de fusion en français
        return new Rule(rule, product, quantity);
    }
}
