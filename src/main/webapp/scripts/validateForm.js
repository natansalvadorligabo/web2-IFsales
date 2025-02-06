/*
* This script is responsible for validating the form fields.
*
* To use it, need a span element with the id error-{name} for each input element.
* */


initPage();

function initPage() {
    let form = document.querySelector('form');
    form.noValidate = true;


    // Add event listener to phone input to mask phone number
    let phone = document.getElementsByName('phone')[0];
    if (phone) phone.addEventListener('input', maskPhone);

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let valid = processValidity(this);
        if (valid) {
            this.submit();
        }
    });
}

function processValidity(form) {
    validatePassword(form);
    return applyValidity(form);
}

function applyValidity(form) {
    let valid = true;
    let count = 0;
    let elements = form.elements;

    for (let i = 0; i < elements.length - 1; i++) {
        let element = elements[i];
        if (element.name === 'id') continue

        let span = document.getElementById(`error-${element.name}`);
        let input = document.querySelector(`input[name=${element.name}]`) || document.querySelector(`select[name=${element.name}]`);

        if (!element.validity.valid) {
            span.innerHTML = element.validationMessage;
            span.classList.remove('hidden');
            input.classList.add('select-error');
            count++;
        } else {
            span.innerHTML = '';
            span.classList.add('hidden');
            input.classList.remove('select-error');
        }
    }

    if (count > 0) {
        valid = false
    }

    return valid
}


function validatePassword(form) {
    let password = form.querySelector('input[name=password]')
    let confirmPassword = form.querySelector('input[name=confirmPassword]')

    if (password && confirmPassword) {
        if (password.value !== confirmPassword.value) {
            password.setCustomValidity('As senhas não coincidem.');
        } else {
            password.setCustomValidity('');
        }
    }
}

function maskPhone(e) {
    let phone = e.target
    let value = phone.value.replace(/\D/g, '')

    if (value.length > 0) value = '(' + value

    if (value.length > 3) value = value.slice(0, 3) + ') ' + value.slice(3)

    if (value.length > 13) value = value.slice(0, 10) + '-' + value.slice(10)
    else if (value.length > 9) value = value.slice(0, 9) + '-' + value.slice(9)

    phone.value = value.slice(0, 15)
}
