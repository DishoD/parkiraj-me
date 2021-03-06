import React, {Component} from 'react'
import {Modal} from "react-bootstrap";
import './registrationModal.css'
import ReservationsList from './reservationsList'

export default class ReservationsModal extends Component {
    render() {
        const { show, onHide, parkingName } = this.props;


        return (
            <Modal show={show} onHide={onHide} keyboard={true}>
                <div>
                    <Modal.Header closeButton onHide={onHide}>
                        <Modal.Title>Vaše trajne rezervacije</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <ReservationsList parkingName={parkingName}/>
                    </Modal.Body>
                    <Modal.Footer>
                        <small>Napravite novu rezervaciju tako da kliknete na željeno parkiralište na karti te potom gumb 'rezerviraj'.</small>
                    </Modal.Footer>
                </div>
            </Modal>
        );
    }
}