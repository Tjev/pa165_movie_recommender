import './App.css';
import React from "react";
import {BrowserRouter, NavLink, Route, Switch} from "react-router-dom";
import Login from "./components/Login";
import {SearchMovie} from "./components/SearchMovie";
import {SearchPerson} from "./components/SearchPerson";
import {getAdminStatus, useToken} from "./utils/Common";
import {CreatePerson} from "./components/CreatePerson";
import {CreateMovie} from "./components/CreateMovie";
import {AddDirector} from "./components/AddDirector";
import {AddActor} from "./components/AddActor";
import {GetRecommendations} from "./components/GetRecommendations";
import {YourRatings} from "./components/YourRatings";
import {Logout} from "./components/Logout";
import {CreateRating} from "./components/CreateRating"


function LoginLink(token) {
    if (!token) {
        return <NavLink activeClassName="active" to="/login">Login</NavLink>;
    }
}

function CreatePersonLink(token) {
    if (token && getAdminStatus()) {
        return <NavLink activeClassName="active" to="/create-person">Create Person</NavLink>;
    }
}

function CreateMovieLink(token) {
    if (token && getAdminStatus()) {
        return <NavLink activeClassName="active" to="/create-movie">Create Movie</NavLink>;
    }
}

function CreateYourRatingsLink(token) {
    if (token) {
        return <NavLink activeClassName="active" to="/your-ratings">Your Ratings</NavLink>;
    }
}

function CreateLogoutLink(token) {
    if (token) {
        return <NavLink activeClassName="active" to="/logout">Logout</NavLink>;
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
                    {CreatePersonLink(token)}
                    {CreateMovieLink(token)}
                    {CreateYourRatingsLink(token)}
                    {CreateLogoutLink(token)}
                </div>
                <div className="content">
                    <Switch>
                        <Route path="/login" render={() => (
                            <Login token={token} setToken={setToken} />
                        )} />
                        <Route path="/search-movie" render={() => (
                            <SearchMovie token={token} />
                        )} />
                        <Route path="/search-person" component={SearchPerson} />
                        <Route path="/create-person" component={CreatePerson} />
                        <Route path="/add-director" component={AddDirector} />
                        <Route path="/add-actor" component={AddActor} />
                        <Route path="/create-movie" component={CreateMovie} />
                        <Route path="/your-ratings" component={YourRatings} />
                        <Route path="/get-recommendations" component={GetRecommendations} />
                        <Route path="/create-rating" component={CreateRating}/>
                        <Route path="/logout" render={() => (
                            <Logout token={token} setToken={setToken} />
                        )} />
                    </Switch>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default App;

