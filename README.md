# ☕ Java Exception Handling — Complete Guide

> *"An Exception is an **event** that occurs during the execution of a program which **disrupts the normal flow** of set instructions."*

---

## 📑 Table of Contents

- [What is an Exception?](#-what-is-an-exception)
- [What is Exception Handling?](#-what-is-exception-handling)
- [Why Do We Need Exception Handling?](#-why-do-we-need-exception-handling)
- [Types of Exceptions](#-types-of-exceptions)
- [Exception Hierarchy](#-exception-hierarchy-in-java)
- [Keywords](#-keywords-in-exception-handling)
  - [try](#1-try)
  - [catch](#2-catch)
  - [finally](#3-finally)
  - [throw](#4-throw)
  - [throws](#5-throws)
- [Summary Table](#-summary-table)

---

## ❓ What is an Exception?

Think of a program as a set of instructions given to Java, one after another:

```
Step 1 → Step 2 → Step 3 → Step 4 → Step 5
```

When an **unexpected event** occurs at, say, Step 3 — like trying to divide a number by zero, or reading a file that doesn't exist — Java **cannot continue** to Step 4 and Step 5.

That unexpected event is called an **Exception**.

```java
public class WhatIsException {
    public static void main(String[] args) {
        System.out.println("Step 1 - Start");
        System.out.println("Step 2 - Before error");

        int result = 10 / 0; // ❌ Exception occurs here! (ArithmeticException)

        System.out.println("Step 3 - After error"); // ❌ This line NEVER executes
        System.out.println("Step 4 - End");          // ❌ This line NEVER executes
    }
}
```

**Output:**
```
Step 1 - Start
Step 2 - Before error
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

> ⚡ Normal flow was **disrupted** at `10 / 0`. Steps 3 and 4 were skipped, and the program **crashed**.

---

## 🛡️ What is Exception Handling?

By default, when an exception occurs, **Java decides** to stop the program.

But **you (the programmer)** may want to **decide what to do** when an exception occurs — show a friendly message, retry, log the error, or continue with something else.

> **Exception Handling** is the mechanism that allows the programmer to take control of what happens when an exception occurs, instead of letting Java crash the program.

```java
public class ExceptionHandling {
    public static void main(String[] args) {

        System.out.println("Step 1 - Start");

        try {
            System.out.println("Step 2 - Before error");
            int result = 10 / 0; // ⚠️ Exception occurs here
            System.out.println("Step 3 - After error"); // Skipped
        } catch (ArithmeticException e) {
            // ✅ YOU decide what happens — not Java!
            System.out.println("Caught! Cannot divide by zero: " + e.getMessage());
        }

        System.out.println("Step 4 - Program continues normally ✅");
    }
}
```

**Output:**
```
Step 1 - Start
Step 2 - Before error
Caught! Cannot divide by zero: / by zero
Step 4 - Program continues normally ✅
```

> ✅ Now **you** are in control. The program did not crash!

---

## 🤔 Why Do We Need Exception Handling?

| Without Exception Handling | With Exception Handling |
|---|---|
| Program crashes abruptly | Program continues gracefully |
| User sees scary error messages | User sees friendly messages |
| Data can get corrupted | Resources are safely closed |
| Hard to find where the error occurred | Error is caught and logged clearly |
| Bad user experience | Smooth user experience |

### Real-World Example

```java
import java.util.Scanner;

public class WhyNeedHandling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");

        try {
            int num = Integer.parseInt(sc.nextLine()); // User types "abc" instead of number
            System.out.println("You entered: " + num);
        } catch (NumberFormatException e) {
            // ✅ Instead of crashing, we inform the user politely
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }
}
```

---

## 📂 Types of Exceptions

Java exceptions are divided into **2 main types**:

### 1. ✅ Checked Exception (Compile-Time Exception)

- These are exceptions that the **compiler checks** at compile time.
- If you don't handle them, your **code will not compile**.
- Usually related to **external resources** (files, databases, networks).

```java
import java.io.*;

public class CheckedExample {
    public static void main(String[] args) {

        // ❌ Without try-catch or throws — COMPILE ERROR
        // FileReader fr = new FileReader("abc.txt");

        // ✅ With try-catch — Compiles successfully
        try {
            FileReader fr = new FileReader("abc.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
```

**Common Checked Exceptions:**

| Exception | When It Occurs |
|---|---|
| `FileNotFoundException` | File does not exist |
| `IOException` | Input/Output operation fails |
| `SQLException` | Database access error |
| `ClassNotFoundException` | Class is not found at runtime |

---

### 2. ⚠️ Unchecked Exception (Runtime Exception)

- These are exceptions that occur **at runtime**, not at compile time.
- The compiler does **not force** you to handle them.
- Usually caused by **programmer mistakes** (wrong logic, null values, etc.).

```java
public class UncheckedExample {
    public static void main(String[] args) {

        // ✅ Compiles fine — but crashes at runtime!
        int[] arr = new int[3];
        arr[10] = 5; // ❌ ArrayIndexOutOfBoundsException at runtime

        String str = null;
        str.length(); // ❌ NullPointerException at runtime

        int result = 10 / 0; // ❌ ArithmeticException at runtime
    }
}
```

**Common Unchecked Exceptions:**

| Exception | When It Occurs |
|---|---|
| `ArithmeticException` | Division by zero |
| `NullPointerException` | Using a null reference |
| `ArrayIndexOutOfBoundsException` | Accessing invalid array index |
| `NumberFormatException` | Parsing invalid number string |
| `ClassCastException` | Invalid type casting |
| `StackOverflowError` | Infinite recursion |

---

## 🌳 Exception Hierarchy in Java

All exceptions in Java come from a single root class — `Throwable`.

```
java.lang.Object
    └── java.lang.Throwable
            ├── java.lang.Error                         ← Serious system errors (don't handle)
            │       ├── OutOfMemoryError
            │       ├── StackOverflowError
            │       └── VirtualMachineError
            │
            └── java.lang.Exception                     ← Things we can handle
                    ├── IOException                     ← Checked
                    │       └── FileNotFoundException   ← Checked
                    ├── SQLException                    ← Checked
                    ├── ClassNotFoundException          ← Checked
                    │
                    └── RuntimeException               ← Unchecked
                            ├── ArithmeticException
                            ├── NullPointerException
                            ├── NumberFormatException
                            ├── ArrayIndexOutOfBoundsException
                            └── ClassCastException
```

### Key Difference: Error vs Exception

| | `Error` | `Exception` |
|---|---|---|
| Caused by | JVM / System | Programmer / External |
| Recoverable? | ❌ No | ✅ Yes |
| Should we handle? | ❌ No | ✅ Yes |
| Example | `OutOfMemoryError` | `FileNotFoundException` |

---

## 🔑 Keywords in Exception Handling

Java provides **5 keywords** to handle exceptions:

```
try  →  catch  →  finally  →  throw  →  throws
```

---

### 1. `try`

> **"Try to execute this block of code that might cause an exception."**

- The block of code that might throw an exception is placed inside `try`.
- If an exception occurs inside `try`, the remaining lines are skipped and control jumps to `catch`.

```java
try {
    // Risky code goes here
    int result = 10 / 0; // This might throw an exception
    System.out.println("Result: " + result); // Skipped if exception occurs
}
```

> 🔴 `try` block **cannot exist alone** — it must be followed by `catch`, `finally`, or both.

---

### 2. `catch`

> **"If an exception occurs in try, catch it here and handle it."**

- `catch` block receives the exception object and allows you to handle it.
- You can have **multiple catch blocks** for different exception types.

```java
public class CatchExample {
    public static void main(String[] args) {
        try {
            int[] arr = new int[5];
            arr[10] = 100; // ❌ ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array error: " + e.getMessage());
        } catch (Exception e) {
            // Generic catch — catches any other exception
            System.out.println("Some error: " + e.getMessage());
        }
    }
}
```

**Multiple catch blocks:**

```java
try {
    int result = Integer.parseInt("abc"); // NumberFormatException
    int div = 10 / 0;                    // ArithmeticException
} catch (NumberFormatException e) {
    System.out.println("Not a valid number!");
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero!");
} catch (Exception e) {
    System.out.println("Unknown error: " + e.getMessage());
}
```

> ⚠️ Always put **specific exceptions first**, and `Exception` (generic) last.

---

### 3. `finally`

> **"This block ALWAYS executes — whether an exception occurred or not."**

- Used to **release resources** like closing files, database connections, etc.
- Executes even if:
  - No exception occurs ✅
  - An exception occurs and is caught ✅
  - An exception occurs and is NOT caught ✅

```java
import java.io.*;

public class FinallyExample {
    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("abc.txt");
            System.out.println("File opened successfully");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } finally {
            // ✅ Always runs — perfect place to close resources
            System.out.println("Finally block executed — closing resources");
            if (fis != null) {
                try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }
}
```

**Output (if file not found):**
```
File not found: abc.txt (No such file or directory)
Finally block executed — closing resources
```

> 💡 `finally` block does **not execute** only if `System.exit()` is called inside `try`.

---

### 4. `throw`

> **"Manually throw an exception yourself."**

- Used when you want to **explicitly create and throw** an exception based on your own condition.
- You use the `new` keyword to create an exception object and throw it.

```java
public class ThrowExample {

    static void validateAge(int age) {
        if (age < 18) {
            // ✅ Manually throwing an exception with a custom message
            throw new ArithmeticException("Age must be 18 or above. You entered: " + age);
        } else {
            System.out.println("Access granted! Age: " + age);
        }
    }

    public static void main(String[] args) {
        try {
            validateAge(15); // ❌ This will trigger the throw
        } catch (ArithmeticException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        validateAge(20); // ✅ This works fine
    }
}
```

**Output:**
```
Exception caught: Age must be 18 or above. You entered: 15
Access granted! Age: 20
```

> ⚠️ `throw` is always used with an **object** of an exception class — `throw new ExceptionName("message");`

---

### 5. `throws`

> **"This method might throw these exceptions — caller must handle them."**

- Used in a **method signature** to declare that the method might throw one or more checked exceptions.
- It does **not handle** the exception — it passes the responsibility to the **caller**.
- Required only for **Checked Exceptions** (Compile-time).

```java
import java.io.*;

public class ThrowsExample {

    // Declaring that this method might throw these exceptions
    static void readFile(String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        FileReader fr = new FileReader(file);            // Might throw FileNotFoundException
        FileInputStream fis = new FileInputStream(file); // Might throw IOException

        int data = fis.read();
        while (data != -1) {
            System.out.print((char) data);
            data = fis.read();
        }
        fr.close();
        fis.close();
    }

    public static void main(String[] args) {
        // Caller MUST handle the declared exceptions
        try {
            readFile("abc.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }
    }
}
```

---

### `throw` vs `throws` — Key Difference

| Feature | `throw` | `throws` |
|---|---|---|
| **Purpose** | Actually throws an exception | Declares a method might throw |
| **Where used** | Inside method body | In method signature |
| **Followed by** | An exception object | Exception class name(s) |
| **Example** | `throw new IOException("error");` | `void read() throws IOException` |
| **Count** | One exception at a time | Multiple (comma-separated) |

```java
// throw — used inside the method body
void checkAge(int age) {
    throw new IllegalArgumentException("Invalid age");  // throws ONE exception object
}

// throws — used in the method signature
void readFile() throws IOException, FileNotFoundException {  // declares MULTIPLE
    // method body
}
```

---

## 📊 Summary Table

| Keyword | Role | Where Used | Example |
|---|---|---|---|
| `try` | Wraps risky code | Inside method | `try { int x = 10/0; }` |
| `catch` | Handles the exception | After `try` | `catch(Exception e) { }` |
| `finally` | Always executes (cleanup) | After `catch` | `finally { file.close(); }` |
| `throw` | Manually throws exception | Inside method body | `throw new Exception("msg");` |
| `throws` | Declares possible exceptions | Method signature | `void m() throws IOException` |

---

## 🔄 Complete Example — All 5 Keywords Together

```java
import java.io.*;

public class CompleteExample {

    // 'throws' — declares this method might throw IOException
    static void processFile(String filename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            // 'throw' — manually throw an exception
            throw new FileNotFoundException("Custom: File '" + filename + "' not found!");
        }

        FileReader fr = new FileReader(file);
        System.out.println("File found and opened!");
        fr.close();
    }

    public static void main(String[] args) {
        // 'try' — attempt risky code
        try {
            processFile("test.txt");
        }
        // 'catch' — handle FileNotFoundException
        catch (FileNotFoundException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        // 'catch' — handle any other IOException
        catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        // 'finally' — always runs no matter what
        finally {
            System.out.println("Finally: Program ends cleanly.");
        }
    }
}
```

**Output (if file doesn't exist):**
```
Caught: Custom: File 'test.txt' not found!
Finally: Program ends cleanly.
```

---

## 📌 Quick Recap in Simple Words

```
Exception     → An event that disrupts normal flow of instructions
Handling      → You decide what to do when that event occurs

try           → "Watch this risky code"
catch         → "If something goes wrong, do this"
finally       → "Always do this, no matter what"
throw         → "I am intentionally creating an exception"
throws        → "This method may cause these exceptions — caller, you handle it"

Checked       → Compiler forces you to handle it  (FileNotFoundException, IOException)
Unchecked     → Occurs at runtime, optional to handle  (NullPointerException, ArithmeticException)
Error         → Serious JVM issues, don't try to handle  (OutOfMemoryError)
```

---

<div align="center">
  <b>⭐ Star this repo if it helped you understand Java Exception Handling!</b>
</div>
