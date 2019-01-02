import React, { Component } from 'react'
import { Map, TileLayer, Marker, Popup } from 'react-leaflet'
import {Button} from "react-bootstrap";
import '../../node_modules/leaflet/dist/leaflet.css'
import './map.css'
import L from "leaflet";
import CenterMeBtn from './centerMeBtn'
import ParkingMarker from './parkingMarker'

var myIcon = L.icon({
    iconUrl: 'icons/my_location.png',
    iconSize: [270/10, 398/10],
    iconAnchor: [270/2/10, 398/10],
    popupAnchor: [0, -398/10]
});

const positions = {
    Zagreb: [45.810875, 15.974711]
};

function latLngDistance(lat1, lon1, lat2, lon2, unit) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
        return 0;
    }
    else {
        var radlat1 = Math.PI * lat1/180;
        var radlat2 = Math.PI * lat2/180;
        var theta = lon1-lon2;
        var radtheta = Math.PI * theta/180;
        var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        if (dist > 1) {
            dist = 1;
        }
        dist = Math.acos(dist);
        dist = dist * 180/Math.PI;
        dist = dist * 60 * 1.1515;
        if (unit=="K") { dist = dist * 1.609344 }
        if (unit=="N") { dist = dist * 0.8684 }
        return dist;
    }
}

function posDistance(pos1, pos2) {
    return latLngDistance(pos1[0], pos1[1], pos2[0], pos2[1]);
}


export default class MapControler extends Component{
    state = {
        viewport: {center: positions.Zagreb, zoom: 13},
        maxZoom: 19,
        minZoom: 7,
        maxBounds: L.latLngBounds(L.latLng(47.094206, 12.028730), L.latLng(42.128702, 20.731908)),
        watchId: 0,
        selectedParking: null,
        markerRefs: {}
    };

    onViewportChanged = (viewport) => {
        setTimeout(() => this.setState({viewport: viewport}), 10);
    };

    centerClicked = () => {
        this.centerMe();
    };

    centerMe = () => {
        const vp = {
            center: this.props.myPosition,
            zoom: this.state.viewport.zoom
        };

        this.setState({viewport: vp});
    };

    centerTo = (pos) => {
        const vp = {
            center: pos,
            zoom: 15
        };

        this.setState({viewport: vp});
    };

    getParkingsOfTvrtkaID = (id) => {
        let ret = [];
        this.props.parkings.forEach(p => {
            if(p.tvrtkaID === id) ret.push(p);
        });
        return ret;
    };

    getParkingOfId = (id) => {
        let ret = null;
        this.props.parkings.forEach(p => {
            if(p.id === id) ret = p;
        });

        return ret;
    };

    openPopup = (id) => {
      this.state.markerRefs[id].openPopup();
    };

    updateMarkerRef = (id, ref) => {
      this.setState(state => ({
          markerRefs: {
              ...state.markerRefs,
              [id]: ref
          }
      }));
    };

    centerToClosestParking = () => {
      const {myPosition, parkings} = this.props;
      if(myPosition == null || parkings == null) return;
      let minDistP, minDistP10;
      let p = null;
      let p10 = null;

      parkings.forEach(parking => {
          const dist = posDistance(myPosition, parking.lokacija);
          const rule10 = (parking.kapacitet - parking.popunjenost) > 10;

          if(p == null) {
              p = parking;
              minDistP = dist;
          } else {
              if(dist < minDistP) {
                  p = parking;
                  minDistP = dist;
              }
          }

          if(p10 == null && rule10) {
              p10 = parking;
              minDistP10 = dist;
          } else if(p10 && rule10) {
              if(dist < minDistP10) {
                  p10 = parking;
                  minDistP10 = dist;
              }
          }
      });

      const resP = p10 || p;
      this.centerTo(resP.lokacija);
      setTimeout(() => this.openPopup(resP.id), 700);
    };

    selectedParkingUpdate = (id) => {
        this.setState({selectedParking: this.getParkingOfId(id)});
    };

    constructor(props) {
        super(props);
        this.state.watchId = navigator.geolocation.watchPosition(this.geoSucces, this.geoError);
    }

    geoSucces = (position) => {
        const pos = [position.coords.latitude, position.coords.longitude];
        if(this.props.myPosition == null && this.props.tipKorisnika != 1) this.centerTo(pos);
        this.props.myPositionUpdate(pos);
    };

    geoError = () => {

    };


    componentWillUnmount() {
        navigator.geolocation.clearWatch(this.state.watchId)
    }

    render() {
        const { viewport, maxZoom, maxBounds, minZoom, selectedParking } = this.state;
        const  { myPosition, tipKorisnika, parkings } = this.props;
        let myPositionMarker = null;
        const centerMeBtn = tipKorisnika != 1 ? <CenterMeBtn myPosition={myPosition} position={viewport.center} onClick={this.centerClicked}/> : null;
        if(myPosition && tipKorisnika != 1) {
            myPositionMarker = (
                <Marker position={myPosition} icon={myIcon}>
                    <Popup>Ovdje se vi nalazite.</Popup>
                </Marker>
            );
        }


        let parkingsToShow = parkings;
        if(tipKorisnika === 1) {
            parkingsToShow = this.getParkingsOfTvrtkaID(sessionStorage.getItem('id'));
        }

        const parkingMarkers = parkingsToShow.map(parking => <ParkingMarker key={parking.id} parking={parking} tipKorisnika={tipKorisnika} selectedParkingUpdate={this.selectedParkingUpdate}
                                                                            updateMarkerRef={this.updateMarkerRef}/>)

        const parkirajMeBtn = tipKorisnika != 1 && myPosition != null ? <Button id='parkiraj-me-btn' bsStyle='primary' onClick={this.centerToClosestParking}>Parkiraj<br/>Me</Button> : null;

        return(
          <div>
              <Map className='map' viewport={viewport}
                   animate={true} zoomControl={false} onViewportChanged={this.onViewportChanged}
                   maxZoom={maxZoom} maxBounds={maxBounds} minZoom={minZoom}
              >
                  <TileLayer
                      attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                      url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  />

                  {myPositionMarker}
                  {parkingMarkers}
                  {parkirajMeBtn}
                  {centerMeBtn}
              </Map>


          </div>
        );
    }
}