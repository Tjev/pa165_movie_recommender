import './App.css';
import React from "react";
import {BrowserRouter, NavLink, Route, Switch} from "react-router-dom";
import {Login} from "./components/Login";
import {SearchMovie} from "./components/SearchMovie";
import {SearchPerson} from "./components/SearchPerson";
import {useToken} from "./utils/Common";

function App() {

    const { token, setToken } = useToken();

    /*if(!token) {
        return <Login setToken={setToken} />
    }*/

    return (
      <div className="App">
          <BrowserRouter>
              <div className="header">
                  <NavLink exact activeClassName="active" to="/login">Login</NavLink>
                  <NavLink activeClassName="active" to="/search-movie">Search Movie</NavLink>
                  <NavLink activeClassName="active" to="/search-person">Search Person</NavLink>
                  <NavLink activeClassName="active" to="/your-ratings">Your Ratings</NavLink>
              </div>
              <div className="content">
                  <Switch>
                      <Route path="/login" component={Login} />
                      <Route path="/search-movie" component={SearchMovie} />
                      <Route path="/search-person" component={SearchPerson} />
                  </Switch>
              </div>
          </BrowserRouter>
      </div>
  );
}

export default App;
