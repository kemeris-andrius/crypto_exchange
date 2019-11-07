package lt.codeacademy;

import lt.codeacademy.transcations.Transaction;
import lt.codeacademy.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;
        List<User> users = new ArrayList<>();

        do {
            printMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createUser(scanner, users);
                    break;
                case 2:
                    System.out.println("Vartotoju sarasas: ");
                    printUsers(users);
                    break;
                case 3:
                    deleteUser(scanner, users);
                    break;
                case 4:
                    transactMoney(scanner, users);
                    break;
                case 5:
                    topUpAccount(scanner, users);
                    break;
                case 0:
                    System.out.println("Baigiame darba");
                    break;
                default:
                    System.out.println("Bloga ivestis");
            }
        } while(choice != 0);

    }

    private static void topUpAccount(Scanner scanner, List<User> users) {
        printUsers(users);
        System.out.println("Iveskite saskaitos numeri");
        String accNumber = scanner.next();
        System.out.println("Kiek pinigu norite inesti");
        double topUp = scanner.nextDouble();

        for (User u: users) {
            if(u.getAccountNumber().equals(accNumber)) {
                u.setBalance(u.getBalance() + topUp);
            }
        }
    }

    private static void transactMoney(Scanner scanner, List<User> users) {
        printUsers(users);

        System.out.println("Iveskite sask numeri is kurio darysite pavedima: ");
        String accountFrom = scanner.next();

        System.out.println("Iveskite sask numeri i kuri darysite pavedima: ");
        String accountTo = scanner.next();

        System.out.println("Iveskite suma kuria norite pervesti: ");
        double amount = scanner.nextDouble();

        User from = null;
        User to = null;

        for(User u: users) {
            if(u.getAccountNumber().equals(accountFrom)) {
                from = u;
            }
            if(u.getAccountNumber().equals(accountTo)) {
                to = u;
            }
        }
        users.remove(from);
        users.remove(to);

        Transaction transaction = new Transaction(from, to, amount);

        List<User> transactedUsers = transaction.execute().stream()
                .filter(u -> u != null)
                .collect(Collectors.toList());

        users.addAll(transactedUsers);
    }

    private static void deleteUser(Scanner scanner, List<User> users) {
        printUsers(users);
        System.out.println("Iveskite norimo istrinti vartotojo numeri: ");
        int idx = scanner.nextInt();
        users.remove(idx);
        printUsers(users);
    }

    private static void createUser(Scanner scanner, List<User> users) {
        System.out.println("Iveskite savo varda: ");
        String name = scanner.next();

        System.out.println("Iveskite savo balansa: ");
        double balance = scanner.nextDouble();

        User user = new User(name, balance);
        users.add(user);
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("1. Sukurti vartotoja");
        System.out.println("2. Perziureti vartotojus");
        System.out.println("3. Istrinti vartotoja");
        System.out.println("4. Daryti pavedima");
        System.out.println("5. Inesti pinigus");
        System.out.println("----------------------------");
        System.out.println("0. Baigti");
    }

    public static void printUsers(List<User> users) {
        int i = 0;
        for (User u: users) {
            System.out.println(i+ ". "+u.toString());
            i++;
        }
    }
}
