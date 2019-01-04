import React, {Component} from 'react';
import L from "leaflet";
import {Marker, Popup} from 'react-leaflet'
import {Button} from "react-bootstrap";
import './parkingMarker.css'
import ReserveModal from './reserveModal'
import UserModal from "./navBar";

var parkingIcon = L.icon({
    iconUrl: 'icons/parking.png',
    iconSize: [498/15, 604/15],
    iconAnchor: [498/2/15, 604/15],
    popupAnchor: [0, -604/15]
});

export default class ParkingMarker extends Component {
    state = {
        reserveShow: false
    };

    reserveShowOn = () => {
        this.setState({reserveShow: true});
    };

    reserveShowOff = () => {
        this.setState({reserveShow: false});
    };

    spu = () => {
        setTimeout(() => this.props.selectedParkingUpdate(this.props.parking.id), 50);
    };

    ref = React.createRef();

    componentDidMount() {
        this.props.updateMarkerRef(this.props.parking.id, this.ref.current.leafletElement);
    }

    deleteParking = () => {
        //TODO

        setTimeout(() => this.props.parkingUpdate(), 200);
    };


    render() {
        const {parking, tipKorisnika, editParking, cars } = this.props;
        const {reserveShow} = this.state;

        let btn = null;
        if(tipKorisnika === 1) {
            btn = (
                <div>
                    <Button className='marker-btns' bsStyle='info' bsSize='small' onClick={editParking}>Uredi podatke</Button><br/>
                    <Button className='marker-btns' bsStyle='danger' bsSize='small' onClick={this.deleteParking}>Izbriši</Button>
                </div>
            );
        } else if(tipKorisnika === 0) {
            btn = <Button bsStyle='info' bsSize='small' onClick={this.reserveShowOn}>Rezerviraj</Button>
        }

        return (
            <Marker position={parking.lokacija} icon={parkingIcon} ref={this.ref}>
                <Popup onOpen={this.spu} onClose={this.closePopup} autoPan={false}>
                    <h3>{parking.ime}</h3>
                    <p><small>popunjenost:</small> {parking.popunjenost}/{parking.kapacitet}</p>
                    <p><small>cijena: </small> {parking.cijena} kn/h</p>
                    {btn}
                </Popup>
                <ReserveModal show={reserveShow} onHide={this.reserveShowOff} parking={parking} cars={cars}/>
            </Marker>
        );
    }
}
