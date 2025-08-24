import React from "react";
import FavoriteButton from "./FavoriteButton";
import LikeButton from "./LikeButton";

function Backdrop(props) {
    const titleData = props;

    function truncate(str, n){
            return str?.length > n ? str.substr(0, n-1) + "..." : str;
        }
    return(
        <div className={'backdrop-container'}>
            <div className={'backdrop-overlay'}>
                <h1 className={'title is-size-2 has-text-weight-bold has-text-white'}>{titleData.title}</h1>
                <p className={'mb-3'}>
                    <span>{new Date(titleData.date).getFullYear()} | </span>
                    { titleData.seasons && <span>{titleData.seasons} Staffeln | </span> }
                    { titleData.runtime && <span>{titleData.runtime} Min. | </span> } 
                    <span>{titleData.genres?.shift()}</span>
                </p>
                <p>{truncate(titleData.description, 200)}</p>
                <div className={'buttons'}>
                    <FavoriteButton titleId={titleData.id} />
                    <LikeButton titleId={titleData.id} titleType={titleData.seasons ? 'serie' : 'movie'} />
                </div>
            </div>
            <div style={{
                backgroundImage: `url(${titleData.src})`
            }} className={'backdrop'}>
            </div>
        </div>
    )
}

export default Backdrop;