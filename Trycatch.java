class Trycatch
{
    public static void main(String args[])
    {
    int a=2;
    int b=0;
    int r=0;
    try{//if try is there,catch or finally must be there
        r=a/b;
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    finally
    {
        System.out.println("finally block is always executed");//optional
    }
}