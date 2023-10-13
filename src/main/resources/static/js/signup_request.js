// Проверка совпадения паролей и предотвращение отправки формы при их несовпадении
let match = false;
function checkPass() {
    let password = $("#password").val();
    let confirmPassword = $("#confirmpassword").val();
    match = (password === confirmPassword);
}

$(document).ready(function () {
    $("#confirmpassword").keyup(checkPass);
});

function checkPasswordMatch() {
    if (match) {
        return true;
    } else {
        $("#confirmpassword").val('');
        alert('Passwords do not match!');
        return false;
    }
}

// Получение CSRF токена
let token = document.querySelector('meta[name="_csrf"]').content;

function signup() {
    if (!checkPasswordMatch()) {
        return;
    }
    let object = {
        'login': document.getElementById('login').value,
        'email': document.getElementById('email').value,
        'password': document.getElementById('password').value
    }
    console.log(object);
    fetch('/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify(object)
    })
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Network response was not ok');
        })
        .then(function (data) {
            alert('Успешно')
            location.replace('/login');
        })
        .catch(function (error) {
            alertErrors(error);
        });
}