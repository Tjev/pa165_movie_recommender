import {getAdminStatus} from "../utils/Common";
import {NavLink} from "react-router-dom";

export function AddDirectorLink(id, title, token) {
    if (token && getAdminStatus()) {
        return (<NavLink exact activeClassName="active" to={{
            pathname:'/add-director',
            state: {id: id, title: title}
        }} >
            <button type="button">Add director</button>
        </NavLink>);
    }
}