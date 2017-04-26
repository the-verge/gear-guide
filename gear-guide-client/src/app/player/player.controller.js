export default class PlayerController {
    constructor() {

    }

    sayHello () {
        console.log('hello');
    }

    sayGoodbye () {
        console.log('goodbye');
    }
}

let controller = new PlayerController();

controller.sayHello();
controller.sayGoodbye();
