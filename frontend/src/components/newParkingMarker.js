import React, {Component} from 'react';
import L from "leaflet";
import {Marker} from 'react-leaflet'
import './parkingMarker.css'

var parkingIcon = L.icon({
    iconUrl: 'icons/new_parking.png',
    iconSize: [498/15, 604/15],
    iconAnchor: [498/2/15, 604/15],
    popupAnchor: [0, -604/15],
    className: 'new-parking-marker'
});

export default class NewParkingMarker extends Component {

    ref = React.createRef();

    update = () => {
        const marker = this.ref.current;
        if (marker != null) {
            const pos = [marker.leafletElement.getLatLng().lat, marker.leafletElement.getLatLng().lng];
            this.props.newParkingLocationUpdate(pos);
        }
    };

    render() {
        const {newParkingLocation} = this.props;

        return (
            <Marker position={newParkingLocation || [0,0]} ref={this.ref} icon={parkingIcon} draggable={true} onDragend={this.update}>
            </Marker>
        );
    }
}
