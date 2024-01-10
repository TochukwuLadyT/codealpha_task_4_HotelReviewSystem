import React, { useContext } from "react"
import MainHeader from "../layout/MainHeader"
import HotelService from "../common/HotelService"
import RoomCarousel from "../common/RoomCarousel"
import RoomSearch from "../common/RoomSearch"
import { Link } from "react-router-dom"


const Home = () => {
	return (
		<section>
			{/* {message && <p className="text-warning px-5">{message}</p>}
			{currentUser && (
				<h6 className="text-success text-center"> You are logged-In as {currentUser}</h6>
			)} */}
			<MainHeader />
			<p></p>
			<div className="container" >
				<RoomSearch/>
				<Link to= {"/review"}>
                 Add Rating
               </Link><br />
				<RoomCarousel/>	
				<br></br>
				<Link to= {"/clients-reviews"}>
                 What our clients say
               </Link><br />
			   <br></br>
				<HotelService />
				
			</div>
		</section>
	)
}

export default Home