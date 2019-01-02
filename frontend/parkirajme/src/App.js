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

var auti = {
  auti: [
    {
      ime: "audi",
      registracija: "12-asbs-56"
    },
    {
      ime: "mercedes",
      registracija: "56-ccds-78"
    },
    {
      ime: "golf",
      registracija: "25-lom-36"
    },
    {
      ime: "lamburghini",
      registracija: "11-xxx-11"
    }
  ]
};

function addCarStatic(ime, reg) {
  auti.auti.push({
    ime: ime,
    registracija: reg
  });
}

export default class App extends Component {
  state = {
    myPosition: null,
    parkings: [],
    cars: [],
    tipKorisnika: 3
  };


  componentWillMount() {
    const tip = sessionStorage.getItem('tip');
    if(tip != null) {
      this.updateTipKorisnika();
      this.carsUpdate();
    }
    this.parkingsUpdate();
  }

  getParkings = () => {
    //TODO
    return parkiralista;
  };

  getCars = () => {
    //TODO
    return auti.auti;
  };

  addCar = (ime, reg) => {
    //TODO
    addCarStatic(ime, reg);
  };

  myPositionUpdate = (pos) => {this.setState({myPosition: pos})};

  loginSuccess = () => {
    setTimeout(() => this.updateTipKorisnika(), 501);
    setTimeout(() => this.carsUpdate(), 501);
  };

  logoutSuccess = () => {
    //TODO odlogiraj sa servera
    setTimeout(() => this.setState({tipKorisnika: 3}), 501)

    sessionStorage.clear()
  };

  updateTipKorisnika = () => {
    this.setState({tipKorisnika: Number(sessionStorage.getItem('tip'))});
  };

  carsUpdate = () => {
    this.setState({cars: this.getCars()});
  };

  parkingsUpdate = () => {
    this.setState({parkings: this.getParkings()});
  };

  componentWillUnmount() {
    //TODO odlogiraj sa servera
  }

  render() {
    const { myPosition, tipKorisnika, cars, parkings } = this.state;


    return (
      <div>
        <NavBar id="navbar" loginSuccess={this.loginSuccess} logoutSuccess={this.logoutSuccess} tipKorisnika={tipKorisnika} cars={cars} carsUpdate={this.carsUpdate} addCar={this.addCar}/>
        <MapCntroler myPosition={myPosition} myPositionUpdate={this.myPositionUpdate} tipKorisnika={tipKorisnika} parkings={parkings} parkingsUpdate={this.parkingsUpdate}/>
      </div>
    );
  }
}

