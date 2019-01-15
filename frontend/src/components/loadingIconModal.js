import React, {Component} from 'react';
import './registrationModal.css'
import {Fade} from "react-bootstrap";

export default class LoadingIconModal extends Component {
    render() {
        const { show } = this.props;

        return (
            <div className='center'>
                <Fade in={show}>
                    <img alt='Učitavam...' src="./icons/loading_modal.gif"/>
                </Fade>
            </div>
        );
    }
}
