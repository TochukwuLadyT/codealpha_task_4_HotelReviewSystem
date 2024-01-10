import React, { useEffect, useState } from "react";
import {deleteReview, getAllReviews} from "../utils/ApiFunctions"; 
import ReviewFilter from "../common/ReviewFilter";
import { Col, Row } from "react-bootstrap";
import RoomPaginator from "../common/RoomPaginator";
import { FaTrashAlt } from "react-icons/fa";

const ExistingReviews = () => {
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
      const result = await getAllReviews(); // Call getAllReviews as a function
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

  const handleDelete = async (reviewId) => {
    try {
      const result = await deleteReview(reviewId);
      if (result === "") {
        setSuccessMessage(`Review No ${reviewId} was deleted`);
        fetchReviews();
      } else {
        console.error(`Error deleting review: ${result.message}`);
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
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
        <p>Loading existing reviews</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
              <h4>User Reviews</h4>
            </div>

            <Row>
              <Col md={6} className="mb-2 md-mb-0">
                <ReviewFilter data={reviews} setFilteredData={setFilteredReviews} />
              </Col>
            </Row>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>Rating</th>
                  <th>Comment</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
     
                {currentReviews.map((rat) => (
                  <tr key={rat.id} className="text-center">
                    <td>{rat.id}</td>
                    <td>{rat.rating}</td>
                    <td>{rat.comment}</td>
                    <td className="gap-2">
                      <button
                        className="btn btn-danger btn-sm ml-5"
                        onClick={() => handleDelete(rat.id)}>
                        <FaTrashAlt />
                      </button>
                    </td>
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

export default ExistingReviews;
