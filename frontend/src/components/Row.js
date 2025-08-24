import React, {useState, useEffect} from 'react'
import "../css/Row.css"
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from 'react-slick';
import { useAuth } from '../utils/useAuth';
import {NavLink} from "react-router-dom";
import placeholder from "../images/x_files_placeholder.jpg"

const baseImgUrl = "https://image.tmdb.org/t/p/original"

function Row({title, fetchUrl}) {
  const [movies, setMovies] = useState([])
  const auth = useAuth();

  const sliderSettings = {
    lazyLoad: true,
    infinite: false,
    speed: 500,
    slidesToShow: 6,
    slidesToScroll: 6
  }

  useEffect(() => {
    async function fetchData(){
      const request = await fetch(fetchUrl,{
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth.user.accessToken}`
        }
      })
      const movies = await request.json();

      console.log("ROW : ",movies);

      setMovies(movies);
      return request;
    }
    fetchData();
  },[fetchUrl, auth]);

  if(movies.length) {
    return (
      <>
        <h2 className="has-text-white has-text-weight-semibold mb-2 mt-5 pl-3">{title}</h2>
        <Slider {...sliderSettings}>
          {movies.length && movies.map(movie => (
            <div key={movie.id} className="title-tile" >
              <NavLink to={`/movie/${movie.id}`} >
                <img src={movie.posterPath ? `${baseImgUrl}${movie.posterPath}` : placeholder} alt={movie.title} />
              </NavLink>
            </div>
          ))}
        </Slider>
      </>
    )
  } else {
    return(<></>);
  }
}

export default Row;