import coreModule from './core/core.module';
import routingModule from './routing/routing.module';
import playerModule from './player/players.module';

angular.module('app', [
    coreModule,
    routingModule,
    playerModule
]);