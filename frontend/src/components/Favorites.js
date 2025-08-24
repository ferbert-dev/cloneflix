import React, { useState, useEffect } from "react";
import { useAuth } from "../utils/useAuth";
import { NavLink } from "react-router-dom";
import MovieInfo from "./MovieInfo";

function Favorites() {

    const auth = useAuth();
    const [favorites, setFavorites] = useState([]);

    useEffect(() => {
        async function fetchFavoriteMoviesAndSeries() {
            const [moviesResponse, seriesResponse] = await Promise.all([
                fetch('/api/v1/users/favorite/movies', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${auth.user.accessToken}`
                    }
                }),
                fetch('/api/v1/users/favorite/series', {
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
        
        fetchFavoriteMoviesAndSeries().then(([movies, series]) => {
            const allTopTitles = movies.concat(series);
            console.log(allTopTitles);
            setFavorites(allTopTitles);
        }).catch(error => {
            console.log('Anfrage fehlgeschlagen: ', error);
        })
    }, [auth])

    return(
        <div className={"container is-fluid"}>
            <div className="columns is-multiline">
                {favorites.length ? favorites.map(favorite => (
                        <div key={favorite.id} className="column is-2 title-tile">
                            <MovieInfo title={favorite} />
                        </div>
                )) : 
                <section className="section is-medium">
                    <h1 className="title has-text-white">Hier scheint noch nichts zu sein...</h1>
                    <h2 className="subtitle has-text-white">
                        Schau doch mal bei <NavLink to="/series" className="has-text-success" >Serien</NavLink> und <NavLink to="/movies" className="has-text-success" >Filme</NavLink> vorbei!
                    </h2>
                </section>}
            </div>
        </div>
    );
}

export default Favorites;