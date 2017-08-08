import java.util.Random;

public class SuggCharGen
{
    private char UCAlphabetArray[], LCAlphabetArray[], SCArray[];
    private int digitArray[];
    private final int NUM_OF_ELEMENTS = 26;
    private final int NUM_OF_DIGITS = 10;

    public SuggCharGen ()
    {
        UCAlphabetArray = new char[NUM_OF_ELEMENTS];
        LCAlphabetArray = new char[NUM_OF_ELEMENTS];
        digitArray = new int[NUM_OF_DIGITS];


        //Lowercase letters
        for(int i= 0; i < LCAlphabetArray.length; i++)
        {
            char alphabet = (char)( 'a' + i);
            LCAlphabetArray[i] = alphabet;
            alphabet++;
        }


        //Uppercase letters
        for(int i= 0; i < UCAlphabetArray.length; i++)
        {
            char alphabet = (char)( 'A' + i);
            UCAlphabetArray[i] = alphabet;
            alphabet++;
        }

        //Digits
        for(int i = 0; i < digitArray.length; i++)
        {
            digitArray[i] = i;
        }

        SCArray = new char[]{'!', '-', '_', '$', '#', '@'};
    }

    void printValues()
    {
        for(int i = 0; i < UCAlphabetArray.length; i++)
            System.out.print(UCAlphabetArray[i] + " ");

        System.out.println();

        for(int i = 0; i < LCAlphabetArray.length; i++)
            System.out.print(LCAlphabetArray[i] + " ");

        System.out.println();

        for(int i = 0; i <digitArray.length; i++)
            System.out.print(digitArray[i] + " ");

        System.out.println();

        for(int i = 0; i < SCArray.length; i++)
            System.out.print(SCArray[i] + " ");

    }


    String UCGenerator()
    {
        Random rand = new Random();
        int n = rand.nextInt(26);

        return Character.toString(UCAlphabetArray[n]);
    }

    String LCGenerator()
    {
        Random rand = new Random();
        int n = rand.nextInt(26);

        return Character.toString(LCAlphabetArray[n]);
    }

    String digitGenerator()
    {
        Random rand = new Random();
        int n = rand.nextInt(9);

        return Integer.toString(digitArray[n]);
    }

    String specialCharGenerator()
    {
        Random rand = new Random();
        int n = rand.nextInt(5);

        return Character.toString(SCArray[n]);
    }


   /* public static void main(String[] args)
    {
        SuggCharGen trial1 = new SuggCharGen();
        trial1.printValues();
    }
    */
}
