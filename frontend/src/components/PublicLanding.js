import background from "../images/netflix_background.jpg";
import tv_card from "../images/netflix_fernseher.png";
import mobile_card from "../images/netflix_mobile_card.jpg";
import device_card from "../images/netflix_device_pile.png";
import kids_card from "../images/netflix_kidz.png";

import { useHistory } from 'react-router-dom';
import {useRef, useState} from "react";

// Public landing page for unauthenticated users.
function PublicLanding() {

    const history = useHistory();
    const userEmailInput = useRef(null);
    const [error, setError] = useState(false);

    function handleSubmit(event) {
        event.preventDefault();
        const email = userEmailInput.current.value;
        if(email) {
            history.push({
                pathname: "/registration",
                state: {
                    email
                }
            });
        } else {
            setError(true);
        }
    }

    return(
        <>
            <div className="hero is-fullheight">
                <div className="cloneflix-intro-background">
                    <img src={background} alt="Landing Page Background" />
                    <div className="cloneflix-intro-gradient"></div>
                </div>
                <div className="hero-body cloneflix-intro-text">
                    <div className="container has-text-centered has-text-white">
                        <div className="columns">
                            <div className="column is-three-fifths is-offset-one-fifth">
                                <h1 className="is-very-large has-text-weight-semibold">
                                    Unbegrenzt Filme, Serien und mehr
                                </h1>
                                <h2 className="is-size-4 mt-4">
                                    Genießen Sie Netflix, wo immer sie möchten. Jederzeit kündbar.
                                </h2>
                                <h3 className="is-size-5 mt-4">
                                    Sind sie startklar? Geben Sie ihre E-Mail-Adresse ein, um Ihre Mitgliedschaft zu beginnen oder zu reaktivieren.
                                </h3>
                                <form onSubmit={handleSubmit}>
                                    <div className="field has-addons is-centered ml-6 mr-6 mt-3">
                                        <div className="control is-expanded">
                                            <input ref={userEmailInput} className="input is-large" type="text" placeholder="E-Mail Adresse" />
                                        </div>
                                        <div className="control">
                                            <input className="button is-large is-success" type="submit" value="Loslegen >" />
                                        </div>
                                    </div>
                                    {error && <p className="help has-text-danger">Da stimmt was nicht mit der E-Mail!</p>}
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <section className="section is-small custom-card">
                <div className="container is-max-widescreen">
                    <div className="columns is-vcentered">
                        <div className="column has-text-white">
                            <h1 className="is-size-1 has-text-weight-bold card-title">
                                Auf Ihrem Fernseher
                            </h1>
                            <h2 className="is-size-4">
                                Streamen Sie mit Smart-TVs, Playstation, Xbox, Chromecast, Apple TV, Blu-ray-Player u. v. m.
                            </h2>
                        </div>
                        <div className="column">
                            <img src={tv_card} alt="TV Card Bild" />
                        </div>
                    </div>
                </div>
            </section>

            <section className="section is-small custom-card">
                <div className="container is-max-widescreen">
                    <div className="columns is-vcentered">
                        <div className="column">
                            <img src={mobile_card} alt="Mobile Card Bild" />
                        </div>
                        <div className="column has-text-white">
                            <h1 className="is-size-1 has-text-weight-bold card-title">
                                Serien herunterladen und offline genießen
                            </h1>
                            <h2 className="is-size-4">
                                Lieblingstitel ganz leicht speichern und jederzeit ansehen
                            </h2>
                        </div>
                        
                    </div>
                </div>
            </section>

            <section className="section is-small custom-card">
                <div className="container is-max-widescreen">
                    <div className="columns is-vcentered">
                        <div className="column has-text-white">
                            <h1 className="is-size-1 has-text-weight-bold card-title">
                                Auf allen Geräten
                            </h1>
                            <h2 className="is-size-4">
                                Sie können unbegrenzt Filme und Serien auf Ihrem Smartphone, Tablet, Laptop und Fernseher ansehen – ohne Extragebühren.
                            </h2>
                        </div>
                        <div className="column">
                            <img src={device_card} alt="Device Card Bild" />
                        </div>
                    </div>
                </div>
            </section>

            <section className="section is-small custom-card">
                <div className="container is-max-widescreen">
                    <div className="columns is-vcentered">
                        <div className="column">
                            <img src={kids_card} alt="Kinder Card Bild" />
                        </div>
                        <div className="column has-text-white">
                            <h1 className="is-size-1 has-text-weight-bold card-title">
                                Profile für Kinder erstellen
                            </h1>
                            <h2 className="is-size-4">
                                Schicken Sie Kinder auf Abenteuer mit ihren Lieblingsfiguren in einem speziell auf ihre Bedürfnisse abgestimmten Kids-Bereich, der ohne Aufpreis in Ihrer Mitgliedschaft inbegriffen ist.
                            </h2>
                        </div>
                    </div>
                </div>
            </section>

            <section className="section is-small custom-card">
                <div className="container is-max-widescreen">
                    <div className="columns is-centered">
                        <div className="column has-text-white has-text-centered">
                            <h1 className="is-size-1 has-text-weight-bold card-title">
                                FAQs Section
                            </h1>
                        </div>
                    </div>
                </div>
            </section>
        </>
    );
}

export default PublicLanding;