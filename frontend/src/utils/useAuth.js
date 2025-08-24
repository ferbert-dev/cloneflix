// SIEHE: https://usehooks.com/useAuth/

import React, { useState, useEffect, useContext, createContext } from "react";

const authContext = createContext();

export function ProvideAuth({ children }) {
  const auth = useProvideAuth();
  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

export const useAuth = () => {
  return useContext(authContext);
};

function useProvideAuth() {
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('cloneflixUser')));

  const signin = (username, password, cb) => {
    const credentials = {username, password};
    fetch('/api/auth/signin', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    })
    .then(response => {
      if(response.status === 200) {
        return response.json();
      } else {
        throw new Error('Sign in failed!')
      }
    })
    .then(data => {
      localStorage.setItem('cloneflixUser', JSON.stringify(data));
      setUser(data);
      cb();
      return data;
    })
    .catch(e => console.log('Sign in failed!'));
  }
  
  const signup = (data, cb) => {
    fetch('/api/auth/signup', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
    .then(response => {
      if(response.status === 200) {
        return response.json();
      } else {
        throw new Error('Sign in failed!')
      }
    })
    .then(data => {
      console.log(data);
      cb();
      return data;
    })
    .catch(e => console.log('Sign up failed!'));
  }

  const checkJwtStatus = (jwt) => {
    fetch('/api/auth/signedin', {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${jwt}`
      }
    })
    .then(response => response.json())
    .then(data => {
      console.log(data);
      return data;
    })
  }

  const signout = () => {
    localStorage.removeItem('cloneflixUser');
    setUser(false);
  };

  useEffect(() => {
    const unsubscribe = () => {
      const userData = JSON.parse(localStorage.getItem('cloneflixUser'));
      if(userData) {
        let jwt = userData.accessToken;
        if(!checkJwtStatus(jwt)) {
          signout(); // Das sollte ja passieren, wenn der Token ungültig ist.
          console.log('Token ungültig!');
        }
      }     
    };

    return () => unsubscribe();
  }, []);

  return {
    user,
    signin,
    signup,
    signout
  };
}