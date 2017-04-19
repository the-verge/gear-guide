export default function ($resource, API_ROOT) {
    'ngInject';

    return $resource(API_ROOT + 'players/:id', {id: '@id'}, {
        get: {
            method: 'get',
            timeout: 10000,
            isArray: true
        },

        getPlayer: {
            url: API_ROOT + 'players/:id',
            method: 'get',
            timeout: 10000
        }
    });
}