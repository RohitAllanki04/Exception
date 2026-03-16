class throwkeyword
{
    public static void main(String args[])
    {
    int a=2;
    int b=0;
    int r=0;
    try{
        r=a/b;
    }
    catch(Exception e)
    {
        System.out.println(e);
        e.printStackTrace();//gives the line number where exception is occurred
        throw e;//gives java to handle the exception
    }
    finally{
        System.out.println("finally block executed");
    }
    }
}