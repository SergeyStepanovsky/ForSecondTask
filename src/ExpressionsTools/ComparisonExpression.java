package ExpressionsTools;


import Main.Animal;

// Класс ComparisonExpression реализует сравнение свойства животного с заданным значением
class ComparisonExpression implements Expression {
    // Имя свойства, которое будет проверяться (например, "ТИП" или "РОСТ")
    private String property;
    // Оператор сравнения, может быть "==" или "!="
    private String operator;
    // Значение, с которым будет сравниваться свойство животного
    private String value;

    // Конструктор принимает имя свойства, оператор и значение для сравнения
    public ComparisonExpression(String property, String operator, String value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    // Метод evaluate проверяет, удовлетворяет ли животное условию сравнения
    @Override
    public boolean evaluate(Animal animal) {
        // Получаем значение свойства у данного животного
        String propValue = animal.getProperty(property);
        // Если оператор равен "==", проверяем равенство значений
        if (operator.equals("==")) {
            return value.equals(propValue);
            // Если оператор равен "!=", проверяем неравенство значений
        } else if (operator.equals("!=")) {
            return !value.equals(propValue);
        }
        // Если оператор неизвестен, возвращаем false
        return false;
    }
}
