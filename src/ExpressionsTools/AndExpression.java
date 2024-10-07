package ExpressionsTools;


import Main.Animal;

// Класс AndExpression реализует логическую операцию "И" (AND) между двумя выражениями
class AndExpression implements Expression {
    // Левое и правое подвыражения, которые будут оцениваться
    private Expression left, right;

    // Конструктор принимает два выражения и инициализирует ими поля left и right
    public AndExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    // Метод evaluate оценивает оба подвыражения и возвращает true, только если оба выражения истинны
    @Override
    public boolean evaluate(Animal animal) {
        return left.evaluate(animal) && right.evaluate(animal);
    }
}
