"use strict"

window.onload = () =>
{
    let form;

    form = document.getElementById('form1');

    form.noValidate = true;

    form.addEventListener('submit', function(e)
    {
        let valid = processValidity(this);

        if(!valid)
            e.preventDefault();
    });

    document.getElementsByName('phone')[0].addEventListener('input', maskPhone);
}

function processValidity(form)
{
    let valid;

    valid = applyValidity(form);

    return valid;
}

function applyValidity(form)
{
    let valid = true;
    let count = 0;
    let elements = form.elements;

    for(let i = 0; i < elements.length - 1; i++)
    {
        let element = elements[i];
        let span = document.getElementById(i);

        if(!element.validity.valid)
        {
            span.innerHTML = element.validationMessage;
            count++;
        }
        else
            span.innerHTML = "";
    }
    if(count > 0)
        valid = false;

    return valid;
}

function maskPhone() {
    let phone = document.getElementsByName('phone')[0];
    let value = phone.value.replace(/\D/g, '');

    if (value.length > 0)
        value = '(' + value;

    if (value.length > 3)
        value = value.slice(0, 3) + ') ' + value.slice(3);

    if (value.length > 13)
        value = value.slice(0, 10) + '-' + value.slice(10);

    else if (value.length > 9)
        value = value.slice(0, 9) + '-' + value.slice(9);

    phone.value = value.slice(0, 15);
}