import React, {Component} from 'react'
import {Button, Navbar } from "react-bootstrap";
import LoadingIcon from './loadingIcon'

export default class LogoutBtn extends Component{
    state = {
        isLoggingOut: false
    };

    sendLogoutReq = () => {
        this.setState({isLoggingOut: true});
        //posalji zahtjev na server za logout

        this.props.logoutSuccess();
        setTimeout(() => {this.setState({isLoggingOut: false})}, 5000);
    };

    render() {
        const { isLoggingOut } = this.state;
        const { isAddingNewParking } = this.props;

        return (
            <Navbar.Form pullRight>
                <Button bsStyle="danger" disabled={isAddingNewParking} onClick={this.sendLogoutReq}>Odjavi se</Button>
                {'   '}
                <LoadingIcon show={isLoggingOut}/>
            </Navbar.Form>
        );
    }
}