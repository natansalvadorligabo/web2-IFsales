let details = document.querySelector('details')

if (details) {
    details.addEventListener('toggle', function (e) {
        // localStorage.setItem('sidebar', 'open')

        if (e.target.open) {
            localStorage.setItem('sidebar', 'open')
        } else {
            localStorage.setItem('sidebar', 'closed')
        }
    })

    let storedSidebar = localStorage.getItem('sidebar')
    if (storedSidebar) {
        details.open = storedSidebar === 'open'
    }
}

function closeSidebar() {
    details.open = false
    localStorage.setItem('sidebar', 'closed')
}

let logoutButton = document.querySelector('#logout-button')
if (logoutButton) {
    logoutButton.addEventListener('click', function () {
        closeSidebar()
    })
}