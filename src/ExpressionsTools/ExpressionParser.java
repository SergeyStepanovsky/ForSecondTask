package ExpressionsTools;



import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// Класс для разбора выражений в правилах
public class ExpressionParser {
    // Входная строка, содержащая выражение для разбора
    private String input;
    // Текущая позиция в строке
    private int pos = 0;
    // Текущий токен (лексема), полученный из входной строки
    private String token;
    // Набор операторов, используемых в выражениях
    private static final Set<String> operators = new HashSet<>(Arrays.asList("==", "!=", "AND", "OR", "NOT", "(", ")"));

    // Конструктор принимает строку с выражением и инициирует разбор первого токена
    public ExpressionParser(String input) {
        this.input = input;
        nextToken();
    }

    // Метод для получения следующего токена из входной строки
    private void nextToken() {
        // Пропускаем пробелы
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) pos++;
        // Если достигнут конец строки, устанавливаем token в null
        if (pos == input.length()) {
            token = null;
            return;
        }
        // Получаем текущий символ
        char ch = input.charAt(pos);
        // Если символ является открывающей или закрывающей скобкой
        if (ch == '(' || ch == ')') {
            token = String.valueOf(ch);
            pos++;
        }
        // Если символ является буквой или оператором сравнения
        else if (Character.isLetter(ch) || ch == '>' || ch == '<') {
            int start = pos;
            // Собираем последовательность символов, составляющих идентификатор или оператор
            while (pos < input.length() && (Character.isLetterOrDigit(input.charAt(pos)) || input.charAt(pos) == '_'
                    || input.charAt(pos) == '>' || input.charAt(pos) == '<'))
                pos++;
            token = input.substring(start, pos);
        }
        // Если символ является частью оператора == или !=
        else if (ch == '=' || ch == '!') {
            if (pos + 1 < input.length() && input.charAt(pos + 1) == '=') {
                token = input.substring(pos, pos + 2);
                pos += 2;
            } else {
                token = String.valueOf(ch);
                pos++;
            }
        }
        // Для остальных символов
        else {
            token = String.valueOf(ch);
            pos++;
        }
    }

    // Начальный метод для разбора выражения
    public Expression parseExpression() {
        Expression expr = parseOrExpression();
        // Проверяем, что после разбора выражения не осталось неожиданных токенов
        if (token != null && !token.equals(")")) {
            throw new RuntimeException("Unexpected token: " + token);
        }
        return expr;
    }

    // Разбор выражений с оператором OR
    private Expression parseOrExpression() {
        Expression left = parseAndExpression();
        // Пока текущий токен является оператором OR
        while ("OR".equals(token)) {
            nextToken();
            Expression right = parseAndExpression();
            left = new OrExpression(left, right);
        }
        return left;
    }

    // Разбор выражений с оператором AND
    private Expression parseAndExpression() {
        Expression left = parseNotExpression();
        // Пока текущий токен является оператором AND
        while ("AND".equals(token)) {
            nextToken();
            Expression right = parseNotExpression();
            left = new AndExpression(left, right);
        }
        return left;
    }

    // Разбор выражений с оператором NOT
    private Expression parseNotExpression() {
        if ("NOT".equals(token)) {
            nextToken();
            Expression expr = parsePrimary();
            return new NotExpression(expr);
        }
        return parsePrimary();
    }

    // Разбор первичных выражений (скобки, сравнения)
    private Expression parsePrimary() {
        // Если текущий токен — открывающая скобка
        if ("(".equals(token)) {
            nextToken();
            Expression expr = parseExpression();
            // Ожидаем закрывающую скобку
            if (!")".equals(token)) {
                throw new RuntimeException("Expected ')'");
            }
            nextToken();
            return expr;
        }
        // Если текущий токен является идентификатором (имя свойства)
        else if (isIdentifier(token)) {
            String property = token;
            nextToken();
            // Ожидаем оператор сравнения после идентификатора
            if ("==".equals(token) || "!=".equals(token)) {
                String operator = token;
                nextToken();
                // Ожидаем значение для сравнения
                String value = token;
                nextToken();
                return new ComparisonExpression(property, operator, value);
            } else {
                throw new RuntimeException("Expected operator after property");
            }
        } else {
            throw new RuntimeException("Unexpected token: " + token);
        }
    }

    // Проверка, является ли строка идентификатором (не оператором)
    private boolean isIdentifier(String s) {
        return !operators.contains(s);
    }
}

