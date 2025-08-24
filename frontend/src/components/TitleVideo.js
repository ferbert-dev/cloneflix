import React, { useEffect, useRef } from 'react';

import { useAuth } from "../utils/useAuth";

function TitleVideo({ytids, titleId, titleType}) {
    
    let played = false;
    const YT = useRef();
    const player = useRef();
    const auth = useAuth();
    const titleTypeSpecificEndpoint = (titleType === 'serie') ? 'createWatchedSeries' : 'createWatchedMovie';

    useEffect(() => {
        let tag = document.createElement('script');
        tag.src = "https://www.youtube.com/iframe_api";
        let firstScriptTag = document.getElementsByTagName('script')[0];
        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
        window.onYouTubeIframeAPIReady = function() {
            YT.current = window.YT;
            player.current = new YT.current.Player('player', {
                events: {
                  'onStateChange': createWatched
                }
              });
        }
        return () => {
            window.onYouTubeIframeAPIReady = '';
            window.YT = '';
        }
    })

    function createWatched() {
        console.log('Player State changed!');
        const state = player.current.getPlayerState()
        if(played || state !== 1) {
            console.log('Already played or event not related to replay!');
            return
        }
        fetch(`/api/v1/recommendations/${titleId}/${titleTypeSpecificEndpoint}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => {
            if(response.status === 200) {
                played = true;
            }
        });
    }

    return(
        <div className={'container mt-2'} >
            <div className={'videoWrapper'} >
                <iframe id={"player"} src={`https://www.youtube.com/embed/${ytids?.shift()}?enablejsapi=1`} title={titleId} ></iframe>
            </div>
        </div>

    )
}

export default TitleVideo;