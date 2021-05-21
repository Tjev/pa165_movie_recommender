import {getAdminStatus} from "../utils/Common";
import {NavLink} from "react-router-dom";

export function AddActorLink(id, title, token) {
    if (token && getAdminStatus()) {
        return (<NavLink exact activeClassName="active" to={{
            pathname:'/add-actor',
            state: {id: id, title: title}
        }} >
            <button type="button">Add actor</button>
        </NavLink>);
    }
}
