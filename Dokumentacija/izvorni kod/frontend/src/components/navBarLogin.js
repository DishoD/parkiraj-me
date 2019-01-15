import React, {Component} from 'react'
import {Button, FormGroup, FormControl, HelpBlock} from "react-bootstrap";
import './navBar.css'
import LoadingIcon from './loadingIcon'

export default class NavBarLogin extends Component {
    state = {
        email: '',
        password: '',
        loginLoading: false,
        invalidLogin: false
    };

    logIn = (event) => {
        event.preventDefault();
        this.setState({loginLoading: true});
        const {email, password} = this.state;
        let body = new FormData();
        body.append('username', email);
        body.append('password', password);
        const options = {
            method: 'POST',
            body: body
        };
        fetch('/login', options)
            .then(res => {
                if(res.status != 200) {
                    this.setState({loginLoading: false});
                    setTimeout(() => this.setState({invalidLogin: true}), 10);
                    return;
                } else {
                    res.json().then(data => {
                        sessionStorage.setItem('tip', data.tip);
                        sessionStorage.setItem('name', data.ime);
                        sessionStorage.setItem('id', data.id);
                        sessionStorage.setItem('username', data.username);
                        this.setState({loginLoading: false});
                        setTimeout(this.props.loginSuccess, 10);
                    });
                }
            });
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
                        <FormControl name="email" value={email} type="email" placeholder="e-mail"
                                     onChange={this.updateLogin} autoComplete="email"/> {' '}
                        <FormControl name="password" value={password} type="password" placeholder="lozinka"
                                     onChange={this.updateLogin} autoComplete="current-password"/>
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