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

export default class CompanyRegisterForm extends Component {
    state = {
        oib: '',
        ime: '',
        email: '',
        password: '',
        address: '',
        voib: null,
        vime: null,
        vemail: null,
        vpassword: null,
        vaddress: null,
        alertMsg: null,
        registrationValid: false
    };

    emailChange = (e) => {
        const email = e.target.value;
        let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        let test = re.test(email.toLowerCase());
        this.setState({
            email: email,
            vemail: test,
            alertMsg: null
        })
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

    passwordChange = (e) => {
        const password = e.target.value;
        let test = password.length > 4;
        this.setState({
            password: password,
            vpassword: test,
            alertMsg: null
        })
    };

    oibChange = (e) => {
        const oib = e.target.value;
        let test = oib.length === 11 && /^\d+$/.test(oib);
        this.setState({
            oib: oib,
            voib: test,
            alertMsg: null
        })
    };

    addressChange = (e) => {
        const address = e.target.value;
        let test = address.length > 0;
        this.setState({
            address: address,
            vaddress: test,
            alertMsg: null
        })
    };

    validationState = (test) => {
        if(test == null) return null;
        return test ? "success" : "error";
    };

    register = () => {
        this.setState({showLoadinIcon: true});
        const {voib, vime, vemail, vpassword, vaddress } = this.state;
        let test = (voib && vime && vemail && vpassword && vaddress);
        let alertMsg = test ? null : "Krivi podatci.";

        if(test) {
            //TODO
            //registriraj na backendu

            //ako je i dalje ispravno
            alertMsg = test ? "Uspješna registracija! Sada se možete prijaviti." : "Krivi podatci."; //ovisno o erroru treba postavit grešku
            setTimeout(() => this.props.onHide(), 5000);
        }

        this.setState({
            registrationValid: test,
            alertMsg: alertMsg
        });
    };

    render() {
        const {oib, ime, email, password, address, voib, vime, vemail, vpassword, vaddress, alertMsg, registrationValid } = this.state;
        const {back} = this.props;

        return (
            <div>
                <Modal.Body>
                    <small>sva polja su oabvezna*</small>
                    <Form>
                        <FormGroup validationState={this.validationState(vemail)}>
                            <h5><b>E-mail</b></h5>
                            <FormControl name="email" type="email" value={email} onChange={this.emailChange} autoComplete="email"/>
                            <HelpBlock>{(vemail == null) || (vemail ? '' : 'Krivi format')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vpassword)}>
                            <h5><b>Lozinka</b></h5>
                            <FormControl name="password" type="password" value={password} onChange={this.passwordChange} autoComplete="new-password"/>
                            <HelpBlock>{(vpassword == null) || (vpassword ? '' : 'Prekratka lozinka')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vime)}>
                            <h5><b>Ime</b></h5>
                            <FormControl name="ime" type="text" value={ime} onChange={this.imeChange} autocomplete="organization"/>
                            <HelpBlock>{(vime == null) || (vime ? '' : 'Morate unjeti ime')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(voib)}>
                            <h5><b>OIB</b></h5>
                            <FormControl name="oib" type="text" value={oib} onChange={this.oibChange}/>
                            <HelpBlock>{(voib == null) || (voib ? '' : 'Neispravan OIB')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vaddress)}>
                            <h5><b>Adresa sjedišta</b></h5>
                            <FormControl name="address" type="address" value={address} onChange={this.addressChange} autoComplete="address-line1"/>
                            <HelpBlock>{(vaddress == null) || (vaddress ? '' : 'Morate unijeti adresu')}</HelpBlock>
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
                    <Button bsStyle="success" onClick={this.register}>Registriraj se</Button>
                </Modal.Footer>
            </div>
        );
    }
}