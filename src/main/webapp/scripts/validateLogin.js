import {applyValidity} from './validateUserRegister.js';

initPage()

function initPage() {
    let form = document.querySelector('form');

    form.noValidate = true;

    form.addEventListener('submit', function (e) {
        let valid = processValidity(this);
        if (!valid) {
            e.preventDefault();
        }
    });
}

function processValidity(form) {
    return applyValidity(form);
}