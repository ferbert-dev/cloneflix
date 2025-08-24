import { NavLink } from "react-router-dom";
import { useAuth } from '../utils/useAuth.js';
import { useHistory } from 'react-router-dom';
import searchIcon from '../images/search.png';

function Header() {
    const auth = useAuth();
    const history = useHistory();

    const searchStyles = {
        cursor: 'pointer'
    }

    function switchToSearchPage() {
        history.push("/search");
    }
    
    return (
        <nav className="navbar is-transparent custom-navbar container is-fluid">
            <div className="navbar-brand">
                <div className="navbar-item">
                    <NavLink exact to="/" activeClassName="is-active">
                        <h1 className="has-text-success has-text-weight-bold is-size-3">Cloneflix</h1>
                    </NavLink>
                </div>
            </div>
            {auth.user && (
                <div className="navbar-menu">
                    <div className="navbar-start">
                        <NavLink exact to="/" className="navbar-item" activeClassName="is-active">Startseite</NavLink>
                        <NavLink to="/series" className="navbar-item" activeClassName="is-active">Serien</NavLink>
                        <NavLink to="/movies" className="navbar-item" activeClassName="is-active">Filme</NavLink>
                        <NavLink to="/popular" className="navbar-item" activeClassName="is-active">Neu und beliebt</NavLink>
                        <NavLink to="/favorites" className="navbar-item" activeClassName="is-active">Meine Favoriten</NavLink>
                    </div>
                </div>
            )}
            <div className="navbar-end">
                {auth.user && <div className="navbar-item">
                    <div className="field is-horizontal">
                        <div className="field-body">
                            <div className="field has-addons">
                                <img alt="Suchen" src={searchIcon} style={searchStyles} onClick={switchToSearchPage} />
                            </div>
                        </div>
                    </div>
                </div>}       
                <div className="navbar-item">
                    <div className="buttons">
                        {auth.user ? (
                            <NavLink to="/" className="button is-danger" onClick={() => auth.signout()}>
                                Log out
                            </NavLink>
                        ) : (
                            <NavLink to="/login" className="button is-success" >
                                Log in
                            </NavLink>
                        )}
                    </div>
                </div>
            </div>
        </nav>
    );
}

export default Header;