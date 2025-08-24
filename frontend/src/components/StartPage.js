import { useAuth } from "../utils/useAuth";

import RegisteredLanding from "./RegisteredLanding";
import PublicLanding from "./PublicLanding";

// Component to render a landing page depending on user authentication state.
function StartPage() {
    const auth = useAuth();
    return(
        <>
            {auth.user ? (
                <RegisteredLanding />
            ) : (
                <PublicLanding />
            )}
        </>
    );
}

export default StartPage;