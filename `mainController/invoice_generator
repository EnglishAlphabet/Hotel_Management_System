package dbcontroller;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

public class ByteInvoiceGen {
    public static ByteArrayOutputStream createInvoice(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(createHeader("Somber Sunset", "Tokyo\nJapan", "hoteltky@gmail.com"));

        String check_out = LocalDate.now().toString();
        document.add(createCustomerDetails("Booking ID: Se260911","Guest Name: John Doe", "Check-in: 2024-09-10", "Check-out: " +check_out));
        document.add(new Paragraph("\n"));
        document.add(createItemizedTable("Single","3 nights","$20","$60"));
        document.add(createTotalSection(60.00));
        document.add(createFooter("Thank you for staying with us!"));

        document.close();

        return byteArrayOutputStream;

    }
    public static Paragraph createHeader(String hotelName, String address, String contact) {
        Paragraph header = new Paragraph()
                .add(hotelName + "\n")
                .add(address + "\n")
                .add(contact + "\n")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16);
        return header;
    }

    public static Paragraph createCustomerDetails(String bkID, String guestName, String checkIn, String checkOut) {
        return new Paragraph()
                .add(bkID +"\n")
                .add(guestName + "\n")
                .add(checkIn + "\n")
                .add(checkOut + "\n")
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(12);
    }

    public static Table createItemizedTable(String itemDetails,String qnt, String unitPrice, String total) {
        float[] columnWidths = {2, 5, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(createTableHeaderCell("Item Details"));
        table.addHeaderCell(createQtyTableHeaderCell("Qty"));
        table.addHeaderCell(createTableHeaderCell("Unit Price"));
        table.addHeaderCell(createTableHeaderCell("Total"));


        table.addCell(itemDetails);
        table.addCell(qnt).setTextAlignment(TextAlignment.RIGHT);
        table.addCell(unitPrice);
        table.addCell(total);



        return table;
    }

    public static Cell createTableHeaderCell(String text) {
        Cell cell = new Cell().setTextAlignment(TextAlignment.CENTER);
        cell.add(new Paragraph(text).setBold());
        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        return cell;
    }

    public static Cell createQtyTableHeaderCell(String text) {
        Cell cell = new Cell().setTextAlignment(TextAlignment.RIGHT);
        cell.add(new Paragraph(text).setBold());
        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        return cell;
    }

    public static Paragraph createTotalSection(double totalAmount) {
        Paragraph total = new Paragraph()
                .add("\nTotal: $" + String.format("%.2f", totalAmount) + "\n")
                .setTextAlignment(TextAlignment.RIGHT)
                .setBold()
                .setFontSize(14);
        return total;
    }

    public static Paragraph createFooter(String footerText) {
        return new Paragraph()
                .add("\n" + footerText)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setItalic();
    }
}
