import React, {Component} from 'react';
import {Table, Button} from "react-bootstrap";

function TvrtkaRow(props) {

    function removeTvrtka() {
        fetch('/tvrtke/'+props.company.email, {
            method: 'DELETE'
        }).then(() => {
            props.update();
            props.parkingsUpdate();
        });
    }

    return (
        <tr>
            <td>{props.company.oib}</td>
            <td>{props.company.ime}</td>
            <td>{props.company.email}</td>
            <td>{props.company.adresaSjedista}</td>
            <td><Button bsStyle='danger' bsSize='xsmall' onClick={removeTvrtka}>Izbriši</Button></td>
        </tr>
    );
}

function KorisnikRow(props) {

    function removeKorisnik() {
        fetch('/korisnici/'+props.user.email, {
            method: 'DELETE'
        }).then(() => props.update());
    }

    return (
        <tr>
            <td>{props.user.oib}</td>
            <td>{props.user.ime} {props.user.prezime}</td>
            <td>{props.user.email}</td>
            <td><Button bsStyle='danger' bsSize='xsmall' onClick={removeKorisnik}>Izbriši</Button></td>
        </tr>
    );
}

export default class UsersList extends Component {
    render() {
        const {type, users, companies, updateUsers, parkingsUpdate} = this.props;

        let head = null, body = null;

        if(type === 'tvrtke') {

            head = (
                <thead>
                    <tr>
                        <th>OIB</th>
                        <th>Naziv</th>
                        <th>E-mail</th>
                        <th>Adresa sjedišta</th>
                        <th>#</th>
                    </tr>
                </thead>
            );
            body = (
                <tbody>
                {
                    companies.map(company =>
                       <TvrtkaRow  key={company.id} company={company} update={updateUsers} parkingsUpdate={parkingsUpdate} />
                    )
                }
                </tbody>
            );
        } else {

            head = (
                <thead>
                <tr>
                    <th>OIB</th>
                    <th>Puno ime</th>
                    <th>email</th>
                    <th>#</th>
                </tr>
                </thead>
            );
            body = (
                <tbody>
                {
                    users.map(user =>
                        <KorisnikRow  key={user.id} user={user} update={updateUsers}/>
                    )
                }
                </tbody>
            );
        }

        return (
            <Table striped bordered hover responsive>
                {head}
                {body}
            </Table>
        );
    }
}