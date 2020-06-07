package com.pdf.generation.com.pdf.itextpdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.StringTokenizer;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.InsetBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

public class App {
	public static void main(String[] args) throws IOException {

		File target = new File("/Users/x236657/Downloads/smaple.pdf");
		PdfWriter writer = new PdfWriter(target);
		String para1 = "Tutorials Point originated from the idea that there exists"
				+ "a class of readers who respond better to online content and prefer to learn "
				+ " new skills at their own pace from the comforts of their drawing rooms.";

		String para2 = "The journey commenced with a single tutorial on HTML in 2006 "
				+ "and elated by the response it generated, we worked our way to adding fresh "
				+ "  tutorials to our repository which now proudly flaunts a wealth of tutorials "
				+ " and allied articles on topics ranging from programming languages to web designing "
				+ "to academics and much more.";
		String p3 = para1 + para2;
		p3 += p3 + p3;
		Paragraph p1 = new Paragraph(p3);
		PdfDocument document = new PdfDocument(writer);
		
		
		document.setDefaultPageSize(PageSize.A2.rotate());
		
		
		List map = new List();
		for (int i = 0; i < 12; i++) {
			map.add(Integer.toString(i));
		}
		float f[] = new float[18];
		Arrays.fill(f, 1F);
		Table table = new Table(f);
		Reader read = new FileReader("/Users/x236657/Downloads/FL_insurance_sample.csv");
		BufferedReader reader = new BufferedReader(read);
		String line = reader.readLine();
		process(table, line, true);
		int i = 0;
		while ((line = reader.readLine()) != null && i < 43) {

			process(table, line, false);
			i++;
		}
		read.close();

		String imgPath = "/Users/x236657/Downloads/logo.png";
		ImageData imageData = ImageDataFactory.create(imgPath);
		Image image = new Image(imageData);
		// image.scaleAbsolute(image.getImageWidth() + 300, image.getImageHeight() +
		// 100);
		// image.setFixedPosition(800, 900);

		Border border = new InsetBorder((DeviceRgb) DeviceRgb.WHITE, 0);
		float f2[] = new float[2];
		Arrays.fill(f, 1F);
		Table first = new Table(f2);
		Cell cell = new Cell(0, 0);
		first.addCell(cell.add(p1).setBorder(border));
		cell = new Cell(0, 0);
		first.addCell(cell.add(image).setBorder(border));
		
		//creating a interactive pdf form
		
		
		Document doc = new Document(document);
		PdfAcroForm from= PdfAcroForm.getAcroForm(document, true);
		//added TextFiled
		PdfTextFormField field=PdfTextFormField.createText(doc.getPdfDocument(),new Rectangle(300,753,425,15),"name","");
		from.addField(field);
		//adding Radiobuttons
		
		PdfButtonFormField buttonFormField=PdfFormField.createRadioGroup(doc.getPdfDocument(), "language", "");
		PdfFormField.createRadioButton(document, new Rectangle(430, 728, 15, 15), buttonFormField, "English");
		PdfFormField.createRadioButton(document, new Rectangle(480, 728, 15, 15), buttonFormField, "Hindi");
		PdfFormField.createRadioButton(document, new Rectangle(520, 728, 15, 15), buttonFormField, "Punjabi");
		PdfFormField.createRadioButton(document, new Rectangle(570, 728, 15, 15), buttonFormField, "French");
		PdfFormField.createRadioButton(document, new Rectangle(620, 728, 15, 15), buttonFormField, "Spanish");
		
		from.addField(buttonFormField);
		// doc.add(image);
//		table.setPaddingRight(10);
//		table.setPaddingLeft(10);

		doc.add(first);
		table.setAutoLayout();
		
		doc.add(table);
		doc.close();
		System.out.println("Done");

	}

	private static void process(Table table, String line, boolean b) {
		StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
		while (stringTokenizer.hasMoreTokens()) {
			if (b) {
				table.addHeaderCell(new Cell().add(new Paragraph(stringTokenizer.nextToken()))
						.addStyle(new Style().setBold()).setPaddingLeft(10).setPaddingRight(10));
			} else {
				table.addCell(new Cell().add(new Paragraph(stringTokenizer.nextToken())))
						.setTextAlignment(TextAlignment.CENTER);
			}
		}
	}
}
