import React, { useState } from 'react'
import { FaStar } from 'react-icons/fa'
import { addReview } from '../utils/ApiFunctions';

const Review = () => {
  const [ratings, setRating] = useState(null);
  const [hover, setHover] = useState(null);
  const [newReview, setNewReview] = useState({
    rating: "",
    comment: ""
})

const[successMessage, setSuccessMessage] = useState("")
const[errorMessage, setErrorMessage] = useState("")

const handleReviewInputChange = (e) =>{
    const name = e.target.name
    let value = e.target.value
    setNewReview({...newReview, [name]: value})
}

const handleSubmit = async (e) => {
    e.preventDefault()
    try {
        const success = await addReview(newReview.rating, newReview.comment)
        if (success !== undefined) {
            setSuccessMessage("Thanks, your suggestions is saved!")
            setNewReview({ rating: "", comment: "" })
            setErrorMessage("")
        } else {
            setErrorMessage("Error saving review")
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
    
        <div className="text-center">
        
                {successMessage && (
							<div className="alert alert-success fade show"> {successMessage}</div>
						)}

						{errorMessage && <div className="alert alert-danger fade show"> {errorMessage}</div>}
        <form onSubmit={handleSubmit}>
        
        <div className='text-center'>
            <br></br>
            <br></br>
        {[...Array(5)].map((star, index) => {
            const currentRating = index + 1
            return(
            <label>
                <input
                type="radio"
                name="rating"
                value={currentRating}
                onClick={() => setRating(currentRating)}
                onChange={handleReviewInputChange}
                />
               <FaStar 
               className="star" 
               size = {20}
               color={currentRating <= (hover || ratings) ? "#ffc107" : "#e4e5e9"}
               onMouseEnter={() => setHover(currentRating)}
               onMouseLeave={() => setHover(null)}
               />
            </label>
            
            )
             
        })}
        
        </div>
        <br />
        <p>Thanks! you have rated us {ratings}</p>
        <div className="mb-3">
            <label  htmlFor="comment" className="form-label">Review</label><br></br>
            <textarea
            id = "comment"
            name = "comment"
            text = "text"
            value={newReview.comment}
            onChange={handleReviewInputChange}
            />  
        </div>
        <button type="submit" className="btn btn-outline-primary ml-5">
         Save
        </button>
      
        </form>
        </div>
  
  )
}

export default Review
