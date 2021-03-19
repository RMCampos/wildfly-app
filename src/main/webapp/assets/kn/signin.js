(() => {
    "use strict";

    ko.applyBindings(new Signin());

    function Signin() {
        const self = this;

        self.email = ko.observable();
        self.password = ko.observable();

        self.doSignin = function(theForm) {
            const body = {
                email: self.email(),
                password: self.password(),
            };

            doPost('/login/try', body)
                .then((user) => {
                    console.log('done!', user);
                    if (!user.id) {
                        console.log('fail!');
                    } else {
                        console.log('OKK!');
                        window.location.href = '/wildapp/home'/
                    }
                }).catch((err) => {
                    console.log('err!', err);
                    if (typeof(err) === "object") {
                        window.alert(JSON.stringify(err));
                    } else {
                        window.alert(err);
                    }
                });

            return false;
        };
    }
})();