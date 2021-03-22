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

            doPost('/login/try', body, '/wildapp')
                .then((response) => {
                    console.log('done!', response);
                    if (!response.account.user.nome) {
                        console.log('fail!');
                    } else {
                        console.log('OKK!');
                        //window.location.href = '/wildapp/home';
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

        function _getCookie() {
            const value = "; " + document.cookie;
            const parts = value.split("; JRICTOKEN=");
            if (parts.length == 2) return parts.pop().split(";").shift();
        }

        function _b64DecodeUnicode(str) {
            return decodeURIComponent(Array.prototype.map.call(window.atob(str), function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
        }

        function _parseJwt(token) {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace('-', '+').replace('_', '/');
            return JSON.parse(_b64DecodeUnicode(base64));
        }

        function _start() {
            let token = _getCookie('JRICTOKEN');
            if (token) {
                let usuario = _parseJwt(token);
                console.log('logado:', usuario);
                // logado
                /*
                $http.get("pages/x.html").then(
                    function(response) {},
                    function(response) {
                        if (response.status == 404) {
                            // Só retorna 404 quando o usuário está logado
                            window.location.href = 'pages/#/PWDASHB';
                        }
                    });*/

            } else {
                console.log('nao logado!');
            }
        }

        _start();
    }
})();