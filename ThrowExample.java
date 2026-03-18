import java.util.*;

public class ThrowExample {

    static class AgeException extends Exception {
        public AgeException(String message) {
            super(message);
        }
    }

    static void validateAge(int age) throws AgeException {
        if (age < 18) {
            throw new AgeException("Must be 18+. You entered: " + age);
        }
        System.out.println("Access granted! Age: " + age);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        try {
            validateAge(age);
        } catch (AgeException e) {                          // ✅ handles checked exception
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Validation complete.");     //  always runs
        }
    }
}

