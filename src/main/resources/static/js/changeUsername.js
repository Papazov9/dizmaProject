let changeUsernameDiv = document.getElementById("changeUsernameDiv");
let changeUsernameBtn = document.getElementById("changeUsernameBtn");
let submitBtn = document.getElementById("submitUsernameByn");
let previousUsername = document.getElementById("previousUsername").textContent.replace("Username: ", "");
let csrfHeaderKey = document.head.querySelector('[name=_csrf_header]').content;
let csrfHeaderValue = document.head.querySelector('[name=_csrf]').content;

changeUsernameBtn.addEventListener("click", () => {

    if ( changeUsernameDiv.style.getPropertyValue("visibility") === "hidden") {
        changeUsernameDiv.style.visibility = "visible";
    }
    else {
        changeUsernameDiv.style.visibility = "hidden"
    }
});

submitBtn.addEventListener("click", submitChange);

async function submitChange(event) {
    let newUsername = event.target.parentNode.firstElementChild.value;

    if (previousUsername === newUsername) {
        window.alert("Same username!");
        return;
    }

    let url = `http://localhost:8080/user/changeUsername/${newUsername}`;
    const changeUsernameHandler = new PatchHTTPHandler;

    await changeUsernameHandler.change(url)
        .then(() => console.log("Username changed successfully!"))
        .catch(err => console.log(err));
    sleep(1000);
    window.location.replace("http://localhost:8080/user/profile");
}

class PatchHTTPHandler {

    async change(url) {
        const res = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                [csrfHeaderKey]: csrfHeaderValue
            }
        });
    }
}

function sleep(milliseconds) {
    const start = Date.now();
    while (Date.now() - start < milliseconds);
}

