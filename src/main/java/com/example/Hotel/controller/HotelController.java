package com.example.Hotel.controller;


import com.example.Hotel.dto.PageRequestDto;
import com.example.Hotel.dto.filter.HotelSearch;
import com.example.Hotel.dto.hotelDto.HotelRate;
import com.example.Hotel.dto.hotelDto.HotelRequestDto;
import com.example.Hotel.dto.hotelDto.HotelResponseDto;
import com.example.Hotel.dto.PageResponseDto;
import com.example.Hotel.service.impl.HotelServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelServiceImpl hotelService;
    @GetMapping
    public ResponseEntity<PageResponseDto<HotelResponseDto>> getAllHotels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(hotelService.findALL(pageRequest));
    }
    @PostMapping
    public ResponseEntity<HotelResponseDto> createHotel(@RequestBody @Valid HotelRequestDto hotelRequestDto){
        return ResponseEntity.ok(hotelService.save(hotelRequestDto));
    }
    @GetMapping("/by-id/{id}")
    public ResponseEntity<HotelResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(hotelService.findById(id));
    }
    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        hotelService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/by-id/{id}")
    public ResponseEntity<HotelResponseDto> updateById(@RequestBody @Valid HotelRequestDto hotelRequestDto, @PathVariable Long id){
        return ResponseEntity.ok(hotelService.update(hotelRequestDto, id));
    }
    @PostMapping("/rate")
     public ResponseEntity<HotelResponseDto> rate(@RequestBody @Valid HotelRate hotelRate){

        return ResponseEntity.ok(hotelService.changeRate(hotelRate));
    }
    @PostMapping("/search")
    public ResponseEntity<PageResponseDto<HotelResponseDto>> search(@RequestBody HotelSearch hotelSearch, @RequestBody PageRequestDto pageRequestDto){
        return ResponseEntity.ok(hotelService.search(hotelSearch, pageRequestDto));
    }
}
