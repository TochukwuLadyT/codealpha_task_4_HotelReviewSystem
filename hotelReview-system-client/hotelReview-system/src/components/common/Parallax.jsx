import React from "react"
import { Container } from "react-bootstrap"

const Parallax = () => {
	return (
		<div className="parallax mb-5">
			<Container className="text-center px-5 py-5 justify-content-center">
				<div className="animated-texts bounceIn">
					<h1>
						Experience the Best hospitality at <span className="hotel-color">Hotel Review Systeml</span>
					</h1>
					<h3>Book your Room at your Choice Location</h3>
				</div>
			</Container>
		</div>
	)
}

export default Parallax