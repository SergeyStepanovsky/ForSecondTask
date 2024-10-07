package ExpressionsTools;


import Main.Animal;

// Класс NotExpression реализует логическую операцию "НЕ" (NOT) над выражением
class NotExpression implements Expression {
    // Выражение, результат которого нужно инвертировать
    private Expression expr;

    // Конструктор принимает выражение и инициализирует поле expr
    public NotExpression(Expression expr) {
        this.expr = expr;
    }

    // Метод evaluate возвращает инвертированный результат оценки выражения
    @Override
    public boolean evaluate(Animal animal) {
        return !expr.evaluate(animal);
    }
}

