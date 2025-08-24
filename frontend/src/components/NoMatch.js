import app_error_placeholder from "../images/app_error_placeholder.png"

// Component to be displayed if the URL is invalid.
function NoMatch() {
    return(
        <div className="hero is-fullheight">
            <div className="hero-body">
                <div className="container">
                    <div className="columns">
                        <div className="column is-half is-offset-one-quarter">
                            <img src={app_error_placeholder} alt={"Seite nicht gefunden!"} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default NoMatch;