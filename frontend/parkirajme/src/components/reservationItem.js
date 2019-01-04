import React, {Component} from 'react';
import './carItem.css'
import {Button, Fade} from "react-bootstrap";
import './reservationItem.css'

class ReservationItem extends Component {
    state = {
        show: true
    };

    reservationRemoved = () => {
        //TODO
        this.setState({show:false});

        setTimeout(() => this.props.reservationsUpdate(), 200);
    };

    render() {
        const {reservation} = this.props;
        const {show} = this.state;

        return (
            <Fade in={show}>
                <li className="list-group-item">
                    <div className='reservation'>
                    <h5><small>parkiralište: </small><b>{reservation.parkiraliste}</b></h5>
                    <Button className="right" bsStyle="danger" bsSize="xsmall" onClick={this.reservationRemoved}>Ukini</Button>
                    </div>
                </li>
            </Fade>
        );
    }
}

export default ReservationItem;