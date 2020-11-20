package com.eam.IngSoft1.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Factura;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFExporterClass {
	
	private List<Detallefactura> listDetalleFactura;
	private Factura factura;
    

    public PDFExporterClass(List<Detallefactura> listDetalleFactura) {
		super();
		this.listDetalleFactura = listDetalleFactura;
	}
    


	public PDFExporterClass(List<Detallefactura> listaDetalle, Factura factura) {
		super();
		this.listDetalleFactura = listaDetalle;
		this.factura = factura;
	}



	private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Nombre Producto", font));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Marca Producto", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Cantidad", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Valor Unitario ", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Valor IVA ", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Valor Total + IVA", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Valor Total  Factura", font));
        table.addCell(cell);
        
    }
     
    private void writeTableData(PdfPTable table) {
        for (int i=0;i<listDetalleFactura.size();i++) {
            table.addCell(listDetalleFactura.get(i).getProducto().getNombreProducto());
            table.addCell(listDetalleFactura.get(i).getProducto().getMarca());
            table.addCell(String.valueOf(listDetalleFactura.get(i).getCantidadProducto()));
            table.addCell(String.valueOf(listDetalleFactura.get(i).getProducto().getValorUnitario()));
            table.addCell(String.valueOf(listDetalleFactura.get(i).getValorIvaTotal()));
            table.addCell(String.valueOf(listDetalleFactura.get(i).getValorTotal()));
            table.addCell("");
        }
        
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell(String.valueOf(factura.getPrecioTotal()));
        
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(15);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("Factura Compra", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.5f, 2.0f,1.5f, 2.0f, 2.0f, 1.5f , 1.5f });
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }

}
