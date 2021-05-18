import React, { useState } from "react";
import axios from "axios";

export function SearchMovie() {

    const [title, setTitle] = useState('');
    const [movies, setMovies] = useState([]);

    const handleSubmit = () => {
        alert("Hello");
    }

    return (
        <div>
            <div className="login-wrapper">
                <h1>Log in:</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        Email:
                        <input type="email"
                               value={title}
                               onChange={e => setTitle(e.target.value)}
                               name="email"
                               placeholder="Search..."/>
                    </label>
                    <input type="submit" value="Login" />
                </form>
            </div>
        </div>
    );
}