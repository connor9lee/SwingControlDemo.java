import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.Border;

public class SwingControlDemo implements ActionListener {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel header;
    private JLabel statusLabel;
    private JPanel controlPanel, printerPanel, resultPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea a; // 1st text box
    private JTextArea b;// 2nd text box
    private JTextArea c; //result box
    private JScrollPane scroll;
    private String aRealText;
    private int xResult = 0;
    private int yResult = 0;

    private int WIDTH=800;
    private int HEIGHT=700;


    public SwingControlDemo() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);
        mainFrame.add(mb);
        mainFrame.setJMenuBar(mb);

        a = new JTextArea();
        a.setEditable(true);
        b = new JTextArea();
        c = new JTextArea();

        a.setLineWrap(true);
        b.setLineWrap(true);
        c.setLineWrap(true);

        //c.setBounds(50,5,WIDTH-100,HEIGHT-50);

        headerLabel = new JLabel("URL");
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel = new JLabel("Keyword");
        statusLabel.setSize(350, 100);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        printerPanel = new JPanel();
        printerPanel.setLayout(new GridLayout(5,1));
        printerPanel.add(headerLabel, BorderLayout.NORTH);
        //1st print box

        printerPanel.add(a, BorderLayout.NORTH);
        printerPanel.add(statusLabel, BorderLayout.NORTH);
       // 2nd print box
        printerPanel.add(b, BorderLayout.NORTH);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(printerPanel, BorderLayout.NORTH);
        printerPanel.add(controlPanel, BorderLayout.CENTER);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.add(c, BorderLayout.CENTER);
        c.setEditable(true);
        scroll = new JScrollPane(c);
        resultPanel.add(scroll, BorderLayout.CENTER);


        mainFrame.add(resultPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        //JScrollPane scrollableResultPanel = new JScrollPane(resultPanel);
        //scrollableResultPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollableResultPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //mainFrame.getContentPane().add(scrollableResultPanel);
    }

    private void showEventDemo() {
        //headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("OK");
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        okButton.setActionCommand("OK");
        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());

        controlPanel.add(okButton, BorderLayout.CENTER);
       //controlPanel.add(submitButton);
       //controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            b.cut();
        if (e.getSource() == paste)
            b.paste();
        if (e.getSource() == copy)
            b.copy();
        if (e.getSource() == selectAll)
            b.selectAll();
    }

    public void HtmlRead() {
        try {
            System.out.println();
            System.out.print("hello \n");
            URL url = new URL(aRealText);
            //url - new URL(ta.getText());
            //use .contains(), .indexOf(), .substring()
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ( (line = reader.readLine()) != null ) {
                if (line.contains("https")){
                    xResult = line.indexOf("https:");
                    System.out.println(xResult);
                    String halfString;
                    halfString = line.substring(xResult,line.length());
                    if(halfString.contains("\"")){
                        yResult = line.indexOf("\"", xResult);
                        System.out.println(yResult);
                    }
                    else{
                        if(halfString.contains("'")){
                            yResult = line.indexOf("'", xResult);
                            System.out.println(yResult);
                        }
                        else{
                            if(halfString.contains("-")){
                                yResult = line.indexOf("-",xResult);
                                System.out.println(yResult);
                            }
                        }
                    }
                    System.out.println(line);
                    String Links = line.substring(xResult, yResult);
                    System.out.println("*****" + Links);
                    if (Links.contains(b.getText())){
                        c.append(Links);
                        c.append("\n");
                    }
//                    c.append(line);
                }
//                System.out.println(line);
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("OK")) {
                aRealText = a.getText();
                System.out.println(aRealText);

                HtmlRead();
            } else if (command.equals("Submit")) {
                statusLabel.setText("Submit Button clicked.");
            } else {
                statusLabel.setText("Cancel Button clicked.");
            }
        }
    }
}