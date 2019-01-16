import React, {Component} from 'react';
import {ListGroup} from "react-bootstrap";
import CarItem from './carItem'

class CarsList extends Component {
    render() {
        const {cars, carsUpdate} = this.props;

        return (
            <ListGroup componentClass="ul">
                {cars.map(car => <CarItem key={car.registracijskaOznaka} car={car} carsUpdate={carsUpdate} />)}
            </ListGroup>
        );
    }
}

export default CarsList;