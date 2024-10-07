package Main;

import ExpressionsTools.Expression;
import ExpressionsTools.ExpressionParser;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Главный класс для выполнения программы
class AnimalAccountingRunner {

    public static void main(String[] args) {
        try {
            // Загрузка соответствия параметров свойствам из файла "Parameters.txt"
            Map<String, String> parameterToPropertyMap = loadParameterPropertyMap("Parameters.txt");

            // Загрузка списка животных из файла "Animals.txt"
            List<Animal> animals = loadAnimals("Animals.txt", parameterToPropertyMap);

            // Загрузка и разбор правил из файла "Rules.txt"
            List<Rule> rules = loadRules("Rules.txt");

            // Оценка правил и вывод результатов
            for (Rule rule : rules) {
                int count = 0;
                // Проход по каждому животному для оценки правила
                for (Animal animal : animals) {
                    // Если животное удовлетворяет условию правила, увеличиваем счетчик
                    if (rule.getExpression().evaluate(animal)) {
                        count++;
                    }
                }
                // Выводим название правила и количество животных, удовлетворяющих ему
                System.out.println(rule.getName() + ": " + count);
            }
        } catch (Exception e) {
            // Вывод стека ошибок в случае исключения
            e.printStackTrace();
        }
    }

    // Метод для загрузки соответствия параметров свойствам из файла
    private static Map<String, String> loadParameterPropertyMap(String filename) throws IOException {
        Map<String, String> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        // Чтение файла построчно
        while ((line = br.readLine()) != null) {
            // Пропускаем пустые строки
            if (line.trim().isEmpty()) continue;
            // Разделяем строку по двоеточию на параметр и свойство
            String[] parts = line.split(":");
            if (parts.length != 2) continue; // Пропускаем строки неправильного формата
            String parameter = parts[0].trim(); // Параметр (например, "ЛЕГКОЕ")
            String property = parts[1].trim();  // Свойство (например, "ВЕС")
            // Добавляем в карту соответствие параметра свойству
            map.put(parameter, property);
        }
        br.close(); // Закрываем BufferedReader
        return map; // Возвращаем карту соответствий
    }

    // Метод для загрузки списка животных из файла
    private static List<Animal> loadAnimals(String filename, Map<String, String> parameterToPropertyMap) throws IOException {
        List<Animal> animals = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        // Чтение файла построчно
        while ((line = br.readLine()) != null) {
            // Пропускаем пустые строки
            if (line.trim().isEmpty()) continue;
            // Разделяем строку по запятым на параметры животного
            String[] params = line.split(",");
            Animal animal = new Animal();
            // Обработка каждого параметра животного
            for (String param : params) {
                String parameter = param.trim(); // Очищаем параметр от лишних пробелов
                // Получаем свойство, соответствующее параметру, из карты
                String property = parameterToPropertyMap.get(parameter);
                if (property != null) {
                    // Добавляем свойство и параметр в объект животного
                    animal.addProperty(property, parameter);
                }
            }
            // Добавляем животное в список
            animals.add(animal);
        }
        br.close(); // Закрываем BufferedReader
        return animals; // Возвращаем список животных
    }

    // Метод для загрузки и разбора правил из файла
    private static List<Rule> loadRules(String filename) throws IOException {
        List<Rule> rules = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        // Чтение файла построчно
        while ((line = br.readLine()) != null) {
            // Пропускаем пустые строки
            if (line.trim().isEmpty()) continue;
            // Разделяем строку по двоеточию на название правила и выражение
            String[] parts = line.split(":", 2);
            if (parts.length != 2) continue; // Пропускаем строки неправильного формата
            String ruleName = parts[0].trim(); // Название правила
            // Убираем префикс "COUNT WHERE" из выражения
            String expressionStr = parts[1].trim().replaceFirst("COUNT WHERE", "").trim();
            // Создаем парсер выражения
            ExpressionParser parser = new ExpressionParser(expressionStr);
            // Разбираем выражение и получаем объект Expression
            Expression expr = parser.parseExpression();
            // Создаем объект Rule с названием и выражением
            rules.add(new Rule(ruleName, expr));
        }
        br.close(); // Закрываем BufferedReader
        return rules; // Возвращаем список правил
    }
}

