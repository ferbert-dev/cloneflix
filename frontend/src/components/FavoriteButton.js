import React, { useEffect, useState, useRef } from 'react';
import { useAuth } from "../utils/useAuth";

function FavoriteButton({titleId}) {

    const favoriteButton = useRef();
    const [favorited, setFavorited] = useState(false);
    const auth = useAuth();

    useEffect(() => {
        fetch(`/api/v1/users/favorite/check/${titleId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => response.text())
        .then(data => {
            (data === 'true') ? setFavorited(true) : setFavorited(false);
            if(favorited) {
                favoriteButton.current.className = "button is-success is-small mt-3";
            } else {
                favoriteButton.current.className = "button is-light is-small mt-3";
            }    
        })
    });

    function handleFavoriteButtonClick() {
        const method = favorited ? 'DELETE' : 'PUT';
        fetch(`/api/v1/users/favorite/${titleId}`, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => response.text())
        .then(data => {
            setFavorited(!favorited);            
        })
    }

    return(
        <button className="button is-light is-small mt-3" onClick={handleFavoriteButtonClick} ref={favoriteButton} >
            { favorited ? 'Gemerkt' : 'Merken' }
        </button>
    );
}

export default FavoriteButton;