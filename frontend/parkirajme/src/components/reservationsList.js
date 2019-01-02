import React, {Component} from 'react';
import {ListGroup} from "react-bootstrap";
import ReservationItem from './reservationItem'

class ReservationsList extends Component {
    state = {
        reservations: [
            {
                id: '1',
                parkiraliste: 'parking1',
                auto: 'BMW'
            },
            {
                id: '2',
                parkiraliste: 'parking2',
                auto: 'AUDI'
            },
            {
                id: '3',
                parkiraliste: 'parking3',
                auto: 'FERRARI'
            }
        ]
    };

    componentWillMount() {
        //TODO dohvati rezervacije
    }

    reservationsUpdate = () => {
        //TODO
    };

    render() {
        const {reservations} = this.state;

        return (
            <ListGroup componentClass="ul">
                {reservations.map(reservation => <ReservationItem key={reservation.id} reservation={reservation} reservationsUpdate={this.reservationsUpdate}/>)}
            </ListGroup>
        );
    }
}

export default ReservationsList;