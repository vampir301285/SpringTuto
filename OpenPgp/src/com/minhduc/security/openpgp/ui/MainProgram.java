package com.minhduc.security.openpgp.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.minhduc.security.openpgp.OpenPGPDecryptor;
import com.minhduc.security.openpgp.Utils;
import com.minhduc.security.openpgp.WordDocumentUtils;

public class MainProgram {

    private JTextField cipherTextFile;

    private JTextField privateKeyFile;

    private JTextField outputFile;

    private JTextField shopName;

    private JPasswordField passPhrase;

    private JProgressBar progressBar;

    private int firtRowY = 10;

    private int secondRowY = 40;

    private int thirdRowY = 70;

    private int fourthRowY = 100;

    private int fifthRowY = 130;

    private int lastRowY = 160;

    private JSpinner tableRow;

    private JSpinner tableCol;

    /** That is the bios serial number */
    /** command to get bios serial: wmic bios get serialnumber */
    private List<String> LICENSES = new ArrayList<String>(Arrays.asList("CNU4259BK9"));

    JFrame frame = new JFrame("PGP Decryptor");

    private String error = "";

    /**
     * to allow the program to be started
     * 
     * @param trialVersion
     *            allow trial version, otherwise check the real license by bios serial number
     * @return
     */
    public boolean checkLicense(boolean trialVersion) {
        boolean licensed = false;
        if (trialVersion) { // alow trial version by one month
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date expiredDate = sdf.parse("2017-11-01");
                Date now = new Date();
                if (expiredDate.getTime() - now.getTime() > 0) {
                    licensed = true;
                } else {
                    error = "Trial version is expired!";
                }
            } catch (ParseException e) {
                licensed = false;
                error = "Trial version is expired!";
            }
        } else { // check the license by compare bios serial number
            try {
                if (LICENSES.contains(Utils.getBiosSerialNumber().toUpperCase())) {
                    licensed = true;
                } else {
                    error = "The software doesn't have a valid license.";
                }
            } catch (IOException e1) {
                error = "Cannot get license." + e1.getMessage();
            }
        }
        return licensed;
    }

    public MainProgram() {
        boolean licensed = checkLicense(true);

        if (licensed) {
            initMainUi();
        } else {
            // show error message
            JLabel l1 = new JLabel(error);
            l1.setBounds(10, firtRowY, 500, 20);
            frame.add(l1);
        }

        frame.setSize(600, 300);
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = frame.getSize().width;
        int h = frame.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        frame.setLocation(x, y);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initMainUi() {
        // Cipher text file
        JLabel l1 = new JLabel("Cipher Text File: ");
        l1.setBounds(10, firtRowY, 100, 20);
        frame.add(l1);

        cipherTextFile = new JTextField("");
        cipherTextFile.setBounds(120, firtRowY, 300, 20);
        frame.add(cipherTextFile);

        JButton cipherFileButton = new JButton("Browse");
        cipherFileButton.setBounds(420, firtRowY, 100, 20);
        cipherFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCipherFile(e);
            }
        });
        frame.add(cipherFileButton);

        // Private key file
        JLabel l2 = new JLabel("Private Key File: ");
        l2.setBounds(10, secondRowY, 100, 20);
        frame.add(l2);

        privateKeyFile = new JTextField("");
        privateKeyFile.setBounds(120, secondRowY, 300, 20);
        frame.add(privateKeyFile);

        JButton privKeyFileButton = new JButton("Browse");
        privKeyFileButton.setBounds(420, secondRowY, 100, 20);
        privKeyFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPriKeyFile(e);
            }
        });
        frame.add(privKeyFileButton);

        // Passphrase
        JLabel l3 = new JLabel("Passphrase: ");
        l3.setBounds(10, thirdRowY, 100, 20);
        frame.add(l3);

        passPhrase = new JPasswordField();
        passPhrase.setBounds(120, thirdRowY, 300, 20);
        frame.add(passPhrase);

        // File out
        JLabel l4 = new JLabel("Save To: ");
        l4.setBounds(10, fourthRowY, 100, 20);
        frame.add(l4);

        outputFile = new JTextField("");
        outputFile.setBounds(120, fourthRowY, 300, 20);
        frame.add(outputFile);

        JButton outputFileBtn = new JButton("Browse");
        outputFileBtn.setBounds(420, fourthRowY, 100, 20);
        outputFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectOutputFolder(e);
            }
        });
        frame.add(outputFileBtn);

        // Table layout
        JLabel l5 = new JLabel("Table Layout: ");
        l5.setBounds(10, fifthRowY, 100, 20);
        frame.add(l5);

        l5 = new JLabel("Rows = ");
        l5.setBounds(120, fifthRowY, 50, 20);
        frame.add(l5);

        SpinnerModel value = new SpinnerNumberModel(8, // initial value
                1, // minimum value
                100, // maximum value
                1); // step
        tableRow = new JSpinner(value);
        tableRow.setBounds(170, fifthRowY, 50, 20);
        frame.add(tableRow);

        l5 = new JLabel(", Cols = ");
        l5.setBounds(230, fifthRowY, 50, 20);
        frame.add(l5);

        value = new SpinnerNumberModel(3, // initial value
                1, // minimum value
                10, // maximum value
                1); // step
        tableCol = new JSpinner(value);
        tableCol.setBounds(280, fifthRowY, 50, 20);
        frame.add(tableCol);

        // Passphrase
        JLabel shopLabel = new JLabel("Shop Name: ");
        shopLabel.setBounds(10, lastRowY, 100, 20);
        frame.add(shopLabel);

        shopName = new JTextField("");
        shopName.setBounds(120, lastRowY, 300, 20);
        frame.add(shopName);

        // main button
        JSeparator sep = new JSeparator();
        sep.setBounds(10, lastRowY + 30, 600, 1);
        frame.add(sep);

        JLabel label = new JLabel("");
        label.setBounds(10, lastRowY + 40, 100, 20);
        frame.add(label);

        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(20, lastRowY + 40, 398, 20);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setVisible(false);
        frame.add(progressBar);

        JButton mainButton = new JButton("Decrypt");
        mainButton.setBounds(420, lastRowY + 40, 100, 20);
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptCipherPGP();
            }
        });

        frame.add(mainButton);
    }

    /**
     * open file chosse to select a cipher text file
     * 
     * @param e
     */
    private void selectCipherFile(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setCurrentDirectory(workingDirectory);
        int i = fc.showOpenDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String filepath = f.getPath();
            cipherTextFile.setText(filepath);
        }
    }

    /**
     * open file chosse to select a cipher text file
     * 
     * @param e
     */
    private void selectPriKeyFile(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setCurrentDirectory(workingDirectory);
        int i = fc.showOpenDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String filepath = f.getPath();
            privateKeyFile.setText(filepath);
        }
    }

    /**
     * open file choose to select a cipher text file
     * 
     * @param e
     */
    private void selectOutputFolder(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents (*.docx)", "docx"));
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setCurrentDirectory(workingDirectory);
        int i = fc.showSaveDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String filepath = f.getPath();
            outputFile.setText(filepath);
        }
    }

    /**
     * decryption method
     */
    private void decryptCipherPGP() {

        Runnable myRunnable = new Runnable() {
            public void run() {
                try {
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    progressBar.setVisible(true);
                    progressBar.setValue(0);
                    progressBar.setString("Starting...");

                    int row = (Integer) tableRow.getValue();
                    int col = (Integer) tableCol.getValue();
                    System.out.println("Cipher file: " + cipherTextFile.getText());
                    System.out.println("Private key file: " + privateKeyFile.getText());
                    System.out.println("Passphrase: *******");
                    System.out.println("Private key file: " + outputFile.getText());
                    System.out.println("Table layout: row = " + row + ", col = " + col);
                    System.out.println("Shop name: " + shopName.getText());
                    System.out.println("-----Decrypting pgp messages, please wait-----");
                    OpenPGPDecryptor decryptor = new OpenPGPDecryptor();
                    progressBar.setString("Descrypting PGP messages....");
                    progressBar.setValue(20);
                    List<String> dectext;
                    dectext = decryptor.decryptMultipleBlocksFile(cipherTextFile.getText(), privateKeyFile.getText(), new String(passPhrase.getPassword()));
                    System.out.println();
                    System.out.println("-----Writing word document...");
                    progressBar.setString("Writing word document...");
                    progressBar.setValue(60);
                    WordDocumentUtils.writeToWordDocument(shopName.getText(), dectext, outputFile.getText(), row, col);
                    System.out.println("Sucessfully created word document");
                    System.out.println("-----End----");
                    progressBar.setString("Successfully decrypted.");
                    progressBar.setValue(100);
                } catch (Exception e) {
                    progressBar.setString("Error: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    frame.setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        Thread decryptionThread = new Thread(myRunnable);
        decryptionThread.start();
    }

    public static void main(String[] args) {
        new MainProgram();
    }
}
