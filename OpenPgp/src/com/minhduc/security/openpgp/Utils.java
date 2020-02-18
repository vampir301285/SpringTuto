package com.minhduc.security.openpgp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Utils {
    public byte[] inputStreamToByteArray(InputStream is) throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[1024];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    /**
     * to read the content of a text file
     * 
     * @param textFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    static String readTextFile(String textFile) throws FileNotFoundException, IOException {
        String outputString = null;
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            outputString = sb.toString();
        }
        return outputString;
    }

    /**
     * convert InputStream to String
     * 
     * @param is
     *            the input stream
     * @return
     */
    static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * to write a text file
     * 
     * @param content
     * @param outputFile
     */
    static void writeToTextFile(String content, String outputFile) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(outputFile);
            bw = new BufferedWriter(fw);
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getBiosSerialNumber() throws IOException {
        // wmic command for diskdrive id: wmic DISKDRIVE GET SerialNumber
        // wmic command for cpu id : wmic cpu get ProcessorId
        // wmic command for cpu id : wmic bios get serialnumber
        Process process = Runtime.getRuntime().exec(new String[] { "wmic", "bios", "get", "serialnumber" });
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        @SuppressWarnings("unused")
        String property = sc.next();
        String serial = sc.next();
        sc.close();
        return serial;
    }
}
