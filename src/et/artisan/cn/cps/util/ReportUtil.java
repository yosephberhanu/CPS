package et.artisan.cn.cps.util;

import et.artisan.cn.cps.dto.*;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import et.artisan.cn.cps.entity.*;
import java.sql.Date;
import java.util.*;
import javax.servlet.http.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Yoseph Berhanu<yoseph@bayeth.com>
 * @since 1.0
 * @version 1.0
 */
public class ReportUtil {

   public void generateReport(HttpServletRequest request, String fileName, Document document) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setWrapText(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet("Payments for " + document.getProject().getCode() + " - " + document.getInComingDocumentNo() + " - " + document.getDocumentYear());
                tempSheet.setFitToPage(true);
                PrintSetup ps = tempSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("Payments in " + document.getProject().getCode() + " - " + document.getInComingDocumentNo() + " - " + document.getDocumentYear());
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Lot No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("Amount"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString("Restricted"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Status"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("remark"));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);

                
                PaymentQueryParameter parameter = new PaymentQueryParameter();
                parameter.setDocumentId(document.getId());
                ArrayList<Payment> payments = CommonStorage.getRepository().getAllPayments(parameter);
                double totalAmount = 0;
                int i = 0;
                for (; i < payments.size(); i++) {
                    totalAmount += payments.get(i).getAmount();
                    Row tempRow = tempSheet.createRow((short) i + 2);

                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + payments.get(i).getLotNo()));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payments.get(i).getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payments.get(i).getAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payments.get(i).isRestricted() ? "Yes" : "No");
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(payments.get(i).getStatus());
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payments.get(i).getRemark());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.setHeight((short) 600);
                }

                Row totalRow = tempSheet.createRow((short) i + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(totalAmount);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(3).setCellValue("");
                totalRow.getCell(3).setCellStyle(bodyStyle);
                totalRow.createCell(4).setCellValue("");
                totalRow.getCell(4).setCellStyle(bodyStyle);
                totalRow.createCell(5).setCellValue("");
                totalRow.getCell(5).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 300);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, ClientRegion clientRegion, Date from, Date to) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);
        title2Style.setWrapText(true);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(clientRegion.getRegionName());
                tempSheet.setFitToPage(true);
                PrintSetup tempPS = tempSheet.getPrintSetup();
                tempPS.setFitWidth( (short) 1);
                tempPS.setFitHeight( (short) 0);
                
                Sheet enSheet = wb.createSheet("EN");
                enSheet.setFitToPage(true);
                PrintSetup ps1 = enSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                Sheet amSheet = wb.createSheet("AM");
                amSheet.setFitToPage(true);
                PrintSetup ps2 = amSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + clientRegion.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY OF " + clientRegion.getRegionName().toUpperCase() + " REGION (From " + from + " To " + to + ")");
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(clientRegion.getRegionName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(clientRegion.getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(clientRegion, from, to);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);
                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                ////totalRow.createCell(3).setCellValue(totalClientAmount);
                ////totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);
//
//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);

                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + clientRegion.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY OF " + clientRegion.getRegionName().toUpperCase() + " REGION FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT (From " + from + " To " + to + ")");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);
//                    totalPaidCount = 0;
//                    //totalClientAmount = 0;
                    totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(clientRegion, from, to);
                Sheet detailsSheet = wb.createSheet("Details");
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }
//--------
    public void generateReport(HttpServletRequest request, User approver, String fileName, Client client, Date from, Date to) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);
        title2Style.setWrapText(true);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(client.getName());
                tempSheet.setFitToPage(true);
                PrintSetup tempPS = tempSheet.getPrintSetup();
                tempPS.setFitWidth( (short) 1);
                tempPS.setFitHeight( (short) 0);
                
                Sheet enSheet = wb.createSheet("EN");
                enSheet.setFitToPage(true);
                PrintSetup ps1 = enSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                Sheet amSheet = wb.createSheet("AM");
                amSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY (From " + from + " To " + to + ")");
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(client.getName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(client.getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(client, from, to);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);
                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                //totalRow.createCell(3).setCellValue(totalClientAmount);
                //totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT (From " + from + " To " + to + ")");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);
                    //totalPaidCount = 0;
                    //totalClientAmount = 0;
                    totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(client, from, to);
                Sheet detailsSheet = wb.createSheet("Details");
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, Client client, Date from, Date to, boolean branchBased) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);
        title2Style.setWrapText(true);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(client.getName());
                Sheet enSheet = wb.createSheet("EN");
                Sheet amSheet = wb.createSheet("AM");
                
                tempSheet.setFitToPage(true);
                PrintSetup ps1 = tempSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                enSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                amSheet.setFitToPage(true);
                PrintSetup ps3 = amSheet.getPrintSetup();
                ps3.setFitWidth( (short) 1);
                ps3.setFitHeight( (short) 0);
                

                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY for All Branches (From " + from + " To " + to + ")");
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(client.getName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(client.getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(client, from, to, branchBased);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);
                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                //totalRow.createCell(3).setCellValue(totalClientAmount);
                //totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT (From " + from + " To " + to + ")");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);
//                    totalPaidCount = 0;
//                    //totalClientAmount = 0;
                    //totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(client, from, to, branchBased);
                Sheet detailsSheet = wb.createSheet("Details");
                
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, Claim claim) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(claim.getClaimNumber());
                Sheet enSheet = wb.createSheet("EN");
                Sheet amSheet = wb.createSheet("AM");
                
                tempSheet.setFitToPage(true);
                PrintSetup ps1 = tempSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                enSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                amSheet.setFitToPage(true);
                PrintSetup ps3 = amSheet.getPrintSetup();
                ps3.setFitWidth( (short) 1);
                ps3.setFitHeight( (short) 0);
                

                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + claim.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY REGION FOR" + claim.getClaimNumber().toUpperCase());
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(claim.getClient().getName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(claim.getClient().getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(claim.getClient().getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(claim);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);
                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                //totalRow.createCell(3).setCellValue(totalClientAmount);
                //totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + claim.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT ");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(claim.getClient().getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);
//                    totalPaidCount = 0;
//                    //totalClientAmount = 0;
                    //totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(claim);
                Sheet detailsSheet = wb.createSheet("Details");
                
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, Client client, String claimCNNumber) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);
        title2Style.setWrapText(true);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
        bodyStyle.setWrapText(true);
        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);
        footerStyle.setWrapText(true);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(claimCNNumber.replace("/", "-"));
                Sheet enSheet = wb.createSheet("EN");
                Sheet amSheet = wb.createSheet("AM");

                tempSheet.setFitToPage(true);
                PrintSetup ps1 = tempSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                enSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                amSheet.setFitToPage(true);
                PrintSetup ps3 = amSheet.getPrintSetup();
                ps3.setFitWidth( (short) 1);
                ps3.setFitHeight( (short) 0);
                

                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY FOR " + claimCNNumber.toUpperCase());
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(client.getName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.createCell(3).setCellValue("Remark");

                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(client.getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                amrow1.setHeight((short) 1100);
                enrow1.setHeight((short) 1100);

                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.getCell(3).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Branch Name"));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Project Name "));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);
                row1.setHeight((short) 1100);

                tempSheet.setColumnWidth(1, 5000);
                tempSheet.setColumnWidth(3, 1700);
                tempSheet.setColumnWidth(3, 2700);
                tempSheet.setColumnWidth(4, 2700);
                tempSheet.setColumnWidth(5, 2700);
                tempSheet.setColumnWidth(6, 2700);
                tempSheet.setColumnWidth(7, 2700);
                tempSheet.setColumnWidth(8, 2700);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(claimCNNumber);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);

                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);
                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                //totalRow.createCell(3).setCellValue(totalClientAmount);
                //totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT (" + claimCNNumber.toUpperCase() + ")");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Branch Name"));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Project Name "));

                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);

//                    totalPaidCount = 0;
//                    //totalClientAmount = 0;
                    //totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);

                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);

                    projectSheet.setColumnWidth(1, 5000);
                    projectSheet.setColumnWidth(3, 1700);
                    projectSheet.setColumnWidth(3, 2700);
                    projectSheet.setColumnWidth(4, 2700);
                    projectSheet.setColumnWidth(5, 2700);
                    projectSheet.setColumnWidth(6, 2700);
                    projectSheet.setColumnWidth(7, 2700);
                    projectSheet.setColumnWidth(8, 2700);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(claimCNNumber);
                Sheet detailsSheet = wb.createSheet("Details");
                
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, ClientRegion clientRegion, String claimCNNumber) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(claimCNNumber.replace("/", "-"));
                Sheet enSheet = wb.createSheet("EN");
                Sheet amSheet = wb.createSheet("AM");

                tempSheet.setFitToPage(true);
                PrintSetup ps1 = tempSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                enSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                amSheet.setFitToPage(true);
                PrintSetup ps3 = amSheet.getPrintSetup();
                ps3.setFitWidth( (short) 1);
                ps3.setFitHeight( (short) 0);
                
                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + clientRegion.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY FOR " + claimCNNumber.toUpperCase() + " ON REGION " + clientRegion.getRegionName().toUpperCase());
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(clientRegion.getClient().getName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);
                enrow1.createCell(3).setCellValue("Remark");

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(clientRegion.getClient().getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(claimCNNumber, clientRegion);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);

                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                //totalRow.createCell(3).setCellValue(totalClientAmount);
                //totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + clientRegion.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT ");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);
