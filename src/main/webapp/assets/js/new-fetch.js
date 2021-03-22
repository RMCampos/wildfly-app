function doGet(url) {
    return new Promise((resolve, reject) => {
        fetch('/wildapp/api' + url)
            .then((response) => response.json())
            .then((dataJson) => {
                resolve(dataJson);
            }).catch((err) => {
                reject(err);
            });
    });
}

function doPost(pUrl, pBody = {}, baseUrl = '/wildapp/api') {
    return new Promise((resolve, reject) => {
        fetch(baseUrl + pUrl, {
            method: 'POST',
            cache: 'no-cache',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(pBody)
        }).then((response) => response.json())
            .then((dataJson) => {
                resolve(dataJson);
            }).catch((err) => {
                reject(err);
            });
    });
}