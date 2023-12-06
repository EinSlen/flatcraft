package fr.univartois.butinfo.r304.flatcraft.model.craft.builder;

import fr.univartois.butinfo.r304.flatcraft.model.craft.rule.Rule;

/**
 * Director for constructing craft rules.
 */
public class RuleDirector {

    private RuleBuilder builder;

    /**
     * Creates a new RuleDirector with a specific builder.
     *
     * @param builder The RuleBuilder to use for construction.
     */
    public RuleDirector(RuleBuilder builder) {
        this.builder = builder;
    }

    /**
     * Constructs a craft rule using the provided information.
     *
     * @param rule The rule part of the craft rule.
     * @param product The product part of the craft rule.
     * @param quantity The quantity part of the craft rule.
     * @return The constructed Rule object.
     */
    public Rule constructCraftRule(String rule, String product, int quantity) {
        return builder.addRule(rule)
                .addProduct(product)
                .addQuantity(quantity)
                .build();
    }

    /**
     * Constructs a smelting rule using the provided information.
     *
     * @param rule The rule part of the smelting rule.
     * @param product The product part of the smelting rule.
     * @param quantity The quantity part of the smelting rule.
     * @return The constructed Rule object.
     */
    public Rule constructSmeltingRule(String rule, String product, int quantity) {
        return builder.addRule(rule)
                .addProduct(product)
                .addQuantity(quantity)
                .buildSmeltingRule();
    }
}
