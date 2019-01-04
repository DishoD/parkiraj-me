import React, {Component} from 'react'
import { Navbar, Button } from "react-bootstrap";
import './navBar.css'
import Image from "react-bootstrap/es/Image";
import NavBarLogin from './navBarLogin'
import LogoutBtn from './logoutBtn'
import RegistrationModal from './registrationModal'
import CarsModal from './carsModal'
import ReservationsModal from './reservationsModal'
import UserModal from './usersModal'

export default class NavBar extends Component{

    state = {
        registerShow: false,
        carsShow: false,
        reservationsShow: false,
        usersShow: false
    };

    registerShowOn = () => {
        this.setState({registerShow: true});
    };

    registerShowOff = () => {
        this.setState({registerShow: false});
    };

    carsShowOn = () => {
        this.setState({carsShow: true});
    };

    carsShowOff = () => {
        this.setState({carsShow: false});
    };

    reservationsShowOn = () => {
        this.setState({reservationsShow: true});
    };

    reservationsShowOff = () => {
        this.setState({reservationsShow: false});
    };

    usersShowOn = () => {
        this.setState({usersShow: true});
    };

    usersShowOff = () => {
        this.setState({usersShow: false});
    };


    render() {
        const { registerShow, carsShow, reservationsShow, usersShow } = this.state;
        const { loginSuccess, tipKorisnika, logoutSuccess, cars, carsUpdate, addCar, newParkingToggle, isAddingNewParking } = this.props;

        let body = null;
        switch (tipKorisnika) {
            case 3:
                body = (
                    <Navbar.Collapse>
                        <Navbar.Form pullLeft>
                            <NavBarLogin loginSuccess={loginSuccess}/>
                        </Navbar.Form>
                        <Navbar.Form pullRight>
                            <Button bsStyle="primary" type="submit" onClick={this.registerShowOn}>Registriraj se</Button>
                        </Navbar.Form>
                    </Navbar.Collapse>
                );
                break;
            case 0:
                body = (
                    <Navbar.Collapse>
                        <Navbar.Text>
                            Bok, <b>{sessionStorage.getItem('name')}</b>
                        </Navbar.Text>
                        <Navbar.Form pullLeft>
                            <Button bsStyle="info" onClick={this.carsShowOn}>Automobili</Button> {'    '}
                            <Button bsStyle="info" onClick={this.reservationsShowOn}>Trajne rezervacije</Button> {'    '}
                        </Navbar.Form>
                        <LogoutBtn logoutSuccess={logoutSuccess}/>
                    </Navbar.Collapse>
                );
                break;
            case 1:
                body = (
                    <Navbar.Collapse>
                        <Navbar.Text>
                            Tvrtka: <b>{sessionStorage.getItem('name')}</b>
                        </Navbar.Text>
                        <Navbar.Form pullLeft>
                            <Button bsStyle="info" onClick={newParkingToggle} disabled={isAddingNewParking}>Dodaj novi parking</Button> {'    '}
                        </Navbar.Form>
                        <LogoutBtn isAddingNewParking={isAddingNewParking} logoutSuccess={logoutSuccess}/>
                    </Navbar.Collapse>
                );
                break;
            case 2:
                body = (
                    <Navbar.Collapse>
                        <Navbar.Text>
                            Administrator
                        </Navbar.Text>
                        <Navbar.Form pullLeft>
                            <Button bsStyle="info" onClick={this.usersShowOn}>Upravljanje računima</Button> {'    '}
                        </Navbar.Form>
                        <LogoutBtn logoutSuccess={logoutSuccess}/>
                    </Navbar.Collapse>
                );
                break;
        }

        return (
            <div>
                <Navbar inverse fixedTop>
                    <Navbar.Header>
                        <Navbar.Brand>
                            <Image src="./icons/brand-logo.png"/>
                        </Navbar.Brand>
                        <Navbar.Toggle />
                    </Navbar.Header>
                    {body}
                </Navbar>

                <RegistrationModal show={registerShow} onHide={this.registerShowOff}/>
                <CarsModal show={carsShow} onHide={this.carsShowOff} cars={cars} carsUpdate={carsUpdate} addCar={addCar}/>
                <ReservationsModal show={reservationsShow} onHide={this.reservationsShowOff}/>
                <UserModal show={usersShow} onHide={this.usersShowOff}/>
            </div>
        );
    }

}