import React, { useState, useRef, useEffect } from "react";
import { useAuth } from "../utils/useAuth";
import MovieInfo from "./MovieInfo";

function SearchPage() {

    const [moreFetchable, setMoreFetchable] = useState(false);
    const auth                              = useAuth();
    const searchBar                         = useRef();
    const [page, setPage]                   = useState(0);
    const [searchTerm, setSearchTerm]       = useState("");
    const [searchResults, setSearchResults] = useState([]);

    useEffect(() => {
        if(searchTerm === '') {
            setSearchResults([]);
            return;
        }
        fetch(`/api/v1/search/${searchTerm}?page=${page}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${auth.user.accessToken}`
            }
        })
        .then(response => response.json())
        .then(data => {
            setMoreFetchable((data.length === 12) ? true : false);
            if(page !== 0) {
                setSearchResults( s => s.concat(data));
            } else {
                setSearchResults(data);
            }
            console.log(data);
        });
    },[page, searchTerm, auth])

    function handleSearch() {
        const searchString = searchBar.current.value;
        setSearchTerm(searchString);
        setPage(0);
    }

    function nextPage() {
        setPage(page + 1);
    }

    return(  
        <section className="hero is-medium">
            <div className="hero-body">
                    <div className="container is-fluid">
                    <div className="columns">
                        <div className="column is-one-third is-offset-one-third has-text-centered">
                            <p className="title has-text-white">
                                Filme und Serien durchsuchen
                            </p>
                            <div className="field">
                                <div className="control">
                                    <input className="input is-medium" size="2" type="text" placeholder="Titel, Genre, Schauspieler" onChange={handleSearch} ref={searchBar} />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="columns is-multiline">
                        {searchResults.length ? searchResults.map(title => (
                                <div key={title.id} className="column is-2 title-tile">
                                    <MovieInfo title={title} />
                                </div>
                        )) : 
                        <div className="column is-one-third is-offset-one-third">
                            <h2 className="has-text-white has-text-centered">{searchTerm === "" ? "Bitte gibt einen Suchbegriff ein!" : `Deine Suche nach "${searchTerm}" hat leider keine Ergebnisse!`}</h2>
                        </div>}
                    </div>
                    {moreFetchable && 
                    <div className="has-text-centered">
                        <button className="button is-success" onClick={nextPage}>Mehr</button>
                    </div>}
                </div>
            </div>
        </section>   
    )
}

export default SearchPage;