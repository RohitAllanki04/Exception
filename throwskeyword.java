import java.io.*;
public class throwskeyword {
    public static void main(String args[]) throws FileNotFoundException,IOException//checked exception must be handled by throws keyword(Compile time exception)
    {
     File file=new File("abc.txt");//if file is not present in the current directory,then it will throw FileNotFoundException
     FileReader fr=new FileReader(file);
     FileInputStream fis=new FileInputStream(file);
    }

}
