package ExpressionsTools;

import Main.Animal;

// Интерфейс для выражений, используемых в правилах
public interface Expression {
    boolean evaluate(Animal animal);
}
