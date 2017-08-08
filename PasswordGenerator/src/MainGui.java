import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class MainGui extends JFrame
{
    private JFrame mainFrame;
    private JButton enterButton, suggestionButton, cancelButton;
    private JTextField passwordTextSpace;
    private JPanel p1, p2 , p3;
    private JLabel strongIcon, weakIcon;
    private JButton helpButton;


    public MainGui()
    {

        mainFrame = new JFrame("Password Generator");
        mainFrame.setSize(800, 190);

        //initialize buttons & textfields
        enterButton = new JButton("Enter");
        suggestionButton = new JButton("Suggestions");
        cancelButton = new JButton("Cancel");
        helpButton = new RoundButton("?");
        passwordTextSpace = new JTextField("Enter your password or get a suggestion!");
        passwordTextSpace.setPreferredSize(new Dimension(600, 37));
        Font textFieldFont = new Font("SanSerif", Font.PLAIN, 28);

        passwordTextSpace.setFont(textFieldFont);

        //initialize round button w/ pop-up
        helpButton.setToolTipText("Press for help in creating a password");





        //import img icons
       strongIcon = new JLabel(new ImageIcon("IconImages/StrongIndicator.png"));
        weakIcon = new JLabel(new ImageIcon("IconImages/WeakIndicator.png"));

        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();

      //  p1.setBackground(Color.CYAN);
      //  p2.setBackground(Color.GREEN);
      //  p3.setBackground(Color.ORANGE);



        //Added text field to middle panel
        p1.add(passwordTextSpace);

        //Added icons to right panel
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.add(strongIcon);
        p2.add(weakIcon);

        //set icons to be not visible until entry
        strongIcon.setVisible(false);
        weakIcon.setVisible(false);



        //Added buttons to bottom panel
        p3.add(Box.createHorizontalStrut(240));
        p3.add(enterButton);
        p3.add(suggestionButton);
        p3.add(cancelButton);
        p3.add(Box.createHorizontalStrut(215));
        p3.add(helpButton);


        //Add panels to the mainFrame
        mainFrame.add(p1, BorderLayout.CENTER);
        mainFrame.add(p2, BorderLayout.EAST);
        mainFrame.add(p3, BorderLayout.PAGE_END);

        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });


        //Set actions for buttons
        //Enter button
        EnterButtonHandler enterHandler = new EnterButtonHandler();
        enterButton.addActionListener(enterHandler);
        //Suggest button
        SuggestButtonHandler suggestHandler = new SuggestButtonHandler();
        suggestionButton.addActionListener(suggestHandler);
        //Cancel button
        CancelButtonHandler cancelHandler = new CancelButtonHandler();
        cancelButton.addActionListener(cancelHandler);
        //Help button
        HelpButtonHandler helpHandler = new HelpButtonHandler();
        helpButton.addActionListener(helpHandler);


    }

    public String passwordInput()
    {
        return passwordTextSpace.getText();
    }


    class EnterButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            TestCases firstTestCase = new TestCases(passwordTextSpace.getText());
            firstTestCase.testCase2();


           if(firstTestCase.testCase3() == true)
           {
               weakIcon.setVisible(false);
               strongIcon.setVisible(true);

           }
            else
           {
               strongIcon.setVisible(false);
               weakIcon.setVisible(true);
           }



        }
    }
    class SuggestButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            TestCases suggestionTestCase = new TestCases(passwordTextSpace.getText());
           
            suggestionTestCase.testCase2();

            passwordTextSpace.setText(suggestionTestCase.SuggConcat());

            strongIcon.setVisible(true);
            weakIcon.setVisible(false);

        }
    }

    class CancelButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }


    class HelpButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(null, "A strong password contains: " + "\n -7 characters min/ 16 characters max"
                    + "\n-At least 2 uppercase letters" + "\n-At least one number"
                    + "\nNOTE: Only use special characters such as: ! @ $ _ - #"
                    + "\nAvoid using names, the word password, or any sensitive information!", "Help", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    public static void main(String [] args)
    {
        new MainGui();
    }

}
