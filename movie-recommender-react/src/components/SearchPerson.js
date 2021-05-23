import React, {useState} from 'react';

/**
 * @author Kristian Tkacik, Jiri Papousek
 */
function PersonList({ persons }) {
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
                </li>
            ))}
        </ul>
    );
}

export function SearchPerson() {
    const [personName, setPersonName] = useState('');
    const [persons, setPersons] = useState([]);

    const handleSubmit = async (e) => {
        if (!personName) return;
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
            <div className="search-wrapper">
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
                <PersonList persons={persons} />
            </div>
        </div>
    );
}
