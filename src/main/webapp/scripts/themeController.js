loadTheme()

function setTheme(theme) {
    document.body.className = theme
    localStorage.setItem('theme', theme)
}

function loadTheme() {
    const storedTheme = localStorage.getItem('theme')
    if (storedTheme) {
        setTheme(storedTheme)
        document.querySelector('.theme-controller').checked = storedTheme === 'dark'
    } else {
        setTheme('winter')
    }
}

document
    .querySelector('.theme-controller')
    .addEventListener('change', function () {
        const newTheme = this.checked ? 'dark' : 'lofi'
        setTheme(newTheme)
    })
