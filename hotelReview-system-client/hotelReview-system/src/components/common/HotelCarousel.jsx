import React, { useEffect, useState } from "react"
import { getLocations } from "../utils/ApiFunctions"
import { Card, Carousel, Col, Container, Row } from "react-bootstrap"

const HotelCarousel = () => {
	const [locations, setRooms] = useState([{ id: "", description: "", photo: "" }])
	const [errorMessage, setErrorMessage] = useState("")
	const [isLoading, setIsLoading] = useState(false)

	useEffect(() => {
		setIsLoading(true)
		getLocations()
			.then((data) => {
				setRooms(data)
				setIsLoading(false)
			})
			.catch((error) => {
				setErrorMessage(error.message)
				setIsLoading(false)
			})
	}, [])

	if (isLoading) {
		return <div className="mt-5">Loading locations....</div>
	}
	if (errorMessage) {
		return <div className=" text-danger mb-5 mt-5">Error : {errorMessage}</div>
	}

	return (
		<section className="bg-light mb-5 mt-5 shadow">
			<Container>
				<Carousel indicators={false}>
					{[...Array(Math.ceil(locations.length / 1))].map((_, index) => (
						<Carousel.Item key={index}>
							<Row>
								{locations.slice(index * 1, index * 1+ 1).map((room) => (
									<Col key={room.id} className="mb-4" xs={50} md={50} lg={50}>
										<Card>
											
												<Card.Img
													variant="top"
													src={`data:image/png;base64, ${room.photo}`}
													alt="Location Photo"
													className="w-100"
													style={{ height: "200px" }}
												/>
											
											
										</Card>
									</Col>
								))}
							</Row>
						</Carousel.Item>
					))}
				</Carousel>
			</Container>
		</section>
	)
}

export default HotelCarousel
