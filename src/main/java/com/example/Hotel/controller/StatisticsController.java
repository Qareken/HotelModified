package com.example.Hotel.controller;

import com.example.Hotel.service.impl.StatisticService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/hotels/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    private final StatisticService service;
    @GetMapping("/userStatistics")
    public void exportUserRegistrationStatistics(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=userRegistration.xlsx");

        service.exportUserRegistrationsToCsv(httpServletResponse.getOutputStream());
        httpServletResponse.getOutputStream().close();
    }
    @GetMapping("/bookingStatistics")
    public void exportBookingStatistics(HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=bookingRegistration.xlsx");
       service.exportRoomBookingsToCsv(httpServletResponse.getOutputStream());
       httpServletResponse.getOutputStream().close();
    }
}
