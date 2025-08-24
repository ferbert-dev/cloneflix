import { useState } from 'react';
import { useAuth } from '../utils/useAuth';
import { useHistory, useLocation } from 'react-router-dom';

import background from "../images/netflix_background.jpg";

// Registration form.
function Registration() {

    const auth = useAuth();
    const history = useHistory();
    const location = useLocation();

    const [error, setError] = useState(false);
    const [firstName, setFirstname] = useState("");
    const [lastName, setLastname] = useState("");
    const [email, setUserEmail] = useState(location.state.email);
    const [password, setPassword] = useState("");

    function handleSubmit(event) {
        event.preventDefault();
        const data={
            firstName,
            lastName,
            email,
            password
        }
        auth.signup(data, () => {
            history.replace('/login');
        });
        if(auth.user) {
            history.push("/");
        } else {
            setError("Das hat wohl nicht geklappt!");
        }
    }

    return(
        <div className="hero is-fullheight">
            <div className="cloneflix-intro-background">
                <img src={background} alt="Landing Page Background" />
                <div className="cloneflix-intro-gradient"></div>
            </div>
            <div className="columns is-centered cloneflix-intro-text mt-6">
                <div className="column is-one-quarter">
                    <form onSubmit={handleSubmit}>
                        <div className="field">
                            <div className="label">
                                <h1 className="is-size-3 has-text-white has-text-weight-semibold">Registrieren</h1>
                                {error && <h2 className="has-text-danger has-text-weight-bold">{error}</h2>}
                            </div>
                        </div>
                        <div className="field">
                            <div className="control">
                                <input className="input is-medium" type="text" placeholder="Vorname" onChange={e => setFirstname(e.target.value)} />
                            </div>
                        </div>
                        <div className="field">
                            <div className="control">
                                <input className="input is-medium" type="text" placeholder="Nachname" onChange={e => setLastname(e.target.value)} />
                            </div>
                        </div>
                        <div className="field">
                            <div className="control">
                                <input className="input is-medium" type="text" placeholder="E-Mail" name="email" value={email} onChange={e => setUserEmail(e.target.value)} />
                            </div>
                        </div>
                        <div className="field">
                            <div className="control">
                                <input className="input is-medium" type="password" placeholder="Passwort" name="password" onChange={e => setPassword(e.target.value)} />
                            </div>
                        </div>
                        <div className="field">
                            <div className="control">
                                <input className="button is-success is-medium is-fullwidth" type="submit" value="Registrieren" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Registration;