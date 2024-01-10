import React, { useState, useEffect } from "react"
import { getLocations } from "../utils/ApiFunctions"

const EditLocationSelector = ({ handleInputChange, room }) => {
	const [Locations, setLocations] = useState([""])
	const [showNewLocationInput, setShowNewLocationInput] = useState(false)
	const [newLocation, setNewLocation] = useState("")
    
	useEffect(() => {
		getLocations().then((data) => {
			setLocations(data)
		})
	}, [])

    
	const handleNewLocationInputChange = (e) => {
		setNewLocation(e.target.value)
	}

	const handleAddNewLocation = () => {
		if (newLocation !== "") {
			setLocations([...Locations, newLocation])
			setNewLocation("")
			setShowNewLocationInput(false)
		}
	}

	return (
		<>
			{Locations.length > 0 && (
				<div>
					<select
						required
						className="form-select"
						name="location"
						onChange={(e) => {
							if (e.target.value === "Add New") {
								setShowNewLocationInput(true)
							} else {
								handleInputChange(e)
							}
						}}
						value={room.location}>
						<option value="">Select Location</option>
						{Locations.map((type, index) => (
							<option key={index} value={type}>
								{type}
							</option>
						))}
					</select>
					{showNewLocationInput && (
						<div className="mt-2">
							<div className="input-group">
								<input
									type="text"
									className="form-control"
									placeholder="Enter New Location"
									value={newLocation}
									onChange={handleNewLocationInputChange}
								/>
								<button className="btn btn-hotel" type="button" onClick={handleAddNewLocation}>
									Add
								</button>
							</div>
						</div>
					)}
				</div>
			)}
		</>
	)
}

export default EditLocationSelector