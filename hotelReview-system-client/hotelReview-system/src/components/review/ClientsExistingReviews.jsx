import React, { useEffect, useState } from "react";
import {getAllReviews} from "../utils/ApiFunctions"; 
import ReviewFilter from "../common/ReviewFilter";
import { Col, Row } from "react-bootstrap";
import RoomPaginator from "../common/RoomPaginator";


const ClientsExistingReviews = () => {
  const [reviews, setReviews] = useState([{ id: "", rating: "", comment: "" }]);
  const [currentPage, setCurrentPage] = useState(1);
  const [reviewsPerPage] = useState(8);
  const [isLoading, setIsLoading] = useState(false);
  const [filteredReviews, setFilteredReviews] = useState([{ id: "", rating: "", comment: "" }]);
  const [selectedRating, setSelectedRating] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    fetchReviews();
  }, []);

  const fetchReviews = async () => {
    setIsLoading(true);
    try {
      const result = await getAllReviews();
      setReviews(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (selectedRating === "") {
      setFilteredReviews(reviews);
    } else {
      const filteredReviews = reviews.filter((ratings) => ratings.rating === selectedRating);
      setFilteredReviews(filteredReviews);
    }
    setCurrentPage(1);
  }, [reviews, selectedRating]);

  const handlePaginationClick = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const calculateTotalPages = (filteredReviews, reviewsPerPage, reviews) => {
    const totalReviews = filteredReviews.length > 0 ? filteredReviews.length : reviews.length;
    return Math.ceil(totalReviews / reviewsPerPage);
  };

  const indexOfLastReview = currentPage * reviewsPerPage;
  const indexOfFirstReview = indexOfLastReview - reviewsPerPage;
  const currentReviews = filteredReviews.slice(indexOfFirstReview, indexOfLastReview);

  return (
    <>
      <div className="container col-md-8 col-lg-6">
        {successMessage && <p className="alert alert-success mt-5">{successMessage}</p>}
        {errorMessage && <p className="alert alert-danger mt-5">{errorMessage}</p>}
      </div>

      {isLoading ? (
        <p>Loading reviews</p>
      ) : (
        <>
          <section className="mt-3 mb-3 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
              <h3>Clients Reviews</h3>
            </div>

            <Row>
              <Col md={6} className="mb-2 md-mb-0">
                <ReviewFilter data={reviews} setFilteredData={setFilteredReviews} />
              </Col>
            </Row>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">     
                  <th>Rating</th>
                  <th>Comment</th>       
                </tr>
              </thead>

              <tbody>
     
                {currentReviews.map((rat) => (
                  <tr key={rat.id} className="text-center">
                    <td>{rat.rating}</td>
                    <td>{rat.comment}</td>
                  
                  </tr>
                ))}
              </tbody>
            </table>
            <RoomPaginator
              currentPage={currentPage}
              totalPages={calculateTotalPages(filteredReviews, reviewsPerPage, reviews)}
              onPageChange={handlePaginationClick}
            />
          </section>
        </>
      )}
    </>
  );
};

export default ClientsExistingReviews;