//                    totalPaidCount = 0;
//                    //totalClientAmount = 0;
                    //totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(claimCNNumber, clientRegion);
                Sheet detailsSheet = wb.createSheet("Details");
                
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, Client client, Branch branch, String claimCNNumber) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(claimCNNumber.replace("/", "-"));
                Sheet enSheet = wb.createSheet("EN");
                Sheet amSheet = wb.createSheet("AM");
                
                tempSheet.setFitToPage(true);
                PrintSetup ps1 = tempSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                enSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                amSheet.setFitToPage(true);
                PrintSetup ps3 = amSheet.getPrintSetup();
                ps3.setFitWidth( (short) 1);
                ps3.setFitHeight( (short) 0);

                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY FOR " + claimCNNumber.toUpperCase() + " ON BRANCH " + branch.getName().toUpperCase());
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(client.getName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(client.getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(claimCNNumber, branch);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);

                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                //totalRow.createCell(3).setCellValue(totalClientAmount);
                //totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT ");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(client.getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);
                    //totalPaidCount = 0;
                    //totalClientAmount = 0;
                    totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(claimCNNumber, branch);
                Sheet detailsSheet = wb.createSheet("Details");
                
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, Client client, String claimCNNumber, boolean branchBased) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(claimCNNumber.replace("/", "-"));
                Sheet enSheet = wb.createSheet("EN");
                Sheet amSheet = wb.createSheet("AM");
                
                tempSheet.setFitToPage(true);
                PrintSetup ps1 = tempSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);

                enSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                amSheet.setFitToPage(true);
                PrintSetup ps3 = amSheet.getPrintSetup();
                ps3.setFitWidth( (short) 1);
                ps3.setFitHeight( (short) 0);

                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY FOR " + claimCNNumber.toUpperCase());
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(client.getName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(client.getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Branch Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString("Total Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Ref No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(claimCNNumber, true);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(8).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);
                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                //totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                //totalRow.getCell(2).setCellStyle(bodyStyle);
                totalRow.createCell(3).setCellValue(totalPaidAmount);
                totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalServiceCharge);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(CNVAT);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(totalServiceVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 9));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + client.getName() + " OBSTRUCTION COMPENSATION\nPAYMENT FOR " + report.get(0).getBranchName().toUpperCase() + " BRANCH ");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Branch Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString("Total Payments"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Service Charge"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Ref No."));

                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    //totalPaidCount = 0;
                    //totalClientAmount = 0;
                    totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(8).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(3).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(CNVAT);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(claimCNNumber);
                Sheet detailsSheet = wb.createSheet("Details");
                
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generateReport(HttpServletRequest request, User approver, String fileName, Claim claim, ClientRegion clientRegion) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        try {
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Sheet tempSheet = wb.createSheet(clientRegion.getRegionName());
                Sheet enSheet = wb.createSheet("EN");
                Sheet amSheet = wb.createSheet("AM");
                
                tempSheet.setFitToPage(true);
                PrintSetup ps1 = tempSheet.getPrintSetup();
                ps1.setFitWidth( (short) 1);
                ps1.setFitHeight( (short) 0);
                
                enSheet.setFitToPage(true);
                PrintSetup ps2 = enSheet.getPrintSetup();
                ps2.setFitWidth( (short) 1);
                ps2.setFitHeight( (short) 0);
                
                amSheet.setFitToPage(true);
                PrintSetup ps3 = amSheet.getPrintSetup();
                ps3.setFitWidth( (short) 1);
                ps3.setFitHeight( (short) 0);

                Row row0 = tempSheet.createRow((short) 0);
                row0.createCell(0).setCellValue("SUMMARY OF " + clientRegion.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT SUMMARY OF " + clientRegion.getRegionName().toUpperCase() + " REGION FOR" + claim.getClaimNumber().toUpperCase());
                row0.setHeight((short) 900);
                row0.getCell(0).setCellStyle(titleStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                Row enrow0 = enSheet.createRow((short) 0);
                enrow0.createCell(0).setCellValue(clientRegion.getRegionName().toUpperCase());
                enrow0.getCell(0).setCellStyle(titleStyle);
                enrow0.setHeight((short) 1200);

                enSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row enrow1 = enSheet.createRow((short) 1);
                enrow1.createCell(0).setCellValue("Project Name");
                enrow1.getCell(0).setCellStyle(title2Style);
                enrow1.createCell(1).setCellValue("Sum of Paid Amount");
                enrow1.getCell(1).setCellStyle(title2Style);
                enrow1.createCell(2).setCellValue("Ref No.");
                enrow1.getCell(2).setCellStyle(title2Style);

                Row amrow0 = amSheet.createRow((short) 0);
                amrow0.createCell(0).setCellValue(clientRegion.getAmharicName());
                amrow0.getCell(0).setCellStyle(titleStyle);
                amrow0.setHeight((short) 900);
                amSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                Row amrow1 = amSheet.createRow((short) 1);
                amrow1.createCell(0).setCellValue("የፕሮጀክት ስም");
                amrow1.getCell(0).setCellStyle(title2Style);
                amrow1.createCell(1).setCellValue("የክፍያ መጠን");
                amrow1.getCell(1).setCellStyle(title2Style);
                amrow1.createCell(2).setCellValue("የክፍያ ትእዛዝ ቁጥር");
                amrow1.getCell(2).setCellStyle(title2Style);
                amrow1.createCell(3).setCellValue("ባለመብቶች የፈረሙበት ኦሪጅናል የሰነድ ብዛት（በገጽ）");
                amrow1.getCell(3).setCellStyle(title2Style);
                amrow1.createCell(4).setCellValue("ክሬዲት አድሚን እና ሌሎች ");
                amrow1.getCell(4).setCellStyle(title2Style);

                Row row1 = tempSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Amount"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));

                row1.getCell(0).setCellStyle(title2Style);
                row1.getCell(1).setCellStyle(title2Style);
                row1.getCell(2).setCellStyle(title2Style);
                row1.getCell(3).setCellStyle(title2Style);
                row1.getCell(4).setCellStyle(title2Style);
                row1.getCell(5).setCellStyle(title2Style);
                row1.getCell(6).setCellStyle(title2Style);
                row1.getCell(7).setCellStyle(title2Style);
                row1.getCell(8).setCellStyle(title2Style);
                row1.getCell(9).setCellStyle(title2Style);
                row1.getCell(10).setCellStyle(title2Style);
                row1.getCell(11).setCellStyle(title2Style);

                HashMap<String, ArrayList<PaymentReport>> reports = CommonStorage.getRepository().getPaymentReport(claim, clientRegion);
                //double totalClientAmount = 0;
                //long totalPaidCount = 0;
                double totalPaidAmount = 0;
                double totalServiceCharge = 0;
                double totalBranchServiceCharge = 0;
                double CNVAT = 0;
                double totalServiceVAT = 0;
                int j = 0;
                for (Map.Entry<String, ArrayList<PaymentReport>> entry : reports.entrySet()) {
                    ArrayList<PaymentReport> report = entry.getValue();
                    for (int i = 0; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        //totalPaidCount += report.get(i).getNoOfPayment();
                        totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = tempSheet.createRow((short) j + 2);
                        Row enRow = enSheet.createRow((short) j + 2);
                        Row amRow = amSheet.createRow((short) j + 2);
                        j++;

                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);

                        enRow.createCell(0).setCellValue(report.get(i).getProjectName());
                        enRow.getCell(0).setCellStyle(bodyStyle);
                        enRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        enRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        enRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        enRow.getCell(2).setCellStyle(bodyStyle);

                        amRow.createCell(0).setCellValue(report.get(i).getProjectAmharicName());
                        amRow.getCell(0).setCellStyle(bodyStyle);
                        amRow.createCell(1).setCellValue(report.get(i).getPaidAmount());
                        amRow.getCell(1).setCellStyle(styleCurrencyFormat);
                        amRow.createCell(2).setCellValue(report.get(i).getReferenceNo());
                        amRow.getCell(2).setCellStyle(bodyStyle);
                        amRow.createCell(3).setCellValue("");
                        amRow.getCell(3).setCellStyle(bodyStyle);
                        amRow.createCell(4).setCellValue("");
                        amRow.getCell(4).setCellStyle(bodyStyle);
                    }
                }

                Row totalRow = tempSheet.createRow((short) j + 2);
                totalRow.createCell(0).setCellValue("Total");
                totalRow.getCell(0).setCellStyle(bodyStyle);
                totalRow.createCell(1).setCellValue("");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                ////totalRow.createCell(2).setCellValue(createHelper.createRichTextString("" + totalPaidCount));
                ////totalRow.getCell(2).setCellStyle(bodyStyle);
                //totalRow.createCell(3).setCellValue(totalClientAmount);
                //totalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(4).setCellValue(totalPaidAmount);
                totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(5).setCellValue(totalServiceCharge);
                totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(6).setCellValue(CNVAT);
                totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(7).setCellValue(totalServiceVAT);
                totalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                totalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                totalRow.createCell(9).setCellStyle(bodyStyle);
                totalRow.createCell(10).setCellStyle(bodyStyle);
                totalRow.createCell(11).setCellStyle(bodyStyle);
                totalRow.setHeight((short) 600);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 0, 1));
                tempSheet.addMergedRegion(new CellRangeAddress(j + 2, j + 2, 9, 11));

                Row enTotalRow = enSheet.createRow((short) j + 2);
                enTotalRow.createCell(0).setCellValue("Total");
                enTotalRow.getCell(0).setCellStyle(bodyStyle);
                enTotalRow.createCell(1).setCellValue(totalPaidAmount);
                enTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                enTotalRow.createCell(2).setCellValue("");
                enTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row amTotalRow = amSheet.createRow((short) j + 2);
                amTotalRow.createCell(0).setCellValue("ድምር");
                amTotalRow.getCell(0).setCellStyle(bodyStyle);
                amTotalRow.createCell(1).setCellValue(totalPaidAmount);
                amTotalRow.getCell(1).setCellStyle(styleCurrencyFormat);
                amTotalRow.createCell(2).setCellValue("");
                amTotalRow.getCell(2).setCellStyle(bodyStyle);

                Row signRow1 = tempSheet.createRow((short) j + 3);
                signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 3));
                signRow1.getCell(0).setCellStyle(footerStyle);
                signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                signRow1.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 5, 8));
                signRow1.setHeight((short) 600);

                Row signRow2 = tempSheet.createRow((short) j + 4);
                signRow2.createCell(0).setCellValue("Signature   : _____________________");
                signRow2.getCell(0).setCellStyle(footerStyle);

                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 3));
                signRow2.createCell(5).setCellValue("Signature   : _____________________");
                signRow2.getCell(5).setCellStyle(footerStyle);
                tempSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 5, 8));
                signRow2.setHeight((short) 600);

