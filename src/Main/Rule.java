package Main;

import ExpressionsTools.Expression;

// Класс, представляющий каждое правило
class Rule {
    private String name;
    private Expression expression;

    public Rule(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return expression;
    }
}
