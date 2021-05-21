import React, {useState} from "react";
import {useLocation} from "react-router-dom";
import axios from "axios";
import {getUserId} from "../utils/Common";

/**
 * @author Radoslav Chudovsky
 */
const RatingSelect = ({rating, setter}) => {
    const values = [1, 2, 3, 4, 5]

    return (
        <select name={3} value={rating} onChange={e => setter(e.target.value)}>
            {values.map((option, i) => <option key={i} value={option}>
                {option}
            </option>)}
        </select>
    )

}

export function CreateRating() {
    let location = useLocation();

    const [originality, setOriginality] = useState(1);
    const [soundtrack, setSoundtrack] = useState(1);
    const [narrative, setNarrative] = useState(1);
    const [cinematography, setCinematography] = useState(1);
    const [depth, setDepth] = useState(1);

    const handleSubmit = async (e) => {
        e.preventDefault();
        await axios.post('http://localhost:8080/pa165/rest/ratings/create',
            {
                user: {id: getUserId()},
                movie: {id: location.state.id},
                originality: originality,
                soundtrack: soundtrack,
                narrative: narrative,
                cinematography: cinematography,
                depth: depth
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(
                () => alert("Rating added successfully"))
            .catch((msg) => alert(msg))
    }

    return (
        <div className="create-rating-wrapper">
            <h1>Create rating for {location.state.title}:</h1>
            <form onSubmit={handleSubmit}>
                <table>
                    <tr>
                        <td>
                            <label>
                                Originality:
                            </label>
                        </td>
                        <td>
                            <RatingSelect rating={originality} setter={setOriginality} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Soundtrack:
                            </label>
                        </td>
                        <td>
                            <RatingSelect rating={soundtrack} setter={setSoundtrack} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Narrative:
                            </label>
                        </td>
                        <td>
                            <RatingSelect rating={narrative} setter={setNarrative} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Cinematography:
                            </label>
                        </td>
                        <td>
                            <RatingSelect rating={cinematography} setter={setCinematography} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Depth:
                            </label>
                        </td>
                        <td>
                            <RatingSelect rating={depth} setter={setDepth} />
                        </td>
                    </tr>
                    <input type="submit" value="Submit" />
                </table>
            </form>
        </div>
    )
}