function submitRegistrationForm() {
    let username = document.getElementById('username').value;
    let firstname = document.getElementById('firstname').value;
    let lastname = document.getElementById('lastname').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let type = document.getElementById('type').value;

    const registrationData = {
        username: username,
        firstname: firstname,
        lastname: lastname,
        email: email,
        password: password,
        type: type
    };

    fetch('http://localhost:8081/api/v1/registration', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registrationData)
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            window.location.href = '/login';
        })
        .catch(error => {
            console.error('Error:', error);
        });
}