import React, {Component} from 'react'
import {Button, Navbar } from "react-bootstrap";
import LoadingIcon from './loadingIcon'

export default class LogoutBtn extends Component{
    state = {
        isLoggingOut: false
    }

    sendLogoutReq = () => {
        this.setState({isLoggingOut: true});
        //posalji zahtjev na server za logout

        setTimeout(() => {this.setState({isLoggingOut: false})}, 500);

        this.props.logoutSuccess();
    }

    render() {
        const { isLoggingOut } = this.state;

        return (
            <Navbar.Form pullRight>
                <Button bsStyle="danger" onClick={this.sendLogoutReq}>Odjavi se</Button>
                {'   '}
                <LoadingIcon show={isLoggingOut}/>
            </Navbar.Form>
        );
    }
}