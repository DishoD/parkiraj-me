import React, { Component } from 'react';
import './App.css';
import MapCntroler from './components/mapControler'
import NavBar from './components/navBar'


const position = {
  Zagreb: [45.810875, 15.974711]
};

function getRndInteger(min, max) {
  return Math.floor(Math.random() * (max - min + 1) ) + min;
}

var parkiralista = [
  {
    id: '1',
    OIB: '12',
    tvrtkaID: '11',
    ime: 'Tuškanac',
    lokacija: [45.815458, 15.969460],
    kapacitet: 500,
    popunjenost: getRndInteger(300,500),
    cijena: 12
  },
  {
    id: '2',
    OIB: '12',
    tvrtkaID: '11',
    ime: 'Ilica Parking',
    lokacija: [45.813111, 15.968962],
    kapacitet: 600,
    popunjenost: getRndInteger(400,500),
    cijena: 15
  },
  {
    id: '3',
    OIB: '56',
    tvrtkaID: '17',
    ime: 'Arena Zagreb',
    lokacija: [45.769904, 15.943784],
    kapacitet: 500,
    popunjenost: getRndInteger(490,500),
    cijena: 7
  },
  {
    id: '4',
    OIB: '88',
    tvrtkaID: '56',
    ime: 'Paromlin',
    lokacija: [45.802892, 15.978865],
    kapacitet: 500,
    popunjenost: getRndInteger(490,500),
    cijena: 5
  },
  {
    id: '5',
    OIB: '88',
    tvrtkaID: '56',
    ime: 'Javna garaža Svetice',
    lokacija: [45.814839, 16.013869],
    kapacitet: 150,
    popunjenost: getRndInteger(50,150),
    cijena: 6
  },
];


export default class App extends Component {
  state = {
    myPosition: null,
    parkings: [],
    cars: [],
    tipKorisnika: 3,
    isAddingNewParking: false
  };

  puID = 0;

  componentWillMount() {
    const tip = sessionStorage.getItem('tip');
    if(tip != null) {
      this.updateTipKorisnika();
      if(tip === '0') this.carsUpdate();
    }
    this.puID = setInterval(this.parkingsUpdate(), 60000);
  }

  getParkingOfId = (id, parkings) => {
    let ret = null;
    parkings.forEach(p => {
      if(p.id === id) {
        ret = p;
        return;
      }
    });

    return ret;
  };

  getParkingNameOfId = (id) => {
    let ret = null;
    this.state.parkings.forEach(p => {
      if(p.id == id) {
        ret = p.ime;
        return;
      }
    });

    return ret;
  };


  myPositionUpdate = (pos) => {this.setState({myPosition: pos})};

  loginSuccess = () => {
    this.updateTipKorisnika();

    if(sessionStorage.getItem('tip') === '0') this.carsUpdate();
  };

  logoutSuccess = () => {
    fetch('/logout')
        .then(() =>this.setState({tipKorisnika: 3}))
        .catch(() => this.setState({tipKorisnika: 3}));

    sessionStorage.clear()
  };

  updateTipKorisnika = () => {
    this.setState({tipKorisnika: Number(sessionStorage.getItem('tip'))});
  };

  carsUpdate = () => {
    fetch('/automobili')
        .then(res => res.json())
        .then(data => {
          this.setState({cars: data});
        })
        .catch(err => this.logoutSuccess());
  };

  parkingsUpdate = () => {

    fetch('/parkiralista')
        .then(res => res.json())
        .then(parkings => {
          fetch('/parkiralista/slobodna')
              .then(res => res.json())
              .then(data => {
                let rez = [];
                data.forEach(p => {
                  const id = p.parkiralisteID;
                  let parking = this.getParkingOfId(id, parkings);
                  parking = {
                    ...parking,
                    popunjenost: parking.kapacitet - p.brojSlobodnihMjesta
                  };
                  rez.push(parking);
                });
                this.setState({parkings: rez})
              })
        });
  };


  isAddingNewParkingToggle = () => {
    this.setState(state => ({isAddingNewParking: !state.isAddingNewParking}));
  };

  componentWillUnmount() {
    clearInterval(this.puID);
  }

  render() {
    const { myPosition, tipKorisnika, cars, parkings, isAddingNewParking } = this.state;

    let navBar = <NavBar id="navbar" loginSuccess={this.loginSuccess} logoutSuccess={this.logoutSuccess} tipKorisnika={tipKorisnika}
                           cars={cars} carsUpdate={this.carsUpdate} addCar={this.addCar} newParkingToggle={this.isAddingNewParkingToggle}
                           isAddingNewParking={isAddingNewParking} parkingName={this.getParkingNameOfId} parkingsUpdate={this.parkingsUpdate}
    />;

    navBar = isAddingNewParking ? null : navBar;

    return (
      <div>

        {navBar}

        <MapCntroler myPosition={myPosition} myPositionUpdate={this.myPositionUpdate} tipKorisnika={tipKorisnika} parkings={parkings} parkingsUpdate={this.parkingsUpdate}
                     isAddingNewParking={isAddingNewParking} newParkingToggle={this.isAddingNewParkingToggle} cars={cars}
        />
      </div>
    );
  }
}

