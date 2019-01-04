import React, {Component} from 'react'
import {Alert, Button, Modal} from 'react-bootstrap'
import 'react-widgets/dist/css/react-widgets.css';
import Moment from 'moment'
import momentLocalizer from 'react-widgets-moment';
import {DateTimePicker} from 'react-widgets';

Moment.locale('hr', {
    months : 'Siječanj_Veljača_Ožujak_Travanj_Svibanj_Lipanj_Srpanj_Kolovoz_Rujan_Listopad_Studeni_Prosinac'.split('_'),
    monthsShort : 'Sij._Velj._Ožu._Tra._Svi._Lip._Srp._Kol._Ruj._Lis._Stu._Pro.'.split('_'),
    monthsParseExact : true,
    weekdays : 'Nedjelja_Ponedjeljak_Utorak_Srijeda_Četvrtak_Petak_Subota'.split('_'),
    weekdaysShort : 'Ned_Pon_Uto_Sri_Čet_Pet_Sub'.split('_'),
    weekdaysMin : 'N_P_U_S_Č_P_S'.split('_'),
    weekdaysParseExact : true,
    longDateFormat : {
        LT : 'HH:mm',
        LTS : 'HH:mm:ss',
        L : 'DD.MM.YYYY',
        LL : 'D MMMM YYYY',
        LLL : 'D. MMMM YYYY. HH:mm',
        LLLL : 'dddd D MMMM YYYY HH:mm'
    },
    calendar : {
        sameDay : '[Danas] LT',
        nextDay : '[Sutra] LT',
        nextWeek : '[Sljedeći tjedan] LT',
        lastDay : '[Jučer] LT',
        lastWeek : 'dddd [dernier à] LT',
        sameElse : 'L'
    },
    relativeTime : {
        future : 'dans %s',
        past : 'il y a %s',
        s : 'quelques secondes',
        m : 'une minute',
        mm : '%d minutes',
        h : 'une heure',
        hh : '%d heures',
        d : 'un jour',
        dd : '%d jours',
        M : 'un mois',
        MM : '%d mois',
        y : 'un an',
        yy : '%d ans'
    },
    dayOfMonthOrdinalParse : /\d{1,2}(er|e)/,
    ordinal : function (number) {
        return number + (number === 1 ? 'er' : 'e');
    },
    meridiemParse : /PD|MD/,
    isPM : function (input) {
        return input.charAt(0) === 'M';
    },
    // In case the meridiem units are not separated around 12, then implement
    // this function (look at locale/id.js for an example).
    // meridiemHour : function (hour, meridiem) {
    //     return /* 0-23 hour, given meridiem token and hour 1-12 */ ;
    // },
    meridiem : function (hours, minutes, isLower) {
        return hours < 12 ? 'PD' : 'MD';
    },
    week : {
        dow : 1, // Monday is the first day of the week.
        doy : 4  // Used to determine first week of the year.
    }
});
momentLocalizer();


export default class JednokratnaRezervacijaForm extends Component {
    state = {
        minDatetime: null,
        datetime: null,
        lenght: 1,
        alertMsg: null,
        registrationValid: false
    };



    constructor(props) {
        super(props);
        let d = new Date();
        d.setHours(d.getHours() + 6);
        this.state.datetime = d;
        this.state.minDatetime = d;
    }



    datetimeChange = value => this.setState({datetime: value, alertMsg: null});
    lenghtChange = e => this.setState({lenght: e.target.value, alertMsg: null});


    register = () => {
        const {datetime, lenght} = this.state;
        let test = datetime != null;
        let alertMsg = test ? null : 'Morate odabrati vrijeme rezervacije!';

        if(test) {
            //TODO
            //registriraj na backendu


            //ako je i dalje ispravno
            alertMsg = test ? "Uspješna rezervacija!" : "Parkiralište je popunjeno u odabranom periodu! Odaberite drugo vrijeme."; //ovisno o erroru treba postavit grešku
            if (test) setTimeout(() => this.props.onHide(), 5000);
        }


        this.setState({
            registrationValid: test,
            alertMsg: alertMsg
        });
    };


    render() {
        const {datetime, lenght, minDatetime, alertMsg, registrationValid } = this.state;
        const {back} = this.props;

        return (
            <div>
                <Modal.Body>
                    <p>Izaberite datum i vrijeme rezervacije:</p>
                    <DateTimePicker min={minDatetime} value={datetime} onChange={this.datetimeChange}/>
                    <p><br/>Izaberite trajanje <small>(u satima)</small> rezervacije:</p>
                    <input type='number' min={1} max={24} value={lenght} onChange={this.lenghtChange} />
                    <p><br/></p>
                    {
                        alertMsg ?
                            <Alert bsStyle={registrationValid ? "success" : "danger"}>
                                {alertMsg}
                            </Alert> :
                            null
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button bsStyle="primary" onClick={back}>Natrag</Button>{'  '}
                    <Button bsStyle="success" onClick={this.register}>Rezerviraj</Button>
                </Modal.Footer>
            </div>
        );
    }
}