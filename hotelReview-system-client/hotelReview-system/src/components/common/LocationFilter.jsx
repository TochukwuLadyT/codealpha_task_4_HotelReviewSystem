import React, { useState } from "react"

const LocationFilter = ({ data, setFilteredData }) => {
	const [filter, setFilter] = useState("")

	const handleSelectChange = (e) => {
		const selectedType = e.target.value
		setFilter(selectedType)

		const filteredRooms = data.filter((locat) =>
			locat.location.toLowerCase().includes(selectedType.toLowerCase())
		)
		setFilteredData(filteredRooms)
	}

	const clearFilter = () => {
		setFilter("")
		setFilteredData(data)
	}

	const Locationss = ["", ...new Set(data.map((locat) => locat.locat))]

	return (
		<div className="input-group mb-3">
			<span className="input-group-text" id="location-filter">
				FIlter locations
			</span>
			<select
				className="form-select"
				aria-label="location filter"
				value={filter}
				onChange={handleSelectChange}>
				<option value="">select location....</option>
				{Locationss.map((type, index) => (
					<option key={index} value={String(type)}>
						{String(type)}
					</option>
				))}
			</select>
			<button className="btn btn-hotel" type="button" onClick={clearFilter}>
				Clear Filter
			</button>
		</div>
	)
}
export default LocationFilter
