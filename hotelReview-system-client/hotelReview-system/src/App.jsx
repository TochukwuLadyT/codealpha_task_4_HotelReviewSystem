import React from "react"
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "/node_modules/bootstrap/dist/js/bootstrap.min.js"
import AddLocation from "./components/location/AddLocation"
import AddRoom from "./components/room/AddRoom"
import ExistingRooms from "./components/room/ExistingRooms"
import EditRoom from "./components/room/EditRoom"
import Home from "./components/home/Home"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import Footer from "./components/layout/Footer"
import NavBar from "./components/layout/Navbar"
import Admin from "./components/admin/Admin"
import Checkout from "./components/bookings/Checkout"
import BookingSuccess from "./components/bookings/BookingSuccess"
import Bookings from "./components/bookings/Bookings"
import FindBooking from "./components/bookings/FindBooking"
import Review from "./components/review/Review"
import ExistingReviews from "./components/review/ExistingReviews"
import ClientsExistingReviews from "./components/review/ClientsExistingReviews"


function App() {

  return (
    <>

    <main>
      <Router>
        <NavBar/>
        <Routes>
          <Route>
            <Route path="/" element={<Home/>}/>
            <Route path="/hotel-locations" element={<AddLocation/>}/>
            <Route path="/add-rooms" element={<AddRoom/>}/>
            <Route path="/edit-room/:roomId" element={<EditRoom/>}/>
            <Route path="/existing-rooms" element={<ExistingRooms/>}/>
            <Route path="/booking-success" element={<BookingSuccess/>}/>
            <Route path="/book-room/:roomId" element={<Checkout/>}/>
            <Route path="/existing-bookings" element={<Bookings/>}/>
            <Route path="/find-booking" element={<FindBooking/>}/>
            <Route path="/review" element={<Review/>}/>
            <Route path="/existing-reviews" element={<ExistingReviews/>}/>
            <Route path="/clients-reviews" element={<ClientsExistingReviews/>}/>
            <Route path="/admin" element={<Admin/>}/>
          </Route>
        </Routes>
      </Router>
      <Footer/>
    </main>
    </>
  )
}

export default App
