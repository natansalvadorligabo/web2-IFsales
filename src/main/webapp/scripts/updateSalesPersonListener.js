const contextPath = document.getElementById("contextPath").getAttribute("data-contextPath");

document.getElementById("email").addEventListener("keypress", function(event)
{
    if (event.key === "Enter")
    {
        event.preventDefault();

        const emailValue = this.value;

        if (emailValue)
        {
            const encodedEmail = encodeURIComponent(emailValue);
            window.location.href = `${contextPath}/redirect?action=updateSalesPerson&email=${encodedEmail}`;
        }
    }
});