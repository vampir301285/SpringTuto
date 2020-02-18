package com.minhduc.security.openpgp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * @author Minh Duc Ngo (minhducngo85@gmail.com)
 *
 */
public class QRCodeHelper {
    public static void main(String[] args) throws WriterException, IOException, NotFoundException {
        String qrCodeData = "Hello World!";
        String filePath = "QRCode_Example.png";
        createQRCode(qrCodeData, filePath, 200, 200);
        System.out.println("QR Code image created successfully!");
        System.out.println("Data read from QR Code: " + readQRCode(filePath));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static ByteArrayOutputStream createQRCode(String text, int qrCodewidth, int qrCodeheight) throws WriterException, IOException {
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // create QR code
        BitMatrix matrix = new MultiFormatWriter().encode(new String(text.getBytes(charset), charset), BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight,
                hintMap);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return os;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void createQRCode(String qrCodeData, String filePath, int qrCodeheight, int qrCodewidth) throws WriterException, IOException {
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        createQRCode(qrCodeData, filePath, charset, hintMap, qrCodeheight, qrCodewidth);
    }

    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
    public static void createQRCode(String qrCodeData, String filePath, String charset, Map hintMap, int qrCodeheight, int qrCodewidth) throws WriterException,
            IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight,
                hintMap);
        MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String readQRCode(String filePath) throws FileNotFoundException, IOException, NotFoundException {
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return readQRCode(filePath, charset, hintMap);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String readQRCode(String filePath, String charset, Map hintMap) throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
        return qrCodeResult.getText();
    }
}
