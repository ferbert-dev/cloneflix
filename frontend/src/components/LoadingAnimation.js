import ReactLogo from "../images/react_logo.png";

function LoadingAnimation() {
    return(
        <section className="hero is-fullheight">
            <div className="hero-body is-justify-content-center">
                <img src={ReactLogo} alt={"..."} className={"loading-anim"} />
            </div>    
        </section>
    )
}

export default LoadingAnimation;