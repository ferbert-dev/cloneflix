import { Redirect, Route } from "react-router";
import { useAuth } from "./useAuth";

// A route that is only accessable for authorized users.
// If the user is not authorized, the route becomes a redirect
// to the login form. Otherwise the child components are rendered.
function PrivateRoute({children, ...rest}) {
    let auth = useAuth();
    return(
        <Route
            {...rest}
            render={({ location }) =>
                auth.user ? (
                    children
                ) : (
                    <Redirect
                        to={{
                            pathname: "/login",
                            state: { from: location }
                        }}
                    />
                )
            }
        />    
    );
}

export default PrivateRoute;