import { Fade, Image, Button } from 'react-bootstrap'
import React, { Component } from 'react'
import './centerMe.css'

const eps = 7e-4;

function equalPositions(pos1, pos2) {
    return Math.abs(pos1[0]-pos2[0]) < eps && Math.abs(pos1[1]-pos2[1]) < eps
}

export default class CenterMeBtn extends Component{
    render() {
        const {myPosition, position, onClick } = this.props;

        return (
            <Fade in={myPosition ? !equalPositions(myPosition, position) : false} timeout={500} >
                <div id="center-me" onClick={onClick}>
                    <Image className="fillParent" src="/icons/center_location.png" circle responsive={true} />
                </div>
            </Fade>
        );
    }
}