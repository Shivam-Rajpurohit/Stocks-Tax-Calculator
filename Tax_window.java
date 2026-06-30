import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//This class makes the interface window, receives input, parses and forwards it,
// and displays the results 
public class Tax_window extends JFrame
{
    private JTextField nameField, datesField, num1Field, num2Field, num3Field;
    private JButton submit, clear;
    private JTextArea resultArea;
    private JLabel statusLabel;
    private JRadioButton IntraDay, Log;
    private boolean IsIntraDay;
    private double[] buy_sell, Profit;
    private int SharesNumber;
    private String Buy_date, Sell_date;
    private boolean shouldLog = false;
    //Constructor. Prepares the window
    public Tax_window()  
    {
        IsIntraDay = false; Sell_date = "";
        buy_sell = new double[2];
        Profit = new double[3];
        SharesNumber = 0; Buy_date = "";
        
        setTitle("Tax Calculator - Share Trading");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Enter Quantity of shares along with their Buy and Sell Prices", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Company Name:"),gbc);
        gbc.gridx = 1;
        nameField = new JTextField(50);
        mainPanel.add(nameField, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Dates of Buy and Sell:"),gbc);
        gbc.gridx = 1;
        datesField = new JTextField(50);
        mainPanel.add(datesField, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Share Quantity:"), gbc);
        num1Field = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(num1Field, gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Buy Price:"), gbc);
        num2Field = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(num2Field, gbc);
        
        gbc.gridy = 5;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Sell Price:"), gbc);
        num3Field = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(num3Field, gbc);
        
        JLabel buttonlabel = new JLabel("Is this Trade Intraday?");
        buttonlabel.setFont(new Font("Arial", Font.PLAIN, 12));
        IntraDay = new JRadioButton("Yes");
        IntraDay.setFont(new Font("Arial", Font.PLAIN, 12));
        IntraDay.setBackground(Color.WHITE);
        gbc.gridy = 6;
        gbc.gridx = 0;
        mainPanel.add(buttonlabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(IntraDay, gbc);
        
        JLabel loglabel = new JLabel("Do you wish to Log this trade?");
        loglabel.setFont(new Font("Arial", Font.PLAIN, 12));
        Log = new JRadioButton("Yes");
        Log.setFont(new Font("Arial", Font.PLAIN, 12));
        Log.setBackground(Color.WHITE);  // Add background
        gbc.gridy = 7;
        gbc.gridx = 0;
        mainPanel.add(loglabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(Log, gbc);
        
        JPanel Bpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        submit = new JButton("Submit & Store");
        submit.setPreferredSize(new Dimension(140, 35));
        submit.setBackground(new Color(70, 130, 180));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Arial", Font.BOLD, 12));
        
        clear = new JButton("Clear All");
        clear.setPreferredSize(new Dimension(110, 35));
        clear.setBackground(new Color(220, 80, 80));
        clear.setForeground(Color.WHITE);
        clear.setFont(new Font("Arial", Font.BOLD, 12));
        
        Bpanel.add(submit);
        Bpanel.add(clear);
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(Bpanel, gbc);
        
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Tax Calculation Results"));
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        statusLabel = new JLabel("Ready - Enter share details", SwingConstants.LEFT);
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        add(mainPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        Listeners();
        nameField.requestFocus();
    }
    //Listeners for buttons and textfields
    private void Listeners() {
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processData();
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });
        KeyListener enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processData();
                }
            }
        };
        IntraDay.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(IntraDay.isSelected()) {
                   IsIntraDay = true;
               } else {
                   IsIntraDay = false;
               }
           }
        });
        Log.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               shouldLog = Log.isSelected();
           }
        });
        
        nameField.addKeyListener(enterListener);
        datesField.addKeyListener(enterListener);
        num1Field.addKeyListener(enterListener);
        num2Field.addKeyListener(enterListener);
        num3Field.addKeyListener(enterListener);
    }
    //Parses input and forwards it
    private void processData()
    {
        try
        {
            String nameText = nameField.getText().trim();
            String datesText = datesField.getText().trim();
            String num1Text = num1Field.getText().trim();
            String num2Text = num2Field.getText().trim();
            String num3Text = num3Field.getText().trim();
            
            if (nameText.isEmpty() || datesText.isEmpty() || num1Text.isEmpty() || num2Text.isEmpty() || num3Text.isEmpty())
            {
                JOptionPane.showMessageDialog(this, 
                    "Please enter data in all fields!", 
                    "Input Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int i = datesText.indexOf(' ');
            Buy_date = datesText.substring(0,i);
            Sell_date = datesText.substring(++i,datesText.length());
            
            SharesNumber = Integer.parseInt(num1Text);
            buy_sell[0] = Double.parseDouble(num2Text);
            buy_sell[1] = Double.parseDouble(num3Text);
            Tax_calc doer = new Tax_calc(this);
            doer.caller(SharesNumber, buy_sell, IsIntraDay);
            
            if (shouldLog) {
                Logs_updater();
            }

            statusLabel.setText("✓ Numbers stored successfully! Array contains: [" + 
                               buy_sell[0] + ", " + buy_sell[1] + "]");
            statusLabel.setForeground(new Color(0, 100, 0));
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numeric values!", 
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("✗ Error: Invalid number format");
            statusLabel.setForeground(Color.RED);
        }
    }
    //Displays output
    public void display(double sum[], double profit[]) 
    {
        Profit = profit; 
        String nameText = nameField.getText().trim();
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════\n");
        sb.append("           TAX CALCULATION RESULTS          \n");
        sb.append("═══════════════════════════════════════════════\n\n");
        sb.append("      Company Name:   "+nameText+"\n");
        sb.append("      Bought on: "+Buy_date+"\n");
        sb.append("      Sold on:   "+Sell_date+"\n");
        sb.append(String.format("  Taxes on Buy:  ₹%12.2f\n", sum[0]));
        sb.append(String.format("  Taxes on Sell: ₹%12.2f\n", sum[1]));
        sb.append("-----------------------------------------------\n");
        sb.append(String.format("  Total Taxes:   ₹%12.2f\n\n", (sum[0] + sum[1])));
        sb.append(String.format("  Gross Profit:  ₹%12.2f\n", profit[0]));
        sb.append(String.format("  Net Profit:    ₹%12.2f\n\n", profit[1]));
        sb.append("═══════════════════════════════════════════════\n");
        resultArea.setText(sb.toString());
        resultArea.setCaretPosition(0);
    }
    //Parses information for logging
    private void Logs_updater()
    {
        String nameText = nameField.getText().trim();
        
        if (Buy_date == null || Buy_date.isEmpty() || 
            Sell_date == null || Sell_date.isEmpty() ||
            buy_sell == null || Profit == null) 
        {
            System.out.println("Cannot log - incomplete data");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Name of Company traded: "+nameText+"\n");
        sb.append("Bought on: "+Buy_date+"  for ₹"+buy_sell[0]+"\n");
        sb.append("Sold on: "+Sell_date+"  for ₹"+buy_sell[1]+"\n");
        sb.append("Gross profit = ₹"+Profit[0]+" and Net profit = ₹"+Profit[1]+"\n");
        Tax_log LOG = new Tax_log(this);
        String S = Double.toString(Profit[1]);
        Profit[2] = LOG.Portfolio_value(S);
        sb.append("Portfolio value stands at: ₹"+Profit[2]+"\n\n\n");
        
        try {
            LOG.do_log(sb);
        }
        catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
        statusLabel.setText("✓ Total Portfolio Value is : ₹" + Profit[2]);
        statusLabel.setForeground(new Color(0, 100, 0));
    }
    private void clearAll() 
    {
        nameField.setText("");
        datesField.setText("");
        num1Field.setText("");
        num2Field.setText("");
        num3Field.setText("");
        IntraDay.setSelected(false);
        Log.setSelected(false);
        IsIntraDay = false;
        shouldLog = false;
        resultArea.setText("");
        statusLabel.setText("Cleared - Enter new numbers");
        statusLabel.setForeground(Color.BLACK);
        nameField.requestFocus();
    }
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {   @Override
            public void run() 
            {
                new Tax_window().setVisible(true);
            }
        });
    }
}