import React, {useState} from 'react';
import DatePicker from 'react-date-picker'
import axios from "axios";

/**
 * @author Kristian Tkacik
 */
export function CreatePerson() {
    const [personName, setPersonName] = useState('');
    const [personBio, setPersonBio] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        return await axios.post('http://localhost:8080/pa165/rest/persons/create',
            {
            name: personName,
            bio: personBio,
            dateOfBirth: dateOfBirth})
            .then(
                response => JSON.stringify(response))
            .then(
                () => alert("Person added successfully"))
            .catch((msg) => alert(msg))
    }

    return (
        <div className="create-person-wrapper">
            <h1>Create person:</h1>
            <form onSubmit={handleSubmit}>
                <table>
                    <tr>
                        <td>
                            <label>
                                Name:
                            </label>
                        </td>
                        <td>
                            <input type="text"
                                   value={personName}
                                   onChange={e => setPersonName(e.target.value)}
                                   name="name"
                                   placeholder="Enter person name"
                                   style={{width: 700}} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Bio:
                            </label>
                        </td>
                        <td>
                            <textarea value={personBio}
                                      onChange={e => setPersonBio(e.target.value)}
                                      name="bio"
                                      placeholder="Enter person bio"
                                      style={{width: 700, height: 100}} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Date of birth:
                            </label>
                        </td>
                        <td>
                            <DatePicker value={dateOfBirth} onChange={setDateOfBirth} />
                        </td>
                    </tr>
                    <input type="submit" value="Submit" />
                </table>
            </form>
        </div>
    );
}
