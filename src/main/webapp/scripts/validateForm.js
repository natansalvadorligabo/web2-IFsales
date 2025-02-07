/*
* This script is responsible for validating the form fields.
*
* To use it, need a span element with the id error-{name} for each input element.
* */


initPage();

function initPage() {
    let form = document.querySelector('form');
    form.noValidate = true;

    let elementsWithMask = []

    // Add event listener to phone input to mask phone number
    let phone = document.getElementsByName('phone')[0];
    if (phone) {
        maskPhone({target: phone});
        phone.addEventListener('input', maskPhone);
        elementsWithMask.push(phone);
    }

    let mobile = document.getElementsByName('mobile')[0];
    if (mobile) {
        maskPhone({target: mobile});
        mobile.addEventListener('input', maskPhone);
        elementsWithMask.push(mobile);
    }

    let cpf = document.getElementsByName('cpf')[0];
    if (cpf) {
        maskCpf({target: cpf});
        cpf.addEventListener('input', maskCpf);
        elementsWithMask.push(cpf);
    }

    let cnpj = document.getElementsByName('cnpj')[0];
    if (cnpj) {
        maskCnpj({target: cnpj});
        cnpj.addEventListener('input', maskCnpj);
        elementsWithMask.push(cnpj);
    }

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let valid = processValidity(this);
        if (valid) {
            elementsWithMask.forEach(element => {
                element.value = element.value.replace(/\D/g, '');
            });

            trimAndCleanInputs(this);
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
        if (element.type === 'checkbox' || element.type === 'hidden') continue

        let span = document.getElementById(`error-${element.name}`);
        let input = document.querySelector(`input[name=${element.name}]`) || document.querySelector(`select[name=${element.name}]`);

        if (!element.validity.valid || element.value.trim() === '') {
            span.innerHTML = element.validationMessage || 'Preencha este campo.'
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

function maskCpf(e) {
    let cpf = e.target;

    if (cpf.value.length <= 14) {
        cpf.value = cpf.value.replace(/\D/g, '');

        cpf.value = cpf.value.replace(/(\d{3})(\d)/, '$1.$2');
        cpf.value = cpf.value.replace(/(\d{3})(\d)/, '$1.$2');
        cpf.value = cpf.value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    }

    if (cpf.value.length > 14) {
        cpf.value = cpf.value.substring(0, 14);
    }

    return cpf.value;
}

function maskCnpj(e) {
    let cnpj = e.target;

    if (cnpj.value.length <= 18) {
        cnpj.value = cnpj.value.replace(/\D/g, '');

        cnpj.value = cnpj.value.replace(/(\d{2})(\d)/, '$1.$2');
        cnpj.value = cnpj.value.replace(/(\d{3})(\d)/, '$1.$2');
        cnpj.value = cnpj.value.replace(/(\d{3})(\d)/, '$1/$2');
        cnpj.value = cnpj.value.replace(/(\d{4})(\d{1,2})$/, '$1-$2');
    }

    if (cnpj.value.length > 18) {
        cnpj.value = cnpj.value.substring(0, 18);
    }

    return cnpj.value;
}

function trimAndCleanInputs(form) {
    let elements = form.elements;
    for (let i = 0; i < elements.length; i++) {
        let element = elements[i];
        if (element.type === 'text' || element.type === 'textarea') {
            element.value = element.value.trim().replace(/\s+/g, ' ');
        }
    }
}