package fr.univartois.butinfo.r304.flatcraft.model.craft.builder;

import fr.univartois.butinfo.r304.flatcraft.model.craft.rule.Rule;

public class RuleBuilder {

    private String rule;
    private String product;
    private int quantity;

    public RuleBuilder setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public RuleBuilder setProduct(String product) {
        this.product = product;
        return this;
    }

    public RuleBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Rule build() {
        return new Rule(rule, product, quantity);
    }
}