//                Row ensignRow1 = enSheet.createRow((short) j + 3);
//                ensignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                ensignRow1.getCell(0).setCellStyle(footerStyle);
//                ensignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                ensignRow1.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                ensignRow1.setHeight((short) 600);
//
//                Row ensignRow2 = enSheet.createRow((short) j + 4);
//                ensignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(0).setCellStyle(footerStyle);
//
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                ensignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                ensignRow2.getCell(3).setCellStyle(footerStyle);
//                enSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                ensignRow2.setHeight((short) 600);
//
//                Row amsignRow1 = amSheet.createRow((short) j + 3);
//                amsignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 0, 2));
//                amsignRow1.getCell(0).setCellStyle(footerStyle);
//                amsignRow1.createCell(3).setCellValue("Approved By:" + approver.getFullName());
//                amsignRow1.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 3, j + 3, 3, 5));
//                amsignRow1.setHeight((short) 600);
//
//                Row amsignRow2 = amSheet.createRow((short) j + 4);
//                amsignRow2.createCell(0).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(0).setCellStyle(footerStyle);
//
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 0, 2));
//                amsignRow2.createCell(3).setCellValue("Signature   : _____________________");
//                amsignRow2.getCell(3).setCellStyle(footerStyle);
//                amSheet.addMergedRegion(new CellRangeAddress(j + 4, j + 4, 3, 5));
//                amsignRow2.setHeight((short) 600);
                tempSheet.autoSizeColumn(1, true);
                tempSheet.autoSizeColumn(3, true);
                tempSheet.autoSizeColumn(4, true);
                tempSheet.autoSizeColumn(5, true);
                tempSheet.autoSizeColumn(6, true);
                tempSheet.autoSizeColumn(7, true);
                tempSheet.autoSizeColumn(8, true);
                tempSheet.autoSizeColumn(9, true);
                tempSheet.autoSizeColumn(10, true);
                tempSheet.autoSizeColumn(11, true);

                enSheet.autoSizeColumn(0, true);
                enSheet.autoSizeColumn(1, true);
                enSheet.autoSizeColumn(2, true);
                enSheet.autoSizeColumn(3, true);

                amSheet.autoSizeColumn(0, true);
                amSheet.autoSizeColumn(1, true);
                amSheet.autoSizeColumn(2, true);
                amSheet.autoSizeColumn(3, true);

                String[] projects = reports.keySet().toArray(new String[0]);
                for (String project : projects) {
                    Sheet projectSheet = wb.createSheet(project.replace("/", "-"));
                    
                    projectSheet.setFitToPage(true);
                    PrintSetup ps = projectSheet.getPrintSetup();
                    ps.setFitWidth( (short) 1);
                    ps.setFitHeight( (short) 0);
                    
                    ArrayList<PaymentReport> report = reports.get(project);
                    Row projectRow0 = projectSheet.createRow((short) 0);
                    projectRow0.createCell(0).setCellValue("SUMMARY OF " + claim.getClient().getName() + " OBSTRUCTION COMPENSATION\nPAYMENT FOR " + report.get(0).getProjectName().toUpperCase() + " PROJECT ");
                    projectRow0.setHeight((short) 1200);
                    projectRow0.getCell(0).setCellStyle(titleStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
                    Row projectRow1 = projectSheet.createRow((short) 1);
                    projectRow1.createCell(0).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.createCell(1).setCellValue(createHelper.createRichTextString("Project Name "));
                    projectRow1.createCell(2).setCellValue(createHelper.createRichTextString("No. Of Payments"));
                    projectRow1.createCell(3).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Amount"));
                    projectRow1.createCell(4).setCellValue(createHelper.createRichTextString("Sum of Paid Amount"));
                    projectRow1.createCell(5).setCellValue(createHelper.createRichTextString("Service Charge CN"));
                    projectRow1.createCell(6).setCellValue(createHelper.createRichTextString("CN VAT"));
                    projectRow1.createCell(7).setCellValue(createHelper.createRichTextString("Total Ser. VAT"));
                    projectRow1.createCell(8).setCellValue(createHelper.createRichTextString("Branch Service Charge"));
                    projectRow1.createCell(9).setCellValue(createHelper.createRichTextString("Ref No."));
                    projectRow1.createCell(10).setCellValue(createHelper.createRichTextString("Region"));
                    projectRow1.createCell(11).setCellValue(createHelper.createRichTextString("Branch No."));
                    projectRow1.getCell(0).setCellStyle(title2Style);
                    projectRow1.getCell(1).setCellStyle(title2Style);
                    projectRow1.getCell(2).setCellStyle(title2Style);
                    projectRow1.getCell(3).setCellStyle(title2Style);
                    projectRow1.getCell(4).setCellStyle(title2Style);
                    projectRow1.getCell(5).setCellStyle(title2Style);
                    projectRow1.getCell(6).setCellStyle(title2Style);
                    projectRow1.getCell(7).setCellStyle(title2Style);
                    projectRow1.getCell(8).setCellStyle(title2Style);
                    projectRow1.getCell(9).setCellStyle(title2Style);
                    projectRow1.getCell(10).setCellStyle(title2Style);
                    projectRow1.getCell(11).setCellStyle(title2Style);
//                    totalPaidCount = 0;
//                    //totalClientAmount = 0;
                    //totalPaidAmount = 0;
                    totalBranchServiceCharge = 0;
                    totalServiceCharge = 0;
                    CNVAT = 0;
                    totalServiceVAT = 0;
                    int i = 0;

                    for (; i < report.size(); i++) {
                        //totalClientAmount += report.get(i).getClientAmount();
                        ////totalPaidCount += report.get(i).getNoOfPayment();
                        //totalPaidAmount += report.get(i).getPaidAmount();
                        totalBranchServiceCharge += report.get(i).getBranchServiceCharge();
                        totalServiceCharge += report.get(i).getCnServiceCharge();
                        CNVAT += report.get(i).getCnVAT();
                        totalServiceVAT += report.get(i).getTotal();
                        Row tempRow = projectSheet.createRow((short) i + 2);
                        tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + report.get(i).getBranchNumber()));
                        tempRow.getCell(0).setCellStyle(bodyStyle);
                        tempRow.createCell(1).setCellValue(report.get(i).getProjectName());
                        tempRow.getCell(1).setCellStyle(bodyStyle);
                        tempRow.createCell(2).setCellValue(createHelper.createRichTextString("" + report.get(i).getNoOfPayment()));
                        tempRow.getCell(2).setCellStyle(bodyStyle);
                        tempRow.createCell(3).setCellValue(report.get(i).getClientAmount());
                        tempRow.getCell(3).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(4).setCellValue(report.get(i).getPaidAmount());
                        tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(5).setCellValue(report.get(i).getCnServiceCharge());
                        tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(6).setCellValue(report.get(i).getCnVAT());
                        tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(7).setCellValue(report.get(i).getTotal());
                        tempRow.getCell(7).setCellStyle(styleCurrencyFormat);
                        tempRow.createCell(8).setCellValue(report.get(i).getBranchServiceCharge());
                        tempRow.getCell(8).setCellStyle(styleCurrencyFormat);

                        tempRow.createCell(9).setCellValue(report.get(i).getReferenceNo());
                        tempRow.getCell(9).setCellStyle(bodyStyle);
                        tempRow.createCell(10).setCellValue(report.get(i).getRegion());
                        tempRow.getCell(10).setCellStyle(bodyStyle);
                        tempRow.createCell(11).setCellValue(report.get(i).getBranchName());
                        tempRow.getCell(11).setCellStyle(bodyStyle);
                        tempRow.setHeight((short) 600);
                    }
                    Row projectTotalRow = projectSheet.createRow((short) i + 2);
                    projectTotalRow.createCell(0).setCellValue("Total");
                    projectTotalRow.getCell(0).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(1).setCellValue("");
                    projectTotalRow.getCell(1).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(2).setCellValue("" + totalPaidCount);
                    //projectTotalRow.getCell(2).setCellStyle(bodyStyle);
                    //projectTotalRow.createCell(3).setCellValue(totalClientAmount);
                    //projectTotalRow.getCell(3).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(4).setCellValue(totalPaidAmount);
                    projectTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(5).setCellValue(totalServiceCharge);
                    projectTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(6).setCellValue(CNVAT);
                    projectTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(7).setCellValue(totalServiceVAT);
                    projectTotalRow.getCell(7).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(8).setCellValue(totalBranchServiceCharge);
                    projectTotalRow.getCell(8).setCellStyle(styleCurrencyFormat);
                    projectTotalRow.createCell(9).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(10).setCellStyle(bodyStyle);
                    projectTotalRow.createCell(11).setCellStyle(bodyStyle);
                    projectTotalRow.setHeight((short) 600);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 1));
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 9, 11));
                    Row signProjectRow1 = projectSheet.createRow((short) i + 3);
                    signProjectRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                    signProjectRow1.getCell(0).setCellStyle(footerStyle);
                    signProjectRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                    signProjectRow1.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                    signProjectRow1.setHeight((short) 600);
                    Row signProjectRow2 = projectSheet.createRow((short) i + 4);
                    signProjectRow2.createCell(0).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(0).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                    signProjectRow2.createCell(5).setCellValue("Signature   : _____________________");
                    signProjectRow2.getCell(5).setCellStyle(footerStyle);
                    projectSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                    signProjectRow2.setHeight((short) 600);
                    projectSheet.autoSizeColumn(1, true);
                    projectSheet.autoSizeColumn(3, true);
                    projectSheet.autoSizeColumn(4, true);
                    projectSheet.autoSizeColumn(5, true);
                    projectSheet.autoSizeColumn(6, true);
                    projectSheet.autoSizeColumn(7, true);
                    projectSheet.autoSizeColumn(8, true);
                    projectSheet.autoSizeColumn(9, true);
                    projectSheet.autoSizeColumn(10, true);
                    projectSheet.autoSizeColumn(11, true);
                }

                ArrayList<PaymentDetailReport> payments = CommonStorage.getRepository().getPaymentReportList(claim, clientRegion);
                Sheet detailsSheet = wb.createSheet("Details");
                
                detailsSheet.setFitToPage(true);
                PrintSetup ps = detailsSheet.getPrintSetup();
                ps.setFitWidth( (short) 1);
                ps.setFitHeight( (short) 0);
                
                Row detailsTitleRow = detailsSheet.createRow((short) 0);
                detailsTitleRow.createCell(0).setCellValue("No.");
                detailsTitleRow.getCell(0).setCellStyle(title2Style);
                detailsTitleRow.createCell(1).setCellValue("Full Name");
                detailsTitleRow.getCell(1).setCellStyle(title2Style);
                detailsTitleRow.createCell(2).setCellValue("Paid Amount");
                detailsTitleRow.getCell(2).setCellStyle(title2Style);
                detailsTitleRow.createCell(3).setCellValue("Ref. No.");
                detailsTitleRow.getCell(3).setCellStyle(title2Style);
                detailsTitleRow.createCell(4).setCellValue("Lot No.");
                detailsTitleRow.getCell(4).setCellStyle(title2Style);
                detailsTitleRow.createCell(5).setCellValue("Order Date");
                detailsTitleRow.getCell(5).setCellStyle(title2Style);
                detailsTitleRow.createCell(6).setCellValue("Remark");
                detailsTitleRow.getCell(6).setCellStyle(title2Style);
                int i = 1;
                double total = 0;
                for (PaymentDetailReport payment : payments) {
                    Row tempRow = detailsSheet.createRow((i));
                    tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + i++));
                    tempRow.getCell(0).setCellStyle(bodyStyle);
                    tempRow.createCell(1).setCellValue(payment.getName());
                    tempRow.getCell(1).setCellStyle(bodyStyle);
                    tempRow.createCell(2).setCellValue(payment.getPaidAmount());
                    tempRow.getCell(2).setCellStyle(styleCurrencyFormat);
                    tempRow.createCell(3).setCellValue(payment.getRefNo());
                    tempRow.getCell(3).setCellStyle(bodyStyle);
                    tempRow.createCell(4).setCellValue(createHelper.createRichTextString("" + payment.getLotNo()));
                    tempRow.getCell(4).setCellStyle(bodyStyle);
                    tempRow.createCell(5).setCellValue(payment.getOrderDate());
                    tempRow.getCell(5).setCellStyle(bodyStyle);
                    tempRow.createCell(6).setCellValue(payment.getRemark());
                    tempRow.getCell(6).setCellStyle(bodyStyle);
                    total += payment.getPaidAmount();
                }
                totalRow = detailsSheet.createRow(i);
                totalRow.createCell(1).setCellValue("Total");
                totalRow.getCell(1).setCellStyle(bodyStyle);
                totalRow.createCell(2).setCellValue(total);
                totalRow.getCell(2).setCellStyle(styleCurrencyFormat);

                detailsSheet.autoSizeColumn(0, true);
                detailsSheet.autoSizeColumn(1, true);
                detailsSheet.autoSizeColumn(2, true);
                detailsSheet.autoSizeColumn(3, true);
                detailsSheet.autoSizeColumn(4, true);
                detailsSheet.autoSizeColumn(5, true);

                Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
                detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
                detailsSignRow1.getCell(0).setCellStyle(footerStyle);
                detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
                detailsSignRow1.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
                detailsSignRow1.setHeight((short) 600);

                Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
                detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(0).setCellStyle(footerStyle);

                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
                detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
                detailsSignRow2.getCell(5).setCellStyle(footerStyle);
                detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
                detailsSignRow2.setHeight((short) 600);

                wb.setSheetOrder("EN", wb.getNumberOfSheets() - 1);
                wb.setSheetOrder("AM", wb.getNumberOfSheets() - 1);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generatePaidUnpadiSummary(HttpServletRequest request, User approver, String fileName, Client client, java.sql.Date from, java.sql.Date to) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            Sheet tempSheet = wb.createSheet(client.getName());

            tempSheet.setFitToPage(true);
            PrintSetup ps = tempSheet.getPrintSetup();
            ps.setFitWidth( (short) 1);
            ps.setFitHeight( (short) 0);
            
            Row row0 = tempSheet.createRow((short) 0);
            row0.createCell(0).setCellValue("PAID/UNPAID SUMMARY FOR " + client.getName() + " FOR THE TIME PERIOD " + from + " TO " + to);
            row0.setHeight((short) 900);
            row0.getCell(0).setCellStyle(titleStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row row1 = tempSheet.createRow((short) 1);
            row1.createCell(0).setCellValue(createHelper.createRichTextString("No."));
            row1.createCell(1).setCellValue(createHelper.createRichTextString("Bank"));
            row1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No. & Date"));
            row1.createCell(3).setCellValue(createHelper.createRichTextString("CN Ref. No. & Date"));
            row1.createCell(4).setCellValue(createHelper.createRichTextString("Amount"));
            row1.createCell(5).setCellValue(createHelper.createRichTextString("CN & CBE Paid"));
            row1.createCell(6).setCellValue(createHelper.createRichTextString("CN & CBE Unpaid"));
            row1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            row1.getCell(0).setCellStyle(title2Style);
            row1.getCell(1).setCellStyle(title2Style);
            row1.getCell(2).setCellStyle(title2Style);
            row1.getCell(3).setCellStyle(title2Style);
            row1.getCell(4).setCellStyle(title2Style);
            row1.getCell(5).setCellStyle(title2Style);
            row1.getCell(6).setCellStyle(title2Style);
            row1.getCell(7).setCellStyle(title2Style);

            ArrayList<PaidUnpaidReport> reports = CommonStorage.getRepository().getPaidUpaidSummary(client, from, to);
            int i = 0;
            double totalAmount = 0;
            double totalPaid = 0;
            double totalUnpaid = 0;
            for (; i < reports.size(); i++) {
                totalAmount += reports.get(i).getAmount();
                totalPaid += reports.get(i).getPaidAmount();
                totalUnpaid += reports.get(i).getUnpaidAmount();
                Row tempRow = tempSheet.createRow((short) i + 2);
                tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + (i + 1)));
                tempRow.getCell(0).setCellStyle(bodyStyle);
                tempRow.createCell(1).setCellValue(reports.get(i).getBranchName());
                tempRow.getCell(1).setCellStyle(bodyStyle);
                tempRow.createCell(2).setCellValue(reports.get(i).getClientRefNo());
                tempRow.getCell(2).setCellStyle(bodyStyle);
                tempRow.createCell(3).setCellValue(reports.get(i).getCnRefo());
                tempRow.getCell(3).setCellStyle(bodyStyle);
                tempRow.createCell(4).setCellValue(reports.get(i).getAmount());
                tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(5).setCellValue(reports.get(i).getPaidAmount());
                tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(6).setCellValue(reports.get(i).getUnpaidAmount());
                tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(7).setCellValue("");
                tempRow.getCell(7).setCellStyle(bodyStyle);
                tempRow.setHeight((short) 600);
            }

            Row totalRow = tempSheet.createRow((short) i + 2);
            totalRow.createCell(0).setCellValue("Grand Total");
            totalRow.getCell(0).setCellStyle(bodyStyle);
            totalRow.createCell(1).setCellValue("");
            totalRow.getCell(1).setCellStyle(bodyStyle);
            totalRow.createCell(2).setCellValue("");
            totalRow.getCell(2).setCellStyle(bodyStyle);
            totalRow.createCell(3).setCellValue("");
            totalRow.getCell(3).setCellStyle(bodyStyle);
            totalRow.createCell(4).setCellValue(totalAmount);
            totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(5).setCellValue(totalPaid);
            totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(6).setCellValue(totalUnpaid);
            totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(7).setCellValue("");
            totalRow.getCell(7).setCellStyle(bodyStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 3));

            Row signRow1 = tempSheet.createRow((short) i + 3);
            signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            tempSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
            signRow1.getCell(0).setCellStyle(footerStyle);
            signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            signRow1.getCell(5).setCellStyle(footerStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
            signRow1.setHeight((short) 600);

            Row signRow2 = tempSheet.createRow((short) i + 4);
            signRow2.createCell(0).setCellValue("Signature   : _____________________");
            signRow2.getCell(0).setCellStyle(footerStyle);

            tempSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
            signRow2.createCell(5).setCellValue("Signature   : _____________________");
            signRow2.getCell(5).setCellStyle(footerStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
            signRow2.setHeight((short) 600);

            tempSheet.autoSizeColumn(1, true);
            tempSheet.autoSizeColumn(3, true);
            tempSheet.autoSizeColumn(4, true);
            tempSheet.autoSizeColumn(5, true);
            tempSheet.autoSizeColumn(6, true);
            tempSheet.autoSizeColumn(7, true);

            Sheet detailsSheet = wb.createSheet("Details");
            Sheet paidSheet = wb.createSheet("Paid Details");
            Sheet unpaidSheet = wb.createSheet("Unpaid Details");
            
            detailsSheet.setFitToPage(true);
            PrintSetup ps1 = detailsSheet.getPrintSetup();
            ps1.setFitWidth( (short) 1);
            ps1.setFitHeight( (short) 0);

            paidSheet.setFitToPage(true);
            PrintSetup ps2 = paidSheet.getPrintSetup();
            ps2.setFitWidth( (short) 1);
            ps2.setFitHeight( (short) 0);
            
            unpaidSheet.setFitToPage(true);
            PrintSetup ps3 = unpaidSheet.getPrintSetup();
            ps3.setFitWidth( (short) 1);
            ps3.setFitHeight( (short) 0);
            
            Row detailsRow0 = detailsSheet.createRow((short) 0);
            detailsRow0.createCell(0).setCellValue("PAID/UNPAID DETAIL FOR " + client.getName() + " FOR THE TIME PERIOD " + from + " TO " + to);
            detailsRow0.setHeight((short) 900);
            detailsRow0.getCell(0).setCellStyle(titleStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row detailsRow1 = detailsSheet.createRow((short) 1);
            detailsRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            detailsRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            detailsRow1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No"));
            detailsRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            detailsRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            detailsRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            detailsRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            detailsRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));
            detailsSheet.autoSizeColumn(0, true);

            detailsRow1.getCell(0).setCellStyle(title2Style);
            detailsRow1.getCell(1).setCellStyle(title2Style);
            detailsRow1.getCell(2).setCellStyle(title2Style);
            detailsRow1.getCell(3).setCellStyle(title2Style);
            detailsRow1.getCell(4).setCellStyle(title2Style);
            detailsRow1.getCell(5).setCellStyle(title2Style);
            detailsRow1.getCell(6).setCellStyle(title2Style);
            detailsRow1.getCell(7).setCellStyle(title2Style);

            Row paidRow0 = paidSheet.createRow((short) 0);
            paidRow0.createCell(0).setCellValue("PAID DETAIL FOR " + client.getName() + " FOR THE TIME PERIOD " + from + " TO " + to);
            paidRow0.setHeight((short) 900);
            paidRow0.getCell(0).setCellStyle(titleStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row padiRow1 = paidSheet.createRow((short) 1);
            padiRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            padiRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            padiRow1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No"));
            padiRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            padiRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            padiRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            padiRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            padiRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            padiRow1.getCell(0).setCellStyle(title2Style);
            padiRow1.getCell(1).setCellStyle(title2Style);
            padiRow1.getCell(2).setCellStyle(title2Style);
            padiRow1.getCell(3).setCellStyle(title2Style);
            padiRow1.getCell(4).setCellStyle(title2Style);
            padiRow1.getCell(5).setCellStyle(title2Style);
            padiRow1.getCell(6).setCellStyle(title2Style);
            padiRow1.getCell(7).setCellStyle(title2Style);
            paidSheet.autoSizeColumn(0, true);

            Row unpaidRow0 = unpaidSheet.createRow((short) 0);
            unpaidRow0.createCell(0).setCellValue("UNPAID DETAIL FOR " + client.getName() + " FOR THE TIME PERIOD " + from + " TO " + to);
            unpaidRow0.setHeight((short) 900);
            unpaidRow0.getCell(0).setCellStyle(titleStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row unpaidRow1 = unpaidSheet.createRow((short) 1);
            unpaidRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            unpaidRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            unpaidRow1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No"));
            unpaidRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            unpaidRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            unpaidRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            unpaidRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            unpaidRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            unpaidRow1.getCell(0).setCellStyle(title2Style);
            unpaidRow1.getCell(1).setCellStyle(title2Style);
            unpaidRow1.getCell(2).setCellStyle(title2Style);
            unpaidRow1.getCell(3).setCellStyle(title2Style);
            unpaidRow1.getCell(4).setCellStyle(title2Style);
            unpaidRow1.getCell(5).setCellStyle(title2Style);
            unpaidRow1.getCell(6).setCellStyle(title2Style);
            unpaidRow1.getCell(7).setCellStyle(title2Style);
            unpaidSheet.autoSizeColumn(0, true);
            ArrayList<PaymentDetailReport> paymentReports = CommonStorage.getRepository().getPaymentDetail(client, from, to);
            i = 0;
            int paidCount = 0;
            int unpaidCount = 0;
            totalAmount = 0;
            totalPaid = 0;
            totalUnpaid = 0;
            for (; i < paymentReports.size(); i++) {
                totalAmount += paymentReports.get(i).getAmount();
                totalPaid += paymentReports.get(i).getPaidAmount();
                totalUnpaid += paymentReports.get(i).getUnPaidAmount();

                Row tempRow = detailsSheet.createRow((short) i + 2);

                tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                tempRow.getCell(0).setCellStyle(bodyStyle);
                tempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                tempRow.getCell(1).setCellStyle(bodyStyle);
                tempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                tempRow.getCell(2).setCellStyle(bodyStyle);
                tempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                tempRow.getCell(3).setCellStyle(bodyStyle);
                tempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(7).setCellValue("");
                tempRow.getCell(7).setCellStyle(bodyStyle);
                tempRow.setHeight((short) 600);

                if (paymentReports.get(i).getUnPaidAmount() > 0) {
                    Row unpaidTempRow = unpaidSheet.createRow((short) unpaidCount + 2);
                    unpaidCount++;
                    unpaidTempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                    unpaidTempRow.getCell(0).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                    unpaidTempRow.getCell(1).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                    unpaidTempRow.getCell(2).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                    unpaidTempRow.getCell(3).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                    unpaidTempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                    unpaidTempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                    unpaidTempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(7).setCellValue("");
                    unpaidTempRow.getCell(7).setCellStyle(bodyStyle);
                    unpaidTempRow.setHeight((short) 600);
                }
                if (paymentReports.get(i).getPaidAmount() > 0) {
                    Row paidTempRow = paidSheet.createRow((short) paidCount + 2);
                    paidCount++;
                    paidTempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                    paidTempRow.getCell(0).setCellStyle(bodyStyle);
                    paidTempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                    paidTempRow.getCell(1).setCellStyle(bodyStyle);
                    paidTempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                    paidTempRow.getCell(2).setCellStyle(bodyStyle);
                    paidTempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                    paidTempRow.getCell(3).setCellStyle(bodyStyle);
                    paidTempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                    paidTempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                    paidTempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                    paidTempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(7).setCellValue("");
                    paidTempRow.getCell(7).setCellStyle(bodyStyle);
                    paidTempRow.setHeight((short) 600);
                }
            }

            Row detailsTotalRow = detailsSheet.createRow((short) i + 2);
            detailsTotalRow.createCell(0).setCellValue("Grand Total");
            detailsTotalRow.getCell(0).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(1).setCellValue("");
            detailsTotalRow.getCell(1).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(2).setCellValue("");
            detailsTotalRow.getCell(2).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(3).setCellValue("");
            detailsTotalRow.getCell(3).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(4).setCellValue(totalAmount);
            detailsTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(5).setCellValue(totalPaid);
            detailsTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(6).setCellValue(totalUnpaid);
            detailsTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(7).setCellValue("");
            detailsTotalRow.getCell(7).setCellStyle(bodyStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 3));

            Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
            detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
            detailsSignRow1.getCell(0).setCellStyle(footerStyle);
            detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            detailsSignRow1.getCell(5).setCellStyle(footerStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
            detailsSignRow1.setHeight((short) 600);

            Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
            detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            detailsSignRow2.getCell(0).setCellStyle(footerStyle);

            detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
            detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
            detailsSignRow2.getCell(5).setCellStyle(footerStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
            detailsSignRow2.setHeight((short) 600);

            detailsSheet.autoSizeColumn(1, true);
            detailsSheet.autoSizeColumn(3, true);
            detailsSheet.autoSizeColumn(4, true);
            detailsSheet.autoSizeColumn(5, true);
            detailsSheet.autoSizeColumn(6, true);

            Row unpaidTotalRow = unpaidSheet.createRow((short) unpaidCount + 2);
            unpaidTotalRow.createCell(0).setCellValue("Grand Total");
            unpaidTotalRow.getCell(0).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(1).setCellValue("");
            unpaidTotalRow.getCell(1).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(2).setCellValue("");
            unpaidTotalRow.getCell(2).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(3).setCellValue("");
            unpaidTotalRow.getCell(3).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(4).setCellValue(totalAmount);
            unpaidTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(5).setCellValue(totalPaid);
            unpaidTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(6).setCellValue(totalUnpaid);
            unpaidTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(7).setCellValue("");
            unpaidTotalRow.getCell(7).setCellStyle(bodyStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 2, unpaidCount + 2, 0, 2));

            Row unpaidSignRow1 = unpaidSheet.createRow((short) unpaidCount + 3);
            unpaidSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 3, unpaidCount + 3, 0, 3));
            unpaidSignRow1.getCell(0).setCellStyle(footerStyle);
            unpaidSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            unpaidSignRow1.getCell(5).setCellStyle(footerStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 3, unpaidCount + 3, 5, 8));
            unpaidSignRow1.setHeight((short) 600);

            Row unpaidSignRow2 = unpaidSheet.createRow((short) unpaidCount + 4);
            unpaidSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            unpaidSignRow2.getCell(0).setCellStyle(footerStyle);

            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 4, unpaidCount + 4, 0, 3));
            unpaidSignRow2.createCell(5).setCellValue("Signature   : _____________________");
            unpaidSignRow2.getCell(5).setCellStyle(footerStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 4, unpaidCount + 4, 5, 8));
            unpaidSignRow2.setHeight((short) 600);

            unpaidSheet.autoSizeColumn(1, true);
            unpaidSheet.autoSizeColumn(3, true);
            unpaidSheet.autoSizeColumn(4, true);
            unpaidSheet.autoSizeColumn(5, true);
            unpaidSheet.autoSizeColumn(6, true);
            unpaidSheet.autoSizeColumn(7, true);

            Row paidTotalRow = paidSheet.createRow((short) paidCount + 2);
            paidTotalRow.createCell(0).setCellValue("Grand Total");
            paidTotalRow.getCell(0).setCellStyle(bodyStyle);
            paidTotalRow.createCell(1).setCellValue("");
            paidTotalRow.getCell(1).setCellStyle(bodyStyle);
            paidTotalRow.createCell(2).setCellValue("");
            paidTotalRow.getCell(2).setCellStyle(bodyStyle);
            paidTotalRow.createCell(3).setCellValue("");
            paidTotalRow.getCell(3).setCellStyle(bodyStyle);
            paidTotalRow.createCell(4).setCellValue(totalAmount);
            paidTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(5).setCellValue(totalPaid);
            paidTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(6).setCellValue(totalUnpaid);
            paidTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(7).setCellValue("");
            paidTotalRow.getCell(7).setCellStyle(bodyStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 2, paidCount + 2, 0, 2));

            Row paidSignRow1 = paidSheet.createRow((short) paidCount + 3);
            paidSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 3, paidCount + 3, 0, 3));
            paidSignRow1.getCell(0).setCellStyle(footerStyle);
            paidSignRow1.createCell(4).setCellValue("Approved By:" + approver.getFullName());
            paidSignRow1.getCell(4).setCellStyle(footerStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 3, paidCount + 3, 4, 6));
            paidSignRow1.setHeight((short) 600);

            Row paidSignRow2 = paidSheet.createRow((short) paidCount + 4);
            paidSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            paidSignRow2.getCell(0).setCellStyle(footerStyle);

            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 4, paidCount + 4, 0, 3));
            paidSignRow2.createCell(4).setCellValue("Signature   : _____________________");
            paidSignRow2.getCell(4).setCellStyle(footerStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 4, paidCount + 4, 4, 6));
            paidSignRow2.setHeight((short) 600);

            paidSheet.autoSizeColumn(1, true);
            paidSheet.autoSizeColumn(3, true);
            paidSheet.autoSizeColumn(4, true);
            paidSheet.autoSizeColumn(5, true);
            paidSheet.autoSizeColumn(6, true);
            paidSheet.autoSizeColumn(7, true);

            wb.write(fileOut);
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generatePaidUnpadiSummary(HttpServletRequest request, User approver, String fileName, ClientRegion clientRegion, java.sql.Date from, java.sql.Date to) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 14);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            Sheet tempSheet = wb.createSheet(clientRegion.getRegionName());

            tempSheet.setFitToPage(true);
            PrintSetup ps = tempSheet.getPrintSetup();
            ps.setFitWidth( (short) 1);
            ps.setFitHeight( (short) 0);
            
            Row row0 = tempSheet.createRow((short) 0);
            row0.createCell(0).setCellValue("PAID/UNPAID SUMMARY FOR " + clientRegion.getClient().getName() + " " + clientRegion.getRegionName() + " Region FOR THE TIME PERIOD " + from + " TO " + to);
            row0.setHeight((short) 900);
            row0.getCell(0).setCellStyle(titleStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row row1 = tempSheet.createRow((short) 1);
            row1.createCell(0).setCellValue(createHelper.createRichTextString("No."));
            row1.createCell(1).setCellValue(createHelper.createRichTextString("Bank"));
            row1.createCell(2).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Ref. No. & Date"));
            row1.createCell(3).setCellValue(createHelper.createRichTextString("CN Ref. No. & Date"));
            row1.createCell(4).setCellValue(createHelper.createRichTextString("Amount"));
            row1.createCell(5).setCellValue(createHelper.createRichTextString("CN & CBE Paid"));
            row1.createCell(6).setCellValue(createHelper.createRichTextString("CN & CBE Unpaid"));
            row1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            row1.getCell(0).setCellStyle(title2Style);
            row1.getCell(1).setCellStyle(title2Style);
            row1.getCell(2).setCellStyle(title2Style);
            row1.getCell(3).setCellStyle(title2Style);
            row1.getCell(4).setCellStyle(title2Style);
            row1.getCell(5).setCellStyle(title2Style);
            row1.getCell(6).setCellStyle(title2Style);
            row1.getCell(7).setCellStyle(title2Style);

            ArrayList<PaidUnpaidReport> reports = CommonStorage.getRepository().getPaidUpaidSummary(clientRegion, from, to);
            int i = 0;
            double totalAmount = 0;
            double totalPaid = 0;
            double totalUnpaid = 0;
            for (; i < reports.size(); i++) {
                totalAmount += reports.get(i).getAmount();
                totalPaid += reports.get(i).getPaidAmount();
                totalUnpaid += reports.get(i).getUnpaidAmount();
                Row tempRow = tempSheet.createRow((short) i + 2);
                tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + (i + 1)));
                tempRow.getCell(0).setCellStyle(bodyStyle);
                tempRow.createCell(1).setCellValue(reports.get(i).getBranchName());
                tempRow.getCell(1).setCellStyle(bodyStyle);
                tempRow.createCell(2).setCellValue(reports.get(i).getClientRefNo());
                tempRow.getCell(2).setCellStyle(bodyStyle);
                tempRow.createCell(3).setCellValue(reports.get(i).getCnRefo());
                tempRow.getCell(3).setCellStyle(bodyStyle);
                tempRow.createCell(4).setCellValue(reports.get(i).getAmount());
                tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(5).setCellValue(reports.get(i).getPaidAmount());
                tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(6).setCellValue(reports.get(i).getUnpaidAmount());
                tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(7).setCellValue("");
                tempRow.getCell(7).setCellStyle(bodyStyle);
                tempRow.setHeight((short) 600);
            }

            Row totalRow = tempSheet.createRow((short) i + 2);
            totalRow.createCell(0).setCellValue("Grand Total");
            totalRow.getCell(0).setCellStyle(bodyStyle);
            totalRow.createCell(1).setCellValue("");
            totalRow.getCell(1).setCellStyle(bodyStyle);
            totalRow.createCell(2).setCellValue("");
            totalRow.getCell(2).setCellStyle(bodyStyle);
            totalRow.createCell(3).setCellValue("");
            totalRow.getCell(3).setCellStyle(bodyStyle);
            totalRow.createCell(4).setCellValue(totalAmount);
            totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(5).setCellValue(totalPaid);
            totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(6).setCellValue(totalUnpaid);
            totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(7).setCellValue("");
            totalRow.getCell(7).setCellStyle(bodyStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 3));

            Row signRow1 = tempSheet.createRow((short) i + 3);
            signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            tempSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
            signRow1.getCell(0).setCellStyle(footerStyle);
            signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            signRow1.getCell(5).setCellStyle(footerStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
            signRow1.setHeight((short) 600);

            Row signRow2 = tempSheet.createRow((short) i + 4);
            signRow2.createCell(0).setCellValue("Signature   : _____________________");
            signRow2.getCell(0).setCellStyle(footerStyle);

            tempSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
            signRow2.createCell(5).setCellValue("Signature   : _____________________");
            signRow2.getCell(5).setCellStyle(footerStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
            signRow2.setHeight((short) 600);

            tempSheet.autoSizeColumn(1, true);
            tempSheet.autoSizeColumn(3, true);
            tempSheet.autoSizeColumn(4, true);
            tempSheet.autoSizeColumn(5, true);
            tempSheet.autoSizeColumn(6, true);
            tempSheet.autoSizeColumn(7, true);

            Sheet detailsSheet = wb.createSheet("Details");
            Sheet paidSheet = wb.createSheet("Paid Details");
            Sheet unpaidSheet = wb.createSheet("Unpaid Details");

            detailsSheet.setFitToPage(true);
            PrintSetup ps1 = detailsSheet.getPrintSetup();
            ps1.setFitWidth( (short) 1);
            ps1.setFitHeight( (short) 0);

            paidSheet.setFitToPage(true);
            PrintSetup ps2 = paidSheet.getPrintSetup();
            ps2.setFitWidth( (short) 1);
            ps2.setFitHeight( (short) 0);
            
            unpaidSheet.setFitToPage(true);
            PrintSetup ps3 = unpaidSheet.getPrintSetup();
            ps3.setFitWidth( (short) 1);
            ps3.setFitHeight( (short) 0);
            
            Row detailsRow0 = detailsSheet.createRow((short) 0);
            detailsRow0.createCell(0).setCellValue("PAID/UNPAID DETAIL FOR " + clientRegion.getClient().getName() + " " + clientRegion.getRegionName() + " REGION FOR THE TIME PERIOD " + from + " TO " + to);
            detailsRow0.setHeight((short) 900);
            detailsRow0.getCell(0).setCellStyle(titleStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row detailsRow1 = detailsSheet.createRow((short) 1);
            detailsRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            detailsRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            detailsRow1.createCell(2).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Ref. No"));
            detailsRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            detailsRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            detailsRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            detailsRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            detailsRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));
            detailsSheet.autoSizeColumn(0, true);

            detailsRow1.getCell(0).setCellStyle(title2Style);
            detailsRow1.getCell(1).setCellStyle(title2Style);
            detailsRow1.getCell(2).setCellStyle(title2Style);
            detailsRow1.getCell(3).setCellStyle(title2Style);
            detailsRow1.getCell(4).setCellStyle(title2Style);
            detailsRow1.getCell(5).setCellStyle(title2Style);
            detailsRow1.getCell(6).setCellStyle(title2Style);
            detailsRow1.getCell(7).setCellStyle(title2Style);

            Row paidRow0 = paidSheet.createRow((short) 0);
            paidRow0.createCell(0).setCellValue("PAID DETAIL FOR " + clientRegion.getClient().getName() + " " + clientRegion.getRegionName() + " REGION FOR THE TIME PERIOD " + from + " TO " + to);
            paidRow0.setHeight((short) 900);
            paidRow0.getCell(0).setCellStyle(titleStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row padiRow1 = paidSheet.createRow((short) 1);
            padiRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            padiRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            padiRow1.createCell(2).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Ref. No"));
            padiRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            padiRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            padiRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            padiRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            padiRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            padiRow1.getCell(0).setCellStyle(title2Style);
            padiRow1.getCell(1).setCellStyle(title2Style);
            padiRow1.getCell(2).setCellStyle(title2Style);
            padiRow1.getCell(3).setCellStyle(title2Style);
            padiRow1.getCell(4).setCellStyle(title2Style);
            padiRow1.getCell(5).setCellStyle(title2Style);
            padiRow1.getCell(6).setCellStyle(title2Style);
            padiRow1.getCell(7).setCellStyle(title2Style);
            paidSheet.autoSizeColumn(0, true);

            Row unpaidRow0 = unpaidSheet.createRow((short) 0);
            unpaidRow0.createCell(0).setCellValue("UNPAID DETAIL FOR " + clientRegion.getClient().getName() + " " + clientRegion.getRegionName() + " REGION FOR THE TIME PERIOD " + from + " TO " + to);
            unpaidRow0.setHeight((short) 900);
            unpaidRow0.getCell(0).setCellStyle(titleStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row unpaidRow1 = unpaidSheet.createRow((short) 1);
            unpaidRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            unpaidRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            unpaidRow1.createCell(2).setCellValue(createHelper.createRichTextString(clientRegion.getClient().getName() + " Ref. No"));
            unpaidRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            unpaidRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            unpaidRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            unpaidRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            unpaidRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            unpaidRow1.getCell(0).setCellStyle(title2Style);
            unpaidRow1.getCell(1).setCellStyle(title2Style);
            unpaidRow1.getCell(2).setCellStyle(title2Style);
            unpaidRow1.getCell(3).setCellStyle(title2Style);
            unpaidRow1.getCell(4).setCellStyle(title2Style);
            unpaidRow1.getCell(5).setCellStyle(title2Style);
            unpaidRow1.getCell(6).setCellStyle(title2Style);
            unpaidRow1.getCell(7).setCellStyle(title2Style);
            unpaidSheet.autoSizeColumn(0, true);
            ArrayList<PaymentDetailReport> paymentReports = CommonStorage.getRepository().getPaymentDetail(clientRegion, from, to);
            i = 0;
            int paidCount = 0;
            int unpaidCount = 0;
            totalAmount = 0;
            totalPaid = 0;
            totalUnpaid = 0;
            for (; i < paymentReports.size(); i++) {
                totalAmount += paymentReports.get(i).getAmount();
                totalPaid += paymentReports.get(i).getPaidAmount();
                totalUnpaid += paymentReports.get(i).getUnPaidAmount();

                Row tempRow = detailsSheet.createRow((short) i + 2);

                tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                tempRow.getCell(0).setCellStyle(bodyStyle);
                tempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                tempRow.getCell(1).setCellStyle(bodyStyle);
                tempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                tempRow.getCell(2).setCellStyle(bodyStyle);
                tempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                tempRow.getCell(3).setCellStyle(bodyStyle);
                tempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(7).setCellValue("");
                tempRow.getCell(7).setCellStyle(bodyStyle);
                tempRow.setHeight((short) 600);

                if (paymentReports.get(i).getUnPaidAmount() > 0) {
                    Row unpaidTempRow = unpaidSheet.createRow((short) unpaidCount + 2);
                    unpaidCount++;
                    unpaidTempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                    unpaidTempRow.getCell(0).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                    unpaidTempRow.getCell(1).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                    unpaidTempRow.getCell(2).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                    unpaidTempRow.getCell(3).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                    unpaidTempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                    unpaidTempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                    unpaidTempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(7).setCellValue("");
                    unpaidTempRow.getCell(7).setCellStyle(bodyStyle);
                    unpaidTempRow.setHeight((short) 600);
                }
                if (paymentReports.get(i).getPaidAmount() > 0) {
                    Row paidTempRow = paidSheet.createRow((short) paidCount + 2);
                    paidCount++;
                    paidTempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                    paidTempRow.getCell(0).setCellStyle(bodyStyle);
                    paidTempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                    paidTempRow.getCell(1).setCellStyle(bodyStyle);
                    paidTempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                    paidTempRow.getCell(2).setCellStyle(bodyStyle);
                    paidTempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                    paidTempRow.getCell(3).setCellStyle(bodyStyle);
                    paidTempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                    paidTempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                    paidTempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                    paidTempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(7).setCellValue("");
                    paidTempRow.getCell(7).setCellStyle(bodyStyle);
                    paidTempRow.setHeight((short) 600);
                }
            }

            Row detailsTotalRow = detailsSheet.createRow((short) i + 2);
            detailsTotalRow.createCell(0).setCellValue("Grand Total");
            detailsTotalRow.getCell(0).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(1).setCellValue("");
            detailsTotalRow.getCell(1).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(2).setCellValue("");
            detailsTotalRow.getCell(2).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(3).setCellValue("");
            detailsTotalRow.getCell(3).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(4).setCellValue(totalAmount);
            detailsTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(5).setCellValue(totalPaid);
            detailsTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(6).setCellValue(totalUnpaid);
            detailsTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(7).setCellValue("");
            detailsTotalRow.getCell(7).setCellStyle(bodyStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 3));

            Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
            detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
            detailsSignRow1.getCell(0).setCellStyle(footerStyle);
            detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            detailsSignRow1.getCell(5).setCellStyle(footerStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
            detailsSignRow1.setHeight((short) 600);

            Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
            detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            detailsSignRow2.getCell(0).setCellStyle(footerStyle);

            detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
            detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
            detailsSignRow2.getCell(5).setCellStyle(footerStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
            detailsSignRow2.setHeight((short) 600);

            detailsSheet.autoSizeColumn(1, true);
            detailsSheet.autoSizeColumn(3, true);
            detailsSheet.autoSizeColumn(4, true);
            detailsSheet.autoSizeColumn(5, true);
            detailsSheet.autoSizeColumn(6, true);

            Row unpaidTotalRow = unpaidSheet.createRow((short) unpaidCount + 2);
            unpaidTotalRow.createCell(0).setCellValue("Grand Total");
            unpaidTotalRow.getCell(0).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(1).setCellValue("");
            unpaidTotalRow.getCell(1).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(2).setCellValue("");
            unpaidTotalRow.getCell(2).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(3).setCellValue("");
            unpaidTotalRow.getCell(3).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(4).setCellValue(totalAmount);
            unpaidTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(5).setCellValue(totalPaid);
            unpaidTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(6).setCellValue(totalUnpaid);
            unpaidTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(7).setCellValue("");
            unpaidTotalRow.getCell(7).setCellStyle(bodyStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 2, unpaidCount + 2, 0, 2));

            Row unpaidSignRow1 = unpaidSheet.createRow((short) unpaidCount + 3);
            unpaidSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 3, unpaidCount + 3, 0, 3));
            unpaidSignRow1.getCell(0).setCellStyle(footerStyle);
            unpaidSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            unpaidSignRow1.getCell(5).setCellStyle(footerStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 3, unpaidCount + 3, 5, 8));
            unpaidSignRow1.setHeight((short) 600);

            Row unpaidSignRow2 = unpaidSheet.createRow((short) unpaidCount + 4);
            unpaidSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            unpaidSignRow2.getCell(0).setCellStyle(footerStyle);

            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 4, unpaidCount + 4, 0, 3));
            unpaidSignRow2.createCell(5).setCellValue("Signature   : _____________________");
            unpaidSignRow2.getCell(5).setCellStyle(footerStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 4, unpaidCount + 4, 5, 8));
            unpaidSignRow2.setHeight((short) 600);

            unpaidSheet.autoSizeColumn(1, true);
            unpaidSheet.autoSizeColumn(3, true);
            unpaidSheet.autoSizeColumn(4, true);
            unpaidSheet.autoSizeColumn(5, true);
            unpaidSheet.autoSizeColumn(6, true);
            unpaidSheet.autoSizeColumn(7, true);

            Row paidTotalRow = paidSheet.createRow((short) paidCount + 2);
            paidTotalRow.createCell(0).setCellValue("Grand Total");
            paidTotalRow.getCell(0).setCellStyle(bodyStyle);
            paidTotalRow.createCell(1).setCellValue("");
            paidTotalRow.getCell(1).setCellStyle(bodyStyle);
            paidTotalRow.createCell(2).setCellValue("");
            paidTotalRow.getCell(2).setCellStyle(bodyStyle);
            paidTotalRow.createCell(3).setCellValue("");
            paidTotalRow.getCell(3).setCellStyle(bodyStyle);
            paidTotalRow.createCell(4).setCellValue(totalAmount);
            paidTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(5).setCellValue(totalPaid);
            paidTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(6).setCellValue(totalUnpaid);
            paidTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(7).setCellValue("");
            paidTotalRow.getCell(7).setCellStyle(bodyStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 2, paidCount + 2, 0, 2));

            Row paidSignRow1 = paidSheet.createRow((short) paidCount + 3);
            paidSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 3, paidCount + 3, 0, 3));
            paidSignRow1.getCell(0).setCellStyle(footerStyle);
            paidSignRow1.createCell(4).setCellValue("Approved By:" + approver.getFullName());
            paidSignRow1.getCell(4).setCellStyle(footerStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 3, paidCount + 3, 4, 6));
            paidSignRow1.setHeight((short) 600);

            Row paidSignRow2 = paidSheet.createRow((short) paidCount + 4);
            paidSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            paidSignRow2.getCell(0).setCellStyle(footerStyle);

            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 4, paidCount + 4, 0, 3));
            paidSignRow2.createCell(4).setCellValue("Signature   : _____________________");
            paidSignRow2.getCell(4).setCellStyle(footerStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 4, paidCount + 4, 4, 6));
            paidSignRow2.setHeight((short) 600);

            paidSheet.autoSizeColumn(1, true);
            paidSheet.autoSizeColumn(3, true);
            paidSheet.autoSizeColumn(4, true);
            paidSheet.autoSizeColumn(5, true);
            paidSheet.autoSizeColumn(6, true);
            paidSheet.autoSizeColumn(7, true);

            wb.write(fileOut);
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }

    public void generatePaidUnpadiSummary(HttpServletRequest request, User approver, String fileName, Client client, Branch branch, java.sql.Date from, java.sql.Date to) {
        Workbook wb = new XSSFWorkbook();
        CellStyle titleStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();

        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);
        titleStyle.setWrapText(true);

        titleStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titleStyle.setBorderTop(CellStyle.BORDER_MEDIUM);

        CellStyle title2Style = wb.createCellStyle();
        title2Style.cloneStyleFrom(titleStyle);
        Font title2Font = wb.createFont();
        title2Font.setFontName("Times New Roman");
        title2Font.setFontHeightInPoints((short) 12);
        title2Font.setBold(true);
        title2Style.setFont(title2Font);

        CellStyle bodyStyle = wb.createCellStyle();
        Font bodyFont = wb.createFont();
        bodyFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        bodyFont.setBold(true);
        bodyStyle.setFont(bodyFont);
        bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        bodyStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        bodyStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        CellStyle styleCurrencyFormat = wb.createCellStyle();
        styleCurrencyFormat.cloneStyleFrom(bodyStyle);
        styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        styleCurrencyFormat.setAlignment(CellStyle.ALIGN_JUSTIFY);
        CellStyle footerStyle = wb.createCellStyle();
        Font footerFont = wb.createFont();
        footerFont.setFontName("Arial");
        footerFont.setFontHeightInPoints((short) 12);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            Sheet tempSheet = wb.createSheet(branch.getName());

            tempSheet.setFitToPage(true);
            PrintSetup ps = tempSheet.getPrintSetup();
            ps.setFitWidth( (short) 1);
            ps.setFitHeight( (short) 0);

            Row row0 = tempSheet.createRow((short) 0);
            row0.createCell(0).setCellValue("PAID/UNPAID SUMMARY FOR " + branch.getName() + " BRANCH FOR THE TIME PERIOD " + from + " TO " + to);
            row0.setHeight((short) 900);
            row0.getCell(0).setCellStyle(titleStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row row1 = tempSheet.createRow((short) 1);
            row1.createCell(0).setCellValue(createHelper.createRichTextString("No."));
            row1.createCell(1).setCellValue(createHelper.createRichTextString("Bank"));
            row1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No. & Date"));
            row1.createCell(3).setCellValue(createHelper.createRichTextString("CN Ref. No. & Date"));
            row1.createCell(4).setCellValue(createHelper.createRichTextString("Amount"));
            row1.createCell(5).setCellValue(createHelper.createRichTextString("CN & CBE Paid"));
            row1.createCell(6).setCellValue(createHelper.createRichTextString("CN & CBE Unpaid"));
            row1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            row1.getCell(0).setCellStyle(title2Style);
            row1.getCell(1).setCellStyle(title2Style);
            row1.getCell(2).setCellStyle(title2Style);
            row1.getCell(3).setCellStyle(title2Style);
            row1.getCell(4).setCellStyle(title2Style);
            row1.getCell(5).setCellStyle(title2Style);
            row1.getCell(6).setCellStyle(title2Style);
            row1.getCell(7).setCellStyle(title2Style);

            ArrayList<PaidUnpaidReport> reports = CommonStorage.getRepository().getPaidUpaidSummary(client, branch, from, to);
            int i = 0;
            double totalAmount = 0;
            double totalPaid = 0;
            double totalUnpaid = 0;
            for (; i < reports.size(); i++) {
                totalAmount += reports.get(i).getAmount();
                totalPaid += reports.get(i).getPaidAmount();
                totalUnpaid += reports.get(i).getUnpaidAmount();
                Row tempRow = tempSheet.createRow((short) i + 2);
                tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + (i + 1)));
                tempRow.getCell(0).setCellStyle(bodyStyle);
                tempRow.createCell(1).setCellValue(reports.get(i).getBranchName());
                tempRow.getCell(1).setCellStyle(bodyStyle);
                tempRow.createCell(2).setCellValue(reports.get(i).getClientRefNo());
                tempRow.getCell(2).setCellStyle(bodyStyle);
                tempRow.createCell(3).setCellValue(reports.get(i).getCnRefo());
                tempRow.getCell(3).setCellStyle(bodyStyle);
                tempRow.createCell(4).setCellValue(reports.get(i).getAmount());
                tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(5).setCellValue(reports.get(i).getPaidAmount());
                tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(6).setCellValue(reports.get(i).getUnpaidAmount());
                tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(7).setCellValue("");
                tempRow.getCell(7).setCellStyle(bodyStyle);
                tempRow.setHeight((short) 600);
            }

            Row totalRow = tempSheet.createRow((short) i + 2);
            totalRow.createCell(0).setCellValue("Grand Total");
            totalRow.getCell(0).setCellStyle(bodyStyle);
            totalRow.createCell(1).setCellValue("");
            totalRow.getCell(1).setCellStyle(bodyStyle);
            totalRow.createCell(2).setCellValue("");
            totalRow.getCell(2).setCellStyle(bodyStyle);
            totalRow.createCell(3).setCellValue("");
            totalRow.getCell(3).setCellStyle(bodyStyle);
            totalRow.createCell(4).setCellValue(totalAmount);
            totalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(5).setCellValue(totalPaid);
            totalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(6).setCellValue(totalUnpaid);
            totalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            totalRow.createCell(7).setCellValue("");
            totalRow.getCell(7).setCellStyle(bodyStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 3));

            Row signRow1 = tempSheet.createRow((short) i + 3);
            signRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            tempSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
            signRow1.getCell(0).setCellStyle(footerStyle);
            signRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            signRow1.getCell(5).setCellStyle(footerStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
            signRow1.setHeight((short) 600);

            Row signRow2 = tempSheet.createRow((short) i + 4);
            signRow2.createCell(0).setCellValue("Signature   : _____________________");
            signRow2.getCell(0).setCellStyle(footerStyle);

            tempSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
            signRow2.createCell(5).setCellValue("Signature   : _____________________");
            signRow2.getCell(5).setCellStyle(footerStyle);
            tempSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
            signRow2.setHeight((short) 600);

            tempSheet.autoSizeColumn(1, true);
            tempSheet.autoSizeColumn(3, true);
            tempSheet.autoSizeColumn(4, true);
            tempSheet.autoSizeColumn(5, true);
            tempSheet.autoSizeColumn(6, true);
            tempSheet.autoSizeColumn(7, true);

            Sheet detailsSheet = wb.createSheet("Details");
            Sheet paidSheet = wb.createSheet("Paid Details");
            Sheet unpaidSheet = wb.createSheet("Unpaid Details");

            detailsSheet.setFitToPage(true);
            PrintSetup ps1 = detailsSheet.getPrintSetup();
            ps1.setFitWidth( (short) 1);
            ps1.setFitHeight( (short) 0);

            paidSheet.setFitToPage(true);
            PrintSetup ps2 = paidSheet.getPrintSetup();
            ps2.setFitWidth( (short) 1);
            ps2.setFitHeight( (short) 0);
            
            unpaidSheet.setFitToPage(true);
            PrintSetup ps3 = unpaidSheet.getPrintSetup();
            ps3.setFitWidth( (short) 1);
            ps3.setFitHeight( (short) 0);
            
            Row detailsRow0 = detailsSheet.createRow((short) 0);
            detailsRow0.createCell(0).setCellValue("PAID/UNPAID DETAIL FOR " + branch.getName() + " FOR THE TIME PERIOD " + from + " TO " + to);
            detailsRow0.setHeight((short) 900);
            detailsRow0.getCell(0).setCellStyle(titleStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row detailsRow1 = detailsSheet.createRow((short) 1);
            detailsRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            detailsRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            detailsRow1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No"));
            detailsRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            detailsRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            detailsRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            detailsRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            detailsRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));
            detailsSheet.autoSizeColumn(0, true);

            detailsRow1.getCell(0).setCellStyle(title2Style);
            detailsRow1.getCell(1).setCellStyle(title2Style);
            detailsRow1.getCell(2).setCellStyle(title2Style);
            detailsRow1.getCell(3).setCellStyle(title2Style);
            detailsRow1.getCell(4).setCellStyle(title2Style);
            detailsRow1.getCell(5).setCellStyle(title2Style);
            detailsRow1.getCell(6).setCellStyle(title2Style);
            detailsRow1.getCell(7).setCellStyle(title2Style);

            Row paidRow0 = paidSheet.createRow((short) 0);
            paidRow0.createCell(0).setCellValue("PAID DETAIL FOR " + client.getName() + " FOR THE TIME PERIOD " + from + " TO " + to);
            paidRow0.setHeight((short) 900);
            paidRow0.getCell(0).setCellStyle(titleStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row padiRow1 = paidSheet.createRow((short) 1);
            padiRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            padiRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            padiRow1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No"));
            padiRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            padiRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            padiRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            padiRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            padiRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            padiRow1.getCell(0).setCellStyle(title2Style);
            padiRow1.getCell(1).setCellStyle(title2Style);
            padiRow1.getCell(2).setCellStyle(title2Style);
            padiRow1.getCell(3).setCellStyle(title2Style);
            padiRow1.getCell(4).setCellStyle(title2Style);
            padiRow1.getCell(5).setCellStyle(title2Style);
            padiRow1.getCell(6).setCellStyle(title2Style);
            padiRow1.getCell(7).setCellStyle(title2Style);
            paidSheet.autoSizeColumn(0, true);

            Row unpaidRow0 = unpaidSheet.createRow((short) 0);
            unpaidRow0.createCell(0).setCellValue("UNPAID DETAIL FOR " + branch.getName() + " FOR THE TIME PERIOD " + from + " TO " + to);
            unpaidRow0.setHeight((short) 900);
            unpaidRow0.getCell(0).setCellStyle(titleStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            Row unpaidRow1 = unpaidSheet.createRow((short) 1);
            unpaidRow1.createCell(0).setCellValue(createHelper.createRichTextString("Request Date"));
            unpaidRow1.createCell(1).setCellValue(createHelper.createRichTextString("Name"));
            unpaidRow1.createCell(2).setCellValue(createHelper.createRichTextString(client.getName() + " Ref. No"));
            unpaidRow1.createCell(3).setCellValue(createHelper.createRichTextString("Lot No."));
            unpaidRow1.createCell(4).setCellValue(createHelper.createRichTextString("Total Amount"));
            unpaidRow1.createCell(5).setCellValue(createHelper.createRichTextString("Paid"));
            unpaidRow1.createCell(6).setCellValue(createHelper.createRichTextString("Unpaid"));
            unpaidRow1.createCell(7).setCellValue(createHelper.createRichTextString("Remark"));

            unpaidRow1.getCell(0).setCellStyle(title2Style);
            unpaidRow1.getCell(1).setCellStyle(title2Style);
            unpaidRow1.getCell(2).setCellStyle(title2Style);
            unpaidRow1.getCell(3).setCellStyle(title2Style);
            unpaidRow1.getCell(4).setCellStyle(title2Style);
            unpaidRow1.getCell(5).setCellStyle(title2Style);
            unpaidRow1.getCell(6).setCellStyle(title2Style);
            unpaidRow1.getCell(7).setCellStyle(title2Style);
            unpaidSheet.autoSizeColumn(0, true);
            ArrayList<PaymentDetailReport> paymentReports = CommonStorage.getRepository().getPaymentDetail(client, branch, from, to);
            i = 0;
            int paidCount = 0;
            int unpaidCount = 0;
            totalAmount = 0;
            totalPaid = 0;
            totalUnpaid = 0;
            for (; i < paymentReports.size(); i++) {
                totalAmount += paymentReports.get(i).getAmount();
                totalPaid += paymentReports.get(i).getPaidAmount();
                totalUnpaid += paymentReports.get(i).getUnPaidAmount();

                Row tempRow = detailsSheet.createRow((short) i + 2);

                tempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                tempRow.getCell(0).setCellStyle(bodyStyle);
                tempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                tempRow.getCell(1).setCellStyle(bodyStyle);
                tempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                tempRow.getCell(2).setCellStyle(bodyStyle);
                tempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                tempRow.getCell(3).setCellStyle(bodyStyle);
                tempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                tempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                tempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                tempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                tempRow.createCell(7).setCellValue("");
                tempRow.getCell(7).setCellStyle(bodyStyle);
                tempRow.setHeight((short) 600);

                if (paymentReports.get(i).getUnPaidAmount() > 0) {
                    Row unpaidTempRow = unpaidSheet.createRow((short) unpaidCount + 2);
                    unpaidCount++;
                    unpaidTempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                    unpaidTempRow.getCell(0).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                    unpaidTempRow.getCell(1).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                    unpaidTempRow.getCell(2).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                    unpaidTempRow.getCell(3).setCellStyle(bodyStyle);
                    unpaidTempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                    unpaidTempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                    unpaidTempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                    unpaidTempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    unpaidTempRow.createCell(7).setCellValue("");
                    unpaidTempRow.getCell(7).setCellStyle(bodyStyle);
                    unpaidTempRow.setHeight((short) 600);
                }
                if (paymentReports.get(i).getPaidAmount() > 0) {
                    Row paidTempRow = paidSheet.createRow((short) paidCount + 2);
                    paidCount++;
                    paidTempRow.createCell(0).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getOrderDate()));
                    paidTempRow.getCell(0).setCellStyle(bodyStyle);
                    paidTempRow.createCell(1).setCellValue(paymentReports.get(i).getName());
                    paidTempRow.getCell(1).setCellStyle(bodyStyle);
                    paidTempRow.createCell(2).setCellValue(paymentReports.get(i).getRefNo());
                    paidTempRow.getCell(2).setCellStyle(bodyStyle);
                    paidTempRow.createCell(3).setCellValue(createHelper.createRichTextString("" + paymentReports.get(i).getLotNo()));
                    paidTempRow.getCell(3).setCellStyle(bodyStyle);
                    paidTempRow.createCell(4).setCellValue(paymentReports.get(i).getAmount());
                    paidTempRow.getCell(4).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(5).setCellValue(paymentReports.get(i).getPaidAmount());
                    paidTempRow.getCell(5).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(6).setCellValue(paymentReports.get(i).getUnPaidAmount());
                    paidTempRow.getCell(6).setCellStyle(styleCurrencyFormat);
                    paidTempRow.createCell(7).setCellValue("");
                    paidTempRow.getCell(7).setCellStyle(bodyStyle);
                    paidTempRow.setHeight((short) 600);
                }
            }

            Row detailsTotalRow = detailsSheet.createRow((short) i + 2);
            detailsTotalRow.createCell(0).setCellValue("Grand Total");
            detailsTotalRow.getCell(0).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(1).setCellValue("");
            detailsTotalRow.getCell(1).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(2).setCellValue("");
            detailsTotalRow.getCell(2).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(3).setCellValue("");
            detailsTotalRow.getCell(3).setCellStyle(bodyStyle);
            detailsTotalRow.createCell(4).setCellValue(totalAmount);
            detailsTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(5).setCellValue(totalPaid);
            detailsTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(6).setCellValue(totalUnpaid);
            detailsTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            detailsTotalRow.createCell(7).setCellValue("");
            detailsTotalRow.getCell(7).setCellStyle(bodyStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, 0, 3));

            Row detailsSignRow1 = detailsSheet.createRow((short) i + 3);
            detailsSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 0, 3));
            detailsSignRow1.getCell(0).setCellStyle(footerStyle);
            detailsSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            detailsSignRow1.getCell(5).setCellStyle(footerStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 3, i + 3, 5, 8));
            detailsSignRow1.setHeight((short) 600);

            Row detailsSignRow2 = detailsSheet.createRow((short) i + 4);
            detailsSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            detailsSignRow2.getCell(0).setCellStyle(footerStyle);

            detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 3));
            detailsSignRow2.createCell(5).setCellValue("Signature   : _____________________");
            detailsSignRow2.getCell(5).setCellStyle(footerStyle);
            detailsSheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 5, 8));
            detailsSignRow2.setHeight((short) 600);

            detailsSheet.autoSizeColumn(1, true);
            detailsSheet.autoSizeColumn(3, true);
            detailsSheet.autoSizeColumn(4, true);
            detailsSheet.autoSizeColumn(5, true);
            detailsSheet.autoSizeColumn(6, true);

            Row unpaidTotalRow = unpaidSheet.createRow((short) unpaidCount + 2);
            unpaidTotalRow.createCell(0).setCellValue("Grand Total");
            unpaidTotalRow.getCell(0).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(1).setCellValue("");
            unpaidTotalRow.getCell(1).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(2).setCellValue("");
            unpaidTotalRow.getCell(2).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(3).setCellValue("");
            unpaidTotalRow.getCell(3).setCellStyle(bodyStyle);
            unpaidTotalRow.createCell(4).setCellValue(totalAmount);
            unpaidTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(5).setCellValue(totalPaid);
            unpaidTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(6).setCellValue(totalUnpaid);
            unpaidTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            unpaidTotalRow.createCell(7).setCellValue("");
            unpaidTotalRow.getCell(7).setCellStyle(bodyStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 2, unpaidCount + 2, 0, 2));

            Row unpaidSignRow1 = unpaidSheet.createRow((short) unpaidCount + 3);
            unpaidSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 3, unpaidCount + 3, 0, 3));
            unpaidSignRow1.getCell(0).setCellStyle(footerStyle);
            unpaidSignRow1.createCell(5).setCellValue("Approved By:" + approver.getFullName());
            unpaidSignRow1.getCell(5).setCellStyle(footerStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 3, unpaidCount + 3, 5, 8));
            unpaidSignRow1.setHeight((short) 600);

            Row unpaidSignRow2 = unpaidSheet.createRow((short) unpaidCount + 4);
            unpaidSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            unpaidSignRow2.getCell(0).setCellStyle(footerStyle);

            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 4, unpaidCount + 4, 0, 3));
            unpaidSignRow2.createCell(5).setCellValue("Signature   : _____________________");
            unpaidSignRow2.getCell(5).setCellStyle(footerStyle);
            unpaidSheet.addMergedRegion(new CellRangeAddress(unpaidCount + 4, unpaidCount + 4, 5, 8));
            unpaidSignRow2.setHeight((short) 600);

            unpaidSheet.autoSizeColumn(1, true);
            unpaidSheet.autoSizeColumn(3, true);
            unpaidSheet.autoSizeColumn(4, true);
            unpaidSheet.autoSizeColumn(5, true);
            unpaidSheet.autoSizeColumn(6, true);
            unpaidSheet.autoSizeColumn(7, true);

            Row paidTotalRow = paidSheet.createRow((short) paidCount + 2);
            paidTotalRow.createCell(0).setCellValue("Grand Total");
            paidTotalRow.getCell(0).setCellStyle(bodyStyle);
            paidTotalRow.createCell(1).setCellValue("");
            paidTotalRow.getCell(1).setCellStyle(bodyStyle);
            paidTotalRow.createCell(2).setCellValue("");
            paidTotalRow.getCell(2).setCellStyle(bodyStyle);
            paidTotalRow.createCell(3).setCellValue("");
            paidTotalRow.getCell(3).setCellStyle(bodyStyle);
            paidTotalRow.createCell(4).setCellValue(totalAmount);
            paidTotalRow.getCell(4).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(5).setCellValue(totalPaid);
            paidTotalRow.getCell(5).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(6).setCellValue(totalUnpaid);
            paidTotalRow.getCell(6).setCellStyle(styleCurrencyFormat);
            paidTotalRow.createCell(7).setCellValue("");
            paidTotalRow.getCell(7).setCellStyle(bodyStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 2, paidCount + 2, 0, 2));

            Row paidSignRow1 = paidSheet.createRow((short) paidCount + 3);
            paidSignRow1.createCell(0).setCellValue("Prepared By:" + CommonStorage.getCurrentUser(request).getFullName());
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 3, paidCount + 3, 0, 3));
            paidSignRow1.getCell(0).setCellStyle(footerStyle);
            paidSignRow1.createCell(4).setCellValue("Approved By:" + approver.getFullName());
            paidSignRow1.getCell(4).setCellStyle(footerStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 3, paidCount + 3, 4, 6));
            paidSignRow1.setHeight((short) 600);

            Row paidSignRow2 = paidSheet.createRow((short) paidCount + 4);
            paidSignRow2.createCell(0).setCellValue("Signature   : _____________________");
            paidSignRow2.getCell(0).setCellStyle(footerStyle);

            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 4, paidCount + 4, 0, 3));
            paidSignRow2.createCell(4).setCellValue("Signature   : _____________________");
            paidSignRow2.getCell(4).setCellStyle(footerStyle);
            paidSheet.addMergedRegion(new CellRangeAddress(paidCount + 4, paidCount + 4, 4, 6));
            paidSignRow2.setHeight((short) 600);

            paidSheet.autoSizeColumn(1, true);
            paidSheet.autoSizeColumn(3, true);
            paidSheet.autoSizeColumn(4, true);
            paidSheet.autoSizeColumn(5, true);
            paidSheet.autoSizeColumn(6, true);
            paidSheet.autoSizeColumn(7, true);

            wb.write(fileOut);
        } catch (Exception ex) {
            CommonTasks.logMessage("Report Generation Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while generating test report");
        }
    }
}
