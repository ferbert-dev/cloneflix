import React, { useState, useEffect, useRef } from "react";
import { useAuth } from "../utils/useAuth";
import MovieInfo from "./MovieInfo";
import NoContent from "./NoContent";

function Serie() {

    const auth = useAuth();
    const genreDropdown = useRef();
    const [series, setSeries] = useState([]);

    useEffect(() => {
        fetchAllSeries();
    }, [])

    function handleGenreSelectChange(e) {
        const value = genreDropdown.current.value;
        if(value === '-') {
            fetchAllSeries();
            return;
        }
        fetch(`/api/v1/series/genre/${value}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then((response) => response.json())
        .then(seriesByGenre => {
            setSeries(seriesByGenre);
            console.log(seriesByGenre);
        })
    }

    function fetchAllSeries() {
        fetch('/api/v1/series', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then((response) => response.json())
        .then(serieList => {
            console.log(serieList);
            setSeries(serieList);
        });
    }

    return(
        <div className="container is-fluid">
            <div className="field is-horizontal">
                <div className="field-body">
                    <div className="field is-expanded">
                        <div className="control">
                            <div className="select is-success">
                                <select onChange={handleGenreSelectChange} ref={genreDropdown} >
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
                {series.length ? series?.map(serie => (
                        <div key={serie.id} className="column is-2 title-tile">
                            <MovieInfo title={serie} />
                        </div>
                )) : <NoContent />}
            </div>
        </div>
    );
}

export default Serie;