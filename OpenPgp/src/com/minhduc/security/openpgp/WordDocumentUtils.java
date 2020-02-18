package com.minhduc.security.openpgp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

public class WordDocumentUtils {

    /**
     * to create a paragraph with multiple line breaks
     * 
     * @param input
     * @return
     * @throws IOException
     * @throws WriterException
     * @throws InvalidFormatException
     */
    private static void createParagraph(String shopName, String input, XWPFTableCell cell) throws WriterException, IOException, InvalidFormatException {
        if (input == null) {
            return;
        }
        // shop name
        if (shopName == null) {
            shopName = "";
        }
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        run.setText(shopName);
        run.addBreak();

        // QR Code

        String[] lines = input.split(System.lineSeparator());
        if (lines.length >= 2) {
            String qrCodeData = lines[1];
            // insert image from buffers
            ByteArrayOutputStream image = QRCodeHelper.createQRCode(qrCodeData, 80, 80);
            InputStream imageIs = new ByteArrayInputStream(image.toByteArray());
            run.addPicture(imageIs, XWPFDocument.PICTURE_TYPE_PNG, "QR Code", Units.toEMU(80), Units.toEMU(80)); // 200x200
            imageIs.close();
            image.close();
            paragraph.setSpacingBefore(100);
            paragraph.setIndentationLeft(100);
            paragraph.setIndentationRight(100);
        }

        XWPFParagraph paragraph1 = cell.addParagraph();
        run = paragraph1.createRun();
        for (int i = 0; i < lines.length; i++) {
            run.setText(lines[i]);
            if (i < lines.length - 1) {
                run.addBreak();
            }
        }

        paragraph1.setSpacingAfter(100);
        paragraph1.setSpacingBefore(100);
        paragraph1.setIndentationLeft(100);
        paragraph1.setIndentationRight(100);
    }

    /**
     * to write a word document
     * 
     * @param content
     *            the word content
     * @param wordFile
     *            the output file path
     * @throws IOException
     * @throws WriterException
     * @throws InvalidFormatException
     */
    public static void writeToWordDocument(String shopName, List<String> content, String wordFile, int rowNr, int colNr) throws IOException,
            InvalidFormatException, WriterException {
        XWPFDocument document = new XWPFDocument();
        XWPFTable table = document.createTable(rowNr, colNr);
        int tableWidth = 9000;
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(tableWidth));

        // style
        for (int i = 0; i < table.getNumberOfRows(); i++) {
            XWPFTableRow row = table.getRow(i);
            int numCells = row.getTableCells().size();
            for (int j = 0; j < numCells; j++) {
                XWPFTableCell cell = row.getCell(j);
                CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
                // CTTcPr pr = cell.getCTTc().addNewTcPr();
                // pr.addNewNoWrap();
                // column length 2880 is measured in twentieths of a point (dxa)
                // or 1/1440 inch which is equal to 2 inches = 2880.
                int colWidth = (int) (tableWidth / numCells);
                // System.out.println("colwidth = " + colWidth);
                cellWidth.setW(BigInteger.valueOf(colWidth)); // 3 inches = 4320
                int index = (i * colNr) + j;
                String paraContent = null;
                if (content.size() > index) {
                    paraContent = content.get(index);
                }
                createParagraph(shopName, paraContent, cell);
            }
        }

        // Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File(wordFile));
        document.write(out);
        document.close();
        out.close();
    }

    /**
     * to write a word document
     * 
     * @param content
     *            the word content
     * @param wordFile
     *            the output file path
     * @throws IOException
     * @throws WriterException
     * @throws InvalidFormatException
     */
    public static void writeToWordDocument(List<String> content, String wordFile) throws IOException, InvalidFormatException, WriterException {
        writeToWordDocument(null, content, wordFile, 2, 2);
    }

    public static void main(String[] args) throws InvalidFormatException, IOException, WriterException, NotFoundException {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph title = doc.createParagraph();
        XWPFRun run = title.createRun();
        run.setText("Fig.1 A Natural Scene");
        run.setBold(true);
        title.setAlignment(ParagraphAlignment.CENTER);
        // create qr code and insert to file insert image from file

        String imgFile = "qrcodetest.png";
        QRCodeHelper.createQRCode("Hello world! 1", imgFile, 200, 200);
        System.out.println("QR Code image created successfully!");
        FileInputStream is = new FileInputStream(imgFile);
        run.addBreak();
        run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200
        is.close();

        // insert image from buffers
        String qrCodeData = "Hello World! I'm doning well.";
        ByteArrayOutputStream image = QRCodeHelper.createQRCode(qrCodeData, 200, 200);
        InputStream imageIs = new ByteArrayInputStream(image.toByteArray());
        run.addBreak();
        run.addPicture(imageIs, XWPFDocument.PICTURE_TYPE_PNG, "myqrcodefile.png", Units.toEMU(200), Units.toEMU(200)); // 200x200
        imageIs.close();
        image.close();

        FileOutputStream fos = new FileOutputStream("test4.docx");
        doc.write(fos);
        fos.close();
        doc.close();
    }
}
