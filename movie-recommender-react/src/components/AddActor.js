import React, {useState} from 'react';
import axios from "axios";
import {useLocation} from "react-router-dom";

/**
 * @author Jiri Papousek
 */
function PersonList({ persons, movieId }) {

    const handleAddActor = async (e) => {
        e.preventDefault();
        const movie = await axios.get(`http://localhost:8080/pa165/rest/movies/${movieId}`);

        movie.data.actors.push({id: e.target.value});

        return await axios.put('http://localhost:8080/pa165/rest/movies/update',
            movie.data)
            .then(
                response => JSON.stringify(response))
            .then(
                () => alert("Actor successfully added"))
            .catch((msg) => alert(msg))
    }

    if (persons.length === 0) {
        return <p>No results found</p>;
    }
    return (
        <ul>
            {persons.map(({ id, name, bio, dateOfBirth }) => (
                <li key={id}>
                    <div>
                        <h2>{name}</h2>
                    </div>
                    {<p><b>Bio: </b> {bio}</p>}
                    {<p><b>Date of birth: </b>{dateOfBirth}</p>}
                    <button value={id} onClick={handleAddActor}>Add actor</button>
                </li>
            ))}
        </ul>
    );
}


export function AddActor() {
    let location = useLocation();

    const [personName, setPersonName] = useState('');
    const [persons, setPersons] = useState([]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        await fetch(`http://localhost:8080/pa165/rest/persons/find-by-name?name=${personName}`)
            .then(res => res.json())
            .then((data) => {
                setPersons(data);
            })
            .catch(console.log);
    }

    return (
        <div>
            <div className="add-actor-wrapper">
                <h1>Search person:</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        Name:
                        <input type="text"
                               value={personName}
                               onChange={e => setPersonName(e.target.value)}
                               name="person name"
                               placeholder="Enter person name"/>
                    </label>
                    <input type="submit" value="Search" />
                </form>
            </div>
            <div>
                <PersonList persons={persons} movieId={location.state.id}/>
            </div>
        </div>
    );
}