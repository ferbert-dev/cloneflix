import React, { useState, useEffect, useRef } from "react";
import { useAuth } from "../utils/useAuth";
import MovieInfo from "./MovieInfo";

// Movies page.
// Fetches movies from the backend and renders the MovieInfo components
// for each fetched movie.
function Movie() {

    const auth = useAuth();
    const genreDropdown = useRef();
    const [page, setPage] = useState(0);
    const [movies, setMovies] = useState([]);
    const [moreFetchable, setMoreFetchable] = useState(true);

    useEffect(() => {
        fetch(`/api/v1/movies?page=${page}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then((response) => response.json())
        .then(movieList => {
            console.log(movieList);
            if(page !== 0) {
                setMovies(s => s.concat(movieList));
            } else {
                setMovies(movieList);
            }
        });
    }, [auth, page])

    function handleGenreSelectChange() {
        const value = genreDropdown.current.value;
        let url = '';
        (value === '-') ? url = '/api/v1/movies' : url = `/api/v1/movies/preview/${value}`
        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then((response) => response.json())
        .then(moviesByGenre => {
            setMoreFetchable((moviesByGenre.length === 12) ? true : false);
            setMovies(moviesByGenre);
            console.log(moviesByGenre);
        })
    }

    function nextPage() {
        setPage(p => p + 1)
    }

    return(
        <div className="container is-fluid">
            <div className="field is-horizontal">
                <div className="field-body">
                    <div className="field is-expanded">
                        <div className="control">
                            <div className="select is-success">
                                <select onChange={handleGenreSelectChange} ref={genreDropdown}>
                                    <option value="-">Genre wählen</option>
                                    <option>Action</option>
                                    <option>Adventure</option>
                                    <option>Action & Adventure</option>
                                    <option>Animation</option>
                                    <option>Comedy</option>
                                    <option>Crime</option>
                                    <option>Drama</option>
                                    <option>Reality</option>
                                    <option>Sci-Fi & Fantasy</option>
                                    <option>Thriller</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="columns is-multiline">
                {movies.map(movie => (
                        <div key={movie.id} className="column is-2 title-tile">
                            <MovieInfo title={movie} />
                        </div>
                ))}
            </div>
            {moreFetchable && 
            <div className="has-text-centered">
                <button className="button is-success" onClick={nextPage}>Mehr</button>
            </div>}
        </div>
    );
}

export default Movie;