let allData = undefined;
let tableBody = document.getElementById("tableBody");

fetch(`http://localhost:8080/api/users`, {
    headers: {
        "Accept": "application/json"
    }
}).then(res => res.json())
    .then(users => {
        allData = users;
        if (users.length == 0) {
            tableBody.innerHTML += `
<div class="d-block justify-content-center text-center">
<h3 class="text-center justify-content-center text-danger">No users found!</h3>
</div>`
        }else {
            for (let user of users) {
                tableBody.innerHTML += `
                <tr>
                <td>
                  <i class="fa fa-user" aria-hidden="true"></i><a href="/users/profile/${user.username}" class="user-link">${user.firstName + ' ' + user.lastName}</a>
                </td>
                <td><h6>${user.phone}</h6></td>
                <td class="text-center">
                </td>
                <td>
                  <a href="mailto:${user.email}"><h6>${user.email}</h6></a>
                </td>
                <td style="width: 20%;">
                  <a data-username="${user.username}" class="deleteUserBtn table-link danger" title="Delete User">
                                            <span class="fa-stack">
                                                <i class="fa fa-square fa-stack-2x"></i>
                                                <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                            </span>
                  </a>
                  <a data-username="${user.username}" class="addRolesBtn table-link danger" title="Add Role">
                                            <span class="fa-stack">
                                                <i class="fa fa-square fa-stack-2x"></i>
                                                <i class="fa fa-plus fa-stack-1x fa-inverse"></i>
                                            </span>
                  </a>
                  <a data-username="${user.username}" class="removeRolesBtn table-link danger"  title="Remove Role">
                                            <span class="fa-stack">
                                                <i class="fa fa-square fa-stack-2x"></i>
                                                <i class="fa fa-minus fa-stack-1x fa-inverse"></i>
                                            </span>
                  </a>
                </td>
              </tr>
                `;
            }
        }
    }).then(() => {
        let deleteUserBtn = document.querySelectorAll(".deleteUserBtn")
        let  addRoleBtn = document.querySelectorAll(".addRolesBtn")
        let  removeRoleBtn = document.querySelectorAll(".removeRolesBtn")
        let csrfHeaderKey = document.head.querySelector('[name=_csrf_header]').content
        let csrfHeaderValue = document.head.querySelector('[name=_csrf]').content
        deleteUserBtn.forEach( d => d.addEventListener("click", onDeleteBtn));
        addRoleBtn.forEach( a => a.addEventListener("click", onAddRoleBtn));
        removeRoleBtn.forEach( a => a.addEventListener("click", onRemoveRoleBtn));

    async function onDeleteBtn(event) {
        let username = event.currentTarget.getAttribute('data-username');

        const httpDelete = new DeleteHTTPHandler;

        await httpDelete.delete(`http://localhost:8080/admin/${username}/delete`)
            .then(() => console.log("User deleted successfully!"))
            .catch(err => console.log(err));

        window.location.reload();
    }

    async function onAddRoleBtn(event) {
        let username = event.currentTarget.getAttribute('data-username');

        window.location.replace(`http://localhost:8080/admin/${username}/addRole`);

    }

    async function onRemoveRoleBtn(event) {
        let username = event.currentTarget.getAttribute('data-username');

        window.location.replace(`http://localhost:8080/admin/${username}/removeRole`);
    }
    class DeleteHTTPHandler {

        // Make an HTTP PUT Request
        async delete(url) {

            // Awaiting fetch which contains
            // method, headers and content-type
            const response = await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    [csrfHeaderKey]: csrfHeaderValue
                }
            });

            // Return response data
            return 'deleted successfully';
        }
    }
})
.catch(err => console.log(err))