import {
    CHOOSE_BALL, CLEAR_CHOOSED_BALLS, UPDATE_ROUND_INFO
} from '../actions/Game'

export function game(state, action) {
    const initState = {
        numbers: [],
        roundBalls: [],
        timer: 0,
        round: 0,
        rates: []
    };

    if (state === undefined || state === 'undefined') {
        state = {
            ...initState
        };
    }

    switch (action.type) {
        case CHOOSE_BALL:
            let newNumbers = [ ...state.numbers ];
            if (state.numbers.includes(action.number)) {
                newNumbers.splice(newNumbers.indexOf(action.number), 1)
            } else {
                newNumbers.push(action.number);
            }

            if (newNumbers.length <= 10) {
                return {
                    ...state,
                    numbers: [...newNumbers]
                };
            } else {
                return state;
            }
        case CLEAR_CHOOSED_BALLS:
            return {
                ...state,
                numbers: []
            };
        case UPDATE_ROUND_INFO:
            let roundBalls = [];
            if (action.balls) {
                roundBalls = [...action.balls]
            }
            return {
                ...state,
                timer: action.timer,
                round: action.round,
                roundBalls: [...roundBalls],
                history: [...action.history],
                rates: [...action.rates]
            };
        default:
            return state;
    }
};