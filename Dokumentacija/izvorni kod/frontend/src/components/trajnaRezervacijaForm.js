import React, {Component} from 'react'
import {Alert, Button, Modal, Well} from 'react-bootstrap'




export default class TrajnaRezervacijaForm extends Component {
    state = {
        alertMsg: null,
        registrationValid: false
    };

    register = () => {
        const data = {
            parkingID: this.props.parking.id,
        };

        fetch('/rezervacije/trajna', {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if(response.status != 200) {
                    response.json().then(data => {
                        this.setState({
                            registrationValid: false,
                            alertMsg: data.message
                        });
                    })
                } else {
                    this.setState({
                        registrationValid: true,
                        alertMsg: "Uspješna rezervacija!"
                    });
                    this.props.parkingsUpdate();
                    setTimeout(() => this.props.onHide(), 2500)
                }
            });
    };


    render() {
        const {back} = this.props;
        const {alertMsg, registrationValid} = this.state;

        return (
            <div>
                <Modal.Body>
                    <Well>
                        Trajna rezervacija počinje od trenutka kada kliknete gumb <i>rezerviraj</i>.<br/>
                        Neograničeno ćete biti rezervirani u odabranom parkiralištu.<br/>
                        Da biste rezervaciju ukinuli idite na izbornik <i>Trajne rezervacije</i> te ukiniti ovu trajnu rezervaciju.
                    </Well>
                    {
                        alertMsg ?
                            <Alert bsStyle={registrationValid ? "success" : "danger"}>
                                {alertMsg}
                            </Alert> :
                            null
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button bsStyle="primary" onClick={back}>Natrag</Button>{'  '}
                    <Button bsStyle="success" onClick={this.register}>Rezerviraj</Button>
                </Modal.Footer>
            </div>
        );
    }
}