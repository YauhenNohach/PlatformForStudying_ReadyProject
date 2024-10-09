function submitLoginForm() {
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    const loginData = {
        username: username,
        password: password
    };

    fetch('http://localhost:8081/api/v1/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            console.log("successfully entered")
            window.location.href = '/main';
        })
        .catch(error => {
            console.error('Error:', error);
        });
}