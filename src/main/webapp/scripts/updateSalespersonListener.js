const contextPath = document.getElementById("contextPath").getAttribute("data-contextPath");

document.getElementById("search-salesperson-email").addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault();
        const emailValue = this.value;

        if (emailValue) {
            const encodedEmail = encodeURIComponent(emailValue);
            window.location.href = `${contextPath}/redirect?action=updateSalesperson&email=${encodedEmail}`;
        }
    }
});