package codeframe;

//Error message if the connection has failed has to be displayed
/**
 * @author Piyush
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.io.*;
import java.util.Vector;



public class CodeFrame extends JFrame{

    //For GUI-Initial Frame
    private JTextField userField;
    private JPasswordField passField;
    private JLabel userLabel, passLabel,msgLabel,rulesLabel,errLabel;
    private JButton goButton;
    private JPanel mainPanel;
    
    //For GUI-Final Frame
    private JPanel codePanel,menuPanel,topPanel,buttonPanel,tempPanel;
    private JButton logoutButton,uploadButton,clearButton,submitButton,quesBut0,quesBut1,quesBut2,quesBut3,rankButton,mySubButton,chatButton;
    private JLabel nameLabel,pointLabel;
    private JTextArea quesArea,solArea;
    private JSplitPane textContainer;
    private String fileName = null,selectedQue = null;
    private JComboBox langChooser;
    
    //For Chat Room
    private JPanel chatPanel;
    private JScrollPane chatText;
    private JTextField msgText;
    private JTextArea chatArea;
    private JButton enterButton,arenaButton;
    
    //Table to display Rank and submissions
    private JTable rankTable,subTable;
    private int noUsers;
    
    //Required for connection with database
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private String url = "jdbc:mysql://localhost/CodingTest";
    private String user = "root";
    private String password = "";
        
    //For showing user information
    private String userN = null ,points = null;
    
    //For checking correct login
    private int flag = 0;
    public CodeFrame()
    {
        msgLabel = new JLabel("Welcome to the Coding Arena");
        msgLabel.setForeground(Color.WHITE);
        msgLabel.setSize(400, 70);
        
        errLabel = new JLabel("Incorrect Username or Password!");
        errLabel.setForeground(Color.WHITE);
        errLabel.setSize(400,70);
        errLabel.setVisible(false);
        
        userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
     
        passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        
        userField = new JTextField();
        passField = new JPasswordField();
        
        goButton = new JButton("Go");
        
        rulesLabel = new JLabel("           Rules    ");
        rulesLabel.setForeground(Color.WHITE);
        rulesLabel.setSize(200,400);
        
        mainPanel = new JPanel();
        
        mainPanel.setLayout(null);
        
        mainPanel.setBackground(Color.BLACK);
        
        mainPanel.add(msgLabel);
        msgLabel.setBounds(325,70,260,40);
        
        mainPanel.add(errLabel);
        errLabel.setBounds(315,100,260,40);
        
        mainPanel.add(userLabel);
        userLabel.setBounds(270,180,60,10);
        
        mainPanel.add(passLabel);
        passLabel.setBounds(270,200,60,60);
        
        mainPanel.add(userField);
        userField.setBounds(335,170,180,25);
        
        mainPanel.add(passField);
        passField.setBounds(335,219,180,25);
        
        mainPanel.add(goButton);
        goButton.setBounds(463,270,50,25);
        
        goButton.addActionListener(new goAction());
        
        
        mainPanel.add(rulesLabel);
        rulesLabel.setBounds(340,330,100,100);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        add(mainPanel);
    }

    public void buildMainArena()
    {
        tempPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        
        //Top-Panel Components
        logoutButton =  new JButton("Logout");
        pointLabel = new JLabel("Points: "+points);
        pointLabel.setForeground(Color.red);
        nameLabel = new JLabel("Username: "+userN);
        nameLabel.setForeground(Color.red);
        chatButton = new JButton("Messenger");
        
        
        //BottomPanel Components
        uploadButton = new JButton("Upload");
        clearButton = new JButton("Clear");
        submitButton = new JButton("Submit");
        Vector language = new Vector();
        language.add(0,"C");
        language.add(1,"C++");
        language.add(2,"JAVA");
        language.add(3,"Python");
        langChooser = new JComboBox(language);
       
        
        //CodePanel Components
        quesArea = new JTextArea();
        solArea = new JTextArea();
        JScrollPane quesScroller = new JScrollPane(quesArea);
        JScrollPane solScroller = new JScrollPane(solArea);
        quesArea.setBackground(Color.BLACK);
        quesArea.setForeground(Color.red);
        solArea.setBackground(Color.DARK_GRAY);
        solArea.setForeground(Color.WHITE);
        solArea.setCaretPosition(0);
        solArea.setCaretColor(Color.WHITE);
        solArea.setTabSize(2);
        
        
        //Question Button Initialization
        quesBut0 = new JButton("Problem 1");
        quesBut1 = new JButton("Problem 2");
        quesBut2 = new JButton("Problem 3");
       // quesBut3 = new JButton("Problem 4");
        
        //rankButton = new JButton("Ranks");
        mySubButton = new JButton("Ranks");
        
        topPanel = new JPanel(null);
        topPanel.setBackground(Color.BLACK);
        
        textContainer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
    
        topPanel.add(nameLabel); 
        nameLabel.setBounds(350,20,120,25);  
        topPanel.add(pointLabel);
        pointLabel.setBounds(550,20,120,25);
        topPanel.add(logoutButton);
        logoutButton.setBounds(700,20,80,25);
        topPanel.add(chatButton);
        chatButton.setBounds(50,20,100,25);
        
        topPanel.add(langChooser);
        langChooser.setBounds(350, 520, 80, 25);
        topPanel.add(clearButton);
        clearButton.setBounds(500,520,80,25);
        topPanel.add(uploadButton);
        uploadButton.setBounds(600,520,80,25);
        topPanel.add(submitButton);
        submitButton.setBounds(700,520,80,25);
        
        textContainer.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.GREEN));
        textContainer.add(quesScroller);
        quesArea.setEditable(false);
        textContainer.add(solScroller);
        textContainer.setDividerSize(5);
        textContainer.setDividerLocation(300);
        textContainer.setForeground(Color.red);
        topPanel.add(textContainer);
        textContainer.setBounds(250,120,500,370);
        
        topPanel.add(quesBut0);
        quesBut0.setBounds(60, 120, 120, 40);
        topPanel.add(quesBut1);
        quesBut1.setBounds(60, 200, 120, 40);      
        topPanel.add(quesBut2);
        quesBut2.setBounds(60, 280, 120, 40);       
        //topPanel.add(quesBut3);
        //quesBut3.setBounds(60, 360, 120, 40);
        
        //topPanel.add(rankButton);
        //rankButton.setBounds(60, 440, 120, 40);
        topPanel.add(mySubButton);
        mySubButton.setBounds(20, 530, 90, 25);
        
        //ActionListeners for the buttons
        logoutButton.addActionListener(new logoutAction());
        chatButton.addActionListener(new chatButAction());
        
        quesBut0.addActionListener(new quesBut0());
        quesBut1.addActionListener(new quesBut1());
        quesBut2.addActionListener(new quesBut2());
        //quesBut3.addActionListener(new quesBut3());
        
       // rankButton.addActionListener(new rankAction());
        
        mySubButton.addActionListener(new subAction());
       
        clearButton.addActionListener(new clearAction());
        uploadButton.addActionListener(new uploadAction());
        submitButton.addActionListener(new submitAction());
                
        getContentPane().add(topPanel);
        
        
    }
    
    public void buildChatArena()
    {
        chatPanel = new JPanel(null);
        arenaButton = new JButton("Main Arena");
        enterButton = new JButton("Enter");
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setForeground(Color.GREEN);
        msgText = new JTextField();
        msgText.setForeground(Color.red);
        
        
        chatPanel.add(chatArea);
        chatArea.setBackground(Color.BLACK);
        chatArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GREEN, Color.GREEN));
        chatArea.setBounds(70,120,550,300);
        chatPanel.add(msgText);
        msgText.setBounds(70,423,475,25);
        chatPanel.add(enterButton);
        enterButton.setBounds(550, 423, 70, 25);
        chatPanel.add(arenaButton);
        arenaButton.setBounds(70,20,140,25);
        
        chatPanel.setBackground(Color.BLACK);
        //ActionListener 
        enterButton.addActionListener(new enterAction());
        arenaButton.addActionListener(new arenaAction());
        fileName = "D:\\log.txt";
        fromFileToTextArea(fileName, chatArea);
        getContentPane().add(chatPanel);
    }
    public class enterAction implements ActionListener
    {
        public void  actionPerformed(ActionEvent e)
        {
            chatArea.append(userN+" : "+msgText.getText()+"\n");
            fileName = "D:\\log.txt";
            try{
                
                /*BufferedWriter buff = new BufferedWriter(new FileWriter(fileName));
                buff.append(userN+" : "+msgText.getText()+"\n");
                buff.close();*/
                RandomAccessFile file = new RandomAccessFile(fileName, "rw");
                file.skipBytes((int)file.length());
                file.writeBytes(userN+" : "+msgText.getText()+"\n");
                file.close();
           
            }
            catch(IOException exc)
            {
                
            }
           
            msgText.setText("");
            
        }
    }
    public class arenaAction implements ActionListener
    {
        public void  actionPerformed(ActionEvent e)
        {
            chatPanel.setVisible(false);
            topPanel.setVisible(true);
            
        }
    }
    public class chatButAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            topPanel.setVisible(false);
            buildChatArena();
        }
    }
    
    public class rankAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            textContainer.setVisible(false);
            noUsers = 4;
            rankTable = new JTable(3,noUsers);
            rankTable.setBackground(Color.black);
            rankTable.setRowHeight(10);
            
            topPanel.add(rankTable);
            rankTable.setBounds(250,120,500,370);
            
        }
        
    }
    public class subAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            textContainer.setVisible(false);
            subTable = new JTable();
        
        }
            
    }
    public class uploadAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JFileChooser fileChoose = new JFileChooser();
            fileChoose.showOpenDialog(null);
            File uploadFile = fileChoose.getSelectedFile();
            
             try{
                BufferedReader buff = new BufferedReader(new FileReader(uploadFile));
                String line = null;
                while((line = buff.readLine()) != null)
                {
                    solArea.append(line+"\n");
                }
                buff.close();
            }
            catch(IOException exc)
            {
                System.out.println("File couldnt be uploaded");
            }
            
            
        }
    }
    public class clearAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            solArea.setText("");
        }
    }
    public class submitAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //Check the solution language
            String selectedLang = langChooser.getSelectedItem().toString();
            switch(selectedLang)
            {
                case "C":
                    selectedLang = ".c";
                    break;
                case "C++":
                    selectedLang = ".cpp";
                    break;
                case "JAVA": 
                    selectedLang = ".java";
                    break;
                case "Python":
                    selectedLang = ".py";
                    break;
            }
            
            
            
            //Create a file on the server and write the solution in it
            fileName = "D:\\"+userN+"_"+selectedQue+selectedLang;
            try
            {
                BufferedWriter writeSol = new BufferedWriter(new FileWriter(fileName));
                writeSol.write(solArea.getText());
                writeSol.close();
            }
            catch(IOException expc)
            {
                System.out.println("Cannot write file on the server");
            }
        
        }
    }

    public void fromFileToTextArea(String fileName,JTextArea tempArea)
    {
        try{
                BufferedReader buff = new BufferedReader(new FileReader(new File(fileName)));
                String line = null;
                while((line = buff.readLine()) != null)
                {
                    tempArea.append(line+"\n");
                }
                buff.close();
            }
            catch(IOException exc)
            {
                System.out.println("File couldnt be found");
            }
    }
    public class quesBut0 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            quesArea.setText("Problem 1(250 Points)");
            fileName = "C:\\Prob1.txt";
            selectedQue = "Prob1";
            fromFileToTextArea(fileName,quesArea);
        }
    }

    public class quesBut1 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            quesArea.setText("Problem 2(250 Points)");
            fileName = "C:\\Prob2.txt";
            selectedQue = "Prob2";
            fromFileToTextArea(fileName,quesArea);
         
        }
    }    
    
    public class quesBut2 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            quesArea.setText("Problem 3(250 Points)");
            fileName = "C:\\Prob3.txt";
            selectedQue = "Prob3";
            fromFileToTextArea(fileName,quesArea);
        
        }
    }    
    
    public class quesBut3 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            quesArea.setText("Problem 4(250 Points)");
            fileName = "C:\\Prob4.txt";
            fromFileToTextArea(fileName,quesArea);
        
        }
    }    
    
    public class logoutAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            topPanel.setVisible(false);
            mainPanel.setVisible(true);
            userN = "";
            
        }
    }
    
    public class goAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            
            
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT Username,Password,Points From registration");

            while (rs.next()) {
                if( userField.getText().equals(rs.getString(1)) && String.copyValueOf(passField.getPassword()).equals(rs.getString(2)))
                {
                    userN = userField.getText();
                    points = rs.getString(3);
                    mainPanel.setVisible(false);
                    flag = 1;
                    buildMainArena();
                    
                    userField.setText("");
                    passField.setText("");
                }
            }
            if(flag == 0)
            {
                errLabel.setVisible(true);
            }
            userField.setText("");
            passField.setText("");

        } catch (SQLException ex) {
           
                System.out.println(ex.getMessage());

        } 
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

                 }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
                }
        }
            
        }
    }
   
    
    public static void main(String[] args) {
        // TODO code application logic here
        CodeFrame initFrame = new CodeFrame();
        initFrame.setVisible(true);
        initFrame.setTitle("Coding Arena");
        initFrame.setSize(800,600);
        initFrame.setResizable(false);
    }
}
