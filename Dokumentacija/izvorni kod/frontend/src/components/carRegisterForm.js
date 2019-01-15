import React, {Component} from 'react'
import {
    FormGroup,
    Form,
    Alert,
    FormControl,
    HelpBlock,
    Modal,
    Button
} from 'react-bootstrap'

export default class CarRegisterForm extends Component {
    state = {
        ime: '',
        registracija: '',
        vime: null,
        vregistracija: null,
        alertMsg: null,
        registrationValid: false
    };


    imeChange = (e) => {
        const ime = e.target.value;
        let test = ime.length > 0;
        this.setState({
            ime: ime,
            vime: test,
            alertMsg: null
        })
    };

    registracijaChange = (e) => {
        const registracija = e.target.value;
        let test = /^[a-zA-Z]{2}-[0-9]{3,4}-[a-zA-Z]{1,2}$/.test(registracija);
        this.setState({
            registracija: registracija,
            vregistracija: test,
            alertMsg: null
        })
    };


    validationState = (test) => {
        if(test == null) return null;
        return test ? "success" : "error";
    };

    register = () => {
        const {vime, vregistracija, ime, registracija} = this.state;
        let test = (vime && vregistracija);

        if(test) {
            //registriraj na backendu

            const data = {
                ime: ime,
                registracijskaOznaka: registracija.toUpperCase()
            };

            fetch('/automobili', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            }).then(response => {
                if(response.status != 200) {
                    this.setState({
                        registrationValid: false,
                        alertMsg: 'Automobil s danom registracijom već postoji.'
                    });
                } else {
                    this.setState({
                        registrationValid: true,
                        alertMsg: 'Uspješna registracija automobila!'
                    });
                    this.props.carsUpdate();
                    setTimeout(() => this.props.back(), 2500);
                }
            });
        } else {
            this.setState({
                registrationValid: false,
                alertMsg: 'Neispravni podatci!'
            });
        }
    };

    render() {
        const { ime, registracija, vime, vregistracija, alertMsg, registrationValid } = this.state;
        const {back} = this.props;

        return (
            <div>
                <Modal.Body>
                    <Form>
                        <FormGroup validationState={this.validationState(vime)}>
                            <h5><b>Naziv</b></h5>
                            <FormControl name="ime" type="text" value={ime} onChange={this.imeChange}/>
                            <HelpBlock>{(vime == null) || (vime ? '' : 'Morate unjeti naziv')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vregistracija)}>
                            <h5><b>Registracija</b></h5>
                            <FormControl name="registracija" type="text" value={registracija} onChange={this.registracijaChange}/>
                            <HelpBlock>{(vregistracija == null) || (vregistracija ? '' : 'format: AB-1234-CD')}</HelpBlock>
                        </FormGroup>
                        {
                            alertMsg ?
                                <Alert bsStyle={registrationValid ? "success" : "danger"}>
                                    {alertMsg}
                                </Alert> :
                                null
                        }
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button bsStyle="primary" onClick={back}>Natrag</Button>{'  '}
                    <Button bsStyle="success" onClick={this.register}>Registriraj automobil</Button>
                </Modal.Footer>
            </div>
        );
    }
}