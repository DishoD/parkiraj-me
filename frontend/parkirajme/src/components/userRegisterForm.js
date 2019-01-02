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

export default class UserRegisterForm extends Component {
    state = {
        oib: '',
        ime: '',
        prezime: '',
        email: '',
        password: '',
        creditCardNumber: '',
        voib: null,
        vime: null,
        vprezime: null,
        vemail: null,
        vpassword: null,
        vcreditCardNumber: null,
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

    prezimeChange = (e) => {
        const prezime = e.target.value;
        let test = prezime.length > 0;
        this.setState({
            prezime: prezime,
            vprezime: test,
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
        let test = oib.length == 11 && /^\d+$/.test(oib);
        this.setState({
            oib: oib,
            voib: test,
            alertMsg: null
        })
    };

    creditCardNumberChange = (e) => {
        const ccn = e.target.value;
        let test = ccn.length >= 14 && ccn.length <= 19 && /^\d+$/.test(ccn);
        this.setState({
            creditCardNumber: ccn,
            vcreditCardNumber: test,
            alertMsg: null
        })
    };

    validationState = (test) => {
        if(test == null) return null;
        return test ? "success" : "error";
    };

    register = () => {
        this.setState({showLoadinIcon: true});
        const {voib, vime, vprezime, vemail, vpassword, vcreditCardNumber } = this.state;
        let test = (voib && vime && vprezime && vemail && vpassword && vcreditCardNumber);
        let alertMsg = test ? null : "Krivi podatci.";

        if(test) {
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
        const {oib, ime, prezime, email, password, creditCardNumber, voib, vime, vprezime, vemail, vpassword, vcreditCardNumber, alertMsg, registrationValid } = this.state;
        const {back} = this.props;

        return (
            <div>
                <Modal.Body>
                    <small>sva polja su obavezna*</small>
                    <Form>
                        <FormGroup validationState={this.validationState(vemail)}>
                            <h5><b>E-mail</b></h5>
                            <FormControl name="email" type="text" value={email} onChange={this.emailChange}/>
                            <HelpBlock>{(vemail == null) || (vemail ? '' : 'Krivi format')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vpassword)}>
                            <h5><b>Lozinka</b></h5>
                            <FormControl name="password" type="password" value={password} onChange={this.passwordChange}/>
                            <HelpBlock>{(vpassword == null) || (vpassword ? '' : 'Prekratka lozinka')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vime)}>
                            <h5><b>Ime</b></h5>
                            <FormControl name="ime" type="text" value={ime} onChange={this.imeChange}/>
                            <HelpBlock>{(vime == null) || (vime ? '' : 'Morate unjeti ime')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vprezime)}>
                            <h5><b>Prezime</b></h5>
                            <FormControl name="prezime" type="text" value={prezime} onChange={this.prezimeChange}/>
                            <HelpBlock>{(vprezime == null) || (vprezime ? '' : 'Morate unjeti prezime')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(voib)}>
                            <h5><b>OIB</b></h5>
                            <FormControl name="oib" type="text" value={oib} onChange={this.oibChange}/>
                            <HelpBlock>{(voib == null) || (voib ? '' : 'Neispravan OIB')}</HelpBlock>
                        </FormGroup>
                        <FormGroup validationState={this.validationState(vcreditCardNumber)}>
                            <h5><b>Broj kreditne kartice</b></h5>
                            <FormControl name="creditCardNumber" type="text" value={creditCardNumber} onChange={this.creditCardNumberChange}/>
                            <HelpBlock>{(vcreditCardNumber == null) || (vcreditCardNumber ? '' : 'Neispravan broj kreditne kartice')}</HelpBlock>
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