package com.lonar.master.a2zmaster.invoicepdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
//import com.itextpdf.layout.element.Image; 
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//import com.lonar.a2zcommons.model.InvoiceLinesData;
//import com.lonar.a2zcommons.model.InvoicePdfData;
import com.lonar.master.a2zmaster.common.NumberToWord;
import com.lonar.master.a2zmaster.model.InvoiceLinesData;
import com.lonar.master.a2zmaster.model.InvoicePdfData;

public class InvoicePDFGenarate {

	SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font colorBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
	static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	static DecimalFormat twoDForm = new DecimalFormat("0.00");

	public static void main(String[] args) {

		InvoicePdfData pdfData = new InvoicePdfData();
		pdfData.setSupplierName("Lonar Technology Pvt.");
		pdfData.setSupplierAddress("At-Post:Deshmukhwad, Tah:Khed, Dist:Pune-410505");
		pdfData.setMobileNumber("+919623451248l");
		pdfData.setCustomerName("Anita Gajanan Wayal");
		pdfData.setCustomerAddress(
				"F-101,Solitaire Residency,Solitaire ResidencySolitaire ResidencyJagtap Dairy Road,Rahatani,Pune");

		pdfData.setGstNumber("27AADCL1747D1Z0");
		pdfData.setInvoiceDate("01-Feb-2019");
		pdfData.setDueDate("05-Feb-2019");
		pdfData.setPoNumber(" ");
		pdfData.setDispatchThrough(" ");
		pdfData.setFromDeliveryDate("01-Jan-2019");
		pdfData.setToDeliveryDate("31-Jan-2019");
		// pdfData.setDiscount();
		// pdfData.setGrandTotal();
		// pdfData.setInvoiceAmount();
		// pdfData.setAmountInWords();
		pdfData.setInvoiceNumber("123456");

		List<InvoiceLinesData> tableDataList = new ArrayList<InvoiceLinesData>();

		/*
		 * for (int i = 0; i < 5; i++) { InvoiceLinesData tableData = new
		 * InvoiceLinesData(); // tableData.setSrNo(srNo + i);
		 * tableData.setDeliveryDate("date" + i); tableData.setProductName("product" +
		 * i); tableData.setUom("uom" + i); tableData.setDeliveryQuantity("quantity" +
		 * i);
		 * 
		 * tableDataList.add(tableData);
		 * 
		 * }
		 */
		pdfData.setInvoiceLines(tableDataList);

		InvoicePDFGenarate invoicePDFGenarate = new InvoicePDFGenarate();
		//invoicePDFGenarate.createPDF("SecondPDF", "E:", pdfData);
	}

