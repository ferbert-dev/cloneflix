import React from 'react';

function TitleDetails(props) {
    const title = props.title;
    return(
       <div className={'container mt-5'}>
            <p className={'mb-2 is-size-3 has-text-weight-semibold has-text-white'}>Weitere Details</p>
            <div className={'columns'}>
                <div className={'column is-one-fifth'}>
                    <p>Offline ansehen</p>
                    <p className={'has-text-white'}>Als Download verfügbar</p>
                </div>
                <div className={'column is-one-fifth'}>
                    <p>Genres</p>
                    {title.genres?.map(genre => <p className={'has-text-white'}>{genre}</p>)}
                </div>
            </div>
            <p>Besetzung</p>
            <div className={'columns'}>
                <div className={'column is-full has-text-white'}>
                    {
                        title.starring?.map((actor,i) => {
                                if(title.starring?.length === i + 1) {
                                    return <span key={i}>{actor}</span>
                                } else {
                                    return <span key={i}>{actor}, </span>
                                }
                        })
                    }
                </div>
            </div>
        </div>
    )
}

export default TitleDetails;