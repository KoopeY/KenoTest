import React, { Component } from 'react';
import Input from "@material-ui/core/es/Input/Input";
import Button from "@material-ui/core/es/Button/Button";

export class Rate extends Component {
    constructor() {
        super();
        this.state = {
            rate: 0
        };

        this.makeRate = this.makeRate.bind(this);
    }

    s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }

    makeRate() {
        if (this.state.rate <= 0) {
            alert("Ставка должна быть больше 0!");
            return;
        }

        let uuid = this.s4() + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + this.s4() + this.s4();
        this.props.createRate(this.state.rate, this.props.numbers, uuid, this.props.round);
    }

    changeRate = (event) => this.setState({rate: event.target.value});

    render() {
        return(
            <div>
                <Input type="number" placeholder="Ставка" value={this.state.rate} onChange={this.changeRate}/>
                <Button color="secondary" onClick={this.makeRate}>
                    Сделать ставку
                </Button>
            </div>
        );
    }
}