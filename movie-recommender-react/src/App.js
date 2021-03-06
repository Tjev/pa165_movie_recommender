import './App.css';
import React from "react";
import {HashRouter, NavLink, Redirect, Route, Switch, useLocation} from "react-router-dom";
import {useToken, getAdminStatus} from "./utils/Common";
import Login from "./components/Login";
import {SearchMovie} from "./components/SearchMovie";
import {SearchPerson} from "./components/SearchPerson";
import {CreatePerson} from "./components/CreatePerson";
import {CreateMovie} from "./components/CreateMovie";
import {CreateRating} from "./components/CreateRating";
import {AddDirector} from "./components/AddDirector";
import {AddActor} from "./components/AddActor";
import {YourRatings} from "./components/YourRatings";
import {GetRecommendations} from "./components/GetRecommendations";
import {ManageUsers} from "./components/ManageUsers";
import {HomePage} from "./components/HomePage";
import { theme } from "./utils/Common";
import {MuiThemeProvider} from "@material-ui/core";


export function App() {
    const { token, setToken } = useToken();
    const isAdmin = token ? getAdminStatus() : null;

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

    const GetAdminRouteContent = () => {
        let location = useLocation();
        return (
            <div className="content">
                <Switch>
                    <Route exact path="/" component={HomePage} />
                    <Route path="/login"><Redirect to="/"/></Route>
                    <Route path="/search-movie"><SearchMovie token={token} /></Route>
                    <Route path="/search-person" component={SearchPerson} />
                    <Route path="/create-person" component={CreatePerson} />
                    <Route path="/add-director" component={AddDirector} />
                    <Route path="/add-actor" component={AddActor} />
                    <Route path="/create-movie" component={CreateMovie} />
                    <Route path="/your-ratings" component={YourRatings} />
                    <Route path="/users" component={ManageUsers} />
                    <Route path="/get-recommendations">{location.state?.id ? <GetRecommendations /> : <NoMatch />}</Route>
                    <Route path="/create-rating">{location.state?.id ? <CreateRating /> : <NoMatch />}</Route>
                    <Route path='*'><NoMatch /></Route>
                </Switch>
            </div>
        )
    }

    const GetRouteContent = () => {
        let location = useLocation();
        return (
            <div className="content">
                <Switch>
                    <Route exact path="/" component={HomePage} />
                    <Route path="/login"><Redirect to="/"/></Route>
                    <Route path="/search-movie"><SearchMovie token={token} /></Route>
                    <Route path="/search-person" component={SearchPerson} />
                    <Route path="/your-ratings" component={YourRatings} />
                    <Route path="/get-recommendations">{location.state?.id ? <GetRecommendations /> : <NoMatch />}</Route>
                    <Route path="/create-rating">{location.state?.id ? <CreateRating /> : <NoMatch />}</Route>
                    <Route path='/:path(create-person|create-movie|add-director|add-actor)'><NoAccess /></Route>
                    <Route path='*'><NoMatch /></Route>
                </Switch>
            </div>
        )
    }

    const GetUnauthorizedRouteContent = () => {
        return (
            <div className="content">
                <Switch>
                    <Route exact path="/" component={HomePage} />
                    <Route path="/login"><Login token={token} setToken={setToken} /></Route>
                    <Route path="/search-movie" component={SearchMovie} />
                    <Route path="/search-person" component={SearchPerson} />
                    <Route path='/:path(your-ratings|create-person|create-movie|add-director|add-actor)'><Login token={token} setToken={setToken} /></Route>
                    <Route path='*'><NoMatch /></Route>
                </Switch>
            </div>
        )
    }

    const GetAdminHeader = () => {
        return (
            <div className="header">
                <NavLink activeClassName="active" to="/login" onClick={Logout}>Logout</NavLink>
                <NavLink activeClassName="active" to="/search-movie">Search Movie</NavLink>
                <NavLink activeClassName="active" to="/search-person">Search Person</NavLink>
                <NavLink activeClassName="active" to="/create-person">Create Person</NavLink>
                <NavLink activeClassName="active" to="/create-movie">Create Movie</NavLink>
                <NavLink activeClassName="active" to="/your-ratings">Your Ratings</NavLink>
                <NavLink activeClassName="active" to="/users">Users</NavLink>
            </div>
        )
    }

    const GetHeader = () => {
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
            <MuiThemeProvider theme={theme}>
                <HashRouter>
                    {(!token) ? <GetUnauthorizedHeader /> : ((!isAdmin) ? <GetHeader /> : <GetAdminHeader />)}
                    {(!token) ? <GetUnauthorizedRouteContent/> : ((!isAdmin) ? <GetRouteContent/> : <GetAdminRouteContent />)}
                </HashRouter>
            </MuiThemeProvider>
        </div>
      );
}

export default App;
