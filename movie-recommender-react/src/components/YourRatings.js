import React, {useEffect, useState} from 'react';
import {getUserId} from "../utils/Common";
import axios from "axios";

/**
 * @author Kristian Tkacik
 */
export function YourRatings() {
    const [ratings, setRatings] = useState([]);

    useEffect(() => {
        const getRatings = async () => {
            return await axios.get(`http://localhost:8080/pa165/rest/ratings/find-by-user?id=${getUserId()}`)
                .catch(console.log);
        }
        getRatings().then(ratings => setRatings(ratings.data));
    }, [])

    const handleDelete = async (ratingId) => {
        await axios.delete(`http://localhost:8080/pa165/rest/ratings/remove`, {
            data: {id: ratingId},
            headers: {
                'Content-Type': 'application/json'
            }
        })
        setRatings((prev) => prev.filter((rating) => rating.id !== ratingId));
    }

    return (
        <div>
            {ratings.length === 0 && <p>You do not have any ratings</p>}
            <ul>
                {ratings.map(({ id, movie, originality, soundtrack, narrative, cinematography, depth }) => (
                    <li key={id}>
                        <div>
                            <h2>{movie.title}</h2>
                        </div>
                        {<p><b>Originality: </b>{originality}</p>}
                        {<p><b>Soundtrack: </b>{soundtrack}</p>}
                        {<p><b>Narrative: </b>{narrative}</p>}
                        {<p><b>Cinematography: </b>{cinematography}</p>}
                        {<p><b>Depth: </b>{depth}</p>}
                        <button onClick={() => handleDelete(id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}