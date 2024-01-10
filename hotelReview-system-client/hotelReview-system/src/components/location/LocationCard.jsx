import React, { useContext } from "react"
import { Card, Col } from "react-bootstrap"
import { Link } from "react-router-dom"

const LocationCard = ({ locat }) => {
	return (
		<Col key={locat.id} className="mb-4" xs={12}>
			<Card>
				<Card.Body className="d-flex flex-wrap align-items-center">
					<div className="flex-shrink-0 mr-3 mb-3 mb-md-0">
						<Link to={`/book-room/${locat.id}`}>
							<Card.Img
								variant="top"
								src={`data:image/png;base64, ${locat.photo}`}
								alt="Location Photo"
								style={{ width: "100%", maxWidth: "200px", height: "auto" }}
							/>
						</Link>
					</div>
					<div className="flex-grow-1 ml-3 px-5">
						<Card.Title className="hotel-color">{locat.location}</Card.Title>
						<Card.Title className="description">{locat.description}</Card.Title>
					</div>
					<div className="flex-shrink-0 mt-3">
						<Link to={`/book-room/${locat.id}`} className="btn btn-hotel btn-sm">
							Book this hotel
						</Link>
					</div>
				</Card.Body>
			</Card>
		</Col>
	)
}

export default LocationCard