import React, {Component} from 'react'
import {Button, Modal} from "react-bootstrap";
import './registrationModal.css'
import CarsList from './carsList'
import CarRegisterForm from './carRegisterForm'

export default class CarsModal extends Component {
    state = {
        stage: 0
    };

    hide = () => {
        this.props.onHide();
        setTimeout(this.downStage, 200);
    };

    upStage = () => {
      this.setState({stage: 1});
    };

    downStage = () => {
      this.setState({stage: 0});
    };

    render() {
        const { show, cars, carsUpdate, addCar } = this.props;
        const { stage } = this.state;

        let list = null;

        if (cars.length == 0) {
            list = <div className="center"><h4>Niste registrirali niti jedan automobil.</h4></div>
        } else {
            list = <CarsList cars={cars} carsUpdate={carsUpdate}/>
        }

        let body = null;
        if(stage == 0) {
            body = (
                <div>
                    <Modal.Header closeButton onHide={this.hide}>
                        <Modal.Title>Vaši automobili</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {list}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this.upStage}>Dodaj automobil</Button>
                    </Modal.Footer>
                </div>
            );
        } else if(stage == 1) {
            body = (
                <div>
                    <Modal.Header closeButton onHide={this.hide}>
                        <Modal.Title>Registracija automobila:</Modal.Title>
                    </Modal.Header>
                    <CarRegisterForm back={this.downStage} addCar={addCar} carsUpdate={carsUpdate}/>
                </div>
            );
        }

        return (
            <Modal show={show} onHide={this.hide} keyboard={true}>
                {body}
            </Modal>
        );
    }
}