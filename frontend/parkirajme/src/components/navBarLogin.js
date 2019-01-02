import React, {Component} from 'react'
import {Navbar, Button, FormGroup, FormControl, HelpBlock} from "react-bootstrap";
import './navBar.css'
import LoadingIcon from './loadingIcon'

export default class NavBarLogin extends Component {
    state = {
        email: '',
        password: '',
        loginLoading: false,
        invalidLogin: false
    }

    logIn = (event) => {
        //TODO
        event.preventDefault();
        this.setState({loginLoading: true});
        const {email, password} = this.state;
        if (email == 'disho' && password == 'abc') {
            sessionStorage.setItem("id", "1");
            sessionStorage.setItem("name", "disho");
            sessionStorage.setItem("tip", "0");
            this.props.loginSuccess();
        } else  if(email == 'admin' && password == '123') {
            sessionStorage.setItem("id", "1");
            sessionStorage.setItem("name", "admin");
            sessionStorage.setItem("tip", "2");
            this.props.loginSuccess();
        } else  if(email == 'tvrtka' && password == '123abc') {
            sessionStorage.setItem("id", "11");
            sessionStorage.setItem("name", "tvrtka");
            sessionStorage.setItem("tip", "1");
            this.props.loginSuccess();
        } else {
            setTimeout(() => this.setState({invalidLogin: true}), 500);
        }

        setTimeout(() => this.setState({loginLoading: false}), 500);
    };

    updateLogin = (e) => {
        this.setState({
            [e.target.name]: e.target.value,
            invalidLogin: false
        })
    }

    render() {
        const {email, password, loginLoading, invalidLogin} = this.state;

        return (

            <form onSubmit={this.logIn}>
                    <FormGroup validationState={invalidLogin ? 'error' : null}>
                        <FormControl name="email" value={email} type="text" placeholder="e-mail"
                                     onChange={this.updateLogin}/> {' '}
                        <FormControl name="password" value={password} type="password" placeholder="lozinka"
                                     onChange={this.updateLogin}/>
                        {' '}
                        <Button bsStyle="success" type="submit" onClick={this.logIn}
                                disabled={loginLoading}>
                            {loginLoading ? 'Provjera...' : 'Prijavi se'}
                        </Button> {'  '}
                        <HelpBlock className="inline">{invalidLogin ? 'Neispravni podatci.' : null}</HelpBlock>
                        <LoadingIcon show={loginLoading}/>
                    </FormGroup>
            </form>

        );
    }

}