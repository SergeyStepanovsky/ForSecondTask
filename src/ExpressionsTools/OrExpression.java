package ExpressionsTools;


import Main.Animal;

// Класс OrExpression реализует логическую операцию "ИЛИ" (OR) между двумя выражениями
class OrExpression implements Expression {
    // Левое и правое подвыражения, которые будут оцениваться
    private Expression left, right;

    // Конструктор принимает два выражения и инициализирует ими поля left и right
    public OrExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    // Метод evaluate оценивает оба подвыражения и возвращает true, если хотя бы одно из них истинно
    @Override
    public boolean evaluate(Animal animal) {
        return left.evaluate(animal) || right.evaluate(animal);
    }
}

