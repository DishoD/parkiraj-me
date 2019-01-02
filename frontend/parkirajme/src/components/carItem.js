import React, {Component} from 'react';
import './carItem.css'
import {Button, Fade} from "react-bootstrap";

class CarItem extends Component {
    state = {
        show: true
    };

    carRemoved = () => {
        //TODO
        console.log('izbrisan auto:' + this.props.car.registracija);
        this.setState({show:false});

        setTimeout(() => this.props.carsUpdate(), 200);
    };

    render() {
        const {car} = this.props;
        const {show} = this.state;

        return (
            <Fade in={show}>
                <li className="list-group-item">
                    <h5><b>{car.ime}</b></h5>  <small>registracija:</small> {car.registracija}
                    <Button className="right" bsStyle="danger" bsSize="xsmall" onClick={this.carRemoved}>Ukloni</Button>
                </li>
            </Fade>
        );
    }
}

export default CarItem;