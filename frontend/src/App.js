import React, { Component } from 'react';
import './App.css';
import MapCntroler from './components/mapControler'
import NavBar from './components/navBar'


const position = {
  Zagreb: [45.810875, 15.974711]
};



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
    this.parkingsUpdate();
    this.onFocus();
    this.puID = setInterval(this.parkingsUpdate, 30000);
    window.addEventListener("focus", this.onFocus);
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

  onFocus = () => {
    const tip = sessionStorage.getItem('tip');
    const username = sessionStorage.getItem('username');
    if(tip != null) {
        fetch('korisnici/current')
            .then(res => res.json())
            .then( data => {
                console.log(data);
                if(username !== data.email) {
                  console.log(username);
                  alert("Niste više prijavljeni!");
                  sessionStorage.clear();
                  this.setState({tipKorisnika: 3});
                }
            })
    }
  };

  componentWillUnmount() {
    clearInterval(this.puID);
    window.removeEventListener("focus", this.onFocus)
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

