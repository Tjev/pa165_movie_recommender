import './App.css';
import React from "react";
import {BrowserRouter, NavLink, Route, Switch} from "react-router-dom";
import Login from "./components/Login";
import {SearchMovie} from "./components/SearchMovie";
import {SearchPerson} from "./components/SearchPerson";
import {getToken, useToken} from "./utils/Common";
import {CreatePerson} from "./components/CreatePerson";
import {CreateMovie} from "./components/CreateMovie";
import {AddDirector} from "./components/AddDirector";
import {AddActor} from "./components/AddActor";
import {GetRecommendations} from "./components/GetRecommendations";

function LoginLink(token) {
    if (!token) {
        return <NavLink activeClassName="active" to="/login">Login</NavLink>
    }
}

export function App() {
    const { token, setToken } = useToken();

    return (
      <div className="App">
          <BrowserRouter basename="/pa165">
              <div className="header">
                  {LoginLink(token)}
                  <NavLink activeClassName="active" to="/search-movie">Search Movie</NavLink>
                  <NavLink activeClassName="active" to="/search-person">Search Person</NavLink>
                  <NavLink activeClassName="active" to="/create-person">Create Person</NavLink>
                  <NavLink activeClassName="active" to="/create-movie">Create Movie</NavLink>
                  <NavLink activeClassName="active" to="/your-ratings">Your Ratings</NavLink>
              </div>
              <div className="content">
                  <Switch>
                      <Route path="/login" render={() => (
                              <Login token={token} setToken={setToken} />
                        )}
                      />
                      <Route path="/search-movie" component={SearchMovie} />
                      <Route path="/search-person" component={SearchPerson} />
                      <Route path="/create-person" component={CreatePerson} />
                      <Route path="/add-director" component={AddDirector} />
                      <Route path="/add-actor" component={AddActor} />
                      <Route path="/create-movie" component={CreateMovie} />
                      <Route path="/get-recommendations" component={GetRecommendations} />
                  </Switch>
              </div>
          </BrowserRouter>
      </div>
  );
}

export default App;
