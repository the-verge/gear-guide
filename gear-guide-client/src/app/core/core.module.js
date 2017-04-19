const moduleName = 'app.core';

angular.module(moduleName, [
    'ngResource',
    'ui.router',
    'ui.bootstrap'
])
.constant('API_ROOT', '/gear-guide-server/api/');

export default moduleName;