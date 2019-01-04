import React, {Component} from 'react'
import {Alert, Button, Modal, Well} from 'react-bootstrap'




export default class TrajnaRezervacijaForm extends Component {
    state = {
        alertMsg: null,
        registrationValid: false
    };

    register = () => {

        //TODO

        //registriraj na backendu

        let test = true;
        //ako je i dalje ispravno
        let alertMsg = test ? "Uspješna rezervacija!" : "Parkiralište je popunjeno u nekim periodima. Ne mogu rezervirati."; //ovisno o erroru treba postavit grešku
        if (test) setTimeout(() => this.props.onHide(), 5000);

        this.setState({
            registrationValid: test,
            alertMsg: alertMsg
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