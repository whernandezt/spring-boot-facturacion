package com.hyc.springboot.facturacion.view.pdf;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.hyc.springboot.facturacion.models.entity.Factura;
import com.hyc.springboot.facturacion.models.entity.ItemFactura;
import com.hyc.springboot.facturacion.models.entity.Negocio;
import com.hyc.springboot.facturacion.models.service.IConfiguracionService;
import com.hyc.springboot.facturacion.models.service.IUploadFileService;
import com.hyc.springboot.facturacion.util.general.NumeroLetras;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("facturas/ver")//igual a la vista que retorna el controlador
public class FacturaPdfView extends AbstractPdfView {
	
	/*Multilenguaje
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
*/
	
	//private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IConfiguracionService configuracionService;
	
	@Autowired
	IUploadFileService uploadFileService;
	
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Negocio negocio = configuracionService.findNegocioById(Long.valueOf(1));		
		Image img = Image.getInstance(uploadFileService.loadAsUrl(negocio.getLogo()));
		img.scaleAbsolute(150, 60);
		
		Factura factura = (Factura)model.get("factura");
		//Locale locale = LocaleResolver.resolveLocale("request");
		
		//Font fontNormal = FontFactory.getFont("Arial", 12, Font.NORMAL, Color.black);
		Font fontBold = FontFactory.getFont("Arial", 12, Font.BOLD, Color.black);
		Font fontItalic = FontFactory.getFont("Arial", 10, Font.ITALIC, Color.black);
		
		if(factura.getTipoDocumento().getId() == Long.valueOf(1))
		{
		PdfPTable tabla0 = new PdfPTable(3);
		
		tabla0.setWidths(new float[] {2, 3.5f, 1});
		tabla0.setSpacingAfter(10);
		
		PdfPCell cell = null;
		cell = new PdfPCell(img);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setRowspan(4);
		tabla0.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Comprobante de Venta", fontBold));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
		cell.setBorder(Rectangle.NO_BORDER);
		tabla0.addCell(cell);
		
		cell = new PdfPCell(new Phrase("No. ".concat(factura.getNumero().toString())));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setRowspan(4);
		tabla0.addCell(cell);
		
		cell = new PdfPCell(new Phrase(negocio.getDireccion(), fontItalic));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
		cell.setBorder(Rectangle.NO_BORDER);
		tabla0.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Tel.: " + negocio.getTelefono(), fontItalic));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setRowspan(2);
		tabla0.addCell(cell);
		
		
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		
		//Obtener el titulo segun el lenguaje
		//messageSource.getMessage("text.cliente.listar.titulo", null, locale)
		//cell.setPadding(2f);
		//cell.setBorder(Rectangle.NO_BORDER);
		//tabla.addCell(cell);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tabla.addCell("Fecha: " + formatter.format(factura.getCreateAt()));
		tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tabla.addCell("Cliente: " + factura.getCliente().toString());
		tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tabla.addCell("Descripción: " + factura.getDescripcion());
		
		document.add(tabla0);
		document.add(tabla);
		
		PdfPTable tabla2 = new PdfPTable(4);
		tabla2.setWidths(new float[] {1, 3.5f, 1, 1});
		
		tabla2.addCell("Cantidad");
		tabla2.addCell("Producto");
		tabla2.addCell("Precio");
		tabla2.addCell("Total");
		
		for(ItemFactura item: factura.getItems()) {
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tabla2.addCell(cell);
			
			tabla2.addCell(item.getProducto().getNombre());
			tabla2.addCell("$" + String.format("%.2f",item.getPrecio()));
			
			
			
			cell = new PdfPCell(new Phrase("$" + String.format("%.2f",item.calcularImporte())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tabla2.addCell(cell);
		}
		
		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla2.addCell(cell);
		
		cell = new PdfPCell(new Phrase("$" + String.format("%.2f",factura.getTotal())));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla2.addCell(cell);
		
		NumeroLetras nl = new NumeroLetras();
		
		cell = new PdfPCell(new Phrase(nl.Convertir(String.format("%.2f",factura.getTotal()), true)));
		cell.setColspan(4);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(cell);
		
		cell = new PdfPCell(new Phrase(" "));
		cell.setColspan(4);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Elaborado por: " + factura.getUsuario()));
		cell.setColspan(4);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		tabla2.addCell(cell);
		
		document.add(tabla2);
		}
		else{
			PdfPTable tabla0 = new PdfPTable(1);
			
			tabla0.setSpacingAfter(10);
			
			PdfPCell cell = null;
			
			cell = new PdfPCell(new Phrase("No. ".concat(factura.getNumero().toString())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
			cell.setBorder(Rectangle.NO_BORDER);
			tabla0.addCell(cell);
			
			
			PdfPTable tabla = new PdfPTable(1);
			tabla.setSpacingAfter(20);
			
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tabla.addCell(formatter.format(factura.getCreateAt()));
			tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tabla.addCell(factura.getCliente().toString());
			tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tabla.addCell(factura.getDescripcion());
			
			document.add(tabla0);
			document.add(tabla);
			
			PdfPTable tabla2 = new PdfPTable(5);
			tabla2.setWidths(new float[] {1, 3.5f, 1, 1, 1});
			
			
			for(ItemFactura item: factura.getItems()) {
				cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				cell.setBorder(Rectangle.NO_BORDER);
				tabla2.addCell(cell);
				
				tabla2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				tabla2.addCell(item.getProducto().getNombre());
				tabla2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				tabla2.addCell("$" + String.format("%.2f", item.getPrecio() - item.getIva()));
				tabla2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				tabla2.addCell("$" + String.format("%.2f", item.getIva()));
				
				
				cell = new PdfPCell(new Phrase("$" + String.format("%.2f",item.calcularImporte())));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				cell.setBorder(Rectangle.NO_BORDER);
				tabla2.addCell(cell);
			}
			
			cell = new PdfPCell(new Phrase("Total: "));
			cell.setColspan(4);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			tabla2.addCell(cell);
			
			cell = new PdfPCell(new Phrase("$" + String.format("%.2f",factura.getTotal())));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			tabla2.addCell(cell);
			
			NumeroLetras nl = new NumeroLetras();
			
			cell = new PdfPCell(new Phrase(nl.Convertir(String.format("%.2f",factura.getTotal()), true)));
			cell.setColspan(5);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			tabla2.addCell(cell);
			
			cell = new PdfPCell(new Phrase(" "));
			cell.setColspan(5);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			tabla2.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Elaborado por: " + factura.getUsuario()));
			cell.setColspan(5);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			tabla2.addCell(cell);
			
			document.add(tabla2);
		}
	}
	
	
}
