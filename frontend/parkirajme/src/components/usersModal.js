import React, {Component} from 'react'
import { Button, Modal} from "react-bootstrap";
import './registrationModal.css'
import UsersList from './usersList'

var korisnici = [
    {
        oib: '11111111111',
        ime: 'Marko',
        prezime: 'Markić',
        email: 'marko.markic@gmail.com',
    },
    {
        oib: '22222222222',
        ime: 'Pero',
        prezime: 'Perić',
        email: 'pp@fer.hr',
    },
    {
        oib: '12121516154',
        ime: 'Tamara',
        prezime: 'Tam',
        email: 'tam.tam@yahoo.hr',
    },
    {
        oib: '98745632159',
        ime: 'Barbara',
        prezime: 'Bić',
        email: 'barbie.bic@gmail.com',
    }
];

var tvrtke = [
    {
        oib: '12312312312',
        ime: 'Zagreb parking',
        email: 'zp@zg.hr',
        adresa: 'Ilica 26, 10000 Zagreb'
    },
    {
        oib: '32132132132',
        ime: 'Parkiraj se, org.',
        email: 'parkiraj@se.hr',
        adresa: 'Savska 13, 10000 Zagreb'
    },
    {
        oib: '15915915995',
        ime: 'Rebro',
        email: 'rebro@zg-bolnice.hr',
        adresa: 'Kišpatićeva ul. 12, 10000, Zagreb'
    },
];


export default class UsersModal extends Component {
    state = {
        stage: null,
        users: null
    };

    hide = () => {
        this.props.onHide();
        setTimeout(this.resetStage, 200);
    };

    onChoose = (e) => {
        this.setState({stage: e.target.name});
        if(e.target.name === 'korisnici') {
            this.updateKorisnici();
        } else {
            this.updateTvrtke();
        }
    };

    resetStage = () => {
        this.setState({stage: null});
    };

    getKorisnici = () => {
        //TODO
        return korisnici;
    };

    getTvrtke = () => {
        //TODO
        return tvrtke;
    };

    updateTvrtke = () => {
        this.setState({users: this.getTvrtke()});
    };

    updateKorisnici = () => {
        this.setState({users: this.getKorisnici()});
    };

    render() {
        const { show, onHide } = this.props;
        const { stage, users } = this.state;

        let body = null;
        if(stage == null) {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Odaberite vrstu računa:</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className='center'>
                            <div className="reg-choose">
                                <Button name="tvrtke" className="reg-btn"  bsStyle="info" onClick={this.onChoose}>Tvrtke</Button>
                            </div>
                            <div className="reg-choose">
                                <Button name="korisnici" className="reg-btn" bsStyle="info" onClick={this.onChoose}>Korisnici</Button>
                            </div>
                        </div>
                    </Modal.Body>
                </div>
            );
        } else if(stage === "tvrtke") {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Tvrtke</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <UsersList type={stage} users={users} updateUsers={this.updateTvrtke}/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this.resetStage}>Natrag</Button>
                    </Modal.Footer>
                </div>
            );
        } else if(stage === "korisnici") {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Korisnici</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <UsersList type={stage} users={users} updateUsers={this.updateKorisnici}/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this.resetStage}>Natrag</Button>
                    </Modal.Footer>
                </div>
            );
        }

        return (
            <Modal className="registration-modal" show={show} onHide={this.hide} keyboard={true}>
                {body}
            </Modal>
        );
    }
}