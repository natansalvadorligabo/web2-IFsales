let elementsWithMask = []
let elementsMaskName = ['phone', 'cpf', 'cnpj', 'date', 'price', 'percent']

elementsMaskName.forEach(maskName => {
    let elements = document.getElementsByClassName(maskName)
    elementsWithMask.push(elements)
})

elementsWithMask.forEach(elements => {
    for (let i = 0; i < elements.length; i++) {
        if (elements[i].classList.contains('phone')) {
            elements[i].innerText = maskPhone(elements[i].innerText)
        } else if (elements[i].classList.contains('cpf')) {
            elements[i].innerText = maskCpf(elements[i].innerText)
        } else if (elements[i].classList.contains('cnpj')) {
            elements[i].innerText = maskCnpj(elements[i].innerText)
        } else if (elements[i].classList.contains('date')) {
            elements[i].innerText = maskDate(elements[i].innerText)
        } else if (elements[i].classList.contains('price')) {
            elements[i].innerText = parseFloat(elements[i].innerText).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
        } else if (elements[i].classList.contains('percent')) {
            // Just need to add '%' to the end of the number
            elements[i].innerText = elements[i].innerText + '%'
        }
    }
})


function maskDate(date) {
    let value = date.replace(/\D/g, '');

    if (value.length === 8) {
        value = value.slice(6) + '/' + value.slice(4, 6) + '/' + value.slice(0, 4);
    } else {
        if (value.length > 2) value = value.slice(0, 2) + '/' + value.slice(2);
        if (value.length > 5) value = value.slice(0, 5) + '/' + value.slice(5);
    }

    return value.slice(0, 10);
}

function maskPhone(phone) {
    let value = phone.replace(/\D/g, '')

    if (value.length > 0) value = '(' + value

    if (value.length > 3) value = value.slice(0, 3) + ') ' + value.slice(3)

    if (value.length > 13) value = value.slice(0, 10) + '-' + value.slice(10)
    else if (value.length > 9) value = value.slice(0, 9) + '-' + value.slice(9)

    phone = value.slice(0, 15)

    return phone
}

function maskCpf(cpf) {
    if (cpf.length <= 14) {
        cpf = cpf.replace(/\D/g, '');

        cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
        cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
        cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    }

    if (cpf.length > 14) {
        cpf = cpf.substring(0, 14);
    }

    return cpf;
}

function maskCnpj(cnpj) {
    if (cnpj.length <= 18) {
        cnpj = cnpj.replace(/\D/g, '');

        cnpj = cnpj.replace(/(\d{2})(\d)/, '$1.$2');
        cnpj = cnpj.replace(/(\d{3})(\d)/, '$1.$2');
        cnpj = cnpj.replace(/(\d{3})(\d)/, '$1/$2');
        cnpj = cnpj.replace(/(\d{4})(\d{1,2})$/, '$1-$2');
    }

    if (cnpj.length > 18) {
        cnpj = cnpj.substring(0, 18);
    }

    return cnpj;
}