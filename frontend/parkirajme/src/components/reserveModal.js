import React, {Component} from 'react'
import {Button, Modal, Well} from "react-bootstrap";
import './registrationModal.css'
import JednokratnaRezervacijaForm from './jednokratnaRezervacijaForm'
import PonavljajucaRezervacijaForm from './ponavljajucaRezervacijaForm'
import TrajnaRezervacijaForm from './trajnaRezervacijaForm'

export default class ReserveModal extends Component {
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
        const { show, onHide, parking, cars } = this.props;
        const { stage } = this.state;

        let body = null;
        if(stage == null) {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Odaberite vrstu rezervacije:</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className='center1'>
                            <div className='center2'>
                                <div className="reg-choose">
                                    <Button name="jednokratna" className="reg-btn"  bsStyle="info" onClick={this.onChoose}>Jednokratna</Button>
                                    <p>Jednokratna rezervacija za <b>vremenski period kraći od 24 sata</b> i to morate obaviti <b>barem 6 sati unaprijed</b>.</p>
                                </div>
                                <div className="reg-choose">
                                    <Button name="ponavljajuca" className="reg-btn" bsStyle="info" onClick={this.onChoose}>Ponavljajuća</Button>
                                    <p>Rezervacija koja se <b>ponavlja tjedno</b> sljedećih <b>mjesec dana</b>.</p>
                                </div>
                                <div className="reg-choose">
                                    <Button name="trajna" className="reg-btn" bsStyle="info" onClick={this.onChoose}>Trajna</Button>
                                    <p>Rezervacija na <b>neograničeni period</b> (sve dok ju ne ukinete).</p>
                                </div>
                            </div>
                        </div>
                    </Modal.Body>
                </div>
            );
        } else if(stage == "jednokratna") {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Jednokratna rezervacija</Modal.Title>
                    </Modal.Header>
                    <JednokratnaRezervacijaForm back={this.resetStage} onHide={this.hide} parking={parking}/>
                </div>
            );
        } else if(stage == "ponavljajuca") {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Ponavljajuća rezervacija</Modal.Title>
                    </Modal.Header>
                    <PonavljajucaRezervacijaForm back={this.resetStage} onHide={this.hide} parking={parking}/>
                </div>
            );
        } else if(stage == "trajna") {
            body = (
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Trajna rezervacija</Modal.Title>
                    </Modal.Header>
                    <TrajnaRezervacijaForm back={this.resetStage} onHide={this.hide} parking={parking}/>
                </div>
            );
        }

        return (
            <Modal className="registration-modal" show={show} onHide={this.hide} keyboard={true}>
                {cars.length != 0 ?
                    body :
                    <div>
                        <Modal.Header closeButton onHide={onHide}>
                            <Modal.Title>Trenutno nije moguće rezervirati</Modal.Title>
                        </Modal.Header>
                        <Well>
                            <p>
                                Nemate registriran nijedan automobil.<br/>
                                Idite u izbornik <i>Automobili</i> te dodajte barem jedan automobil.
                            </p>
                        </Well>
                    </div>
                    }
            </Modal>
        );
    }
}