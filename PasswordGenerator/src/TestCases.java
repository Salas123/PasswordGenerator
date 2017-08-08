import javax.swing.*;
import java.util.Random;

public class TestCases {
    private String passwordInput;
   private int lowerCaseCount, upperCaseCount, exclamationPointCount,atCount,
           hyphenCount, lowerHypenCount, poundCount,dollarSignCount, numberCount, specialCharacterCount;
   private boolean indicator;

    TestCases(String newPassword)
    {
        passwordInput = newPassword;
         lowerCaseCount = 0;
         upperCaseCount = 0;
         exclamationPointCount = 0;
         atCount = 0;
         hyphenCount = 0;
         lowerHypenCount = 0;
         poundCount = 0;
         dollarSignCount = 0;
         numberCount = 0;
        specialCharacterCount =0;

    }

    boolean testCase1()
    {
        if(passwordInput != null)
            return true;
        else
            return false;
    }

    //Count the different number of characters
    void testCase2()
    {
        /*
        int lowerCaseCount = 0;
        int upperCaseCount = 0;
        int spaceCount = 0;
        int exclamationPointCount = 0;
        int atCount = 0;
        int hyphenCount = 0;
        int lowerHypenCount = 0;
        int poundCount = 0;
        int dollarSignCount = 0;
        int numberCount = 0;
        */

       if(passwordInput.length() != 0)
       {
           if(passwordInput.length() >= 7 && passwordInput.length() <= 16)
           {
               for (int i = 0; i < passwordInput.length(); i++)
               {
                   if (Character.isUpperCase(passwordInput.charAt(i)))
                       upperCaseCount++;
                   else if (Character.isLowerCase(passwordInput.charAt(i)))
                       lowerCaseCount++;
                   else if (Character.isSpaceChar(passwordInput.charAt(i)))
                   {
                       JOptionPane.showMessageDialog(null, "Password should not contain any spaces!", "Error!", JOptionPane.ERROR_MESSAGE);
                       return;
                   } else if (passwordInput.charAt(i) == '!')
                       exclamationPointCount++;
                   else if (passwordInput.charAt(i) == '-')
                       hyphenCount++;
                   else if (passwordInput.charAt(i) == '@')
                       atCount++;
                   else if (passwordInput.charAt(i) == '_')
                       lowerHypenCount++;
                   else if (passwordInput.charAt(i) == '#')
                       poundCount++;
                   else if (passwordInput.charAt(i) == '$')
                       dollarSignCount++;
                   else if(Character.isDigit(passwordInput.charAt(i)))
                       numberCount++;


               }

               specialCharacterCount = dollarSignCount + poundCount + lowerHypenCount + hyphenCount + exclamationPointCount + atCount;

        /*
               JOptionPane.showMessageDialog(null, "The number of Uppercase Characters:" + upperCaseCount +
                       "\n The number of Lowercase Characters: " + lowerCaseCount +
                       "\nNumber of special characters: " + specialCharacterCount
                        +"\nNumbers: " + numberCount);
        */

           }

           else if (passwordInput.length() >= 17)
               JOptionPane.showMessageDialog(null, "Max 16 character length exceeded!", "Requirement not met!", JOptionPane.ERROR_MESSAGE);
           else
               JOptionPane.showMessageDialog(null, "Enter a minimum of 7 characters!", "Requirement not met!", JOptionPane.ERROR_MESSAGE);



       }

       else
           JOptionPane.showMessageDialog(null, "You have not entered anything!", "Error!", JOptionPane.ERROR_MESSAGE);
    }

    /*
     * check if requirements are met:
     * -At least 2 uppercase characters
     * -At least one special character
     * -At least one number
     * -16 character max
     */
    boolean testCase3()
    {

        if(upperCaseCount >= 2 && passwordInput.length() >= 7 && specialCharacterCount >= 1 && numberCount >= 1)
            indicator = true;
        else
            indicator = false;

        return indicator;


    }


    //add until conditions are satisfied
    String SuggConcat()
    {

        SuggCharGen upperCase = new SuggCharGen();
        SuggCharGen lowerCase = new SuggCharGen();
        SuggCharGen digit = new SuggCharGen();
        SuggCharGen specialChar = new SuggCharGen();

            //concatenate upper case letters
            while(upperCaseCount < 2)
            {

                passwordInput = passwordInput + upperCase.UCGenerator();
                upperCaseCount++;

            }

            //concatenate if string length less than 7 characters
            while(passwordInput.length() < 7)
            {
                passwordInput = passwordInput + lowerCase.LCGenerator();

            }

            //concatenate digits
            while(numberCount < 1)
            {
                passwordInput = passwordInput + digit.digitGenerator();
                numberCount++;
            }

            //concatenate special characters
            while(specialCharacterCount < 1)
            {
                passwordInput = passwordInput + specialChar.specialCharGenerator();
                specialCharacterCount++;
            }



            return passwordInput;

    }


}
