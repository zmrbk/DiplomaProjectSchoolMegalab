package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import kg.megacom.diplomaprojectschoolmegalab.dto.AttendanceDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Service
public class ReportService {

    // Метод для создания отчета в формате Excel
    public void generateReport(HttpServletResponse response, List<String > headers, List<List<Object>> data) throws IOException {
        // Создаем Excel книгу
        Workbook workbook = new XSSFWorkbook();

        // Создаем лист в Excel
        Sheet sheet = workbook.createSheet("Отчет");

        // Создаем стиль для заголовков
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Создаем строку заголовков
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }

        // Заполняем строки данными
        int rowNum = 1;
        for (List<?> rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.size(); i++) {
                Cell cell = row.createCell(i);
                Object value = rowData.get(i);

                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                }
            }
        }

        // Автоматическая настройка ширины столбцов
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Настройка заголовка ответа для скачивания файла
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");

        // Записываем содержимое книги в ответ
        workbook.write(response.getOutputStream());

        // Закрываем книгу
        workbook.close();
    }
}