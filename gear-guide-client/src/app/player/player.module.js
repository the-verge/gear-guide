const moduleName = 'app.player';

import PlayerController from './player.controller';

angular.module(moduleName, [])
    .controller('playerController', PlayerController);

export default moduleName;