let userTable = $('#allUsersTable')
let getAllUser = []

const addPostForm = document.querySelector('.newUserFormClass')

addPostForm.addEventListener('submit', (e) => {
    e.preventDefault();
    console.log('Form submitted!')
    let name = document.getElementById('newFirstname').value;
    let lastname = document.getElementById('newLastname').value;
    let age = document.getElementById('newAge').value;
    let email = document.getElementById('newEmail').value;
    let password = document.getElementById('newPassword').value;
    let roles = getRoles(Array.from(document.getElementById('newRole').selectedOptions)
        .map(role => role.value));
    fetch("/api/users", {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json; charset=UTF-8'
        }),
        body: JSON.stringify({
            firstName: name,
            lastName: lastname,
            age: age,
            email: email,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            userTable.empty();
            getUsers();
            document.getElementById("newUserForm").reset();
        })
})


getUsers()

function getUsers() {
    fetch("/api/users").then((response) => {
        response.json().then((users) => {
            users.forEach(user => {
                addUserForTable(user)
                getAllUser.push(user)
            });
        });
    });
}

function addUserForTable(user) {
    userTable.append(
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.firstName + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + user.roles.map(roleUser => roleUser.role) + '</td>' +
        '<td>' +
        '<button onclick="editUserById(' + user.id + ')" class="btn btn-info edit-btn" data-toggle="modal" data-target="#editModal"' +
        '>Edit</button></td>' +
        '<td>' +
        '<button onclick="deleteUserById(' + user.id + ')" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"' +
        '>Delete</button></td>' +
        '</tr>'
    )
}

function editUserById(id) {
    fetch("/api/users/" + id)
        .then(res => {
            res.json().then(user => {
                $('#editId').val(user.id)
                $('#editFirstname').val(user.firstName)
                $('#editLastname').val(user.lastName)
                $('#editAge').val(user.age)
                $('#editEmail').val(user.email)
            })
        })
}

function updateUser() {
    let id = document.getElementById('editId').value;
    let name = document.getElementById('editFirstname').value;
    let lastname = document.getElementById('editLastname').value;
    let age = document.getElementById('editAge').value;
    let email = document.getElementById('editEmail').value;
    let password = document.getElementById('editPassword').value;
    let roles = getRoles(Array.from(document.getElementById('editRole').selectedOptions)
        .map(role => role.value));
    fetch("/api/users/" + id, {
        method: "PATCH",
        headers: new Headers( {
            'Content-Type': 'application/json;charset=UTF-8'
        }),
        body: JSON.stringify({
            firstName: name,
            lastName: lastname,
            age: age,
            email: email,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            userTable.empty();
            getUsers();
            closeForm();
        })
}

function deleteUserById(id) {
    fetch("/api/users/" + id)
        .then(res => {
            res.json().then(user => {
                $('#deleteId').val(user.id)
                $('#deleteFirstname').val(user.firstName)
                $('#deleteLastname').val(user.lastName)
                $('#deleteAge').val(user.age)
                $('#deleteEmail').val(user.email)
                user.roles.map(role => {
                    $('#deleteRole').append('<option id="' + role.id + '" name="' + role.role + '">' +
                        role.role + '</option>')
                })
            })
        })
}

function deleteUser() {
    fetch("/api/users/" + ($('#deleteId').val()), {method: "DELETE"})
        .then(() => {
            userTable.empty();
            getUsers();
            closeForm();
        })
}

function closeForm() {
    $("#editModal .close").click();
    document.getElementById("editUserForm").reset();
    $("#deleteModal .close").click();
    document.getElementById("deleteUserForm").reset();
    $('#deleteRole > option').remove();
}

function getRoles(list) {
    let roles = [];
    if (list.indexOf("ROLE_USER") >= 0) {
        roles.push({"id": 2});
    }
    if (list.indexOf("ROLE_ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    return roles;
}