import React, {Component} from 'react'
import { Button, Modal} from "react-bootstrap";
import './registrationModal.css'
import UserRegisterForm from "./userRegisterForm";
import CompanyRegisterForm from './companyRegisterForm'

export default class RegistrationModal extends Component {
    state = {
        stage: null
    };

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

    render() {
        const { show, onHide } = this.props;
        const { stage } = this.state;

        let body = null;
        if(stage == null) {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Odaberite vrstu registracije:</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className='center'>
                            <div className="reg-choose">
                                <Button name="tvrtka" className="reg-btn"  bsStyle="info" onClick={this.onChoose}>Tvrtka</Button>
                                <p>Moći ćete <b>nuditi usluge parkinga</b> kroz ovu aplikaciju.</p>
                            </div>
                            <div className="reg-choose">
                                <Button name="korisnik" className="reg-btn" bsStyle="info" onClick={this.onChoose}>Korisnik</Button>
                                <p>Moći ćete <b>rezervirati parking</b> kroz ovu aplikaciju.</p>
                            </div>
                        </div>
                    </Modal.Body>
                </div>
            );
        } else if(stage == "tvrtka") {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Registracija tvrtke:</Modal.Title>
                    </Modal.Header>
                    <CompanyRegisterForm back={this.resetStage} onHide={this.hide}/>
                </div>
            );
        } else if(stage == "korisnik") {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Registracija korisnika:</Modal.Title>
                    </Modal.Header>
                    <UserRegisterForm back={this.resetStage} onHide={this.hide}/>
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