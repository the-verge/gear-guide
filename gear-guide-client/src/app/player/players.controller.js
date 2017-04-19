export default class PlayerController {

    constructor(Player) {
        'ngInject';
        this.player = Player;
        this.players = [

        ];

        this.players = Player.get();
    }

    getPlayer(id) {
        this.player.getPlayer({id: id});
    }

}
