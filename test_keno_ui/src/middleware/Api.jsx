import axios from 'axios';
import protobuf from 'protobufjs'
import {clearChoosedBalls, updateRoundInfo} from '../actions/Game'

export const Api = {
    createRate: function(rate, balls, uuid, round) {
        return function (dispatch) {
            axios.post(
                "http://localhost:9999/game/newRate",
                {
                    rate: rate,
                    balls: balls,
                    uuid: uuid,
                    round: round
                }
            ).then(result => {
                dispatch(clearChoosedBalls());
            }).catch(error => {
                console.log(error);
            });
        }
    },

    getRoundInfo: function() {
        return function (dispatch) {
            axios.post(
                "http://localhost:9999/game/getRoundInfo",
                {}
            ).then(result => {
                dispatch(updateRoundInfo(result.data));
            }).catch(error => {
                console.log(error);
            });
        }
    }
}