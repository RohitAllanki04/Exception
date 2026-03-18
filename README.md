<div align="center">

# ☕ Java Exception Handling
### Complete Guide — From Basics to Advanced

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/Status-Complete-blue?style=for-the-badge)

> *"An Exception is an **event** that disrupts the **normal flow** of a program's instructions."*

</div>

---

## 📑 Table of Contents

| # | Topic |
|---|---|
| 1 | [What is an Exception?](#1--what-is-an-exception) |
| 2 | [What Prints on Console?](#2--what-prints-on-console) |
| 3 | [What is Exception Handling?](#3--what-is-exception-handling) |
| 4 | [Why Do We Need It?](#4--why-do-we-need-exception-handling) |
| 5 | [Types of Exceptions](#5--types-of-exceptions) |
| 6 | [Exception Hierarchy](#6--exception-hierarchy) |
| 7 | [Keyword — try](#7--keyword--try) |
| 8 | [Keyword — catch](#8--keyword--catch) |
| 9 | [Multiple Catches](#9--multiple-catches) |
| 10 | [Multi-Catch `Ex1 \| Ex2`](#10--multi-catch--catchex1--ex2-e) |
| 11 | [Keyword — finally](#11--keyword--finally) |
| 12 | [When finally Does NOT Execute](#12--when-finally-does-not-execute) |
| 13 | [Keyword — throw](#13--keyword--throw) |
| 14 | [throw new Exception("msg") — Console Behavior](#14--throw-new-exceptionmessage--console-behavior) |
| 15 | [Keyword — throws](#15--keyword--throws) |
| 16 | [Try-with-Resources](#16--try-with-resources) |
| 17 | [AutoCloseable Interface](#17--autocloseable-interface) |
| 18 | [Parameters vs Resources](#18--parameters-vs-resources) |
| 19 | [User-Defined Exceptions](#19--user-defined-exceptions) |
| 20 | [Exception Methods Cheat Sheet](#20--exception-methods-cheat-sheet) |
| 21 | [Summary Table](#21--summary-table) |
| 22 | [Complete Example](#22--complete-example--all-concepts) |

---

## 1. ❓ What is an Exception?

Think of a program as a sequence of steps:

```
Step 1 → Step 2 → Step 3 → Step 4 → Step 5
```

When an **unexpected event** occurs at Step 3 (like dividing by zero, or reading a missing file), Java **cannot move** to Step 4. That unexpected event is called an **Exception**.

```java
public class WhatIsException {
    public static void main(String[] args) {
        System.out.println("Step 1 - Start");
        System.out.println("Step 2 - Before error");

        int result = 10 / 0;  // ❌ ArithmeticException thrown here

        System.out.println("Step 3 - After error");  // ❌ NEVER executes
        System.out.println("Step 4 - End");           // ❌ NEVER executes
    }
}
```

**Console Output:**
```
Step 1 - Start
Step 2 - Before error
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at WhatIsException.main(WhatIsException.java:6)
```

> ⚡ Steps 3 and 4 were **skipped** — the program **crashed**.

---

## 2. 🖥️ What Prints on Console?

When an exception is **not caught**, Java prints a **Stack Trace**. Understanding it is key to debugging.

```java
public class ConsoleOutput {
    public static void main(String[] args) { methodA(); }
    static void methodA()                  { methodB(); }
    static void methodB() {
        int result = 10 / 0;  // ❌ Crash happens here
    }
}
```

**Console Output:**
```
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at ConsoleOutput.methodB(ConsoleOutput.java:4)
    at ConsoleOutput.methodA(ConsoleOutput.java:3)
    at ConsoleOutput.main(ConsoleOutput.java:2)
```

**Breaking Down Each Part:**

```
Exception in thread "main"                   → Which thread crashed
java.lang.ArithmeticException                → Full class name (package.ClassName)
: / by zero                                  → The message — what went wrong
at ConsoleOutput.methodB(ConsoleOutput.java:4) → Exact crash location (top = actual crash)
at ConsoleOutput.methodA(ConsoleOutput.java:3) → methodA called methodB
at ConsoleOutput.main(ConsoleOutput.java:2)    → main called methodA
```

> 📌 Read `at` lines **bottom to top** for call flow — but the **actual crash** is always the **topmost** `at` line.

### `getMessage()` vs `toString()` vs `printStackTrace()`

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println(e.getMessage());   // (1)
    System.out.println(e.toString());     // (2)
    e.printStackTrace();                  // (3)
}
```

**Output:**
```
(1) → / by zero
(2) → java.lang.ArithmeticException: / by zero
(3) → java.lang.ArithmeticException: / by zero
          at ConsoleOutput.main(ConsoleOutput.java:3)
```

| Method | What It Shows |
|---|---|
| `e.getMessage()` | Only the message string |
| `e.toString()` | ClassName + message |
| `e.printStackTrace()` | Full stack trace with line numbers |
| `System.out.println(e)` | Same as `e.toString()` |

---

## 3. 🛡️ What is Exception Handling?

By default, Java **decides to crash** when an exception occurs. **Exception Handling** lets **you** decide what to do instead.

```java
public class ExceptionHandling {
    public static void main(String[] args) {
        System.out.println("Step 1 - Start");
        try {
            System.out.println("Step 2 - Before error");
            int result = 10 / 0;                         // ⚠️ Exception occurs
            System.out.println("Step 3 - After error");  // Skipped
        } catch (ArithmeticException e) {
            System.out.println("Caught! " + e.getMessage()); // ✅ You handle it
        }
        System.out.println("Step 4 - Program continues ✅");
    }
}
```

**Output:**
```
Step 1 - Start
Step 2 - Before error
Caught! / by zero
Step 4 - Program continues ✅
```

---

## 4. 🤔 Why Do We Need Exception Handling?

| Without Exception Handling | With Exception Handling |
|---|---|
| Program crashes abruptly | Program continues gracefully |
| User sees scary error messages | User sees friendly messages |
| Data can get corrupted | Resources are safely closed |
| Hard to find error location | Error is caught and logged clearly |

```java
try {
    int num = Integer.parseInt(sc.nextLine()); // User types "abc"
} catch (NumberFormatException e) {
    System.out.println("Please enter a valid number."); // ✅ Friendly message
}
```

---

## 5. 📂 Types of Exceptions

### ✅ Checked Exception *(Compile-Time)*

The **compiler forces** you to handle these. If you don't, your code **won't compile**.
Usually involves **external resources** — files, databases, networks.

```java
// ❌ Without handling — COMPILE ERROR
FileReader fr = new FileReader("abc.txt");

// ✅ With handling — compiles fine
try {
    FileReader fr = new FileReader("abc.txt");
} catch (FileNotFoundException e) {
    System.out.println("File not found: " + e.getMessage());
}
```

| Exception | When It Occurs |
|---|---|
| `FileNotFoundException` | File does not exist |
| `IOException` | Input/Output operation fails |
| `SQLException` | Database access error |
| `ClassNotFoundException` | Class not found at runtime |

---

### ⚠️ Unchecked Exception *(Runtime)*

Compiler **does not force** you to handle these. They occur at **runtime** due to programmer mistakes.

```java
int[] arr = new int[3];
arr[10] = 5;        // ❌ ArrayIndexOutOfBoundsException

String str = null;
str.length();       // ❌ NullPointerException

int result = 10/0;  // ❌ ArithmeticException
```

| Exception | When It Occurs |
|---|---|
| `ArithmeticException` | Division by zero |
| `NullPointerException` | Using a null reference |
| `ArrayIndexOutOfBoundsException` | Invalid array index |
| `NumberFormatException` | Parsing invalid number string |
| `ClassCastException` | Invalid type casting |
| `StringIndexOutOfBoundsException` | Invalid String index |
| `StackOverflowError` | Infinite recursion |

---

## 6. 🌳 Exception Hierarchy

```
java.lang.Object
    └── java.lang.Throwable
            ├── java.lang.Error                          ← System-level, don't handle
            │       ├── OutOfMemoryError
            │       ├── StackOverflowError
            │       └── VirtualMachineError
            │
            └── java.lang.Exception                      ← We handle these
                    ├── IOException                      ← Checked
                    │       └── FileNotFoundException    ← Checked
                    ├── SQLException                     ← Checked
                    ├── ClassNotFoundException           ← Checked
                    │
                    └── RuntimeException                 ← Unchecked
                            ├── ArithmeticException
                            ├── NullPointerException
                            ├── NumberFormatException
                            ├── ArrayIndexOutOfBoundsException
                            └── ClassCastException
```

| | `Error` | `Exception` |
|---|---|---|
| Caused by | JVM / System | Programmer / External resource |
| Recoverable? | ❌ No | ✅ Yes |
| Should we handle? | ❌ No | ✅ Yes |
| Example | `OutOfMemoryError` | `FileNotFoundException` |

---

## 7. 🔑 Keyword — `try`

> *"Try to execute this block of code that might cause an exception."*

**⚠️ IMPORTANT — Once an exception occurs, ALL lines below it inside `try` are immediately skipped.**

```java
public class TryFlowExample {
    public static void main(String[] args) {
        try {
            System.out.println("Line 1 ✅");
            System.out.println("Line 2 ✅");
            int result = 10 / 0;                    // 💥 Exception thrown HERE
            System.out.println("Line 3 ❌ SKIPPED"); // Never executes
            System.out.println("Line 4 ❌ SKIPPED"); // Never executes
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        System.out.println("After try-catch ✅");
    }
}
```

**Output:**
```
Line 1 ✅
Line 2 ✅
Caught: / by zero
After try-catch ✅
```

**Visual Flow:**
```
try {
    line 1  ✅ runs
    line 2  ✅ runs
    line 3  💥 exception ──────────────────┐
    line 4  ❌ SKIPPED                     │
    line 5  ❌ SKIPPED                     │
}                                           │
catch (Exception e) { ◄─────────────────────┘
    // control jumps directly here
}
```

> 🔴 `try` **cannot exist alone** — must be followed by `catch`, `finally`, or both.

---

## 8. 🥅 Keyword — `catch`

> *"If an exception occurs in try, catch it here and handle it."*

```java
try {
    int[] arr = new int[5];
    arr[10] = 100;  // ❌ ArrayIndexOutOfBoundsException
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array error: " + e.getMessage());
} catch (Exception e) {
    System.out.println("Other error: " + e.getMessage()); // fallback
}
```

---

## 9. 🔀 Multiple Catches

Use multiple `catch` blocks when your `try` block can throw **different types of exceptions**.

```java
public class MultipleCatches {
    public static void main(String[] args) {
        int[] a = {2, 0};

        try {
            int r = a[0] / a[1];   // 💥 ArithmeticException (a[1] = 0)
            int s = a[0] / a[3];   // ❌ Never reached
        }
        catch (ArithmeticException e) {
            System.out.println("Arithmetic: " + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Other: " + e.getMessage()); // Always last
        }
    }
}
```

**Output:**
```
Arithmetic: / by zero
```

**Why was `a[0] / a[3]` never reached?**

```
try {
    r = a[0] / a[1];   // 💥 Throws here → exits try block immediately
    s = a[0] / a[3];   // ❌ Abandoned — control already in catch
}
```

**Rules for Multiple Catches:**

```
✅  Specific exceptions  →  FIRST
✅  Generic Exception    →  LAST

catch (ArithmeticException e)         ← specific, first
catch (ArrayIndexOutOfBoundsException e)← specific, second
catch (Exception e)                   ← generic, ALWAYS last
```

> ❌ Placing `catch (Exception e)` before specific catches = **compile error**

---

## 10. 🔗 Multi-Catch — `catch(Ex1 | Ex2 e)`

**Java 7+** — Catch multiple exception types in a **single catch block** using the `|` pipe symbol.

```java
try {
    String s = null;
    System.out.println(s.length()); // NullPointerException
    int[] arr = new int[3];
    System.out.println(arr[10]);    // ArrayIndexOutOfBoundsException
}
catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
    System.out.println("Caught: " + e.getMessage()); // handles both
}
```

**Old way vs New way:**

```java
// ❌ Old — repeated code
catch (NullPointerException e)            { log(e); }
catch (ArrayIndexOutOfBoundsException e)  { log(e); } // same body, duplicated

// ✅ New (Java 7+) — DRY and clean
catch (NullPointerException | ArrayIndexOutOfBoundsException e) { log(e); }
```

**Rules:**

| Rule | Detail |
|---|---|
| Separator | `\|` pipe between exception types |
| Variable | Only **one** variable `e` — shared |
| Variable is final | Cannot reassign `e` inside the block |
| No parent-child types | `IOException \| FileNotFoundException` → ❌ compile error |
| Use case | Two **unrelated** exceptions with same handling |

```java
// ❌ Compile error — FileNotFoundException is child of IOException
catch (IOException | FileNotFoundException e) { }

// ✅ Correct — unrelated exceptions
catch (IOException | ArithmeticException e) { }
```

---

## 11. 🔒 Keyword — `finally`

> *"This block ALWAYS executes — whether an exception occurred or not."*

Used to **release resources** (close files, DB connections, etc.).

```java
try {
    FileInputStream fis = new FileInputStream("abc.txt");
    System.out.println("File opened");
} catch (FileNotFoundException e) {
    System.out.println("File not found: " + e.getMessage());
} finally {
    System.out.println("Finally always runs ✅");
}
```

**Output (file missing):**
```
File not found: abc.txt (No such file or directory)
Finally always runs ✅
```

**Executes in all three scenarios:**

| Scenario | `finally` runs? |
|---|---|
| No exception | ✅ Yes |
| Exception caught | ✅ Yes |
| Exception NOT caught | ✅ Yes |
| `System.exit()` called | ❌ No |
| Thread forcefully killed | ❌ No |
| JVM crashes | ❌ No |

---

## 12. ⚠️ When `finally` Does NOT Execute

### Case 1 — `System.exit()`
```java
try {
    System.out.println("Inside try");
    System.exit(0);   // ← Kills JVM immediately
} finally {
    System.out.println("Finally"); // ❌ NEVER prints
}
```
**Output:** `Inside try`

> `System.exit()` terminates the **entire JVM process** — `finally` has no chance to run.

---

### Case 2 — Thread Forcefully Destroyed
```java
Thread t = new Thread(() -> {
    try {
        System.out.println("Thread running");
        Thread.currentThread().stop(); // ← Deprecated, forcefully kills thread
    } finally {
        System.out.println("Finally"); // ❌ May NOT execute
    }
});
t.start();
```

---

### Case 3 — JVM Crash / Fatal Error
```java
try {
    main(args); // ← Infinite recursion → StackOverflowError → JVM may crash
} finally {
    System.out.println("Finally"); // ❌ May not execute
}
```

---

### Case 4 — Infinite Loop in `try`
```java
try {
    while (true) { } // ← Loops forever, finally never reached
} finally {
    System.out.println("Finally"); // ❌ Never reached
}
```

**Summary:**
```
❌ finally SKIPPED when:
   → System.exit() is called
   → Thread is forcefully killed / destroyed
   → JVM crashes (OutOfMemoryError, fatal error)
   → Infinite loop inside try block

✅ finally ALWAYS runs when:
   → Normal execution
   → Any exception (caught or uncaught)
   → return statement inside try / catch
```

---

## 13. 🎯 Keyword — `throw`

> *"Manually throw an exception yourself based on your own condition."*

Syntax: `throw new ExceptionClassName("your message");`

```java
public class ThrowExample {

    static void validateAge(int age) {
        if (age < 18) {
            throw new ArithmeticException("Must be 18+. You entered: " + age);
        }
        System.out.println("Access granted! Age: " + age);
    }

    public static void main(String[] args) {
        try {
            validateAge(15);  // ❌ throws exception
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        validateAge(20);  // ✅ works fine
    }
}
```

**Output:**
```
Caught: Must be 18+. You entered: 15
Access granted! Age: 20
```

---

## 14. 📟 `throw new Exception("message")` — Console Behavior

### When NOT Caught — Default JVM Output

```java
public class ThrowConsole {
    public static void main(String[] args) {
        throw new ArithmeticException("Something went wrong!");
    }
}
```

**Console (uncaught — JVM default):**
```
Exception in thread "main" java.lang.ArithmeticException: Something went wrong!
    at ThrowConsole.main(ThrowConsole.java:3)
```
```
↑ thread name      ↑ exception class        ↑ YOUR message string
```

---

### When Caught — You Control the Output

```java
try {
    throw new ArithmeticException("Custom error message");
} catch (ArithmeticException e) {
    System.out.println(e.getMessage());  // Custom error message
    System.out.println(e.toString());    // java.lang.ArithmeticException: Custom error message
    e.printStackTrace();                 // full stack trace
}
```

**Output:**
```
Custom error message
java.lang.ArithmeticException: Custom error message
java.lang.ArithmeticException: Custom error message
    at ThrowConsole.main(ThrowConsole.java:3)
```

### How the Message Flows

```
throw new ArithmeticException("Age must be 18");
                               ↑
                   This string becomes:
                   e.getMessage() → "Age must be 18"
                   e.toString()   → "java.lang.ArithmeticException: Age must be 18"
```

---

## 15. 📋 Keyword — `throws`

> *"This method might throw these exceptions — caller must handle them."*

- Used in **method signature** — not inside the body
- Does **not handle** the exception — delegates to the caller
- Required only for **Checked Exceptions**

```java
// Declares that this method may throw these exceptions
static void readFile(String path) throws FileNotFoundException, IOException {
    FileReader fr = new FileReader(path);   // may throw FileNotFoundException
    int data = fr.read();                   // may throw IOException
    fr.close();
}

public static void main(String[] args) {
    try {
        readFile("abc.txt");
    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());
    } catch (IOException e) {
        System.out.println("IO Error: " + e.getMessage());
    }
}
```

### `throw` vs `throws`

| Feature | `throw` | `throws` |
|---|---|---|
| Purpose | Actually throws an exception | Declares a method might throw |
| Where used | Inside method **body** | In method **signature** |
| Followed by | An exception **object** | Exception **class name(s)** |
| Count | One at a time | Multiple (comma-separated) |
| Example | `throw new IOException("err");` | `void m() throws IOException` |

```java
// throw — inside method body
void checkAge(int age) {
    throw new IllegalArgumentException("Invalid");  // one object
}

// throws — in method signature
void readFile() throws IOException, FileNotFoundException { }  // multiple classes
```

---

## 16. 🔒 Try-with-Resources

### Why We Need It


**Before Java 7 — Manual Close (verbose and error-prone,Avoid memory leaks if you use close()):**

```java
// ❌ OLD WAY
FileWriter fw = null;
try {
    fw = new FileWriter("abc.txt");
    fw.write("Hello");
} catch (IOException e) {
    System.out.println(e);
} finally {
    try {
        if (fw != null) fw.close(); // Easy to forget!
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

Problems:
- Lots of boilerplate
- Easy to forget `close()`
- If `close()` throws, the original exception is lost
- Hard to read

---

### Java 7+ — Try-with-Resources ✅

Resources declared inside `try(...)` are **automatically closed** when the block ends — even if an exception occurs.

```java
import java.io.*;

public class TrywithResources {
    public static void main(String[] args) throws IOException {

        File f = new File("./abc.txt");
        if (f.exists()) f.delete();
        f.createNewFile();

        try (
            FileWriter fw     = new FileWriter(f);       // Layer 1 — connects to file
            BufferedWriter bw = new BufferedWriter(fw);  // Layer 2 — adds buffering
            PrintWriter pw    = new PrintWriter(bw)      // Layer 3 — adds println()
        ) {
            pw.println("hello");
            pw.println("welcome to java");
            // ✅ pw → bw → fw auto-closed here in REVERSE order
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
```

### Writer Layering Visualized

```
pw.println("hello")
        ↓
  [ PrintWriter ]     ← You write here (println / print / printf)
        ↓
  [ BufferedWriter ]  ← Stores data in memory buffer (efficient)
        ↓
  [ FileWriter ]      ← Actually writes characters to disk
        ↓
  [ abc.txt ]         ← Final file on disk
```

### Auto-Close Order

```
Opened: fw → bw → pw
Closed: pw → bw → fw   ← Always REVERSE of opening order
```

---

## 17. 🔌 AutoCloseable Interface

For a class to work inside `try(...)`, it **must implement** `AutoCloseable` (or `Closeable`).

```java
// Java's built-in AutoCloseable interface
public interface AutoCloseable {
    void close() throws Exception; // called automatically at end of try block
}
```

### What Java Compiles It To

```java
// What YOU write:
try (FileWriter fw = new FileWriter("abc.txt")) {
    fw.write("Hello");
}

// What JAVA generates internally:
FileWriter fw = new FileWriter("abc.txt");
try {
    fw.write("Hello");
} finally {
    if (fw != null) fw.close(); // ← inserted automatically by compiler
}
```

### Creating Your Own AutoCloseable

```java
public class MyResource implements AutoCloseable {

    public MyResource() {
        System.out.println("Resource OPENED");
    }

    public void doWork() {
        System.out.println("Doing work...");
    }

    @Override
    public void close() {
        System.out.println("Resource CLOSED automatically ✅");
    }
}

// Usage
try (MyResource res = new MyResource()) {
    res.doWork();
}
```

**Output:**
```
Resource OPENED
Doing work...
Resource CLOSED automatically ✅
```

### Built-in Classes That Implement AutoCloseable

| Class | Package |
|---|---|
| `FileWriter` | `java.io` |
| `BufferedWriter` | `java.io` |
| `PrintWriter` | `java.io` |
| `FileInputStream` | `java.io` |
| `FileOutputStream` | `java.io` |
| `Scanner` | `java.util` |
| `Connection` (JDBC) | `java.sql` |

---

## 18. 📦 Parameters vs Resources

A common confusion — clearly explained:

| | Parameters | Resources |
|---|---|---|
| Location | Method signature `()` | Inside `try()` |
| Purpose | Pass data to a method | Declare objects to auto-close |
| Auto-closed? | ❌ No | ✅ Yes |
| Must implement AutoCloseable? | ❌ No | ✅ Yes |
| Example | `void read(String path)` | `try(FileWriter fw = ...)` |

```java
//         PARAMETERS — just data passed in, NOT auto-closed
//              ↓            ↓
void writeFile(String msg, File f) throws IOException {

    //  RESOURCE — declared in try(), IS auto-closed
    //       ↓                 ↓
    try (FileWriter fw = new FileWriter(f)) {
        fw.write(msg);
    }
    // ← fw.close() called automatically here
}
```

**Multiple Resources — Semicolon Separated:**

```java
try (
    FileWriter fw     = new FileWriter(f);       // resource 1
    BufferedWriter bw = new BufferedWriter(fw);  // resource 2
    PrintWriter pw    = new PrintWriter(bw)      // resource 3
) {
    pw.println("Hello");
}
// Close order: pw → bw → fw
```

---

## 19. 👤 User-Defined Exceptions

Create your **own custom exception** when built-in ones don't describe your problem well enough.

### Step 1 — Create the Custom Exception

```java
// Checked custom exception — extend Exception
public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message); // passes message to Exception's constructor
    }
}
```

### Step 2 — Use It

```java
public class UserDefinedDemo {

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Age cannot be negative! Got: " + age);
        } else if (age > 150) {
            throw new InvalidAgeException("Age cannot exceed 150! Got: " + age);
        }
        System.out.println("Valid age: " + age);
    }

    public static void main(String[] args) {
        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Caught   : " + e.getMessage());
            System.out.println("toString : " + e.toString());
        }
    }
}
```

**Output:**
```
Caught   : Age cannot be negative! Got: -5
toString : InvalidAgeException: Age cannot be negative! Got: -5
```

---

### Unchecked Custom Exception

```java
// Extend RuntimeException — no need to declare with throws
public class InsufficientFundsException extends RuntimeException {
    private double shortfall;

    public InsufficientFundsException(String message, double shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public double getShortfall() { return shortfall; }
}

public class BankAccount {
    private double balance = 500.0;

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Need " + amount + " but have only " + balance,
                amount - balance
            );
        }
        balance -= amount;
        System.out.println("Withdrawn: " + amount + " | Remaining: " + balance);
    }

    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        try {
            acc.withdraw(200); // ✅
            acc.withdraw(400); // ❌ throws
        } catch (InsufficientFundsException e) {
            System.out.println("Error    : " + e.getMessage());
            System.out.println("Short by : " + e.getShortfall());
        }
    }
}
```

**Output:**
```
Withdrawn: 200.0 | Remaining: 300.0
Error    : Need 400.0 but have only 300.0
Short by : 100.0
```

### Checked vs Unchecked Custom Exception

| | Extend `Exception` | Extend `RuntimeException` |
|---|---|---|
| Type | Checked | Unchecked |
| `throws` in signature? | ✅ Yes | ❌ No |
| Compiler forces handling? | ✅ Yes | ❌ No |
| Use when | External / recoverable error | Programming / logic error |

---

## 20. 🛠️ Exception Methods Cheat Sheet

```java
try {
    int[] arr = new int[3];
    arr[10] = 5;
} catch (ArrayIndexOutOfBoundsException e) {

    e.getMessage();           // "Index 10 out of bounds for length 3"
    e.toString();             // "java.lang.ArrayIndexOutOfBoundsException: Index 10..."
    e.printStackTrace();      // Full stack trace to console
    e.getClass();             // class java.lang.ArrayIndexOutOfBoundsException
    e.getClass().getName();   // "java.lang.ArrayIndexOutOfBoundsException"
    e.getCause();             // null (unless exception was chained)
}
```

| Method | Returns |
|---|---|
| `getMessage()` | The message string only |
| `toString()` | ClassName: message |
| `printStackTrace()` | Full trace printed to stderr |
| `getClass().getName()` | Fully qualified class name |
| `getCause()` | The root cause exception (if chained) |

---

## 21. 📊 Summary Table

| Keyword / Concept | Role | Where Used |
|---|---|---|
| `try` | Wraps risky code — stops at first exception | Inside method |
| `catch` | Handles specific exception type | After `try` |
| `catch(E1 \| E2 e)` | Handles multiple types in one block | After `try` (Java 7+) |
| `finally` | Always executes — cleanup code | After `catch` |
| `throw` | Manually throws one exception object | Inside method body |
| `throws` | Declares checked exceptions a method may throw | Method signature |
| `try(resource)` | Auto-closes resources — no manual close needed | Method body (Java 7+) |
| `AutoCloseable` | Interface a class must implement for try-with-resources | Class definition |
| User-Defined Exception | Custom exception by extending Exception/RuntimeException | Class definition |

---

## 22. 🔄 Complete Example — All Concepts

```java
import java.io.*;

// ─── Custom Exception ──────────────────────────────────────────────────────
class FileProcessingException extends Exception {
    public FileProcessingException(String message) {
        super(message);
    }
}

// ─── Main Class ────────────────────────────────────────────────────────────
public class CompleteExample {

    // 'throws' — declares checked exceptions this method may produce
    static void processFile(String filename) throws IOException, FileProcessingException {

        if (filename == null || filename.isEmpty()) {
            throw new FileProcessingException("Filename cannot be null or empty!");  // 'throw' custom
        }

        File file = new File(filename);

        if (!file.exists()) {
            throw new FileNotFoundException("File '" + filename + "' not found!");   // 'throw' built-in
        }

        // try-with-resources — FileReader auto-closed, no finally needed
        try (FileReader fr = new FileReader(file)) {
            System.out.println("File opened: " + filename);
        }
    }

    public static void main(String[] args) {

        try {                                                        // 'try' — risky code
            processFile("test.txt");
        }
        catch (FileProcessingException e) {                         // specific custom exception first
            System.out.println("Processing Error : " + e.getMessage());
        }
        catch (FileNotFoundException e) {                           // specific built-in second
            System.out.println("File Error       : " + e.getMessage());
        }
        catch (IOException | RuntimeException e) {                  // multi-catch — unrelated types
            System.out.println("General Error    : " + e.getMessage());
        }
        finally {                                                    // 'finally' — always runs
            System.out.println("Program ends cleanly ✅");
        }
    }
}
```

**Output (file doesn't exist):**
```
File Error       : File 'test.txt' not found!
Program ends cleanly ✅
```

---

## 📌 Quick Recap

```
Exception           → Event that disrupts normal program flow
Handling            → You decide what to do when it occurs

try                 → "Watch this code — stop at first problem"
catch               → "If something goes wrong, do this"
finally             → "Always do this, no matter what"
throw               → "I am intentionally creating an exception"
throws              → "Caller — you handle these exceptions"

Multi-catch         → catch(Ex1 | Ex2 e) — one block, two exception types
Try-with-Resources  → try(Resource r) — auto-closes, no finally needed
AutoCloseable       → Interface a class must implement to work in try-with-resources
User-Defined        → Your own exception by extending Exception or RuntimeException

Checked             → Compiler forces handling  → FileNotFoundException, IOException
Unchecked           → Runtime, optional handling → NullPointerException, ArithmeticException
Error               → JVM-level, don't handle   → OutOfMemoryError, StackOverflowError

finally SKIPS when  → System.exit() / Thread destroyed / JVM crashes / Infinite loop
Lines skip when     → Once exception fires in try, rest of try is immediately abandoned
```

---

<div align="center">

**⭐ Star this repo if it helped you understand Java Exception Handling!**

![Java](https://img.shields.io/badge/Java-Exception_Handling-ED8B00?style=flat-square&logo=openjdk&logoColor=white)

</div>
