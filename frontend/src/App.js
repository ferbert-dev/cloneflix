import { BrowserRouter as Router } from "react-router-dom";
import { Route, Switch } from "react-router-dom";
import { ProvideAuth } from "./utils/useAuth.js";
import PrivateRoute from "./utils/PrivateRoute";
import Header from './components/Header';
import StartPage from "./components/StartPage";
import Serie from "./components/Serie";
import Movie from "./components/Movie";
import Recommendations from "./components/Recommendations";
import Favorites from "./components/Favorites";
import MoviePage from "./components/MoviePage"
import SeriePage from "./components/SeriePage"
import Login from "./components/Login";
import Registration from "./components/Registration";
import NoMatch from "./components/NoMatch";
import Footer from "./components/Footer";
import SearchPage from "./components/SearchPage.js";

function App() {
  return (
    <ProvideAuth>
      <Router>
        <Header />
          <Switch>
            <Route exact path="/">
              <StartPage /> 
            </Route>
            <PrivateRoute path="/series">
              <Serie />
            </PrivateRoute>
            <PrivateRoute path="/movies">
              <Movie />
            </PrivateRoute>
            <PrivateRoute path="/popular">
                <Recommendations />
            </PrivateRoute>
            <PrivateRoute path="/favorites">
                <Favorites />
            </PrivateRoute>
            <PrivateRoute path={"/movie/:id"}>
              <MoviePage />
            </PrivateRoute>
            <PrivateRoute path={"/serie/:id"}>
              <SeriePage />
            </PrivateRoute>
            <PrivateRoute path={"/search"}>
              <SearchPage />
            </PrivateRoute>
            <Route path="/login">
                <Login />
            </Route>
            <Route path="/registration">
                <Registration />
            </Route>
            <Route path="*">
              <NoMatch />
            </Route>
          </Switch>
        <Footer />
      </Router>
    </ProvideAuth>
  );
}

export default App;
