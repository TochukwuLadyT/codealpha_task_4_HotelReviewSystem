import axios from "axios"

export const api   = axios.create(
    {
        baseURL:"http://localhost:9192"
    }
)

// Add a new location to the db
export async function addLocation(photo, location, description){
    const formData = new FormData()
    formData.append("photo", photo)
    formData.append("location", location)
    formData.append("description", description)
    const response = await api.post("/locations/add/new-location", formData)
    if(response.status === 201){
        return true
    }else{
        return false
    }
}

//Get saved locations from database
export async function getLocations(){
    try{
        const response = await api.get("/hotelRoom/locations")
        return response.data
    }catch(error){
        throw new Error("Error getting Locations")

    }
}


// add new room in a location
export async function addRoom(photo, location, roomType, roomPrice){
    const formData =  new FormData()
    formData.append("photo", photo)
    formData.append("location", location)
    formData.append("roomType", roomType)
    formData.append("roomPrice", roomPrice)
    const response = await api.post("/hotelRoom/add/new-hotel-room", formData)
    if(response.status === 201){
        return true
    }else{
        return false
    }
}


//Get saved Rooms from database
export async function getRoomTypes(){
    try{
        const response = await api.get("/hotelRoom/room-types")
        return response.data
    }catch(error){
        throw new Error("Error fetching Rooms Types")
    }
}

//Get all rooms from the db
export async function getAllRooms(){
    try{
        const result = await api.get("/hotelRoom/all-rooms")
        return result.data
    }catch(error){
        throw new Error("Error fetching Rooms")

    }
}



/* Deletes a room by the Id */
export async function deleteRoom(roomId) {
	try {
		const result = await api.delete(`/hotelRoom/delete/room/${roomId}`, {
		})
		return result.data
	} catch (error) {
		throw new Error(`Error deleting room ${error.message}`)
	}
}


/* Update a room */
export async function updateRoom(roomId, roomData) {
	const formData = new FormData()
    formData.append("location", roomData.location)
	formData.append("roomType", roomData.roomType)
	formData.append("roomPrice", roomData.roomPrice)
	formData.append("photo", roomData.photo)
	const response = await api.put(`/hotelRoom/update/${roomId}`, formData,{
	})
	return response
}

/* Gets a room by the id */
export async function getRoomById(roomId) {
	try {
		const result = await api.get(`/hotelRoom/room/${roomId}`)
		return result.data
	} catch (error) {
		throw new Error(`Error fetching room ${error.message}`)
	}
}

// Saves a new booking to the database
export async function bookRoom(roomId, booking){
    try{
        const response = await api.post(`/bookings/hotelRoom/${roomId}/booking`, booking)
        return response.data
    }catch(error){
        if(error.response && error.response.data){
            throw new Error(error.response.data)
        }else{
            throw new Error(`Error booking room: ${error.message}`)
        }
    }
}

// Get all bookings drom db
export async function getAllBookings(){
    try{
        const result = await api.get("/bookings/all-bookings")
        return result.data
    }catch(error){
        throw new Error(`Error fetching bookings: ${error.message}`)
    }
}


/* Get booking by the confirmation code */
export async function getBookingByConfirmationCode(confirmationCode) {
	try {
		const result = await api.get(`/bookings/confirmation/${confirmationCode}`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error find booking : ${error.message}`)
		}
	}
}

/* To cancel user booking */
export async function cancelBooking(bookingId) {
	try {
		const result = await api.delete(`/bookings/booking/${bookingId}/delete`)
		return result.data
	} catch (error) {
		throw new Error(`Error cancelling booking :${error.message}`)
	}
}


/* This function gets all availavle rooms from the database with a given date and a room type */
export async function getAvailableRooms(checkInDate, checkOutDate, location, roomType) {
	const result = await api.get(
		`hotelRoom/available-rooms?checkInDate=${checkInDate}
		&checkOutDate=${checkOutDate}&location=${location}&roomType=${roomType}`
	)
	return result
}


// Saves a new rating and review to the database
export async function addReview(rating, comment){
    const formData = new FormData()
    formData.append("rating", rating)
    formData.append("comment", comment)
    const response = await api.post("/hotelReview/add-review", formData)
    if(response.status === 201){
        return true
    }else{
        return false
    }
}

// Get all ratings and reviews drom db
export async function getAllReviews(){
    try{
        const result = await api.get("/hotelReview/all-reviews")
        return result.data
    }catch(error){
        throw new Error(`Error fetching bookings: ${error.message}`)
    }
}

/* Deletes a review */
export async function deleteReview(reviewId) {
	try {
		const result = await api.delete(`/hotelReview/delete/review/${reviewId}`, {
		})
		return result.data
	} catch (error) {
		throw new Error(`Error deleting room ${error.message}`)
	}
}
