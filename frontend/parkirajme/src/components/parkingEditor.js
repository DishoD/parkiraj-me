import React, {Component} from 'react';
import {Alert, Button, Form, FormControl, FormGroup, HelpBlock, Modal, Well} from "react-bootstrap";
import Dock from 'react-dock'
import './parkingEditor.css'

export default class ParkingEditor extends Component {
    state = {
        ime: '',
        kapacitet: '',
        cijena: '',
        vime: null,
        vkapacitet: null,
        vcijena: null,
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

    kapacitetChange = (e) => {
        const kapacitet = e.target.value;
        let test = kapacitet.length > 0 && /^\d+$/.test(kapacitet);
        this.setState({
            kapacitet: kapacitet,
            vkapacitet: test,
            alertMsg: null
        })
    };

    cijenaChange = (e) => {
        const cijena = e.target.value;
        let test = cijena.length > 0 && /^\d+$/.test(cijena);
        this.setState({
            cijena: cijena,
            vcijena: test,
            alertMsg: null
        })
    };


    validationState = (test) => {
        if(test == null) return null;
        return test ? "success" : "error";
    };

    register = () => {
        const {vime, vkapacitet, vcijena } = this.state;
        let test = (vime && vkapacitet && vcijena);
        let alertMsg = test ? null : "Krivi podatci.";

        if(test) {
            //TODO
            //registriraj na backendu

            //ako je i dalje ispravno
            alertMsg = test ? "Podaci uspješno spremljeni!" : "Krivi podatci."; //ovisno o erroru treba postavit grešku

            this.props.parkingsUpdate();
            setTimeout(() => this.props.newParkingToggle(), 2500);
        }

        this.setState({
            registrationValid: test,
            alertMsg: alertMsg
        });
    };

    back = () => {
        this.props.newParkingToggle();
        this.props.isEditingParkingOff();
    };

    componentDidUpdate(prevProps, prevState, snapshot) {
        const {isAddingNewParking, isEditingParking, selectedParking} = this.props;
      if(!prevProps.isEditingParking && isEditingParking) {
          this.setState({
              ime: selectedParking.ime,
              kapacitet: selectedParking.kapacitet,
              cijena: selectedParking.cijena,
              vime: true,
              vkapacitet: true,
              vcijena: true,
              alertMsg: null,
              registrationValid: false
          })
      }

      if(prevProps.isAddingNewParking && !isAddingNewParking) {
          setTimeout(() => {
              this.setState({
                  ime: '',
                  kapacitet: '',
                  cijena: '',
                  vime: null,
                  vkapacitet: null,
                  vcijena: null,
                  alertMsg: null,
                  registrationValid: false
              })
          }, 200);
      }
    };

    render() {
        const {isAddingNewParking} = this.props;
        const {ime, kapacitet, cijena, vime, vkapacitet, vcijena, alertMsg, registrationValid} = this.state;

        return (

            <Dock position='right' isVisible={isAddingNewParking} fluid={true} defaultSize={0.3}  dimMode='none'>
                <div>
                    <Modal.Header>
                        <h4>Uredite podatke parkirališta</h4>
                    </Modal.Header>
                    <Modal.Body>
                        <small>sva polja su obavezna*</small>
                        <Form>
                            <FormGroup validationState={this.validationState(vime)}>
                                <h5><b>Naziv</b></h5>
                                <FormControl name="ime" type="text" value={ime} onChange={this.imeChange} autoComplete='organization'/>
                                <HelpBlock>{(vime == null) || (vime ? '' : 'Morate unjeti naziv')}</HelpBlock>
                            </FormGroup>
                            <FormGroup validationState={this.validationState(vkapacitet)}>
                            <h5><b>Kapacitet</b></h5>
                            <FormControl name="kapacitet" type="number" value={kapacitet} onChange={this.kapacitetChange}/>
                            <HelpBlock>{(vkapacitet == null) || (vkapacitet ? '' : 'Moret unjeti kapacitet')}</HelpBlock>
                        </FormGroup>
                            <FormGroup validationState={this.validationState(vcijena)}>
                                <h5><b>Cijena</b> <small>(kn/h)</small></h5>
                                <FormControl name="cijena" type="number" value={cijena} onChange={this.cijenaChange}/>
                                <HelpBlock>{(vcijena == null) || (vcijena ? '' : 'Moret unjeti cijenu')}</HelpBlock>
                            </FormGroup>
                            <Well>Za odabir lokacije vucite marker na karti.</Well>
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
                        <Button bsStyle="warning" onClick={this.back}>Odustani</Button>{'  '}
                        <Button bsStyle="success" onClick={this.register}>Spremi</Button>
                    </Modal.Footer>
                </div>
            </Dock>
        );
    }
}
