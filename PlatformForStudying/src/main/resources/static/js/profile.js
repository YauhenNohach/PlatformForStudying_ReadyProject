
function createGroup() {
    const groupName = document.getElementById('groupName').value;

    fetch('http://localhost:8081/group/create?groupName=' + encodeURIComponent(groupName), {
        method: 'POST',
        credentials: 'same-origin'
    })
        .then(response => response.json())
        .then(data => {
            const createGroupMessage = document.getElementById('createGroupMessage');
            createGroupMessage.textContent = data.message;
            console.log(data);
            alert("group created");
            if (data.success) {
                document.getElementById('groupName').value = '';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function navigateToCreateTest() {
    window.location.href = "/test";
}

function navigateToAllTests() {
    window.location.href = '/tests';
}

function navigateToAllGroups() {
    window.location.href = '/groups'
}

function navigateToAllForStudents() {
    window.location.href = '/tests-students'
}

function navigateToAssignments() {
    window.location.href = '/assignments'
}