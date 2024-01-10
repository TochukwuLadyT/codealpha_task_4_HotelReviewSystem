import React, { useState } from "react";

const ReviewFilter = ({ data, setFilteredData }) => {
  const [filter, setFilter] = useState("");

  const handleSelectChange = (e) => {
    const selectedType = e.target.value;
    setFilter(selectedType);

    if (Array.isArray(data)) {
      const filteredReviews = data.filter((review) =>
        review.rating.toString().toLowerCase().includes(selectedType.toLowerCase())
      );
      setFilteredData(filteredReviews);
    }
  };

  const clearFilter = () => {
    setFilter("");
    setFilteredData(data);
  };

  const rating = Array.isArray(data)
    ? ["", ...new Set(data.map((review) => review.rating))]
    : [];

  return (
    <div className="input-group mb-3">
      <span className="input-group-text" id="review-filter">
        Rating
      </span>
      <select
        className="form-select"
        aria-label="Rating"
        value={filter}
        onChange={handleSelectChange}
      >
        <option value="">Select Rating</option>
        {rating.map((type, index) => (
          <option key={index} value={type}>
            {type}
          </option>
        ))}
      </select>
      <button className="btn btn-hotel" type="button" onClick={clearFilter}>
        Clear Filter
      </button>
    </div>
  );
};

export default ReviewFilter;
