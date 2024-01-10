package com.ladyt.hotelReviewsystem.controller;

import com.ladyt.hotelReviewsystem.exception.PhotoRetrievalException;
import com.ladyt.hotelReviewsystem.exception.ResourceNotFoundException;
import com.ladyt.hotelReviewsystem.model.HotelRoom;
import com.ladyt.hotelReviewsystem.model.HotelRoomBooking;
import com.ladyt.hotelReviewsystem.response.HotelRoomResponse;
import com.ladyt.hotelReviewsystem.service.HotelBookingService;
import com.ladyt.hotelReviewsystem.service.IHotelLocationService;
import com.ladyt.hotelReviewsystem.service.IHotelRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/hotelRoom")
public class HotelRoomController {
    private final IHotelRoomService hotelRoomService;
    private final IHotelLocationService hotelLocationService;
    private final HotelBookingService hotelBookingService;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/new-hotel-room")
    public ResponseEntity<HotelRoomResponse>addNewHotelRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("location") String location,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {

        HotelRoom savedHotelRoom = hotelRoomService.addNewHotelRoom(photo, location, roomType, roomPrice);
        HotelRoomResponse response = new HotelRoomResponse(savedHotelRoom.getId(), savedHotelRoom.getLocation(),
                savedHotelRoom.getRoomType(), savedHotelRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations")
    public List<String> getLocations(){
        return hotelLocationService.getAllLocations();
    }

    @GetMapping("/room-types")
    public List<String>getRoomTypes(){
        return hotelRoomService.getAllRoomTypes();
    }

    @GetMapping("/all-rooms")
    public ResponseEntity<List<HotelRoomResponse>>getAllHotelRooms() throws SQLException {
        List<HotelRoom> hotelRooms = hotelRoomService.getAllHotelRooms();
        List<HotelRoomResponse> hotelRoomResponses = new ArrayList<>();
        for(HotelRoom hotelRoom : hotelRooms){
            byte[] photoBytes = hotelRoomService.getHotelRoomPhotoByRoomId(hotelRoom.getId());
            if(photoBytes != null && photoBytes.length>0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                HotelRoomResponse hotelRoomResponse = getHotelRoomResponse(hotelRoom);
                hotelRoomResponse.setPhoto(base64Photo);
                hotelRoomResponses.add(hotelRoomResponse);
            }
        }
        return ResponseEntity.ok(hotelRoomResponses);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/room/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomId") Long id){
        hotelRoomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{roomId}")
    public  ResponseEntity<HotelRoomResponse>updateRoom(@PathVariable("roomId")
                                                        Long id,
                                                        @RequestParam(required = false) String location,
                                                        @RequestParam(required = false)String roomType,
                                                        @RequestParam(required = false)BigDecimal roomPrice,
                                                        @RequestParam(required = false)MultipartFile photo) throws IOException, SQLException {
        byte[] photoBytes = photo != null && !photo.isEmpty() ?
                photo.getBytes() : hotelRoomService.getHotelRoomPhotoByRoomId(id);
        Blob photoBlob = photoBytes != null && photoBytes.length >0 ? new SerialBlob(photoBytes): null;
        HotelRoom theRoom = hotelRoomService.updateRoom(id, location, roomType, roomPrice, photoBytes);
        theRoom.setPhoto(photoBlob);
        HotelRoomResponse roomResponse = getHotelRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);

    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<HotelRoomResponse>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("location") String location,
            @RequestParam("roomType") String roomType) throws SQLException {
        List<HotelRoom> availableRooms = hotelRoomService.getAvailableRooms(checkInDate, checkOutDate, location, roomType);
        List<HotelRoomResponse> hotelRoomResponses = new ArrayList<>();
        for (HotelRoom hotelRoom : availableRooms) {
            byte[] photoBytes = hotelRoomService.getHotelRoomPhotoByRoomId(hotelRoom.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                HotelRoomResponse hotelRoomResponse = getHotelRoomResponse(hotelRoom);
                hotelRoomResponse.setPhoto(photoBase64);
                hotelRoomResponses.add(hotelRoomResponse);
            }
        }
        if(hotelRoomResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(hotelRoomResponses);
        }
    }


    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<HotelRoomResponse>> getRoomById(@PathVariable("roomId") Long id){
        Optional<HotelRoom> theRoom = hotelRoomService.getRoomById(id);
        return theRoom.map(room -> {
            HotelRoomResponse roomResponse = getHotelRoomResponse(room);
            return  ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }


    private HotelRoomResponse getHotelRoomResponse(HotelRoom hotelRoom) {
        List<HotelRoomBooking> bookings = getAllHotelBookingsByRoomId(hotelRoom.getId());
        byte[] photoBytes = null;
        Blob photoBlob = hotelRoom.getPhoto();
        if(photoBlob != null){
            try{
                photoBytes =  photoBlob.getBytes(1, (int) photoBlob.length());
            }catch (SQLException e){
                throw new PhotoRetrievalException("Error getting Photo");
            }
        }
        return new HotelRoomResponse(hotelRoom.getId(),
                hotelRoom.getLocation(),
                hotelRoom.getRoomType(),
                hotelRoom.getRoomPrice(),
                hotelRoom.isBooked(),  photoBytes);

    }

    private List<HotelRoomBooking> getAllHotelBookingsByRoomId(Long roomId) {
        return hotelBookingService.getAllHotelBookingsByRoomId(roomId);
    }


}
