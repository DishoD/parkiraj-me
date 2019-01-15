import React, {Component} from 'react';
import {ListGroup} from "react-bootstrap";
import ReservationItem from './reservationItem'
import LoadingIconModal from './loadingIconModal'

class ReservationsList extends Component {
    state = {
        reservations: [],
        loading: true
    };

    componentWillMount() {
        this.reservationsUpdate();
    }

    reservationsUpdate = () => {
        fetch('/rezervacije')
            .then(res => res.json())
            .then(data => {
               let rez = [];
               data.forEach(r => {
                   if(r.trajna) {
                       const reservation = {
                           id: r.id,
                           parkiraliste: this.props.parkingName(r.parkiralisteID)
                       };
                       rez.push(reservation);
                   }
               });
               this.setState({
                   reservations: rez,
                   loading: false
               });
            });
    };

    render() {
        const {reservations, loading} = this.state;

        if(loading) {
            return <LoadingIconModal show={loading}/>;
        }

        if(reservations.length === 0) {
            return <h3>Nemate trajnih rezervacija.</h3>
        }


        return (
            <React.Fragment>
                <ListGroup componentClass="ul">
                    {reservations.map(reservation => <ReservationItem key={reservation.id} reservation={reservation} reservationsUpdate={this.reservationsUpdate}/>)}
                </ListGroup>
            </React.Fragment>
        );
    }
}

export default ReservationsList;