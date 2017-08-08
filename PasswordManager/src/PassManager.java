import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PassManager extends JFrame {
    //password manager
    private JFrame passGenMainFrame;
    private JButton addButton, removeButton, cancelButton, folderButton;
    private JPanel centerPanel, buttonPanel, bottomPanel;
    private JList accountList, passwordList;
    String[] passwords, accounts;
    int sizeOfAcc, sizeOfPass, selectedIndex;
    DefaultListModel passwordContainer = new DefaultListModel();
    DefaultListModel accountContainer = new DefaultListModel();
    private JPopupMenu folderPopUp;
    PrintWriter writer;


    //account input
    private JFrame mainInputFrame;
    private JTextField inputText;
    private JTextArea genMessage;
    private JButton addInputButton, cancelInputButton;
    private JPanel textPanel, buttonInputPanel;

    //password generator
    private JFrame mainFrame;
    private JButton enterButton, suggestionButton, cancelPassButton, addPassButton;
    private JTextField passwordTextSpace;
    private JPanel p1, p2 , p3;
    private JLabel strongIcon, weakIcon;
    private JButton helpButton;
    int disableIndex;

    //constructor for password manager
    public PassManager ()
    {
        passGenMainFrame = new JFrame("Password Manager");
        passGenMainFrame.setSize(900, 700);

        //initialize panels
        centerPanel = new JPanel();
        bottomPanel = new JPanel();
        buttonPanel = new JPanel();

        sizeOfAcc =0;
        sizeOfPass = 0;
        accounts = new String[sizeOfAcc];
        passwords = new String[sizeOfPass];

        //initialize popup menu
        folderPopUp = new JPopupMenu();

        JMenuItem export = new JMenuItem(new ExportItemHandler());
        JMenuItem importFile = new JMenuItem(new ImportItemHandler());

        folderPopUp.add(export);
        folderPopUp.add(importFile);


        //import folder icon from java resource files
        ImageIcon folderImage = new ImageIcon("IconImages/folderImg.png");

        //initialize buttons
        addButton = new JButton("Add...");
        removeButton = new JButton("Remove");
        cancelButton = new JButton("Cancel");
        folderButton = new JButton();

        //set folder button to folder image
        folderButton.setIcon(folderImage);

        //set listener to button once clicked
        folderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                folderPopUp.show(e.getComponent(), e.getX(), e.getY());
            }
        });



        //popup text
        addButton.setToolTipText("Press to add a password");
        removeButton.setToolTipText("Press to remove a password ");
        folderButton.setToolTipText("Press to either export or import accounts & passwords");


        //initialize password list
        passwordList = new JList(passwordContainer);
        passwordList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        passwordList.setLayoutOrientation(JList.VERTICAL);
        passwordList.setVisibleRowCount(-1);
        //passwordList.setEnabled(false);


        //initialize accounts list
        accountList = new JList(accountContainer);
        accountList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        accountList.setLayoutOrientation(JList.VERTICAL);
        accountList.setVisibleRowCount(-1);



        //title for list and title
        Font listFont = new Font("SanSerif", Font.PLAIN, 36);
        Font titleFont = new Font("SanSerif", Font.PLAIN, 26);

        passwordList.setFont(listFont);
        accountList.setFont(listFont);

        // list.setBorder(BorderFactory.createTitledBorder("Passwords"));
        passwordList.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        accountList.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        //creating title for passwords
        TitledBorder border = new TitledBorder("Passwords");
        border.setTitleFont(titleFont);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);

        //creating title for accounts
        TitledBorder accountBorder = new TitledBorder("Accounts");
        accountBorder.setTitleFont(titleFont);
        accountBorder.setTitleJustification(TitledBorder.CENTER);
        accountBorder.setTitlePosition(TitledBorder.TOP);

        //creating a scroll pane and initializing it's size + title
        JScrollPane listScroller = new JScrollPane(passwordList);
        listScroller.setPreferredSize(new Dimension(610, 500));
        listScroller.setBorder(border);


        JScrollPane accountListScroller = new JScrollPane(accountList);
        accountListScroller.setPreferredSize(new Dimension(200, 500));
        accountListScroller.setBorder(accountBorder);
        // listScroller.setBorder(BorderFactory.createTitledBorder("Passwords"));

        //add scroll pane to center panel
        centerPanel.add(accountListScroller, BorderLayout.WEST);
        centerPanel.add(listScroller, BorderLayout.EAST);
        // centerPanel.setBackground(Color.RED);


        //add buttons to panel
        buttonPanel.add(Box.createHorizontalStrut(233));
        buttonPanel.add(addButton, BorderLayout.NORTH);
        buttonPanel.add(removeButton, BorderLayout.NORTH);
        buttonPanel.add(cancelButton, BorderLayout.NORTH);
        buttonPanel.add(Box.createHorizontalStrut(235));
        buttonPanel.add(folderButton, BorderLayout.NORTH);

        // buttonPanel.setBackground(Color.GREEN);
        buttonPanel.setPreferredSize(new Dimension(680, 72));

        //add panel to mainFrame
        passGenMainFrame.add(buttonPanel, BorderLayout.SOUTH);
        passGenMainFrame.add(centerPanel, BorderLayout.CENTER);


        passGenMainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //add actionListener to add button
        addButtonHandler addHandler = new addButtonHandler();
        addButton.addActionListener(addHandler);

        //add actionListener to remove button
        removeButtonHandler removeHandler = new removeButtonHandler();
        removeButton.addActionListener(removeHandler);

        //add actionlistener to cancel button
        cancelButtonHandler cancelHandler = new cancelButtonHandler();
        cancelButton.addActionListener(cancelHandler);


        passGenMainFrame.setResizable(false);
        passGenMainFrame.setLocationRelativeTo(null);
        passGenMainFrame.setVisible(true);

    }


    //add button handler for password manager
    class addButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {


            //beginning of account input
            mainInputFrame = new JFrame("Create Account");
            inputText = new JTextField("Ex: Google");
            genMessage = new JTextArea("Create account to add password to:");
            addInputButton = new JButton("Create Password");
            cancelInputButton = new JButton("Cancel");
            textPanel = new JPanel();
            buttonInputPanel = new JPanel();


            mainInputFrame.setSize(550, 200);

            //Setting font for text areas
            Font generalMessageText = new Font("SanSerf", Font.PLAIN, 25);
            genMessage.setFont(generalMessageText);
            inputText.setFont(generalMessageText);

            genMessage.setFocusable(false);

            // add buttons to panel
            buttonInputPanel.add(addInputButton);
            buttonInputPanel.add(cancelInputButton);

            // add text field/input to panel
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.add(genMessage);
            textPanel.add(inputText);


            // add panels to main frame
            mainInputFrame.add(textPanel, BorderLayout.CENTER);
            mainInputFrame.add(buttonInputPanel, BorderLayout.PAGE_END);


            mainInputFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    mainInputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            });


            //account input add input handler
            class AddInputButtonHandler implements ActionListener
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    //add account input to account list and make window disappear
                    accountContainer.addElement(inputText.getText());
                    mainInputFrame.setVisible(false);

                    //beginning of password generator
                    mainFrame = new JFrame("Password Generator");
                    mainFrame.setSize(800, 190);

                    //initialize buttons & textfields
                    enterButton = new JButton("Enter");
                    suggestionButton = new JButton("Suggestions");
                    cancelPassButton = new JButton("Cancel");
                    addPassButton = new JButton("Add");
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
                    p3.add(addPassButton);
                    p3.add(enterButton);
                    p3.add(suggestionButton);
                    p3.add(cancelPassButton);
                    p3.add(Box.createHorizontalStrut(215));
                    p3.add(helpButton);

                    addPassButton.setVisible(false);


                    //Add panels to the mainFrame
                    mainFrame.add(p1, BorderLayout.CENTER);
                    mainFrame.add(p2, BorderLayout.EAST);
                    mainFrame.add(p3, BorderLayout.PAGE_END);



                    mainFrame.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e) {
                            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }
                    });

                    //Set actions for buttons
                    //Enter button



                    // enter button handler for password generator
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
                                addPassButton.setVisible(true);
                                helpButton.setVisible(false);


                            }
                            else
                            {
                                strongIcon.setVisible(false);
                                weakIcon.setVisible(true);
                                addPassButton.setVisible(false);
                            }



                        }
                    }

                    //suggestion handler for password generator
                    class SuggestButtonHandler implements ActionListener
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            if(removeDefaultText(passwordTextSpace.getText()) == true)
                                passwordTextSpace.setText("");

                            if(passwordTextSpace.getText().length() > 16)
                                passwordTextSpace.setText(removeCharacterMax(passwordTextSpace.getText()));




                            TestCases suggestionTestCase = new TestCases(passwordTextSpace.getText());

                                suggestionTestCase.testCase2();

                                passwordTextSpace.setText(suggestionTestCase.SuggConcat());

                                strongIcon.setVisible(true);
                                weakIcon.setVisible(false);
                                addPassButton.setVisible(true);
                                passwordTextSpace.setEditable(false);

                                helpButton.setVisible(false);




                        }
                    }

                    //cancel button handler for password generator
                    class CancelPassButtonHandler implements ActionListener
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            accountContainer.remove(accountContainer.indexOf(accountContainer.lastElement()));
                            mainFrame.setVisible(false);
                        }
                    }

                    // help button handler for password generator
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

                    //add button for password generator
                    class AddPassButtonHandler implements ActionListener
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {

                            passwordContainer.addElement(passwordTextSpace.getText());
                            mainFrame.setVisible(false);
                        }
                    }
                    EnterButtonHandler enterHandler = new EnterButtonHandler();
                    enterButton.addActionListener(enterHandler);
                    //Suggest button
                    SuggestButtonHandler suggestHandler = new SuggestButtonHandler();
                    suggestionButton.addActionListener(suggestHandler);
                    //Cancel button
                    CancelPassButtonHandler cancelHandler = new CancelPassButtonHandler();
                    cancelPassButton.addActionListener(cancelHandler);
                    //Help button
                    HelpButtonHandler helpHandler = new HelpButtonHandler();
                    helpButton.addActionListener(helpHandler);
                    //Add button
                    AddPassButtonHandler addHandler = new AddPassButtonHandler();
                    addPassButton.addActionListener(addHandler);

                    mainFrame.setResizable(false);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);
                    /* -------------End of password generator ------------*/



                }
            }

            //Cancel button generator for account input
            class CancelInputButtonHandler implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    mainInputFrame.setVisible(false);

                }
            }

            AddInputButtonHandler addInputHandler = new AddInputButtonHandler();
            addInputButton.addActionListener(addInputHandler);

            CancelInputButtonHandler cancelInputHandler = new CancelInputButtonHandler();
            cancelInputButton.addActionListener(cancelInputHandler);

            mainInputFrame.setResizable(false);
            mainInputFrame.setLocationRelativeTo(null);
            mainInputFrame.setVisible(true);

            /*----------End of Account Input ---------------- */

        }
    }

    class removeButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            int confirmationNum;

            if(accountList.getSelectedIndex() != -1 )
            {
                confirmationNum = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + accountList.getSelectedValue() + " account?");
                if(confirmationNum == JOptionPane.YES_OPTION)
                {
                    accountContainer.remove(selectedIndex);
                    passwordContainer.remove(selectedIndex);
                }

            }

            else
                JOptionPane.showMessageDialog(null, "Must select an account!" +
                        "\n~OR~" +
                        "\nNo accounts & passwords on file!", "ERROR!", JOptionPane.ERROR_MESSAGE);




        }
    }

    //cancel button handler for password manager
    class cancelButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    //export item handler
    class ExportItemHandler extends AbstractAction
    {
        public ExportItemHandler()
        {
            super("Export");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                writer = new PrintWriter("pass-acc-data.txt");



                if(accountContainer.isEmpty() != true)
                {
                    for(int i = 0; i < accountContainer.getSize(); i++)
                    {
                        writer.print("\nAccount: " + accountContainer.getElementAt(i) + "\tPassword: "+ passwordContainer.getElementAt(i));
                    }

                    JOptionPane.showMessageDialog(null, "Exporting complete!"
                            +"\nCheck txtfile data", "Exported", JOptionPane.INFORMATION_MESSAGE);
                }

                else
                    JOptionPane.showMessageDialog(null, "Nothing to export!", "ERROR", JOptionPane.ERROR_MESSAGE);

                writer.close();

            }
            catch (FileNotFoundException f)
            {
                JOptionPane.showMessageDialog(null, "Could not create file!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //import item handler
    class ImportItemHandler extends AbstractAction
    {
        public ImportItemHandler()
        {
            super("Import (COMING SOON)");
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(null, "Function coming!" +
                    "\nThis function will search through computer's directory to find a text file for importing.", "TBD", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    /*--------------- End of Password Manager ---------------- */


    public static void main(String[] args)
    {
      new PassManager();

    }


    public static String removeCharacterMax(String passwordInput)
    {

        if(passwordInput != null && passwordInput.length() > 0 )
        {
            while (passwordInput.length() != 10)
            {
                passwordInput = passwordInput.substring(0, passwordInput.length() -1);
            }
        }

        return passwordInput;
    }


    public static boolean removeDefaultText(String passwordInput)
    {
        if(passwordInput.compareTo("Enter your password or get a suggestion!") == 0)
            return true;
        else
            return false;
    }
}
