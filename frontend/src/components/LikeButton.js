import React, { useEffect, useState, useRef } from 'react';
import { useAuth } from "../utils/useAuth";

function LikeButton({titleId, titleType}) {

    const auth = useAuth();
    const likeButton = useRef();
    const [liked, setLiked] = useState();
    const titleTypeSpecificEndpoints = (titleType === 'serie') ? ['createLikedSeries', 'deleteLikedSeries'] : ['createLikedMovie', 'deleteLikedMovies'];

    useEffect(() => {
        fetch(`/api/v1/recommendations/${titleId}/isLiked`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => response.text())
        .then(likeState => {
            const liked = (likeState === 'true')
            setLiked(liked);
            if(liked) {
                likeButton.current.className = "button is-success is-small mt-3";
            } else {
                likeButton.current.className = "button is-light is-small mt-3";
            }
        })
    })

    function handleClick() {
        // Man kann auch einen Endpunkt wie /liked haben. GET: Fragt ab, POST: erzeugt, PUT: ändert, DELETE: entfernt
        const method = liked ? 'DELETE' : 'PUT';
        const endpoint = liked ? titleTypeSpecificEndpoints[1] : titleTypeSpecificEndpoints[0]; 
        fetch(`/api/v1/recommendations/${titleId}/${endpoint}`, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => {
            if(response.status === 200) {
                setLiked(!liked);
            }
        })
    }

    return(
        <button className={"button is-light is-small mt-3"} onClick={handleClick} ref={likeButton} >
            {liked ? 'Liked' : 'Like'}
        </button>
    )
}

export default LikeButton;