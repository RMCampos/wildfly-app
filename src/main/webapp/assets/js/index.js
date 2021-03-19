(() => {
    "use strict";

    function start() {
        console.log('Start JS application! 2');

        fetch('/wildapp/api/user/get-user-test')
            .then((response) => response.json())
            .then((data) => console.log(data));
    }

    start();
})();