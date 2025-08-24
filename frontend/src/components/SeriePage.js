import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from "../utils/useAuth";
import Backdrop from "./Backdrop";
import SeasonOverview from './SeasonOverview';
import TitleVideo from "./TitleVideo";
import TitleDetails from "./TitleDetails";
import LoadingAnimation from './LoadingAnimation';

const baseImgUrl = "https://image.tmdb.org/t/p/original"

function SeriePage() {

    const auth = useAuth();
    const [serie, setSerie] = useState({});
    const [loaded, setLoaded] = useState(false)
    const { id } = useParams();

    useEffect(() => {
        setLoaded(false);
        fetch(`/api/v1/series/${id}`,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            setSerie(data);
            setLoaded(true);
        })
    }, [id, auth]);

    return(
        <>
            {loaded &&
                <>
                    <Backdrop src={`${baseImgUrl}${serie?.backdropPath}`}
                            id={serie?.id}
                            title={serie?.title}
                            description={serie?.description}
                            date={serie?.firstAirDate}
                            seasons={serie?.numberOfSeasons}
                            genres={serie?.genres}
                            starring={serie?.starring}/> 

                    <SeasonOverview serieId={serie?.id} numberOfSeasons={serie?.numberOfSeasons} /> 

                    <TitleVideo ytids={serie?.videos} titleId={serie?.id} titleType={"serie"} />

                    <TitleDetails title={serie} />
                </> }
            {!loaded && <LoadingAnimation />}
        </>
    )
}

export default SeriePage;