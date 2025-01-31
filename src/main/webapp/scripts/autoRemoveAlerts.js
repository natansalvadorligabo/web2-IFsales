window.addEventListener('DOMContentLoaded', (event) => {
    const alerts = document.querySelectorAll('.alert');

    alerts.forEach((alert) => {
        setTimeout(() => {
            alert.classList.add('transition-opacity', 'duration-500', 'opacity-0');

            setTimeout(() => {
                alert.remove();
            }, 500);
        }, 4500);
    });
});
