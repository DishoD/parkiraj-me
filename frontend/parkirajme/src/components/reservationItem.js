import React, {Component} from 'react';
import './carItem.css'
import {Button, Fade} from "react-bootstrap";

class ReservationItem extends Component {
    state = {
        show: true
    };

    reservationRemoved = () => {
        //TODO
        console.log('izbrisan auto:' + this.props.reservation.auto);
        this.setState({show:false});

        setTimeout(() => this.props.reservationsUpdate(), 200);
    };

    render() {
        const {reservation} = this.props;
        const {show} = this.state;

        return (
            <Fade in={show}>
                <li className="list-group-item">
                    <h5><small>parkiralište: </small><b>{reservation.parkiraliste}</b></h5>  <small>automobil:</small> {reservation.auto}
                    <Button className="right" bsStyle="danger" bsSize="xsmall" onClick={this.reservationRemoved}>Ukini</Button>
                </li>
            </Fade>
        );
    }
}

export default ReservationItem;