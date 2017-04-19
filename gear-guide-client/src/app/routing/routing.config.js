export default function ($stateProvider, $urlRouterProvider) {
    'ngInject';

   $stateProvider
    .state('players', {
           url: 'players',
           templateUrl: 'app/player/players.html',
           controller: 'playerController',
           controllerAs: 'vm'
       })
       .state('player-details', {
           url: 'players/:id',
           templateUrl: 'app/player/details/player-details.html',
           controller: 'playerDetailsController',
           controllerAs: 'vm'
       });

    $urlRouterProvider.otherwise('players');
}