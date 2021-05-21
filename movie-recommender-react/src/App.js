import './App.css';
import React from "react";
import {BrowserRouter, NavLink, Route, Switch, useLocation} from "react-router-dom";
import Login from "./components/Login";
import {SearchMovie} from "./components/SearchMovie";
import {SearchPerson} from "./components/SearchPerson";
import {useToken} from "./utils/Common";
import {CreatePerson} from "./components/CreatePerson";
import {CreateMovie} from "./components/CreateMovie";
import {AddDirector} from "./components/AddDirector";
import {AddActor} from "./components/AddActor";
import {YourRatings} from "./components/YourRatings";

export function App() {
    const { token, setToken } = useToken();

    const Logout = () => {
        setToken(null);
    }

    const NoMatch = () => {
        let location = useLocation();
        return (
            <div>
                <h3>
                    404: Unknown request for <code>{location.pathname}</code>.
                </h3>
            </div>
        )
    }

    const NoAccess = () => {
        let location = useLocation();
        return (
            <div>
                <h3>
                    403: Forbidden: you don't have sufficient rights to access <code>{location.pathname}</code>.
                </h3>
            </div>
        )
    }

    const GetRouteContent = () => {
        return (
            <div className="content">
                <Switch>
                    <Route path="/search-movie" component={SearchMovie} />
                    <Route path="/search-person" component={SearchPerson} />
                    <Route path="/your-ratings" component={YourRatings} />
                    <Route path='/:path(create-person|create-movie|add-director|add-actor)'><NoAccess /></Route>
                    <Route path='*'><NoMatch/></Route>
                </Switch>
            </div>
        )
    }

    const GetUnauthorizedRouteContent = () => {
        return (
            <div className="content">
                <Switch>
                    <Route path="/login"><Login token={token} setToken={setToken} /></Route>
                    <Route path="/search-movie" component={SearchMovie} />
                    <Route path="/search-person" component={SearchPerson} />
                    <Route path='/:path(your-ratings|create-person|create-movie|add-director|add-actor)'><Login token={token} setToken={setToken} /></Route>
                    <Route path='*'><NoMatch/></Route>
                </Switch>
            </div>
        )
    }

    const GetHeader =  () => {
        return (
            <div className="header">
                <NavLink activeClassName="active" to="/login" onClick={Logout}>Logout</NavLink>
                <NavLink activeClassName="active" to="/search-movie">Search Movie</NavLink>
                <NavLink activeClassName="active" to="/search-person">Search Person</NavLink>
                <NavLink activeClassName="active" to="/your-ratings">Your Ratings</NavLink>
            </div>
        )
    }

    const GetUnauthorizedHeader = () => {
        return (
            <div className="header">
                <NavLink activeClassName="active" to="/login">Login</NavLink>
                <NavLink activeClassName="active" to="/search-movie">Search Movie</NavLink>
                <NavLink activeClassName="active" to="/search-person">Search Person</NavLink>
            </div>
        )
    }

    return (
        <div className="App">
            <BrowserRouter basename="/pa165">
                {(!token) ? <GetUnauthorizedHeader/> : <GetHeader/>}
                {(!token) ? <GetUnauthorizedRouteContent/> : <GetRouteContent/>}
            </BrowserRouter>
        </div>
      );
}

export default App;

