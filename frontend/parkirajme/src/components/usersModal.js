import React, {Component} from 'react'
import { Button, Modal} from "react-bootstrap";
import './registrationModal.css'
import UsersList from './usersList'

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
        users: [],
        companies: []
    };

    componentDidMount() {
        this.updateTvrtke();
        this.updateKorisnici();
    }

    hide = () => {
        this.props.onHide();
        setTimeout(this.resetStage, 200);
    };

    onChoose = (e) => {
        this.setState({stage: e.target.name});
    };

    resetStage = () => {
        this.setState({stage: null});
    };


    updateTvrtke = () => {
        fetch('/tvrtke')
            .then(res => res.json())
            .then(data => {
                this.setState({companies: data});
            });
    };

    updateKorisnici = () => {
        fetch('/korisnici')
            .then(res => res.json())
            .then(data => {
                this.setState({users: data});
            });
    };

    render() {
        const { show, onHide } = this.props;
        const { stage, users, companies } = this.state;

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
                        <UsersList type={stage} companies={companies} updateUsers={this.updateTvrtke}/>
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