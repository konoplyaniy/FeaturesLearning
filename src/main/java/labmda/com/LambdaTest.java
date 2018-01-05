package labmda.com;

import labmda.com.interfaces.Printerable;
import labmda.com.interfaces.Validable;
import labmda.com.interfaces.Workable;
import org.apache.log4j.BasicConfigurator;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static labmda.com.DataGenerator.users;
import static labmda.com.ExcelWriter.log;

public class LambdaTest {

    private static final Printerable printer = log::info;
    private static final Predicate<User> isNameLong = (user -> user.getName().length() >= 8);
    private static final Validable isAgeAllowed = (Object o) -> {
        if (o instanceof User) {
            User currentUser = (User) o;
            return currentUser.getAge() > 30;
        }
        return false;
    };


    public static void main(String[] args) {
        BasicConfigurator.configure();
      /*  long start = System.currentTimeMillis();
       *//* List<Integer> list = new ArrayList<>();*//*
        for (int i = 1; i <= 1000000; i++) {
            *//*list.add(i);*//*
            String s = "";
            if (i%3==0)
                s+="Fizz";
            if (i%5==0)
                s+="Buzz";
            if (!s.isEmpty())
                log.info(s);
        }
        *//*List<Integer> tempList = list.stream().filter(i -> (i % 3 == 0) || (i % 5 == 0)).collect(Collectors.toList());
        tempList.forEach(i -> {
            if (i % 15 == 0)
                System.out.println("FizzBuzz");
            else {
                System.out.println(((i % 3 == 0) ? "Fizz" : "Buzz"));
            }
        });*//*
        log.info(System.currentTimeMillis() - start);*/

//        usersAgeAverage();
//        userMaxAge();
//        userMinAge();
//        usersNameLengthAverage();
//        userAgeStatistic();
//        validUsersCount();
//        userNameList();
//        longNameUsersList();
//        validUsersList();
//        nameAgeMap();
//        idNameMap();
        userAgeStatistic();
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
        IntSummaryStatistics statisticsByUsersAge = users.stream()
                .collect(Collectors.summarizingInt(User::getAge));
        printer.print("======SUMMARY=======");
        printer.print("Number of Users: " + statisticsByUsersAge.getCount());
        printer.print("Min age: " + statisticsByUsersAge.getMin());
        printer.print("Max age: " + statisticsByUsersAge.getMax());
        printer.print("Age average: " + statisticsByUsersAge.getAverage());

        return users.stream()
                .collect(Collectors.summarizingInt(User::getAge));
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

    private void ttt() {
        isNameLong.test(new User(0, "", 99));
    }

    private static Map<String, Integer> nameAgeMap() {
        return users.stream()
                .filter(isAgeAllowed::isValid) //оставили подходящих по ворзрасту
                .filter(isNameLong) //оставили только с именем длиннее 8 символов (>=8)
                .collect(Collectors.toMap(User::getName, User::getAge)); //конвертировали все это  в мапу, где ключ имя, а возраст значение
    }

    public static List<User> validUsersList() {
        return users.stream()
                .filter(user -> user.getAge() > 30)
                .collect(Collectors.toList());
    }

    public static List<User> longNameUsersList() {
        return users.stream()
                .filter(isNameLong)
                .collect(Collectors.toList());
    }

    private static List<String> userNameList() {
        System.out.println(worker.work(1200));
        System.out.println(ww.apply(1200L));
        return users.stream().map(User::getName).collect(Collectors.toList());
    }

    private static Function<Long, String> ww = (timeDiff) -> {
        long diffSeconds = timeDiff / 1000 % 60;
        long diffMinutes = timeDiff / (60 * 1000) % 60;
        long diffHours = timeDiff / (60 * 60 * 1000) % 24;
        return (diffHours == 0 ? "" : diffHours + "h ") +
                (diffMinutes == 0 ? "" : diffMinutes + "m ") +
                (diffSeconds == 0 ? "" : diffSeconds + "s ");
    };

    private static Workable worker = (long timeDiff) -> {
        long diffSeconds = timeDiff / 1000 % 60;
        long diffMinutes = timeDiff / (60 * 1000) % 60;
        long diffHours = timeDiff / (60 * 60 * 1000) % 24;
        return (diffHours == 0 ? "" : diffHours + "h ") +
                (diffMinutes == 0 ? "" : diffMinutes + "m ") +
                (diffSeconds == 0 ? "" : diffSeconds + "s ");
    };

    private String getLongAsTime(long timeDiff) {
        long diffSeconds = timeDiff / 1000 % 60;
        long diffMinutes = timeDiff / (60 * 1000) % 60;
        long diffHours = timeDiff / (60 * 60 * 1000) % 24;
        return (diffHours == 0 ? "" : diffHours + "h ") +
                (diffMinutes == 0 ? "" : diffMinutes + "m ") +
                (diffSeconds == 0 ? "" : diffSeconds + "s ");
    }

    private static long validUsersCount() {
        return users.stream().map(isAgeAllowed::isValid).count();
    }

}
