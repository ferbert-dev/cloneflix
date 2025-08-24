import {NavLink} from "react-router-dom";
import placeholder from "../images/x_files_placeholder.jpg"

const baseImgUrl = "https://image.tmdb.org/t/p/original"

// TODO: eine Art /title Endpunkt erzeugen der einfach den TITLE egal ob Serie/Film wiedergibt
// Damit kann man diesen Graus da unten umgehen.
// ODER: Eine neue Komponente machen, aber das ist auch nicht Sinn von React. (aktuelle Lösung)
function MovieInfo({title}) {
    let poster = ('posterPath' in title) ? title.posterPath : title.poster;
    let type = ('seasons' in title) || ('model' in title && title.model === 'serie') ? 'serie' : 'movie';
    return(
        <NavLink to={`/${type}/${title.id}`} >
            <img src={poster ? `${baseImgUrl}${poster}` : placeholder} alt={title.title} />
            <h2 className="has-text-white has-text-centered">{title.title}</h2>
        </NavLink>
    );
}

export default MovieInfo;