import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import { connect } from 'react-redux';
import {Ball} from "../component/Ball";
import {chooseBall} from "../actions/Game";
import {Api} from "../middleware/Api"
import Input from "@material-ui/core/es/Input/Input";
import Grid from "@material-ui/core/es/Grid/Grid";
import Table from "@material-ui/core/es/Table/Table";
import TableHead from "@material-ui/core/es/TableHead/TableHead";
import TableRow from "@material-ui/core/es/TableRow/TableRow";
import TableBody from "@material-ui/core/es/TableBody/TableBody";
import TableCell from "@material-ui/core/es/TableCell/TableCell";
import {Rate} from "../component/Rate";

class Game extends Component {
    constructor() {
        super();
        this.width = 10;
        this.height = 8;
        const cookies = new Cookies();

        if (!cookies.get("keno")) {
            let guid = this.s4() + this.s4() + this.s4() + this.s4() + this.s4() + this.s4() + this.s4() + this.s4();
            cookies.set("keno", guid, { path: '/', expires: new Date(Date.now() + 1000 * 3600 * 24 * 360)});
        }
    }

    s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }

    generateButtons() {
        let html = [];
        let rows = [];
        for (let i = 0; i < this.height; i++) {
            let row = [];
            for (let j = 0; j < this.width; j++) {
                let number = i * this.width + j + 1;
                let color = this.props.numbers && this.props.numbers.includes(number) ? "primary" : "default";
                color = this.props.balls.includes(number) ? "secondary" : color;
                row.push(
                    <td>
                        <Ball
                            color={color}
                            onClickFunc={this.props.chooseBall}
                            key={number}
                            number={number}
                        />
                    </td>
                )
            }
            rows.push(<tr key={i}>{row}</tr>);
        }
        html.push(<table><tbody>{rows}</tbody></table>);
        return html;
    }

    generateMenu() {
        let html = [];
        html.push(<Rate round={this.props.round} createRate={this.props.createRate} numbers={this.props.numbers}/>);
        return html;
    }

    generateTable(header, rows) {
        return (
            <Table>
                <TableHead>
                    <TableRow><TableCell>{header}</TableCell></TableRow>
                </TableHead>
                <TableBody>
                    {rows}
                </TableBody>
            </Table>
        );
    }

    generateHistory() {
        let html = [];
        let table = [];
        if (this.props.history) {
            this.props.history.slice(0, 5).forEach(h => {
                table.push(<TableRow><TableCell>Раунд: {h.id}, Выпавшие номера: {h.ball}</TableCell></TableRow>)
            });
        }
        html.push(this.generateTable("История розыгрышей", table));
        return html;
    }

    generateRates() {
        let html = [];
        let table = [];
        if (this.props.rates) {
            this.props.rates.forEach(h => {
                table.push(<TableRow><TableCell>Раунд: {h.round}, Выбранные шары: {h.ball}, Ставка: {h.rate}</TableCell></TableRow>)
            });
        }
        html.push(this.generateTable("История ставок", table));
        return html;
    }

    render() {
        return (
            <div>
                <Grid container spacing={8}>
                    <Grid item xs={6}>
                        {this.generateButtons()}
                    </Grid>
                    <Grid item xs={6}>
                        <Input disabled value={"Раунд: " + this.props.round + " Время: " + this.props.timer}/>
                        <br/><br/>
                        {this.generateMenu()}
                        <br/>
                        {this.generateHistory()}
                        <br/>
                        {this.generateRates()}
                    </Grid>
                </Grid>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        numbers: state.game.numbers,
        balls: state.game.roundBalls,
        timer: state.game.timer,
        round: state.game.round,
        history: state.game.history,
        rates: state.game.rates
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        chooseBall: (number) => {
            dispatch(chooseBall(number))
        },
        createRate: (rate, balls, uuid, round) => {
            dispatch(Api.createRate(rate, balls, uuid, round))
        }
    }
};

const game = connect(mapStateToProps, mapDispatchToProps)(Game);
export default game