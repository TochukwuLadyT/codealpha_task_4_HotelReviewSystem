package com.ladyt.hotelReviewsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "check_In")
    private LocalDate checkInDate;
    @Column(name = "check_Out")
    private LocalDate checkOutDate;
    @Column(name = "guest_FullName")
    private String guestFullName;
    @Column(name = "guest_Email")
    private String guestEmail;
    @Column(name = "no_OfAdults")
    private int NumOfAdults;
    @Column(name = "no_OfChildren")
    private  int NumOfChildren;
    @Column(name = "total_NoOfGuest")
    private int totalNumOfGuest;
    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;
    @Column(name="location")
    private  String location;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn (name = "room_Id")
    private HotelRoom room;

    public void calTotalNumOfGuest(){
        this.totalNumOfGuest = this.NumOfChildren + NumOfAdults;
    }

    public void setBookingId(Long id) {
        this.id = id;
    }

    public void setNumOfAdults(int numOfAdults) {
        NumOfAdults = numOfAdults;
        calTotalNumOfGuest();
    }

    public void setNumOfChildren(int numOfChildren) {
        NumOfChildren = numOfChildren;
        calTotalNumOfGuest();
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
