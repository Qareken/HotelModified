package com.example.Hotel.service.impl;

import com.example.Hotel.entity.event.BookingEvent;
import com.example.Hotel.entity.event.RegistrationEvent;
import com.example.Hotel.repository.BookingEventRepository;
import com.example.Hotel.repository.RegistrationEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticService {
    private final RegistrationEventRepository registrationEventRepository;
    private final BookingEventRepository bookingEventRepository;
    public void exportUserRegistrationsToCsv(OutputStream outputStream) throws IOException {
        List<RegistrationEvent> registrations = registrationEventRepository.findAll();
        try(Workbook workbook =  new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("userRegistration");
            // Создание стиля для даты и времени
            CellStyle dateTimeCellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateTimeCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("User ID");
            headerRow.createCell(2).setCellValue("Created Date");
            int rowNumber = 1 ;
            for(RegistrationEvent registrationEvent: registrations){
                log.info("userId {} ", registrationEvent.getUserId());
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(registrationEvent.getId());
                row.createCell(1).setCellValue(registrationEvent.getUserId());
                Cell dateCell = row.createCell(2);
                dateCell.setCellValue(registrationEvent.getCreatedTime());
                dateCell.setCellStyle(dateTimeCellStyle);

            }
            workbook.write(outputStream);
        }
    }
    public void exportRoomBookingsToCsv(OutputStream outputStream) throws IOException {
        List<BookingEvent> bookings = bookingEventRepository.findAll();
        try(Workbook workbook =  new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("bookingRegistration");
            Row headerRow = sheet.createRow(0);
            CellStyle dateTimeCellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("User ID");
            headerRow.createCell(2).setCellValue("Room ID");
            headerRow.createCell(3).setCellValue("Arrival Date");
            headerRow.createCell(4).setCellValue("Departure Date");
            headerRow.createCell(5).setCellValue("Created Date");
            int rowNumber = 1 ;
            for(BookingEvent bookingEvent: bookings){
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(bookingEvent.getId());
                row.createCell(1).setCellValue(bookingEvent.getUserId());
                row.createCell(2).setCellValue(bookingEvent.getRoomId());
                Cell dateCell1 = row.createCell(3);
                dateCell1.setCellValue(bookingEvent.getArrivalTime());
                dateCell1.setCellStyle(dateTimeCellStyle);

//                row.createCell(3).setCellValue(bookingEvent.getArrivalTime());
                Cell dateCell2 = row.createCell(4);
                dateCell2.setCellValue(bookingEvent.getDepartureTime());
                dateCell2.setCellStyle(dateTimeCellStyle);
//                row.createCell(4).setCellValue(bookingEvent.getDepartureTime());
                Cell dateCell3 = row.createCell(5);
                dateCell3.setCellValue(bookingEvent.getCreatedTime());
                dateCell3.setCellStyle(dateTimeCellStyle);
//                row.createCell(5).setCellValue(bookingEvent.getCreatedTime());
            }
            workbook.write(outputStream);
        }


    }
}
