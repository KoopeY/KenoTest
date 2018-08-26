import React, { Component } from 'react';
import Button from "@material-ui/core/es/Button/Button";

export class Ball extends Component {
    render() {
        return (
            <Button
                onClick={() => this.props.onClickFunc(this.props.number)}
                variant="fab"
                color={this.props.color}
                key={this.props.number}
            >
                {this.props.number}
            </Button>
        );
    }
}