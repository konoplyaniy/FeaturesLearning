package labmda.com;

import labmda.com.interfaces.Printerable;
import labmda.com.interfaces.Validable;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static labmda.com.DataGenerator.users;

public class LambdaTest {

    private static final Printerable printer = System.out::println;
    private static final Predicate<User> isNameLong = (user -> user.getName().length() >= 8);
    private static final Validable isAgeAllowed = (Object o) -> {
        if (o instanceof User) {
            User currentUser = (User) o;
            return currentUser.getAge() > 30;
        }
        return false;
    };

    public static void main(String[] args) {
        usersAgeAverage();
        userMaxAge();
        userMinAge();
        usersNameLengthAverage();
        userAgeStatistic();
        validUsersCount();
        userNameList();
        longNameUsersList();
        validUsersList();
        nameAgeMap();
        idNameMap();
    }

    private static double usersNameLengthAverage() {
        return users.stream()
                .mapToDouble(user -> user.getName().length()).average().getAsDouble();
    }

    private static double userMaxAge() {
        return users.stream().mapToInt(User::getAge).max().getAsInt();
    }

    private static double userMinAge() {
        return users.stream()
                .mapToInt(User::getAge).min().getAsInt();
    }

    private static IntSummaryStatistics userAgeStatistic() {
        return users.stream()
                .collect(Collectors.summarizingInt(User::getAge));
//        printer.print("======SUMMARY=======");
//        printer.print("Number of Users: " + statisticsByUsersAge.getCount());
//        printer.print("Min age: " + statisticsByUsersAge.getMin());
//        printer.print("Max age: " + statisticsByUsersAge.getMax());
//        printer.print("Age average: " + statisticsByUsersAge.getAverage());
    }

    private static void usersAgeAverage() {
        double average = users.stream()
                .mapToInt(User::getAge).average().getAsDouble();
        printer.print(average);
    }

    public static Map<Integer, String> idNameMap() {
        return users.stream()
                .filter(user -> user.getId() % 3 == 0) //filter users
                .collect(Collectors.toMap
                        (User::getId,
                                (value -> ("Name: " + value.getName())))); // collect to map<String, String> where
        // key is user.getId, value is Name: + user.getName
    }

    private static Map<String, Integer> nameAgeMap() {
        return users.stream()
                .filter(isAgeAllowed::isValid) //оставили подходящих по ворзрасту
                .filter(isNameLong) //оставили только с именем длиннее 8 символов (>=8)
                .collect(Collectors.toMap(User::getName, User::getAge)); //конвертировали все это  в мапу, где ключ имя, а возраст значение
    }

    public static List<User> validUsersList() {
        return users.stream().filter(user -> user.getAge() > 30).collect(Collectors.toList());
    }

    public static List<User> longNameUsersList() {
        return users.stream().filter(isNameLong).collect(Collectors.toList());
    }

    private static List<String> userNameList() {
        return users.stream().map(User::getName).collect(Collectors.toList());
    }

    private static long validUsersCount() {
        return users.stream().map(isAgeAllowed::isValid).count();
    }

}
