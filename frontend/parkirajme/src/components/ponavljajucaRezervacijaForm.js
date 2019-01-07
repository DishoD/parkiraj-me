import React, {Component} from 'react'
import {Alert, Button, Modal, ToggleButton, ToggleButtonGroup} from 'react-bootstrap'
import 'react-widgets/dist/css/react-widgets.css';
import {DateTimePicker} from 'react-widgets';



export default class PonavljajucaRezervacijaForm extends Component {
    state = {
        days: [],
        startTime: new Date(),
        lenght: 1,
        alertMsg: null,
        registrationValid: false
    };


    constructor(props) {
        super(props);
        let d = new Date();
        d.setHours(d.getHours() + 6);
        this.state.datetime = d;
        this.state.minDatetime = d;
    }

    startTimeChange = value => this.setState({startTime: value, alertMsg: null});

    daysChange = (e) => {
        this.setState({ days: e, alertMsg: null });
    };

    lenghtChange = e => this.setState({lenght: e.target.value, alertMsg: null});


    register = () => {
        const {days, startTime, lenght} = this.state;
        let test = startTime != null;
        let alertMsg = test ? null : 'Morate odabrati vrijeme rezervacije!';

        if(startTime == null) {
            this.setState({
                registrationValid: false,
                alertMsg: 'Morate odabrati vrijeme rezervacije!'
            });
            return;
        } else if(days.length === 0) {
            this.setState({
                registrationValid: false,
                alertMsg: 'Morate odabrati barem jedan dan u tjednu'
            });
            return;
        }

        if(test) {
            const st = String(startTime.getHours()) + ':' + startTime.getMinutes();

            const data = {
                parkingID: this.props.parking.id,
                dani: days,
                vrijemePocetka: st,
                trajanje: lenght
            };

            fetch('/rezervacije/ponavljajuce', {
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
                                alertMsg: "Parkiralište je popunjeno u odabranom periodu! Odaberite drugo vrijeme."
                            });
                        })
                    } else {
                        this.setState({
                            registrationValid: true,
                            alertMsg: "Uspješna rezervacija!"
                        });
                        setTimeout(() => this.props.onHide(), 2500)
                    }
                });
        }
    };


    render() {
        const {days, startTime, lenght, alertMsg, registrationValid } = this.state;
        const {back} = this.props;

        return (
            <div>
                <Modal.Body>
                    <p>Izaberite dane u tjednu koje želite rezervirati:</p>
                    <ToggleButtonGroup type="checkbox" value={days} onChange={this.daysChange}>
                        <ToggleButton bsStyle='warning' value={2}>Pon</ToggleButton>
                        <ToggleButton bsStyle='warning' value={3}>Uto</ToggleButton>
                        <ToggleButton bsStyle='warning' value={4}>Sri</ToggleButton>
                        <ToggleButton bsStyle='warning' value={5}>Čet</ToggleButton>
                        <ToggleButton bsStyle='warning' value={6}>Pet</ToggleButton>
                        <ToggleButton bsStyle='warning' value={7}>Sub</ToggleButton>
                        <ToggleButton bsStyle='warning' value={1}>Ned</ToggleButton>
                    </ToggleButtonGroup>
                    <p><br/>Izaberite vrijeme početka rezervacije u danu</p>
                    <DateTimePicker value={startTime} value={startTime} onChange={this.startTimeChange} date={false}/>
                    <p><br/>Izaberite trajanje <small>(u satima)</small> rezervacije:</p>
                    <input type='number' min={1} max={24} value={lenght} onChange={this.lenghtChange} />
                    <p><br/></p>
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