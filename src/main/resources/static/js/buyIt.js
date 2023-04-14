let buyBtn = document.getElementById("buyBtn");
let errorPara = document.getElementById("invalidQuantity");
buyBtn.addEventListener("click", onBuyAction);

async function onBuyAction(event) {
    let chosenQuantity = document.getElementById("chosenQuantity").value;
    let productId = event.currentTarget.getAttribute('data-id');

    const httpBuyProduct = new BuyHandler;

    if (chosenQuantity.length === 0) {
        errorPara.style.visibility = "visible";
        return;
    }
    await httpBuyProduct.buy(`http://localhost:8080/products/buyIt/${productId}/${chosenQuantity}`);


    window.location.replace("http://localhost:8080/successful-page")
}

class BuyHandler {
    async buy(url) {
        await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                [csrfHeaderKey]: csrfHeaderValue
            }
        })
    }
}