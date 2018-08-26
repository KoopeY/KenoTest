export const CHOOSE_BALL = 'CHOOSE_BALL';
export const CLEAR_CHOOSED_BALLS = 'CLEAR_CHOOSED_BALLS';
export const UPDATE_ROUND_INFO = 'UPDATE_ROUND_INFO';

export function chooseBall(number) {
    return {
        type: CHOOSE_BALL,
        number: number
    }
}

export function clearChoosedBalls() {
    return {
        type: CLEAR_CHOOSED_BALLS
    }
}

export function updateRoundInfo(info) {
    return {
        type: UPDATE_ROUND_INFO,
        timer: info.timer,
        round: info.round,
        balls: info.balls,
        history: info.history,
        rates: info.rate
    }
}