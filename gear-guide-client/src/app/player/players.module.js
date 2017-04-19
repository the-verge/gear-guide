const moduleName = 'app.player';

import PlayerController from './players.controller';
import PlayerResource from './players.resource';

angular.module(moduleName, [])
    .controller('playerController', PlayerController)
    .factory('Player', PlayerResource);

export default moduleName;