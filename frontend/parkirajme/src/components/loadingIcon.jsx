import React, {Component} from 'react'
import {Fade} from "react-bootstrap";
import './loadingIcon.css'

export default class LoadingIcon extends Component {
    render() {
        const { show } = this.props;

        return (
            <Fade in={show}>
                <img alt='Učitavam...' src="./icons/loading.gif"/>
            </Fade>
        );
    }
}