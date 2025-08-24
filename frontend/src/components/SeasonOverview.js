import React, { useEffect, useState } from 'react';
import { useAuth } from "../utils/useAuth";

function SeasonOverview({serieId, numberOfSeasons}) {
    const [season, setSeason] = useState();
    const [selectedSeason, setSelectedSeason] = useState(1);
    const auth = useAuth();
    const baseImgUrl = "https://image.tmdb.org/t/p/original"

    useEffect(() => {
        console.log('Selected Season: ', selectedSeason);
        fetch(`/api/v1/series/${serieId}/season/${selectedSeason}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => response.json())
        .then(season => {
            console.log('Season fetched!');
            console.log(season);
            setSeason(season);
        })
    }, [selectedSeason, auth, serieId])

    function handleChange(e) {
        setSelectedSeason(e.target.value);
    }

    let options = [];
    for(let i = 1; i <= numberOfSeasons; i++ ) {
        options.push(<option value={i}>Staffel {i}</option>);
    }

    return(
        <div className={"container mt-5"}>
            <div className="columns">
                <div className={"column"}>
                    <select value={selectedSeason} className={"select"} onChange={handleChange}>
                        {options}
                    </select>
                </div>
            </div>
            {
                season?.episodes.map(episode => {
                    return (<div className={"columns"}>
                        <div className={"column is-one-fifth"}>
                            <img src={baseImgUrl + episode.posterPath} alt={`Poster für ${episode.title}`} />
                        </div>
                        <div className={"column is-four-fifths has-text-white"}>
                            <p>Episode {episode.number}</p>
                            <h2 className={"has-text-weight-semibold mb-2"}>{episode.title}</h2>
                            <p>{episode.description}</p>
                        </div>
                    </div>)
                })
            }
        </div>
    )
}

export default SeasonOverview;