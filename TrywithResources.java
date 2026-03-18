import java.io.*;

public class TrywithResources {
    public static void main(String args[]) throws IOException
    {
        // File: Represents the file path/location on disk
        // "./abc.txt" means the file is created in the current working directory
        File f = new File("./abc.txt");

        // Checks if file already exists → deletes it to start fresh
        if(f.exists()) f.delete();

        // Creates a brand new empty file on disk
        f.createNewFile();

        /*
         * try-with-resources:
         * All resources declared inside try(...) are AUTO-CLOSED
         * after the block finishes — no need to call .close() manually.
         * Closing order is REVERSE of declaration: pw → bw → fw
         *
         * FileWriter fw   → Writes raw characters directly to the file
         * BufferedWriter bw → Wraps FileWriter; stores data in a buffer
         *                     before writing to disk (improves performance)
         * PrintWriter pw  → Wraps BufferedWriter; provides convenient methods
         *                   like println(), print(), printf()
         */
        try(
            FileWriter fw   = new FileWriter(f);       // Layer 1: Connects to file
            BufferedWriter bw = new BufferedWriter(fw); // Layer 2: Adds buffering
            PrintWriter pw  = new PrintWriter(bw)       // Layer 3: Adds print methods
        )
        {
            pw.println("hello");            // Writes "hello" + newline to buffer
            pw.println("welcome to java"); // Writes "welcome to java" + newline
            // On try block exit → pw.close() flushes buffer → bw.close() → fw.close()
        }
        catch(IOException e)
        {
            // Catches any file-related errors (file not found, permission denied, etc.)
            System.out.println(e);
        }
    }
}
