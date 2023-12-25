package fr.univartois.butinfo.r304.flatcraft.model.craft.rule;

public class Rule {

    private final String rule;
    private final String product;
    private final int quantity;

    public Rule(String rule, String product, int quantity) {
        this.rule = rule;
        this.product = product;
        this.quantity = quantity;
    }

    public String getRule() {
        return rule;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
