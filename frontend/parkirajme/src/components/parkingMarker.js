import React, {Component} from 'react';
import L from "leaflet";
import {Marker, Popup} from 'react-leaflet'
import {Button} from "react-bootstrap";
import './parkingMarker.css'

var parkingIcon = L.icon({
    iconUrl: 'icons/parking.png',
    iconSize: [498/15, 604/15],
    iconAnchor: [498/2/15, 604/15],
    popupAnchor: [0, -604/15]
});

export default class ParkingMarker extends Component {
    spu = () => {
        setTimeout(() => this.props.selectedParkingUpdate(this.props.parking.id), 50);
    };

    ref = React.createRef();

    componentDidMount() {
        this.props.updateMarkerRef(this.props.parking.id, this.ref.current.leafletElement);
    }


    render() {
        const {parking, tipKorisnika } = this.props;

        let btn = null;
        if(tipKorisnika === 1) {
            btn = <Button bsStyle='info' bsSize='small'>Uredi podatke</Button>
        } else if(tipKorisnika === 0) {
            btn = <Button bsStyle='info' bsSize='small'>Rezerviraj</Button>
        }

        return (
            <Marker position={parking.lokacija} icon={parkingIcon} ref={this.ref}>
                <Popup onOpen={this.spu} onClose={this.closePopup} autoPan={false}>
                    <h3>{parking.ime}</h3>
                    <p><small>popunjenost:</small> {parking.popunjenost}/{parking.kapacitet}</p>
                    <p><small>cijena: </small> {parking.cijena} kn/h</p>
                    {btn}
                </Popup>
            </Marker>
        );
    }
}
