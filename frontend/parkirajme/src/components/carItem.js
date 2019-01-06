import React, {Component} from 'react';
import './carItem.css'
import {Button, Fade} from "react-bootstrap";

class CarItem extends Component {
    state = {
        show: true
    };

    carRemoved = () => {
        //TODO

        fetch('/automobili/' + this.props.car.registracijskaOznaka, {
           method: 'DELETE'
        });

        this.setState({show:false});
        setTimeout(() => this.props.carsUpdate(), 10);
    };

    render() {
        const {car} = this.props;
        const {show} = this.state;

        return (
            <Fade in={show}>
                <li className="list-group-item">
                    <h5><b>{car.ime}</b></h5>  <small>registracija:</small> {car.registracijskaOznaka}
                    <Button className="right" bsStyle="danger" bsSize="xsmall" onClick={this.carRemoved}>Ukloni</Button>
                </li>
            </Fade>
        );
    }
}

export default CarItem;