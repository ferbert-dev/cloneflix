import '../css/App.css';
import requests from '../utils/requests';
import Row from './Row';
import Banner from './Banner';

function RegisteredLanding() {
    return ( <div className="app">
                  <Banner />
                  <div className="container is-fluid">
                    <Row title="Recommended" fetchUrl={requests.fetchRecommended}/>
                    <Row title="Top Rated" fetchUrl={requests.fetchTopRated} />
                    <Row title="Action Movies" fetchUrl={requests.fetchActionMovies} />
                    <Row title="Comedy Movies" fetchUrl={requests.fetchComedyMovies} />
                    <Row title="Horror Movies" fetchUrl={requests.fetchHorrorMovies} />
                    <Row title="Romance Movies" fetchUrl={requests.fetchRomanceMovies} />
                    <Row title="Animation Movies" fetchUrl={requests.fetchAnimationMovies} />
                  </div>
                </div>)
}

export default RegisteredLanding;