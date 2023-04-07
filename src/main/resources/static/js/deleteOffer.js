let deleteBtn = document.getElementById('deleteBtn');
let csrfHeaderKey = document.head.querySelector('[name=_csrf_header]').content;
let csrfHeaderValue = document.head.querySelector('[name=_csrf]').content;


deleteBtn.addEventListener("click", onDelete);
async function onDelete(event) {
    let id = event.target.getAttribute("data-id");

    const http = new DeleteHTTPClass;
    let url = `http://localhost:8080/admin/delete/${id}`;

   await http.delete(url)
        .then(() => console.log("Deletion managed successfully!"))
        .catch(err => console.log(err));

    window.location.replace("http://localhost:8080/products/all");
}

class DeleteHTTPClass {
    async delete(url)  {
        const res = await fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                [csrfHeaderKey]: csrfHeaderValue
            }
        });

    }
}
