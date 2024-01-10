import React, { useState } from 'react'
import { addLocation } from '../utils/ApiFunctions'

const AddLocation = () => {
    const [newLocation, setNewLocation] = useState({
        photo : null,
        location: "",
        description: ""
    })

    const [imagePreview, setImagePreview] = useState("")
    const[successMessage, setSuccessMessage] = useState("")
    const[errorMessage, setErrorMessage] = useState("")

    const handleLocationInputChange = (e) =>{
        const name = e.target.name
        let value = e.target.value
        setNewLocation({...newLocation, [name]: value})
    }

    const handleImageChange = (e) =>{
        const selectedImage = e.target.files[0]
        setNewLocation({...newLocation, photo: selectedImage })
        setImagePreview(URL.createObjectURL(selectedImage))
    }
  
    const handleSubmit = async (e) => {
		e.preventDefault()
		try {
			const success = await addLocation(newLocation.photo, newLocation.location, newLocation.description)
			if (success !== undefined) {
				setSuccessMessage("Location added successfully !")
				setNewLocation({ photo: null, location: "", description: "" })
				setImagePreview("")
				setErrorMessage("")
			} else {
				setErrorMessage("Error adding new location")
			}
		} catch (error) {
			setErrorMessage(error.message)
		}
		setTimeout(() => {
			setSuccessMessage("")
			setErrorMessage("")
		}, 3000)
	}

  return (
    <>
    <section className="container, mt-5 mb-5">
        <div className="row justify-content-center">
            <div className="col-md-8 col-lg-6">
                <h2 className="mt-5 mb-2">Add New Hotel Location</h2>
                {successMessage && (
							<div className="alert alert-success fade show"> {successMessage}</div>
						)}

						{errorMessage && <div className="alert alert-danger fade show"> {errorMessage}</div>}
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label  htmlFor="location" className="form-label">
                            Location</label>
                        <input className="form-control" required 
                        id = "location"
                        name = "location"
                        type = "text"
                        value={newLocation.location}
                        onChange={handleLocationInputChange}
                        /> 
                        <div></div>
                    </div>
                    <div className="mb-3">
                        <label  htmlFor="description" className="form-label">Description</label>
                        <input className="form-control" required 
                        id = "description"
                        name = "description"
                        type = "text"
                        value={newLocation.description}
                        onChange={handleLocationInputChange}
                        />  
                    </div>
                    <div className="mb-3">
								<label htmlFor="photo" className="form-label">
									Location Photo
								</label>
								<input
									required
									name="photo"
									id="photo"
									type="file"
									className="form-control"
									onChange={handleImageChange}
								/>
								{imagePreview && (
									<img
										src={imagePreview}
										alt="Preview  locatiion photo"
										style={{ maxWidth: "400px", maxHeight: "400px" }}
										className="mb-3"></img>
								)}
							</div>
                    <div className="d-grid d-md-flex mt-2">
                    <Link to={"/existing-rooms"} className="btn btn-outline-info">
                                    Back      
                                </Link>
                                <button type="submit" className="btn btn-outline-primary ml-5">
									Save Location
								</button>
                    </div>
                </form>
            </div>
        </div>
    </section>
    
    </>
  )
}

export default AddLocation
