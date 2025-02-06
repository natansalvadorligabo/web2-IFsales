initPage();

function initPage() {
    let form = document.querySelector('form');
    form.noValidate = true;

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

