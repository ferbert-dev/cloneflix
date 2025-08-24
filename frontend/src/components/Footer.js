// Footer component.
import '../css/Footer.css'
import facebook_logo from "../images/facebook_logo.png";
import instagram_logo from "../images/instagram_logo.png";
import twitter_logo from "../images/twitter_logo.png";
import youtube_logo from "../images/youtube_logo.png";
import tmdb_logo from "../images/tmdb_logo.svg";

function Footer() {
    return(
        <div className="container is-fluid member-footer" position="absolute">
            <div className="tmdb-notice">
                <a className="" href="https://www.themoviedb.org/" target="_blank" >
                    <img src={tmdb_logo} alt={"TMDB Logo"} />
                    <p>This product uses the TMDB API but is not endorsed or certified by TMDB</p>
                </a>
            </div>
            <div className="social-links">
                <a className="link" href="https://www.facebook.com"><img src={facebook_logo} alt={"Facebook Logo"} width="60px"/></a>
                <a className="link" href="https://www.instagram.com"><img src={instagram_logo} alt={"Instagram Logo"} width="60px"/></a>
                <a className="link" href="https://www.twitter.com"><img src={twitter_logo} alt={"Twitter Logo"} width="60px"/></a>
                <a className="link" href="https://www.youtube.com"><img src={youtube_logo} alt={"YouTube Logo"} width="60px"/></a>
            </div>

            <ul className="member-footer-links">
                <li className="member-footer-link-wrapper">Audio und Untertitel</li>
                <li className="member-footer-link-wrapper">Audiodeskription</li>
                <li className="member-footer-link-wrapper">
                    <a className="link" href="https://help.netflix.com/">Hilfe-Center</a></li>
                <li className="member-footer-link-wrapper">Geschenkkarten</li>
                <li className="member-footer-link-wrapper">Medien-Center</li>
                <li className="member-footer-link-wrapper">Anlegerbeziehungen</li>
                <li className="member-footer-link-wrapper">Karriere</li>
                <li className="member-footer-link-wrapper">Nutzungsbedingungen</li>
                <li className="member-footer-link-wrapper">
                    <a className="link" href="https://help.netflix.com/legal/privacy">
                        Datenschutz</a></li>
                <li className="member-footer-link-wrapper">
                    <a className="link" href="https://help.netflix.com/legal/notices">
                        Rechtliche Hinweise</a></li>
                <li className="member-footer-link-wrapper">Cookie-Einstellungen</li>
                <li className="member-footer-link-wrapper">
                    <a className="link" href="https://help.netflix.com/node/68708">
                        Impressum</a></li>
                <li className="member-footer-link-wrapper">
                    <a className="link" href="https://help.netflix.com/contactus">
                        Kontakt</a></li>
            </ul>

            <div className="member-footer-service"><button className="service-code">Service-Code</button>
            </div>

            <div className="member-footer-copyright"><span>
                © Projekt Cloneflix SoSe 2021, Uni Hamburg</span>
            </div>
        </div>
    );
}

export default Footer;