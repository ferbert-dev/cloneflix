import React, { useState, useEffect } from "react";
import { useAuth } from "../utils/useAuth";
import MovieInfo from "./MovieInfo";

function Recommendations() {

    const auth = useAuth();
    const [topTitles, setTopTitles] = useState([]);

    useEffect(() => {
        async function fetchTopMoviesAndSeries() {
            const [moviesResponse, seriesResponse] = await Promise.all([
                fetch('/api/v1/recommendations/getTopFavoriteMovies', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${auth.user.accessToken}`
                    }
                }),
                fetch('/api/v1/recommendations/getTopFavoriteSeries', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${auth.user.accessToken}`
                    }
                })
            ]);

            const movies = await moviesResponse.json();
            const series = await seriesResponse.json();

            return([movies, series]);
        }

        fetchTopMoviesAndSeries().then(([movies, series]) => {
            const allTopTitles = movies.concat(series);
            console.log("Recommendations", allTopTitles)
            setTopTitles(allTopTitles);
        }).catch(error => {
            console.log('Anfrage fehlgeschlagen: ', error);
        })
    }, [auth])

    return(
        <div className={"container is-fluid"}>
            <div className="columns is-multiline">
                {topTitles && topTitles.map(topTitle => (
                        <div key={topTitle.id} className="column is-2 title-tile">
                            <MovieInfo title={topTitle} />
                        </div>
                ))}
            </div>
        </div>
    );
}

export default Recommendations;