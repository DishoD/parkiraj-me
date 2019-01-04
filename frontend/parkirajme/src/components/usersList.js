import React, {Component} from 'react';
import {Table, Button} from "react-bootstrap";

function TvrtkaRow(props) {

    function removeTvrtka() {
        //TODO izbrisi na backendu
        setTimeout(() => props.update(), 100);
    }

    return (
        <tr>
            <td>{props.user.oib}</td>
            <td>{props.user.ime}</td>
            <td>{props.user.email}</td>
            <td>{props.user.adresa}</td>
            <td><Button bsStyle='danger' bsSize='xsmall' onClick={removeTvrtka}>Izbriši</Button></td>
        </tr>
    );
}

function KorisnikRow(props) {

    function removeKorisnik() {
        //TODO izbrisi na backendu
        setTimeout(() => props.update(), 100);
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
        const {type, users, updateUsers} = this.props;

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
                    users.map(user =>
                       <TvrtkaRow  key={user.oib} user={user} update={updateUsers} />
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
                        <KorisnikRow  key={user.oib} user={user} update={updateUsers}/>
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