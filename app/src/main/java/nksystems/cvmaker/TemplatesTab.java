package nksystems.cvmaker;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nksystems.cvmaker.adapter.ImageAdapter;

public class TemplatesTab extends Fragment {
    AdView mAdview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_templates_tab, container, false);

        MobileAds.initialize(TemplatesTab.this.getContext(),"ca-app-pub-2342189677319514/2967668185");
//        mAdview=(AdView)rootView.findViewById(R.id.homeAdview);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdview.loadAd(adRequest);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        GridView gridView = (GridView) view.findViewById(R.id.gvTemplates);
        gridView.setAdapter(new ImageAdapter(TemplatesTab.this.getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0: generatePdf("Default");
                        break;
                    case 1: generatePdf("NMIMS");
                        break;
                    case 2: generatePdf("Harvard");
                        break;
                }
            }
        });
    }

    public void generatePdf(String type){
        try{
            File myDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+"CVMaker");
            if(!myDirectory.exists()){
                myDirectory.mkdir();
            }
            if(type.equals("Default")){
                File f = new File(myDirectory, "default_sample_template.pdf");
                FileOutputStream fos = new FileOutputStream(f);
                Document pdfDocument = new Document(PageSize.A4);
                PdfWriter.getInstance(pdfDocument,fos);
                pdfDocument.open();
                createDefaultSampleTemplate(pdfDocument);
                fos.close();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(f),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
            else if(type.equals("NMIMS")){
                File f = new File(myDirectory, "nmims_sample_template.pdf");
                FileOutputStream fos = new FileOutputStream(f);
                Document pdfDocument = new Document(PageSize.A4);
                PdfWriter.getInstance(pdfDocument,fos);
                pdfDocument.open();
                createNMIMSSampleTemplate(pdfDocument);
                fos.close();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(f),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
            else if(type.equals("Harvard")){
                File f = new File(myDirectory, "harvard_sample_template.pdf");
                FileOutputStream fos = new FileOutputStream(f);
                Document pdfDocument = new Document(PageSize.A4);
                PdfWriter.getInstance(pdfDocument,fos);
                pdfDocument.open();
                createHarvardSampleTemplate(pdfDocument);
                fos.close();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(f),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
            else{
                Toast.makeText(TemplatesTab.this.getContext(),"Type not equal to Default",Toast.LENGTH_LONG).show();
            }

        }
        catch(IOException e){
            Toast.makeText(TemplatesTab.this.getContext(),"IOException: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        catch (DocumentException de){
            Toast.makeText(TemplatesTab.this.getContext(),"DocumentException: " + de.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void createDefaultSampleTemplate(Document pdfDocument) throws DocumentException{
            Paragraph paraName = new Paragraph("Mr. John Smith",
                    new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            paraName.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraName);

            Paragraph paraEmail = new Paragraph("johnsmith@gmail.com | +1 4013658792",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraEmail.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraEmail);

            Paragraph paraAddress = new Paragraph("2000 Brigadoon drive, 27 Churman drive, City, State 400007",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraAddress.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraAddress);
            pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraObjectiveHead = new Paragraph("OBJECTIVE", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraObjectiveHead);
            LineSeparator lsObjective = new LineSeparator();
            pdfDocument.add(new Chunk(lsObjective));
            Paragraph paraObjective = new Paragraph("To work for an organization which provides me the opportunity to improve " +
                    "my skills and knowledge to growth along with the organization objective.",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraObjective);
            pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraEduHead = new Paragraph("EDUCATION", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraEduHead);
            LineSeparator lsEducation = new LineSeparator();
            pdfDocument.add(new Chunk(lsEducation));

            float[] columnWidths = {2,5,3,2,2};
            PdfPTable eduTable = new PdfPTable(columnWidths);
            eduTable.setWidthPercentage(100);
            eduTable.getDefaultCell().setUseAscender(true);
            eduTable.getDefaultCell().setUseDescender(true);

            PdfPCell eduHeader1 = new PdfPCell(new Phrase("Degree", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader1);

            PdfPCell eduHeader2 = new PdfPCell(new Phrase("Institute", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader2);

            PdfPCell eduHeader3 = new PdfPCell(new Phrase("Board/University", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader3);

            PdfPCell eduHeader4 = new PdfPCell(new Phrase("Year", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader4.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader4);

            PdfPCell eduHeader5 = new PdfPCell(new Phrase("Grade", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader5.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader5);

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase("Singapore International High School",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase("IB",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase("2012",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase("87",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);

        PdfPCell eduTwelfthCell1 = new PdfPCell(new Phrase("10th",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell1);

        PdfPCell eduTwelfthCell2 = new PdfPCell(new Phrase("Bombay Scottish",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell2);

        PdfPCell eduTwelfthCell3 = new PdfPCell(new Phrase("ICSE",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell3);

        PdfPCell eduTwelfthCell4 = new PdfPCell(new Phrase("2010",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell4);

        PdfPCell eduTwelfthCell5 = new PdfPCell(new Phrase("90",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell5);

            pdfDocument.add(eduTable);
        pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraCertHead = new Paragraph("CERTIFICATIONS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraCertHead);
            LineSeparator lsCertifications = new LineSeparator();
            pdfDocument.add(new Chunk(lsCertifications));
            List CertList = new List(12);
            CertList.setListSymbol("\u2022");
            ListItem CertListItem1 = new ListItem("Oracle certified Java OCPJP 6" + ", " +
                    "2013",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            CertList.add(CertListItem1);

            pdfDocument.add(CertList);

        pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraExpHead = new Paragraph("WORK EXPERIENCE", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraExpHead);
            LineSeparator lsWorkExp = new LineSeparator();
            pdfDocument.add(new Chunk(lsWorkExp));

            float[] columnWidthsCom = {5, 5};
            PdfPTable workTable = new PdfPTable(columnWidthsCom);
            workTable.setWidthPercentage(100);
            workTable.getDefaultCell().setUseAscender(true);
            workTable.getDefaultCell().setUseDescender(true);

            PdfPCell workHeader1 = new PdfPCell(new Phrase("Company", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader1);
            PdfPCell workHeader2 = new PdfPCell(new Phrase("Description", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader2);

            PdfPCell workCell1 = new PdfPCell();
            workCell1.addElement(new Phrase("Accenture",new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
            workCell1.addElement(new Phrase("Intern Developer",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.addElement(new Phrase("June 2014 - July 2014",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase("Web Development",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            pdfDocument.add(workTable);

        pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraAchieveHead = new Paragraph("ACHIEVEMENTS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraAchieveHead);
            LineSeparator lsAchievements = new LineSeparator();
            pdfDocument.add(new Chunk(lsAchievements));
            List AchList = new List(12);
            AchList.setListSymbol("\u2022");
            ListItem AchListItem1 = new ListItem("Team lead for social service trip.",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            AchList.add(AchListItem1);

            pdfDocument.add(AchList);

        pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraTSHead = new Paragraph("TECHNICAL SKILLS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraTSHead);
            LineSeparator lsTSkills = new LineSeparator();
            pdfDocument.add(new Chunk(lsTSkills));

            float[] columnWidthsSkill = {3, 5};
            PdfPTable skillsTable = new PdfPTable(columnWidthsSkill);
            skillsTable.setWidthPercentage(100);
            skillsTable.getDefaultCell().setUseAscender(true);
            skillsTable.getDefaultCell().setUseDescender(true);

            PdfPCell skillAreaCell1 = new PdfPCell(new Phrase("Languages",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaCell1);

            PdfPCell skillSetCell1 = new PdfPCell(new Phrase("Java, C#",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            skillSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillSetCell1);

        PdfPCell skillAreaCell = new PdfPCell(new Phrase("Softwares",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        skillAreaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        skillsTable.addCell(skillAreaCell);

        PdfPCell skillSetCell = new PdfPCell(new Phrase("Unity3D, Microsoft Office",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        skillSetCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        skillsTable.addCell(skillSetCell);

            pdfDocument.add(skillsTable);
        pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraAct = new Paragraph("EXTRA CURRICULAR ACTIVITIES", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraAct);
            LineSeparator lsActivities = new LineSeparator();
            pdfDocument.add(new Chunk(lsActivities));
            List ECList = new List(12);
            ECList.setListSymbol("\u2022");
            ListItem ECListItem1 = new ListItem("IEEE Marketing Head",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            ECList.add(ECListItem1);

            pdfDocument.add(ECList);

        pdfDocument.add(Chunk.NEWLINE);

            Paragraph paraPI = new Paragraph("PERSONAL INTERESTS", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
            pdfDocument.add(paraPI);
            LineSeparator lsPI = new LineSeparator();
            pdfDocument.add(new Chunk(lsPI));
            Paragraph paraInterests = new Paragraph("Football, Music, Reading",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraInterests);

        pdfDocument.close();
    }

    private void createNMIMSSampleTemplate(Document pdfDocument) throws DocumentException{
        float[] columnWidths = {1, 5, 5};

        Paragraph paraName = new Paragraph("Mr. John Smith",
                new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
        paraName.setAlignment(Element.ALIGN_CENTER);
        pdfDocument.add(paraName);

        Paragraph paraEmail = new Paragraph("johnsmith@gmail.com | +1 4013658792",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
        paraEmail.setAlignment(Element.ALIGN_CENTER);
        pdfDocument.add(paraEmail);

        Paragraph paraAddress = new Paragraph("2000 Brigadoon drive, 27 Churman drive, City, State 400007",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
        paraAddress.setAlignment(Element.ALIGN_CENTER);
        pdfDocument.add(paraAddress);
        pdfDocument.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
        PdfPCell cell = new PdfPCell(new Phrase("OBJECTIVE", font));
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        table.addCell(cell);
        pdfDocument.add(table);
        Paragraph paraObjective = new Paragraph("To work for an organization which provides me the opportunity to improve " +
                "my skills and knowledge to growth along with the organization objective.",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
        pdfDocument.add(paraObjective);
        pdfDocument.add(new Paragraph(" "));

        PdfPTable tableEdu = new PdfPTable(columnWidths);
        tableEdu.setWidthPercentage(100);
        tableEdu.getDefaultCell().setUseAscender(true);
        tableEdu.getDefaultCell().setUseDescender(true);
        Font fontEdu = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
        PdfPCell cellEdu = new PdfPCell(new Phrase("EDUCATION", fontEdu));
        cellEdu.setBackgroundColor(BaseColor.GRAY);
        cellEdu.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellEdu.setColspan(3);
        tableEdu.addCell(cell);
        pdfDocument.add(tableEdu);

        float[] columnEduWidths = {2,5,3,2,2};
        PdfPTable eduTable = new PdfPTable(columnEduWidths);
        eduTable.setWidthPercentage(100);
        eduTable.getDefaultCell().setUseAscender(true);
        eduTable.getDefaultCell().setUseDescender(true);

        PdfPCell eduHeader1 = new PdfPCell(new Phrase("Degree", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        eduHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduHeader1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        eduTable.addCell(eduHeader1);

        PdfPCell eduHeader2 = new PdfPCell(new Phrase("Institute", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        eduHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduHeader2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        eduTable.addCell(eduHeader2);

        PdfPCell eduHeader3 = new PdfPCell(new Phrase("Board/University", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        eduHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduHeader3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        eduTable.addCell(eduHeader3);

        PdfPCell eduHeader4 = new PdfPCell(new Phrase("Year", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        eduHeader4.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduHeader4.setBackgroundColor(BaseColor.LIGHT_GRAY);
        eduTable.addCell(eduHeader4);

        PdfPCell eduHeader5 = new PdfPCell(new Phrase("Grade", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        eduHeader5.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduHeader5.setBackgroundColor(BaseColor.LIGHT_GRAY);
        eduTable.addCell(eduHeader5);

        PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTenthCell1);

        PdfPCell eduTenthCell2 = new PdfPCell(new Phrase("Singapore International High School",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTenthCell2);

        PdfPCell eduTenthCell3 = new PdfPCell(new Phrase("IB",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTenthCell3);

        PdfPCell eduTenthCell4 = new PdfPCell(new Phrase("2012",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTenthCell4);

        PdfPCell eduTenthCell5 = new PdfPCell(new Phrase("87",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTenthCell5);

        PdfPCell eduTwelfthCell1 = new PdfPCell(new Phrase("10th",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell1);

        PdfPCell eduTwelfthCell2 = new PdfPCell(new Phrase("Bombay Scottish",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell2);

        PdfPCell eduTwelfthCell3 = new PdfPCell(new Phrase("ICSE",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell3);

        PdfPCell eduTwelfthCell4 = new PdfPCell(new Phrase("2010",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell4);

        PdfPCell eduTwelfthCell5 = new PdfPCell(new Phrase("90",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell5);

        pdfDocument.add(eduTable);

        pdfDocument.add(new Paragraph(" "));

        PdfPTable tableCert = new PdfPTable(columnWidths);
        tableCert.setWidthPercentage(100);
        tableCert.getDefaultCell().setUseAscender(true);
        tableCert.getDefaultCell().setUseDescender(true);
        Font fontCert = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
        PdfPCell cellCert = new PdfPCell(new Phrase("CERTIFICATIONS", fontCert));
        cellCert.setBackgroundColor(BaseColor.GRAY);
        cellCert.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellCert.setColspan(3);
        tableCert.addCell(cellCert);
        pdfDocument.add(tableCert);

        List CertList = new List(12);
        CertList.setListSymbol("\u2022");
        ListItem CertListItem1 = new ListItem("Oracle certified Java OCPJP 6" + ", " +
                "2013",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
        CertList.add(CertListItem1);

        pdfDocument.add(CertList);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableCom = new PdfPTable(columnWidths);
        tableCom.setWidthPercentage(100);
        tableCom.getDefaultCell().setUseAscender(true);
        tableCom.getDefaultCell().setUseDescender(true);
            Font fontCom = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cellCom = new PdfPCell(new Phrase("WORK EXPERIENCE", fontCom));
        cellCom.setBackgroundColor(BaseColor.GRAY);
        cellCom.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellCom.setColspan(3);
        tableCom.addCell(cellCom);
            pdfDocument.add(tableCom);

            float[] columnWidthsWork = {5, 5};
            PdfPTable workTable = new PdfPTable(columnWidthsWork);
            workTable.setWidthPercentage(100);
            workTable.getDefaultCell().setUseAscender(true);
            workTable.getDefaultCell().setUseDescender(true);

            PdfPCell workHeader1 = new PdfPCell(new Phrase("Company", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workHeader1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            workTable.addCell(workHeader1);
            PdfPCell workHeader2 = new PdfPCell(new Phrase("Description", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workHeader2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            workTable.addCell(workHeader2);

            PdfPCell workCell1 = new PdfPCell();
            workCell1.addElement(new Phrase("Accenture",new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
            workCell1.addElement(new Phrase("Intern Developer",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.addElement(new Phrase("June 2014 - July 2014",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase("Web development",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            pdfDocument.add(workTable);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableAch = new PdfPTable(columnWidths);
        tableAch.setWidthPercentage(100);
        tableAch.getDefaultCell().setUseAscender(true);
        tableAch.getDefaultCell().setUseDescender(true);
            Font fontAch = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cellAch = new PdfPCell(new Phrase("ACHIEVEMENTS", fontAch));
        cellAch.setBackgroundColor(BaseColor.GRAY);
        cellAch.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAch.setColspan(3);
        tableAch.addCell(cellAch);
            pdfDocument.add(tableAch);

            List AchList = new List(12);
            AchList.setListSymbol("\u2022");
            ListItem AchListItem1 = new ListItem("Team lead for social service trip.",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            AchList.add(AchListItem1);

            pdfDocument.add(AchList);
        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableSkill = new PdfPTable(columnWidths);
        tableSkill.setWidthPercentage(100);
        tableSkill.getDefaultCell().setUseAscender(true);
        tableSkill.getDefaultCell().setUseDescender(true);
            Font fontSkill = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cellSkill = new PdfPCell(new Phrase("TECHNICAL SKILLS", fontSkill));
        cellSkill.setBackgroundColor(BaseColor.GRAY);
        cellSkill.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellSkill.setColspan(3);
        tableSkill.addCell(cellSkill);
            pdfDocument.add(tableSkill);

            float[] columnWidthsSkills = {3, 5};
            PdfPTable skillsTable = new PdfPTable(columnWidthsSkills);
            skillsTable.setWidthPercentage(100);
            skillsTable.getDefaultCell().setUseAscender(true);
            skillsTable.getDefaultCell().setUseDescender(true);

            PdfPCell skillAreaCell1 = new PdfPCell(new Phrase("Languages",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaCell1);

            PdfPCell skillSetCell1 = new PdfPCell(new Phrase("Java, C#",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            skillSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillSetCell1);

        PdfPCell skillAreaCell2 = new PdfPCell(new Phrase("Softwares",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        skillAreaCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        skillsTable.addCell(skillAreaCell2);

        PdfPCell skillSetCell2 = new PdfPCell(new Phrase("Unity3D, Microsoft Office",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        skillSetCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        skillsTable.addCell(skillSetCell2);

            pdfDocument.add(skillsTable);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableEC = new PdfPTable(columnWidths);
        tableEC.setWidthPercentage(100);
        tableEC.getDefaultCell().setUseAscender(true);
        tableEC.getDefaultCell().setUseDescender(true);
            Font fontEC = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cellEC = new PdfPCell(new Phrase("EXTRA CURRICULAR ACTIVITIES", fontEC));
        cellEC.setBackgroundColor(BaseColor.GRAY);
        cellEC.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellEC.setColspan(3);
        tableEC.addCell(cellEC);
            pdfDocument.add(tableEC);

            List ECList = new List(12);
            ECList.setListSymbol("\u2022");
            ListItem ECListItem1 = new ListItem("IEEE Marketing Head",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            ECList.add(ECListItem1);

            pdfDocument.add(ECList);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tablePI = new PdfPTable(columnWidths);
        tablePI.setWidthPercentage(100);
        tablePI.getDefaultCell().setUseAscender(true);
        tablePI.getDefaultCell().setUseDescender(true);
            Font fontPI = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, GrayColor.GRAYBLACK);
            PdfPCell cellPI = new PdfPCell(new Phrase("PERSONAL INTERESTS", fontPI));
        cellPI.setBackgroundColor(BaseColor.GRAY);
        cellPI.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellPI.setColspan(3);
        tablePI.addCell(cellPI);
            pdfDocument.add(tablePI);
            Paragraph paraInterests = new Paragraph("Football, Music, Reading",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            pdfDocument.add(paraInterests);

        pdfDocument.close();
    }

    private void createHarvardSampleTemplate(Document pdfDocument) throws DocumentException{
            Paragraph paraName = new Paragraph("Mr. John Smith",
                    new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            paraName.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraName);

            Paragraph paraAddress = new Paragraph("2000 Brigadoon drive, 27 Churman drive, City, State 400007",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraAddress.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraAddress);

            Paragraph paraContact = new Paragraph("+1 4013658792", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraContact.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraContact);

            Paragraph paraEmail = new Paragraph("johnsmith@gmail.com ",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            paraEmail.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(paraEmail);

        pdfDocument.add(new Paragraph(" "));

            Paragraph paraObjective = new Paragraph("To work for an organization which provides me the opportunity to improve " +
                    "my skills and knowledge to growth along with the organization objective.",
                    new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL));
            pdfDocument.add(paraObjective);

        pdfDocument.add(new Paragraph(" "));
        float[] columnWidths = {3, 7};
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);

            PdfPTable tableEdu = new PdfPTable(columnWidths);
            tableEdu.setWidthPercentage(100);
            tableEdu.getDefaultCell().setUseAscender(true);
            tableEdu.getDefaultCell().setUseDescender(true);

            PdfPCell headercellEdu = new PdfPCell(new Phrase("EDUCATION", headerFont));
            headercellEdu.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellEdu.setPaddingRight(8.0f);
            headercellEdu.setBorder(Rectangle.NO_BORDER);
            tableEdu.addCell(headercellEdu);

            float[] columnEduWidths = {2,5,3,2,2};
            PdfPTable eduTable = new PdfPTable(columnEduWidths);
            eduTable.setWidthPercentage(100);
            eduTable.getDefaultCell().setUseAscender(true);
            eduTable.getDefaultCell().setUseDescender(true);

            PdfPCell eduHeader1 = new PdfPCell(new Phrase("Degree", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader1);

            PdfPCell eduHeader2 = new PdfPCell(new Phrase("Institute", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader2);

            PdfPCell eduHeader3 = new PdfPCell(new Phrase("Board/University", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader3);

            PdfPCell eduHeader4 = new PdfPCell(new Phrase("Year", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader4.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader4);

            PdfPCell eduHeader5 = new PdfPCell(new Phrase("Grade", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            eduHeader5.setHorizontalAlignment(Element.ALIGN_CENTER);
            eduTable.addCell(eduHeader5);

                PdfPCell eduTenthCell1 = new PdfPCell(new Phrase("12th",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell1);

                PdfPCell eduTenthCell2 = new PdfPCell(new Phrase("Singapore International High School",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell2);

                PdfPCell eduTenthCell3 = new PdfPCell(new Phrase("IB",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell3);

                PdfPCell eduTenthCell4 = new PdfPCell(new Phrase("2012",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell4);

                PdfPCell eduTenthCell5 = new PdfPCell(new Phrase("87",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                eduTenthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                eduTable.addCell(eduTenthCell5);

        PdfPCell eduTwelfthCell1 = new PdfPCell(new Phrase("10th",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell1);

        PdfPCell eduTwelfthCell2 = new PdfPCell(new Phrase("Bombay Scottish",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell2);

        PdfPCell eduTwelfthCell3 = new PdfPCell(new Phrase("ICSE",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell3);

        PdfPCell eduTwelfthCell4 = new PdfPCell(new Phrase("2010",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell4);

        PdfPCell eduTwelfthCell5 = new PdfPCell(new Phrase("90",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        eduTwelfthCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        eduTable.addCell(eduTwelfthCell5);

            PdfPCell contentcellEdu = new PdfPCell(eduTable);
            contentcellEdu.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellEdu.setPaddingLeft(8.0f);
            contentcellEdu.setBorder(Rectangle.NO_BORDER);
            tableEdu.addCell(contentcellEdu);
            pdfDocument.add(tableEdu);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);

            PdfPCell headercellCert = new PdfPCell(new Phrase("CERTIFICATIONS", headerFont));
        headercellCert.setHorizontalAlignment(Element.ALIGN_LEFT);
        headercellCert.setPaddingRight(8.0f);
        headercellCert.setBorder(Rectangle.NO_BORDER);
            table.addCell(headercellCert);

            List CertList = new List(12);
            CertList.setListSymbol("\u2022");
            ListItem CertListItem1 = new ListItem("Oracle certified Java OCPJP 6" + ", " +
                    "2013",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            CertList.add(CertListItem1);

            PdfPCell contentcellCert = new PdfPCell();
        contentcellCert.addElement(CertList);
        contentcellCert.setHorizontalAlignment(Element.ALIGN_LEFT);
        contentcellCert.setPaddingLeft(8.0f);
        contentcellCert.setBorder(Rectangle.NO_BORDER);
            table.addCell(contentcellCert);
            pdfDocument.add(table);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableCom = new PdfPTable(columnWidths);
        tableCom.setWidthPercentage(100);
        tableCom.getDefaultCell().setUseAscender(true);
        tableCom.getDefaultCell().setUseDescender(true);

            PdfPCell headercellExp = new PdfPCell(new Phrase("PROFESSIONAL EXPERIENCE", headerFont));
            headercellExp.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellExp.setPaddingRight(8.0f);
            headercellExp.setBorder(Rectangle.NO_BORDER);
        tableCom.addCell(headercellExp);

            float[] columnWidthsWork = {5, 5};
            PdfPTable workTable = new PdfPTable(columnWidthsWork);
            workTable.setWidthPercentage(100);
            workTable.getDefaultCell().setUseAscender(true);
            workTable.getDefaultCell().setUseDescender(true);

            PdfPCell workHeader1 = new PdfPCell(new Phrase("Company", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader1);
            PdfPCell workHeader2 = new PdfPCell(new Phrase("Description", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD)));
            workHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workHeader2);

            PdfPCell workCell1 = new PdfPCell();
            workCell1.addElement(new Phrase("Accenture",new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL)));
            workCell1.addElement(new Phrase("Intern Developer",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.addElement(new Phrase("June 2014 - July 2014",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell1);

            PdfPCell workCell2 = new PdfPCell(new Phrase("Web development",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            workCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            workTable.addCell(workCell2);

            //PdfPCell contentcellExp = new PdfPCell(new Phrase("Add your professional experience here.", contentFont));
            PdfPCell contentcellExp = new PdfPCell(workTable);
            contentcellExp.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellExp.setPaddingLeft(8.0f);
            contentcellExp.setBorder(Rectangle.NO_BORDER);
        tableCom.addCell(contentcellExp);
            pdfDocument.add(tableCom);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableAchieve = new PdfPTable(columnWidths);
            tableAchieve.setWidthPercentage(100);
            tableAchieve.getDefaultCell().setUseAscender(true);
            tableAchieve.getDefaultCell().setUseDescender(true);

            PdfPCell headercellAchieve = new PdfPCell(new Phrase("ACHIEVEMENTS", headerFont));
            headercellAchieve.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellAchieve.setPaddingRight(8.0f);
            headercellAchieve.setBorder(Rectangle.NO_BORDER);
            tableAchieve.addCell(headercellAchieve);

            List AchList = new List(12);
            AchList.setListSymbol("\u2022");
            ListItem AchListItem1 = new ListItem("Team lead for social service trip.",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            AchList.add(AchListItem1);

            PdfPCell contentcellAchieve = new PdfPCell();
            contentcellAchieve.addElement(AchList);
            contentcellAchieve.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellAchieve.setPaddingLeft(8.0f);
            contentcellAchieve.setBorder(Rectangle.NO_BORDER);
            tableAchieve.addCell(contentcellAchieve);
            pdfDocument.add(tableAchieve);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableSkill = new PdfPTable(columnWidths);
            tableSkill.setWidthPercentage(100);
            tableSkill.getDefaultCell().setUseAscender(true);
            tableSkill.getDefaultCell().setUseDescender(true);

            PdfPCell headercellSkill = new PdfPCell(new Phrase("TECHNICAL SKILLS", headerFont));
            headercellSkill.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellSkill.setPaddingRight(8.0f);
            headercellSkill.setBorder(Rectangle.NO_BORDER);
            tableSkill.addCell(headercellSkill);

            float[] columnWidthsSkills = {3, 5};
            PdfPTable skillsTable = new PdfPTable(columnWidthsSkills);
            skillsTable.setWidthPercentage(100);
            skillsTable.getDefaultCell().setUseAscender(true);
            skillsTable.getDefaultCell().setUseDescender(true);

            PdfPCell skillAreaCell1 = new PdfPCell(new Phrase("Languages",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            skillAreaCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillAreaCell1);

            PdfPCell skillSetCell1 = new PdfPCell(new Phrase("Java,C#",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            skillSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            skillsTable.addCell(skillSetCell1);

        PdfPCell skillAreaCell2 = new PdfPCell(new Phrase("Softwares",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        skillAreaCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        skillsTable.addCell(skillAreaCell2);

        PdfPCell skillSetCell2 = new PdfPCell(new Phrase("Unity3D, Microsoft Office",
                new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
        skillSetCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        skillsTable.addCell(skillSetCell2);

            PdfPCell contentcellSkill = new PdfPCell(skillsTable);
            contentcellSkill.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellSkill.setPaddingLeft(8.0f);
            contentcellSkill.setBorder(Rectangle.NO_BORDER);
            tableSkill.addCell(contentcellSkill);
            pdfDocument.add(tableSkill);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tableAct = new PdfPTable(columnWidths);
            tableAct.setWidthPercentage(100);
            tableAct.getDefaultCell().setUseAscender(true);
            tableAct.getDefaultCell().setUseDescender(true);

            PdfPCell headercellAct = new PdfPCell(new Phrase("EXTRA CURRICULAR ACTIVITIES", headerFont));
            headercellAct.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellAct.setPaddingRight(8.0f);
            headercellAct.setBorder(Rectangle.NO_BORDER);
            tableAct.addCell(headercellAct);

            List ECList = new List(12);
            ECList.setListSymbol("\u2022");
            ListItem ECListItem1 = new ListItem("IEEE Marketing Head",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            ECList.add(ECListItem1);

            PdfPCell contentcellAct = new PdfPCell(new Phrase("Attended 10 Annual Karate Training Camps of All India Gojukai Karate-Do (I.K.G.A), held in Pune and was awarded the Indian Black Belt", contentFont));
            contentcellAct.addElement(ECList);
            contentcellAct.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellAct.setPaddingLeft(8.0f);
            contentcellAct.setBorder(Rectangle.NO_BORDER);
            tableAct.addCell(contentcellAct);
            pdfDocument.add(tableAct);

        pdfDocument.add(new Paragraph(" "));

            PdfPTable tablePI = new PdfPTable(columnWidths);
            tablePI.setWidthPercentage(100);
            tablePI.getDefaultCell().setUseAscender(true);
            tablePI.getDefaultCell().setUseDescender(true);

            PdfPCell headercellPI = new PdfPCell(new Phrase("PERSONAL INTERESTS", headerFont));
            headercellPI.setHorizontalAlignment(Element.ALIGN_LEFT);
            headercellPI.setPaddingRight(8.0f);
            headercellPI.setBorder(Rectangle.NO_BORDER);
            tablePI.addCell(headercellPI);

            PdfPCell contentcellPI = new PdfPCell(new Phrase("Football, Music, Reading", contentFont));
            contentcellPI.setHorizontalAlignment(Element.ALIGN_LEFT);
            contentcellPI.setPaddingLeft(8.0f);
            contentcellPI.setBorder(Rectangle.NO_BORDER);
            tablePI.addCell(contentcellPI);
            pdfDocument.add(tablePI);

        pdfDocument.close();
    }

}