	public String createPDF(String pdfName, String pdfPath, InvoicePdfData pdfData, String logoPath) {
		String status = "Failed";
		try {
			Document document = new Document();
			int pageno = document.getPageNumber();
			PdfWriter.getInstance(document, new FileOutputStream(pdfPath + "/" + pdfName + ".pdf"));
			//PdfWriter.getInstance(document, new FileOutputStream(pdfPath + "\\" + pdfName + ".pdf"));
			document.open();

			addTitlePage(document, pdfData, pdfData.getSupplierId() ,logoPath);
			document.close();
			System.out.println("File Created..");

			status = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	private static void addTitlePage(Document document, InvoicePdfData pdfData, Long supplierId, String logoPath)
			throws DocumentException, ParseException {
		try {
			PdfWriter.getInstance(document, new FileOutputStream("ParaAlign.pdf"));
			document.setMargins(50, 50, 50, 50);
			document.open();

			Chunk chunkCompanyName = new Chunk(pdfData.getSupplierName(), catFont);
			Chunk chunkCompamyAddress = new Chunk(pdfData.getSupplierAddress(), redFont);
			Chunk chunkMobileNumber = new Chunk(pdfData.getMobileNumber(), smallBold);
			//String imageFile = "E:\\A2Z_workspace\\report\\logo\\"+supplierId+".png";
			String imageFile = logoPath+"logo/"+supplierId+".png";
			//String imageFile = logoPath+"logo/pdf_logo.bmp";
			//System.out.println( "imageFile " + imageFile );
			Image img = null;
			PdfPCell cellImg = null;
			try {
				img = Image.getInstance(imageFile);
				img.scaleAbsolute(50, 50);
				cellImg = new PdfPCell(img);
			}catch(Exception e) {
				cellImg = new PdfPCell();
				e.printStackTrace();
			}
						
			float[] coloumnWidth = { 50f, 200f, 50f };
			PdfPTable headerTable = new PdfPTable(coloumnWidth);
			headerTable.setWidthPercentage(100f);
			headerTable.getDefaultCell().setBorderWidth(0f);
			cellImg.addElement(img);
			cellImg.setRowspan(3);
			cellImg.setBorderColor(BaseColor.WHITE);
			headerTable.addCell(cellImg);
			
			PdfPCell hC2 = new PdfPCell(new Phrase(chunkCompanyName));
			hC2.setBorderColor(BaseColor.WHITE);
			hC2.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
			headerTable.addCell(hC2);
			
			PdfPCell hr = new PdfPCell(new Phrase(""));
			hr.setBorderColor(BaseColor.WHITE);
			hr.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
			hr.setRowspan(3);
			headerTable.addCell(hr);
					
			PdfPCell hC3 = new PdfPCell(new Phrase(chunkCompamyAddress));
			hC3.setBorderColor(BaseColor.WHITE);
			hC3.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
			headerTable.addCell(hC3);

			PdfPCell hC4 = new PdfPCell(new Phrase(chunkMobileNumber));
			hC4.setBorderColor(BaseColor.WHITE);
			hC4.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
			headerTable.addCell(hC4);
			
			document.add(headerTable); 

			Paragraph para5 = new Paragraph("Invoice", catFont);
			para5.setAlignment(Paragraph.ALIGN_CENTER);
			para5.setSpacingAfter(10);
			document.add(para5);

			// outerTable
			float[] pointColoumnWidth = { 250f, 200f };
			PdfPTable outerTable = new PdfPTable(pointColoumnWidth);
			outerTable.setWidthPercentage(100f);
			outerTable.getDefaultCell().setBorder(0);
			PdfPCell c1 = new PdfPCell();
			c1.setBorder(PdfPCell.NO_BORDER);

			PdfPCell c2 = new PdfPCell();
			c2.setBorder(PdfPCell.NO_BORDER);
			// c2.disableBorderSide(2);

			// //---------------------------customertable for coloumn1
			float[] pointColoumnWidths = { 80f, 170f };
			PdfPTable nestedTable = new PdfPTable(pointColoumnWidths);
			nestedTable.setWidthPercentage(100f);
			PdfPCell c3 = new PdfPCell(new Phrase("Customer", smallBold));
			PdfPCell c4 = new PdfPCell(new Phrase(pdfData.getCustomerName()));
			nestedTable.addCell(c3);
			nestedTable.addCell(c4);

			nestedTable.addCell("");
			nestedTable.addCell("");
			nestedTable.setHeaderRows(1);

			PdfPCell c5 = new PdfPCell(new Phrase("Address", smallBold));
			// c5.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c6 = new PdfPCell(new Phrase(pdfData.getCustomerAddress()));

			// c6.setBorder(PdfPCell.NO_BORDER);
			nestedTable.addCell(c5);
			nestedTable.addCell(c6);
			nestedTable.addCell("");
			nestedTable.addCell("");
			nestedTable.setHeaderRows(1);

			PdfPCell c7 = new PdfPCell(new Phrase("GSTN", smallBold));
			// c7.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c8 = new PdfPCell(new Phrase(pdfData.getGstNumber(), smallBold));
			// c8.setBorder(PdfPCell.NO_BORDER);
			nestedTable.addCell(c7);
			nestedTable.addCell(c8);
			nestedTable.addCell("");
			nestedTable.addCell("");
			nestedTable.setHeaderRows(1);

			// blank cell after gstn

			PdfPCell c29 = new PdfPCell(new Phrase("   "));
			// c21.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c30 = new PdfPCell(new Phrase("   "));
			// c22.setBorder(PdfPCell.NO_BORDER);
			nestedTable.addCell(c29);
			nestedTable.addCell(c30);
			nestedTable.addCell("");
			nestedTable.addCell("");
			nestedTable.setHeaderRows(1);

			PdfPCell c31 = new PdfPCell(new Phrase("   "));
			// c21.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c32 = new PdfPCell(new Phrase("   "));
			// c22.setBorder(PdfPCell.NO_BORDER);
			nestedTable.addCell(c31);
			nestedTable.addCell(c32);
			nestedTable.addCell("");
			nestedTable.addCell("");
			nestedTable.setHeaderRows(1);

			// ---------------------------invoice details for coloumn2

			float[] pointColoumnWidthOfIn = { 100f, 150f };
			PdfPTable innerTable = new PdfPTable(pointColoumnWidthOfIn);
			innerTable.setWidthPercentage(100f);
			PdfPCell c9 = new PdfPCell(new Phrase("Invoice No", smallBold));
			// c9.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c10 = new PdfPCell(new Phrase(pdfData.getInvoiceNumber()));
			// c10.setBorder(PdfPCell.NO_BORDER);
			innerTable.addCell(c9);
			innerTable.addCell(c10);
			innerTable.addCell("");
			innerTable.addCell("");
			innerTable.setHeaderRows(1);

			PdfPCell c11 = new PdfPCell(new Phrase("Invoice Date", smallBold));
			// c11.setBorder(PdfPCell.NO_BORDER);pdfData.getInvoiceDate()
			PdfPCell c12 = new PdfPCell(
					new Phrase(formatter.format(new SimpleDateFormat("yyyy-MM-dd").parse(pdfData.getInvoiceDate()))));
			// c12.setBorder(PdfPCell.NO_BORDER);
			innerTable.addCell(c11);
			innerTable.addCell(c12);
			innerTable.addCell("");
			innerTable.addCell("");
			innerTable.setHeaderRows(1);

			PdfPCell c13 = new PdfPCell(new Phrase("Due Date", smallBold));
			// c13.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c14 = new PdfPCell(
					new Phrase(formatter.format(new SimpleDateFormat("yyyy-MM-dd").parse(pdfData.getDueDate()))));
			// c14.setBorder(PdfPCell.NO_BORDER);
			innerTable.addCell(c13);
			innerTable.addCell(c14);
			innerTable.addCell("");
			innerTable.addCell("");
			innerTable.setHeaderRows(1);

			//PdfPCell c15 = new PdfPCell(new Phrase("PO No", smallBold));
			// c15.setBorder(PdfPCell.NO_BORDER);
			//PdfPCell c16 = new PdfPCell(new Phrase(pdfData.getPoNumber()));
			// c16.setBorder(PdfPCell.NO_BORDER);
			//innerTable.addCell(c15);
			//innerTable.addCell(c16);
			innerTable.addCell("");
			innerTable.addCell("");
			innerTable.setHeaderRows(1);

			//PdfPCell c17 = new PdfPCell(new Phrase("Dispatch Through", smallBold));
			// c17.setBorder(PdfPCell.NO_BORDER);
			//PdfPCell c18 = new PdfPCell(new Phrase(pdfData.getDispatchThrough()));
			// c18.setBorder(PdfPCell.NO_BORDER);
			//innerTable.addCell(c17);
			//innerTable.addCell(c18);
			innerTable.addCell("");
			innerTable.addCell("");
			innerTable.setHeaderRows(1);

			/*
			 * PdfPCell c19 = new PdfPCell(new Phrase("   ")); //
			 * c21.setBorder(PdfPCell.NO_BORDER); PdfPCell c20 = new PdfPCell(new
			 * Phrase("   ")); // c22.setBorder(PdfPCell.NO_BORDER);
			 * innerTable.addCell(c19); innerTable.addCell(c20); innerTable.addCell("");
			 * innerTable.addCell(""); innerTable.setHeaderRows(1);
			 */

			PdfPCell c21 = new PdfPCell(new Phrase("From", smallBold));
			// c21.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c22 = new PdfPCell(new Phrase(
					formatter.format(new SimpleDateFormat("yyyy-MM-dd").parse(pdfData.getFromDeliveryDate()))));
			// c22.setBorder(PdfPCell.NO_BORDER);
			innerTable.addCell(c21);
			innerTable.addCell(c22);
			innerTable.addCell("");
			innerTable.addCell("");
			innerTable.setHeaderRows(1);

			PdfPCell c23 = new PdfPCell(new Phrase("To", smallBold));
			// c23.setBorder(PdfPCell.NO_BORDER);
			PdfPCell c24 = new PdfPCell(new Phrase(
					formatter.format(new SimpleDateFormat("yyyy-MM-dd").parse(pdfData.getToDeliveryDate()))));
			// c24.setBorder(PdfPCell.NO_BORDER);
			innerTable.addCell(c23);
			innerTable.addCell(c24);
			innerTable.addCell("");
			innerTable.addCell("");
			innerTable.setHeaderRows(1);

			c1.addElement(nestedTable);

			c2.addElement(innerTable);
			outerTable.addCell(c1);
			outerTable.addCell(c2);
			outerTable.addCell("");
			outerTable.addCell("");
			outerTable.setHeaderRows(1);

			document.add(outerTable);

			// data tablefloat[] pointColoumnWidthTotal = { 50f, 110f, 250f, 50f, 60f, 50f, 89f };
			float[] pointColoumnWidthOfData = { 50f, 110f, 250f, 50f, 60f, 70f, 89f };
			PdfPTable tableData = new PdfPTable(pointColoumnWidthOfData);
			tableData.setWidthPercentage(100f);
			PdfPCell c25 = new PdfPCell(new Phrase("SrNo", colorBold));
			// c25.(Color.cyan);
			// c25.setBackgroundColor = new iTextSharp.text.Color(51, 102,102);
			c25.setBackgroundColor(BaseColor.RED);
			tableData.addCell(c25);

			c25 = new PdfPCell(new Phrase("Date", colorBold));
			c25.setBackgroundColor(BaseColor.RED);
			// c25.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableData.addCell(c25);

			c25 = new PdfPCell(new Phrase("Product", colorBold));
			c25.setBackgroundColor(BaseColor.RED);
			tableData.addCell(c25);

			c25 = new PdfPCell(new Phrase("UOM", colorBold));
			c25.setBackgroundColor(BaseColor.RED);
			tableData.addCell(c25);

			c25 = new PdfPCell(new Phrase("Qty", colorBold));
			c25.setBackgroundColor(BaseColor.RED);
			tableData.addCell(c25);

			c25 = new PdfPCell(new Phrase("Rate", colorBold));
			c25.setBackgroundColor(BaseColor.RED);
			tableData.addCell(c25);

			c25 = new PdfPCell(new Phrase("Amount", colorBold));
			c25.setBackgroundColor(BaseColor.RED);
			tableData.addCell(c25);

			tableData.setHeaderRows(1);
			tableData.addCell("");
			tableData.addCell("");
			tableData.addCell("");
			tableData.addCell("");
			tableData.addCell("");
			tableData.addCell("");
			tableData.addCell("");

			// to add data in table
			// --------for Row data entry------
			int count = 1;
			List<InvoiceLinesData> invoiceLines = pdfData.getInvoiceLines();
			
			for (InvoiceLinesData invoiceLinesData : invoiceLines) {

				PdfPCell srNoRow = new PdfPCell(new Phrase("" + count));
				srNoRow.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell dateRow = new PdfPCell(new Phrase(formatter
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(invoiceLinesData.getDeliveryDate()))));
				dateRow.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell productRow = new PdfPCell(new Phrase(invoiceLinesData.getProductName()));
				// productRow.setHorizontalAlignment(Element.ALIGN_RIGHT);
				PdfPCell uomRow = new PdfPCell(new Phrase(invoiceLinesData.getUom()));
				// uomRow.setHorizontalAlignment(Element.ALIGN_RIGHT);
				PdfPCell qtyRow = new PdfPCell(new Phrase(invoiceLinesData.getDeliveryQuantity().toString()));
				qtyRow.setHorizontalAlignment(Element.ALIGN_RIGHT);
				PdfPCell rateRow = null;

				String invoiceRate = null;
				if (invoiceLinesData.getInvoiceRate() != null) {
					if (twoDForm.format(invoiceLinesData.getInvoiceRate()) != null) {
						invoiceRate = twoDForm.format(invoiceLinesData.getInvoiceRate());
					}
				}

				if (invoiceLinesData.getInvoiceRate() != null) {
					rateRow = new PdfPCell(new Phrase(invoiceRate));
				} else {
					rateRow = new PdfPCell(new Phrase("0.00"));
				}
				rateRow.setHorizontalAlignment(Element.ALIGN_RIGHT);
				PdfPCell amountRow = null;

				String lineAmt = null;
				System.out.println(" LINE AMT => "+ invoiceLinesData.getLineAmount());
				
				if (invoiceLinesData.getLineAmount() != null) {
					if (twoDForm.format(invoiceLinesData.getLineAmount()) != null) {
						lineAmt = twoDForm.format(invoiceLinesData.getLineAmount());
					}
				}
				
				//System.out.println(" LINE AMT =>2 "+ lineAmt);
				
				if (invoiceLinesData.getLineAmount() != null) {
					amountRow = new PdfPCell(new Phrase(lineAmt));
					amountRow.setHorizontalAlignment(Element.ALIGN_RIGHT);
				} else {
					amountRow = new PdfPCell(new Phrase(0));
					amountRow.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				tableData.addCell(srNoRow);
				tableData.addCell(dateRow);
				tableData.addCell(productRow);
				tableData.addCell(uomRow);
				tableData.addCell(qtyRow);
				tableData.addCell(rateRow);
				tableData.addCell(amountRow);
				count++;
			}

			document.add(tableData);

			// ------------total display------------
			float[] pointColoumnWidthTotal = { 52f, 200f, 168f, 0f, 54f, 0f, 146f };
			//float[] pointColoumnWidthTotal = { 51f, 200f, 170f, 0f, 54f, 0f, 146f };
			//float[] pointColoumnWidthTotal = { 20f, 40f, 40f, 40f, 40f, 159f, 104f };
			PdfPTable totalTable = new PdfPTable(pointColoumnWidthTotal);
			totalTable.setWidthPercentage(100f);
			totalTable.getDefaultCell().setBorder(0);
			PdfPCell cel1 = new PdfPCell(new Phrase(""));
			cel1.setBorder(PdfPCell.NO_BORDER);
			totalTable.addCell(cel1);
			cel1 = new PdfPCell(new Phrase(""));
			cel1.setBorder(PdfPCell.NO_BORDER);
			totalTable.addCell(cel1);
			cel1.setBorder(PdfPCell.NO_BORDER);
			cel1 = new PdfPCell(new Phrase("Total"));
			cel1.setHorizontalAlignment(Element.ALIGN_LEFT);
			totalTable.addCell(cel1);
			cel1 = new PdfPCell(new Phrase(""));
			totalTable.addCell(cel1);

			String totalQty = null;
			if (twoDForm.format(pdfData.getTotalQty()) != null) {
				totalQty = twoDForm.format(pdfData.getTotalQty());
			}
			cel1 = new PdfPCell(new Phrase(totalQty));
			cel1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalTable.addCell(cel1);
			cel1 = new PdfPCell(new Phrase(""));
			totalTable.addCell(cel1);

			String totalAmt = null;
			if (twoDForm.format(pdfData.getTotalLineAmt()) != null) {
				totalAmt = twoDForm.format(pdfData.getTotalLineAmt());
			}
			
			cel1 = new PdfPCell(new Phrase(totalAmt));
			cel1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalTable.addCell(cel1);
			totalTable.setHeaderRows(1);
			totalTable.addCell("");
			totalTable.addCell("");
			totalTable.addCell("");
			totalTable.addCell("");
			totalTable.addCell("");
			totalTable.addCell("");
			totalTable.addCell("");
			document.add(totalTable);

			// ------discount table--------
			float[] pointColoumnWidthdiscount = { 20f, 40f, 40f, 40f, 40f, 159f, 104f };
			PdfPTable discountTable = new PdfPTable(pointColoumnWidthdiscount);
			discountTable.setWidthPercentage(100f);
			discountTable.getDefaultCell().setBorder(0);
/*			PdfPCell cell1 = new PdfPCell(new Phrase(""));
			cell1.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell1);
			cell1 = new PdfPCell(new Phrase(""));
			cell1.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell1);
			cell1 = new PdfPCell(new Phrase(""));
			cell1.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell1);
			cell1 = new PdfPCell(new Phrase(""));
			cell1.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell1);
			cell1 = new PdfPCell(new Phrase(""));
			cell1.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell1);
			cell1 = new PdfPCell(new Phrase("Discount"));
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			discountTable.addCell(cell1);

			String discountAmt = null;
			if (twoDForm.format(pdfData.getDiscountAmount()) != null) {
				discountAmt = twoDForm.format(pdfData.getDiscountAmount());
			}
			if (pdfData.getDiscountAmount() != null) {
				cell1 = new PdfPCell(new Phrase(discountAmt));
			} else {
				cell1 = new PdfPCell(new Phrase("0.00"));
			}
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			discountTable.addCell(cell1);
*/			
			discountTable.setHeaderRows(1);
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");

			PdfPCell cell2 = new PdfPCell(new Phrase(""));
			cell2.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell2);
			cell2 = new PdfPCell(new Phrase(""));
			cell2.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell2);
			cell2 = new PdfPCell(new Phrase(""));
			cell2.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell2);
			cell2 = new PdfPCell(new Phrase(""));
			cell2.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell2);
			cell2 = new PdfPCell(new Phrase(""));
			cell2.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell2);
			cell2 = new PdfPCell(new Phrase("Grand Total"));
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			discountTable.addCell(cell2);

			String finalAmt = null;
			if (twoDForm.format(pdfData.getFinalAmount()) != null) {
				finalAmt = twoDForm.format(pdfData.getFinalAmount());
			}
			if (pdfData.getFinalAmount() != null) {
				cell2 = new PdfPCell(new Phrase(finalAmt));
			} else {
				cell2 = new PdfPCell(new Phrase(0));
			}
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			discountTable.addCell(cell2);
			discountTable.setHeaderRows(1);
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");

			PdfPCell cell3 = new PdfPCell(new Phrase(""));
			cell3.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell3);
			cell3 = new PdfPCell(new Phrase(""));
			cell3.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell3);
			cell3 = new PdfPCell(new Phrase(""));
			cell3.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell3);
			cell3 = new PdfPCell(new Phrase(""));
			cell3.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell3);
			cell3 = new PdfPCell(new Phrase(""));
			cell3.setBorder(PdfPCell.NO_BORDER);
			discountTable.addCell(cell3);
			cell3 = new PdfPCell(new Phrase("Invoice Amount", smallBold));
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			discountTable.addCell(cell3);

			String invoiceAmt = null;
			if (twoDForm.format(pdfData.getInvoiceAmount()) != null) {
				invoiceAmt = twoDForm.format(pdfData.getFinalAmount());
			}
			if (pdfData.getInvoiceAmount() != null) {
				cell3 = new PdfPCell(new Phrase(invoiceAmt));
			} else {
				cell3 = new PdfPCell(new Phrase(0));
			}
			cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			discountTable.addCell(cell3);
			discountTable.setHeaderRows(1);
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");
			discountTable.addCell("");

			document.add(discountTable);

			// ----Ammount in words table
			PdfPTable amountTable = new PdfPTable(1);
			amountTable.setWidthPercentage(100f);
			amountTable.getDefaultCell().setBorder(0);
			
			NumberToWord numberToWord = new NumberToWord();
			PdfPCell celAmount = new PdfPCell(new Phrase(numberToWord.numberToWord("INR", pdfData.getFinalAmount())));
			celAmount.setHorizontalAlignment(Element.ALIGN_LEFT);
			//celAmount.setBorder(PdfPCell.NO_BORDER);
			//celAmount.setBorder(PdfPCell.);
			amountTable.addCell(celAmount);
			amountTable.addCell("");
			amountTable.setHeaderRows(1);
			// amountTable.addCell("");

			document.add(amountTable);

			Paragraph disclaimer = new Paragraph("Disclaimer : *Prices are inclusive of taxes.", redFont);
			disclaimer.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(disclaimer);
			 
			// //page no
			//
			// int pageNum = document.getPageNumber();
			// PdfPCell cell.setPhrase(new Phrase(String.format("Page %d of",
			// writer.getPageNumber()), normal));
			document.close();
		} catch ( DocumentException | FileNotFoundException  e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	/*private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}*/

}