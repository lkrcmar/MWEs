package cz.zcu.luk.mwes.common;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 7.2.13
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class PdfGraphPrinter {
    public static void writeChartToPDFClipped(JFreeChart chart, int width, int height, String fileName) {
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            document.setPageSize(new com.itextpdf.text.Rectangle(width, height));
            if (document.isOpen()) {
                document.newPage();
            } else {
                document.open();
            }

            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
