package et.artisan.cn.cps.util;

import et.artisan.cn.cps.dao.MasterRepository;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import et.artisan.cn.cps.entity.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import javax.servlet.http.*;
import org.apache.poi.hssf.usermodel.*;

/**
 *
 * @author Yoseph Berhanu<yoseph@bayeth.com>
 * @since 1.0
 * @version 1.0
 */
public class ImportUtil {

    public static int importData(HttpServletRequest request, ClientRegion clientRegion, Branch branch, byte[] input) {
        int returnValue = 0;
        MasterRepository repository = CommonStorage.getRepository();

        try {
            InputStream is = new ByteArrayInputStream(input);
            Workbook workbook = new HSSFWorkbook(is);
            Sheet firstSheet = workbook.getSheetAt(0);
            if (firstSheet == null) {
                return -1;
            }
            Iterator<Row> iterator = firstSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                ImportData data = new ImportData();
                Cell cell0 = currentRow.getCell(0);
                if (cell0 == null || cell0.getCellType() != Cell.CELL_TYPE_NUMERIC) {
                    continue;
                }
                returnValue++;
                data.setRemark(cell0.getNumericCellValue() + "");
                data.setLotNo((int) cell0.getNumericCellValue());
                data.setName(currentRow.getCell(1).getStringCellValue());
                data.setAmount(currentRow.getCell(2).getNumericCellValue());
                data.setOrderDate(new java.sql.Date(currentRow.getCell(3).getDateCellValue().getTime()));
                data.setRefNo(currentRow.getCell(4).getStringCellValue());
                data.setRegion(currentRow.getCell(5).getStringCellValue());
                data.setDocument(createDocument(request, clientRegion, branch, data.getOrderDate(), data.getProjectCode(), data.getDocumentNumber(), data.getDocumentYear()));
                data.setRegisteredBy(CommonStorage.getCurrentUser(request));
                data.setRegisteredOn(new Timestamp(Instant.now().toEpochMilli()));
                data.setStatus(Constants.STATUS_ACTIVE);
                repository.save(data);
                Payment p = new Payment();
                p.setAmount(data.getAmount());
                p.setDocument(data.getDocument());
                p.setLotNo(data.getLotNo());
                p.setName(data.getName());
                p.setRegisteredBy(data.getRegisteredBy());
                p.setRegisteredOn(data.getRegisteredOn());
                p.setRemark("Imported " + data.getRemark());
                p.setStatus(Constants.STATUS_ACTIVE);
                repository.save(p);
            }
        } catch (Exception ex) {
            CommonTasks.logMessage("Data Import Error", Message.MESSEGE_TYPE_ERROR, ex, "Errro while importing data");
        }
        return returnValue;
    }

    private static Document createDocument(HttpServletRequest request, ClientRegion clientRegion, Branch branch, Date orderDate, String projectCode, String documenNo, int documentYear) {
        Document returnValue = CommonStorage.getRepository().getDocument(clientRegion, branch, projectCode, documenNo, documentYear);
        if (returnValue == null) {
            User user = CommonStorage.getCurrentUser(request);
            Timestamp now = new Timestamp(Instant.now().toEpochMilli());
            returnValue = new Document();
            returnValue.setApprovedBy(user);
            returnValue.setApprovedOn(now);
            returnValue.setAssignedTo(user);
            returnValue.setAssignedOn(now);
            returnValue.setBranch(branch);
            returnValue.setClientRegion(clientRegion);
            returnValue.setClosedBy(null);
            returnValue.setClosedOn(null);
            returnValue.setDocumentYear(documentYear);
            returnValue.setInComingDate(orderDate);
            returnValue.setInComingDocumentNo(documenNo);
            returnValue.setOutGoingDate(orderDate);
            returnValue.setOutGoingDocumentNo("Imported");
            returnValue.setProject(createProject(request, clientRegion, branch, projectCode, documenNo, documentYear));
            returnValue.setRegisteredBy(user);
            returnValue.setRegisteredOn(now);
            returnValue.setRemark("Imported Data");
            returnValue.setStatus(Constants.STATUS_ACTIVE);
            //if (returnValue.valideForSave()) {
                try {
                    returnValue = CommonStorage.getRepository().save(returnValue);
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            //}
        }
        return returnValue;
    }

    private static Project createProject(HttpServletRequest request, ClientRegion clientRegion, Branch branch, String projectCode, String documenNo, int documentYear) {
        Project returnValue = CommonStorage.getRepository().getProject(projectCode);
        if (returnValue == null) {
            User user = CommonStorage.getCurrentUser(request);
            Timestamp now = new Timestamp(Instant.now().toEpochMilli());
            returnValue = new Project();
            returnValue.setClient(clientRegion.getClient());
            returnValue.setCode(projectCode);
            returnValue.setName("Imported Project");
            returnValue.setAmharicName("Imported");
            returnValue.setRemark("Imported Project");
            returnValue.setRegisteredBy(user);
            returnValue.setRegisteredOn(now);
            returnValue.setStatus(Constants.STATUS_ACTIVE);
            if (returnValue.valideForSave()) {
                try {
                    returnValue = CommonStorage.getRepository().save(returnValue);
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }
        return returnValue;
    }
}
