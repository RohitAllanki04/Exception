public class MultipleCatches {
    public static void main(String args[])
    {
        int[] a={2,0};
        int r=0;
        try{
            r = a[0] / a[1];   // ← Step 1: Executes → 2/0 → throws ArithmeticException!
    r = a[0] / a[3];   // ← NEVER REACHED (execution already jumped to catch)
        }
        catch(ArithmeticException e)
        {
            System.out.println("Arithmetic exception is occurred");
        }
        catch(IndexOutOfBoundsException e) //r=a[0]/a[3];
        {
            System.out.println("Index out of bounds exception is occurred");
        }
        catch(Exception e)
        {
            System.out.println("Exception is occurred");
        }
    }
}
