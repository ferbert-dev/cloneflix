const API_KEY = "f4bdaa6589bf2478215b7cdf4c18f52f";

const requests = {
  fetchRecommended: `/api/v1/recommendations/movies`,
  fetchTopRated: `/api/v1/recommendations/getTopFavoriteMovies`,
  fetchNetflixOriginals: `/discover/tv?api_key=${API_KEY}&with_networks=213`,
  fetchActionMovies: `/api/v1/movies/preview/Action`,
  fetchComedyMovies: `/api/v1/movies/preview/Comedy`,
  fetchHorrorMovies: `/api/v1/movies/preview/Horror`,
  fetchRomanceMovies: `/api/v1/movies/preview/Romance`,
  fetchAnimationMovies: `/api/v1/movies/preview/Animation`,
};

export default requests;