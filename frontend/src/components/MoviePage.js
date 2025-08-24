import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from "../utils/useAuth";
import Backdrop from "./Backdrop";
import LoadingAnimation from './LoadingAnimation';
import TitleDetails from "./TitleDetails";
import TitleVideo from "./TitleVideo";

const baseImgUrl = "https://image.tmdb.org/t/p/original"

function MoviePage() {

    const auth = useAuth();
    const [movie, setMovie] = useState({});
    const [loaded, setLoaded] = useState(false)
    const { id } = useParams();

    useEffect(() => {
        setLoaded(false);
        async function fetchMovie(){
            const response = await fetch(`/api/v1/movies/${id}`,{
                                method: 'GET',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': `Bearer ${auth.user.accessToken}`
                                }
                            })
            console.log(response);
            const movieData = await response.json();
            return movieData;
        }

        fetchMovie()
        .then(movieData => {
            setMovie(movieData);
        })
        .catch(error => {
            console.log('Anfrage fehlgeschlagen: ', error);
        })
        .finally(() => setLoaded(true));
    }, [id, auth]);

    return(
        <>
            {loaded && 
                <>
                <Backdrop src={`${baseImgUrl}${movie?.backdrop}`}
                          title={movie?.title}
                          description={movie?.description}
                          date={movie?.releaseDate}
                          runtime={movie?.runtime}
                          genres={movie?.genres}
                          starring={movie?.starring}
                          id={movie?.id}
                          /> 
                <TitleVideo ytids={movie.videos} titleId={id} titleType={'movie'}/> 
                <TitleDetails title={movie} />
                </> }
            {!loaded && <LoadingAnimation />}
        </>
    )
}

export default MoviePage;