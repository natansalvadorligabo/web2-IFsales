initPage();

function initPage() {
    let form = document.querySelector('form');
    form.noValidate = true;

    // Add event listener to phone input to mask phone number
    document.getElementsByName('phone')[0].addEventListener('input', maskPhone);

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        let valid = processValidity(this);
        if (valid) {
            this.submit();
        }
    });
}

function processValidity(form) {
    return applyValidity(form);
}

function applyValidity(form) {
    let valid = true;
    let count = 0;
    let elements = form.elements;

    for (let i = 1; i < elements.length - 1; i++) {
        let element = elements[i];
        let span = document.getElementById(`error-${element.name}`);
        let input = document.querySelector(`input[name=${element.name}]`) || document.querySelector(`select[name=${element.name}]`);

        if (!element.validity.valid) {
            span.innerHTML = element.validationMessage;
            span.classList.remove('hidden');
            input.classList.add('input-error');
            count++;
        } else {
            span.innerHTML = '';
            span.classList.add('hidden');
            input.classList.remove('input-error');
        }
    }

    if (count > 0) {
        valid = false
    }

    return valid
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
