import React, { useState } from "react";
import { useHistory, useLocation } from "react-router-dom";
import { useAuth } from "../utils/useAuth";

import background from "../images/netflix_background.jpg";

/*  
Login form for users.
If the user is redirected to /login from a PrivateRoute,
useHistory will redirect the user back to that PrivateRoute
after successfull authentication.
*/
function Login() {

    let history = useHistory();
    let location = useLocation();
    let { from } = location.state || { from: { pathname: "/" } };

    const auth = useAuth();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();

    function handleLogin(event) {
        event.preventDefault();
        auth.signin(email, password, () => history.replace(from));
    }

    return (
        <div className="hero is-fullheight">  
            <div className="cloneflix-intro-background">
                <img src={background} alt="Landing Page Background" />
                <div className="cloneflix-intro-gradient"></div>
            </div> 
            <div className="hero-body cloneflix-intro-text">
                <div className="container">
                    <div className="columns is-centered">
                        <div className="column is-one-quarter">
                            <h1 className="is-size-3 has-text-weight-semibold has-text-white">Einloggen</h1>
                            <form onSubmit={handleLogin}>
                                <div className="field">
                                    <div className="control">
                                        <input className="input is-medium" type="text" placeholder="E-Mail" onChange={e => setEmail(e.target.value)} />
                                    </div>
                                </div>
                                <div className="field">
                                    <div className="control">
                                        <input className="input is-medium" type="password" placeholder="Passwort" onChange={e => setPassword(e.target.value)} />
                                    </div>
                                </div>
                                <div className="field">
                                    <div className="control">
                                        <input className="button is-medium is-success is-fullwidth" type="submit" value="Login" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;