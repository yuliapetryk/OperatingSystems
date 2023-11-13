import java.util.Scanner;

public class Manager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = 0;
        System.out.println("Please, enter x: ");
        if (sc.hasNextInt()) {
            x = sc.nextInt();
        }
        sc.nextLine();
        System.out.println(x);
    }
}